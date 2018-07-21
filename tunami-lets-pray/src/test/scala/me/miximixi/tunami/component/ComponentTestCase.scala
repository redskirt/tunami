package me.miximixi.tunami.component

import org.springframework.beans.factory.annotation.Autowired
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import me.miximixi.tunami.compoment.DataProcessComponent
import org.junit.Test
import org.springframework.test.context.junit4.SpringRunner
import java.io.File

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 20, 2018 11:57:22 PM
 * @Description 
 */
@RunWith(classOf[SpringRunner])
@SpringBootTest
class ComponentTestCase {
  
  @Autowired
  var dataProcessComponent: DataProcessComponent = _

  @Test
  def test: Unit = {
    dataProcessComponent.importGospelProcess(new File("/Users/sasaki/gospels"))
      .foreach(o => println(o.date))
  }

}