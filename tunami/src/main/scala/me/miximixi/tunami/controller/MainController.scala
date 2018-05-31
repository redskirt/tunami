package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.service.LoginService

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description 
 */

@RestController
@RequestMapping(Array("/stu"))
class MainController {
  
  @Autowired
  var loginService: LoginService = _
  
  @RequestMapping(Array("/count_"))
  def count() = 44//loginService.countT

}