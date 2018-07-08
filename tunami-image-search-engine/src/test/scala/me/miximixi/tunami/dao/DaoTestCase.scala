package me.miximixi.tunami.dao

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import me.miximixi.tunami.persistence.ImageContentDao
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import me.miximixi.tunami.persistence.VshViewMapDao
import scala.collection.JavaConverters._
import me.miximixi.tunami.kit.PaginationHandler

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
  

  val page = new Pagination(100, 2, 18, 10)
  
  @Test
  def testListImageContent = {
//    imageContentDao.list() foreach println
    
    
    vshViewMapDao.list("__", page.limit).asScala.foreach(o => println(o.id))
  }
}