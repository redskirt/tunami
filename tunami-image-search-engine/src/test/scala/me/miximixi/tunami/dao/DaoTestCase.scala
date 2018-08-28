package me.miximixi.tunami.dao

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import me.miximixi.tunami.persistence.ImageContentDao
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import me.miximixi.tunami.persistence.VshViewMapDao
import me.miximixi.tunami.persistence.VshViewDao
import scala.collection.JavaConverters._
import me.miximixi.tunami.kit.PaginationHandler
import me.miximixi.tunami.poso.VshViewMap
import me.miximixi.tunami.poso.VshView

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
  var imageContentDao: ImageContentDao = _
  @Autowired
  var vshViewMapDao: VshViewMapDao = _
  @Autowired
  var vshViewDao: VshViewDao = _
  

  val page = new Pagination(100, 1, 18, 10)
  val __ = "__"
  
  @Test
  def testListImageContent: Unit = {
    //    imageContentDao.list() foreach println
//        vshViewMapDao.list("__", "上海", page.limit).asScala.foreach(o => println(o.id + " " + o.city + " " + o.original_title))
        vshViewDao.list("__", "标记", page.limit).asScala.foreach(o => println(o.id + " " + o.city))
//        println(vshViewMapDao.count("TJN"))
//    val o = new VshViewMap
//    o.id = 1
//    o.remark = "___aa"
//    vshViewMapDao.update(o)
  }
  
  @Test
  def testLoad: Unit = {
   scala.io.Source.fromFile("/Users/sasaki/Desktop/t.txt").getLines()
   .foreach { o =>
     println(o)
     val array = o.split("\t")
//     println(array)
     if(2 == array.size) {
       val v = new VshView
     v.setImage_id(array(0))
     v.setRemark(array(1))
     vshViewDao.update(v)
     }
     
   }
  }
}