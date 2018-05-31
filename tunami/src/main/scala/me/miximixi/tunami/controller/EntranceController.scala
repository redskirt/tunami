package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.service.LoginService
import org.springframework.web.servlet.ModelAndView

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class EntranceController {

  @Autowired
  var loginService: LoginService = _

  @RequestMapping(value = { Array("/", "/login", "/home") }, method = Array(RequestMethod.GET))
  def / = new ModelAndView("login")

  @RequestMapping(value = { Array("/_") }, method = Array(RequestMethod.GET))
  def index = new ModelAndView("index")

}