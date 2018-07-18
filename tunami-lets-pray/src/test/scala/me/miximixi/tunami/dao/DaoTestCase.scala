package me.miximixi.tunami.dao

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import scala.collection.JavaConverters._
import me.miximixi.tunami.kit.PaginationHandler
import me.miximixi.tunami.persistence.PrayerDao
import me.miximixi.tunami.persistence.GospelDao
import me.miximixi.tunami.poso.Gospel
import java.sql.Date
import java.sql.Timestamp
import me.miximixi.tunami.poso.Prayer
import scala.util.Random
import me.miximixi.tunami.persistence.ProphetDao
import me.miximixi.tunami.poso.Prophet

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 19, 2018 8:30:35 AM
 * @Description
 */
@RunWith(classOf[SpringRunner])
@SpringBootTest
class DaoTestCase extends PaginationHandler {

  @Autowired
  var gospelDao: GospelDao = _
  @Autowired
  var prayerDao: PrayerDao = _
  @Autowired
  var prophetDao: ProphetDao = _
  

  val page = new Pagination(100, 2, 18, 10)
  
  @Test
  def testListImageContent: Unit = {
//        prayerDao.list(Int.box(318)).foreach(o => println(o.id))
        
//    println(prayerDao.update(Array[Object](Int.box(1), Int.box(3), Int.box(2))))
    
//    println(gospelDao.query(new Date(System.currentTimeMillis())).get.content)
    
//    val o1 = new Gospel
//    o1.setContent("test")
//    o1.setDate(new Date(System.currentTimeMillis()))
//    o1.setTimestamp(new Timestamp(System.currentTimeMillis()))
//    val o2 = o1
//    val o3 = o1
//    println(gospelDao.insert(Seq(o1, o2, o3)))
    
//    for(i <- 0 to 50)
//    println(gospelDao.insert(o1))
    
//      val o = new Prayer
//      val o = new Prophet
//      val str = "这"
//      var str2: String = ""
//      
//      for(i <- 0 to 150) {
//        val random = new Random().nextInt(180)
//        for(j <- 0 to random) {
//          str2 += str
//        		o.setContent(i + str2)
//        }
//        str2 = ""
//        o.setDigg(random)
//        o.setSee(random)
//        o.setGender(random % 2 toString)
//        o.setLocation("上海")
//        o.setTarget("国家")
//        o.setTimestamp(new Timestamp(System.currentTimeMillis()))
//        prayerDao.insert(o)
        
//        o.setCategory("律法")
//        o.setChapter("马太福音|2|3")
//        prophetDao.insert(o)
//      }
    
//    val list = prophetDao.list(0, "__")// .foreach(o => println(o.chapterO))
    val list = prophetDao.listCategory
    println(list.size)
    
  }
}