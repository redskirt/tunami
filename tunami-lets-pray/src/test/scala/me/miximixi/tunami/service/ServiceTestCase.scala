package me.miximixi.tunami.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import me.miximixi.tunami.kit.PaginationHandler
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 19, 2018 8:30:35 AM
 * @Description
 */
@RunWith(classOf[SpringRunner])
@SpringBootTest
class ServiceTestCase {

  @Autowired
  var anthemService: AnthemService = _
  
  @Test
  def test: Unit = {
    anthemService.bizListAnthem.foreach(println)
  }
}