package me.miximixi.tunami.controller

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode

import javax.servlet.http.HttpSession
import me.miximixi.tunami.service.LoginService
import org.json4s.JsonAST.JValue
import org.springframework.web.bind.annotation.RequestMethod


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class EntranceController @Autowired() (loginService: LoginService) {

  implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)

  val SESSION_USER = "SESSION_USER"

  @RequestMapping(value = { Array("/_", "/login") }, method = Array(RequestMethod.GET))
  def login = new ModelAndView("login")
  
  @RequestMapping(value = { Array("/doLogin/{username}/{password}") }, method = Array(RequestMethod.POST))
  def doLogin(@PathVariable username: String, @PathVariable password: String, session: HttpSession): JsonNode = {
    val user = loginService.queryUser(username)
    
    if(null == user)
      render("verify" -> false)
    else if(!password.equals(user.password))
      render("verify" -> false)
    else {
//      session.setAttribute(SESSION_USER, username)
      render("verify" -> true)
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