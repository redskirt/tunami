package me.miximixi.tunami.service

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 9:45:41 AM
 * @Description
 */
@RunWith(classOf[SpringRunner])
@SpringBootTest
class ServiceTestCase {

  @Autowired
  var loginService: LoginService = _

  @Test
  def testQueryPrinciple_username {
    val user = loginService.bizCheckin("tunami")
    println {
      user match {
        case None => false
        case Some(_) => true
      }
    }
    
    assert(user.get.account_name.equals("tunami"))
  }
  
}