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
  

  val page = new Pagination(100, 2, 18, 10)
  
  @Test
  def testListImageContent: Unit = {
//        prayerDao.list.asScala.foreach(println)
    
    println(gospelDao.query(new Date(System.currentTimeMillis())).get.date)
    
//    val o1 = new Gospel
//    o1.setContent("test")
//    o1.setDate(new Date(System.currentTimeMillis()))
//    o1.setTimestamp(new Timestamp(System.currentTimeMillis()))
//    val o2 = o1
//    val o3 = o1
//    println(gospelDao.insert(Seq(o1, o2, o3)))
//    println(gospelDao.insert(o1))
    
    
  }
  
}