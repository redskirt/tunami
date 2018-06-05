package me.miximixi.tunami.controller

import com.sasaki.packages.independent._

import org.json4s.JsonAST.{ JString, JNull }
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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.json4s.JsonAST.JValue
import org.springframework.web.bind.annotation.RequestMethod

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class EntranceController @Autowired() (loginService: LoginService) extends UsefulController {
  
  @RequestMapping(value = { Array("/_", "/login") }, method = Array(GET))
  def login = new ModelAndView("login")

  /**
   * /{username}/{password}
   * @PathVariable
   * 
   * @RequestParam username: String, @RequestParam password: String, 
   */
//  @RequestMapping(value = { Array("/doLogin") }, method = Array(POST))
  @PostMapping(Array("/doLogin"))
  def doLogin(@RequestBody body: JsonNode, session: HttpSession): JsonNode = {
    val json = fromJsonNode(body)

    (json \ "username", json \ "password") match {
      case (JString(username), JString(password)) =>
        if (nonEmpty(username) && nonEmpty(password)) {
          val optionUser = loginService.queryUser(username)
          optionUser match {
            case None => ("verify" -> false) ~ ("message" -> "用户名不存在！")
            case Some(_) => {
              if (md5(password) == optionUser.get.password) {
                session.setAttribute(SESSION_USER, username)
                ("verify" -> true) ~ ("message" -> JNull)
              } else
                ("verify" -> false) ~ ("message" -> "用户名或密码错误！")
            }
          }
        } else
          ("verify" -> false) ~ ("message" -> "用户名和密码不能为空！")
      case _ => ("verify" -> false) ~ ("message" -> "非法参数！")
    }
  }
  
//  @RequestMapping(value = { Array("/doLogout") }, method = Array(GET))
  @GetMapping(Array("/doLogout"))
  def doLogout(session: HttpSession) = {
    session.removeAttribute(SESSION_USER)
    new ModelAndView(s"${_REDIRECT}/_")
  }

  @RequestMapping(value = { Array("/") }, method = Array(GET))
  def /(session: HttpSession): ModelAndView = {
    if (null == session.getAttribute(SESSION_USER))
      new ModelAndView(s"${_REDIRECT}/_")
    else
      new ModelAndView("index")
  }

}