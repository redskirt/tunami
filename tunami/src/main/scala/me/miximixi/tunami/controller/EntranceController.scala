package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.service.LoginService
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpSession
import org.springframework.web.bind.annotation.RequestParam
import org.json4s.jackson.JsonMethods._
import org.springframework.web.bind.annotation.PathVariable

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class EntranceController @Autowired() (loginService: LoginService) {

  val SESSION_USER = "SESSION_USER"

  @RequestMapping(value = { Array("/_", "/login") }, method = Array(RequestMethod.GET))
  def login = new ModelAndView("login")
  
  @RequestMapping(value = { Array("/doLogin/{username}/{password}") }, method = Array(RequestMethod.POST))
  def doLogin(@PathVariable username: String, @PathVariable password: String, session: HttpSession) = {
    val user = loginService.queryUser(username)
    
    if(null == user)
      "no"
    else if(!password.equals(user.password))
      "no2"
    else {
      session.setAttribute(SESSION_USER, username)
      	new ModelAndView("redirect:/")
    }
  }

  @RequestMapping(value = { Array("/") }, method = Array(RequestMethod.GET))
  def /(session: HttpSession) = {
    if(null == session.getAttribute(SESSION_USER)) 
      new ModelAndView("redirect:/_")
    else 
      new ModelAndView("index")
  }

  @RequestMapping(value = { Array("/test") }, method = Array(RequestMethod.GET))
  def test() = "test"
}