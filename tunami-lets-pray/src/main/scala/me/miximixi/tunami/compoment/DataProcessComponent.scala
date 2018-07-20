package me.miximixi.tunami.compoment

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.persistence.ProphetDao
import java.io.File
import scala.io.Source
import me.miximixi.tunami.poso.Prophet
import java.util.Calendar
import java.text.SimpleDateFormat
import me.miximixi.tunami.poso.Gospel
import java.util.Date

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 20, 2018 10:48:57 PM
 * @Description 
 */
@Component
class DataProcessComponent {
  
  @Autowired
  private var prophetDao: ProphetDao = _
  
  private final val / = '/'
  
  private implicit def date2SqlDate(date: java.util.Date) = 
    new java.sql.Date(date.getTime) 
  
  /**
   * content/chapter/{date}
   * Hosanna to the Son of David!/Mattehw|2|3
   */
  def loadProphetProcess(textFile: File): Seq[Gospel] = {
    val maxDate = prophetDao.queryMaxDate.get
    val line = Source.fromFile(textFile).getLines()
      .filter(verifyChapter(_))
      .toSeq

    var days = 1 
    
    {
      for (i <- 0 until line.size) yield {
        val group = line(i).split(/)
        val prophet = new Gospel
        
        prophet.setContent(group(0))
        prophet.setChapter(group(1))
//        prophet.setDate(tomorrow(new Date(maxDate), days))

        days += 1

        prophet
      }
    }
  }
  
  private def verifyChapter(chapter: String): Boolean = true
  
  import java.util.Date
  import com.sasaki.packages.independent.{ TimePattern => Pattern }
  
  final val DAILY_ERRIOD: Long = 24 * 60 * 60 * 1000
  final val formater = new SimpleDateFormat(Pattern.name(Pattern.PATTERN_DATE))
  
  def tomorrow(date: Date, days: Int = 1): Date = new Date(date.getTime + DAILY_ERRIOD * days) 
  
  def tomorrow(date: String): String = formater.format(formater.parse(tomorrow(date)))
  
  def yesterday(date: Date, days: Int = 1): Date = new Date(date.getTime - DAILY_ERRIOD * days) 
  
  def yesterday(date: String): String = formater.format(formater.parse(yesterday(date)))
  
}