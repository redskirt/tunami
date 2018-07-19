package me.miximixi.tunami.service

import com.sasaki.packages.constant._
import com.sasaki.packages.independent.listFiles
import me.miximixi.tunami.poso.AnthemDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.persistence.AnthemDao
import me.miximixi.tunami.poso.Anthem

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 14, 2018 5:55:25 PM
 * @Description 
 */
@org.springframework.stereotype.Service
class AnthemService {

  @Value("${value.repository}")
  private var repo: String = _

  @Autowired
  private var anthemDao: AnthemDao = _

  private val _jpg = ".jpg"
  private val _lrc = ".lrc"
  private val _mp3 = ".mp3"

  def bizListAnthem: Seq[AnthemDTO] = {
    val themes = Array("#ffffff", "#ebd0c2", "#46718b", "#505d6b", "#000000")
    val anthems = anthemDao.list
    val random = new scala.util.Random()

    {
      for (i <- 0 until anthems.size()) yield {
        val anthem = anthems.get(i)
        AnthemDTO(
          anthem.getName(),
          anthem.getArtist(),
          s"/assets/anthem/${anthem.getOrder}${_mp3}",
          s"/assets/anthem/${anthem.getOrder}${_jpg}",
          s"/assets/anthem/${anthem.getOrder}${_lrc}",
          themes(random.nextInt(4)))
      }
    }
  }
}

//  def bizListAnthem: Seq[AnthemDTO] = {
//    val anthemRepo = repo + "anthem"
//    val themes = Array("#ffffff", "#ebd0c2", "#46718b", "#505d6b", "#000000")
//    listFiles(anthemRepo)
//      .filter(o => verifyName(o.getName)) //
//      .map { o => //
//        val prefix_suffix = o.getName.split('.')
//        val item = prefix_suffix(0).split('|')
//        (item(0).toInt, item(1), item(2), prefix_suffix(1))
//      }
//      .groupBy(_._1)
//      .filter(3 == _._2.size)
//      .filter { o =>
//        val suffixs = o._2.map(_._4)
//        suffixs.count(_ == "jpg") == 1 &&
//          suffixs.count(_ == "lrc") == 1 &&
//          suffixs.count(_ == "mp3") == 1
//      }
//      .toArray
//      .sortBy(_._1)
//      .map { o =>
//        val item = o._2(0)
//        val name = item._1 + "|" + item._2 + "|" + item._3
//        AnthemDTO(
//          item._3,
//          item._2,
//          s"'/assets/anthem/$name${_mp3}'",
//          s"'/assets/anthem/$name${_jpg}'",
//          s"'/assets/anthem/$name${_lrc}'",
//          themes(new scala.util.Random().nextInt(4)))
//      }
//  }
//  
//   private def verifyName(name: String): Boolean = 
//    if(name.contains(_mp3) || name.contains(_lrc) || name.contains(_jpg)) {
//      val array = name.split('|')
//      if (3 == array.length) {
//        import scala.util.matching.Regex
//        val regex = """^\d+$""".r // 判断数字
//        array(0) match {
//          case regex(_*) => true
//          case _         => false
//        }
//      } else
//        false
//    } else 
//      false

//object Main extends App {
//  println {
//    Array("1", "234", "11", "1").count(_ == "1")
//  }
//}
