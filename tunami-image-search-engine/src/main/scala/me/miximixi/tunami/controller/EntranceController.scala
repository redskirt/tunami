package me.miximixi.tunami.controller

import org.json4s.JsonAST.JNull
import org.json4s.JsonAST.JString
import org.json4s.JsonDSL.boolean2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.JsonDSL.string2jvalue
import org.json4s.jackson.JsonMethods.fromJsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode
import com.sasaki.packages.independent.md5
import com.sasaki.packages.independent.nonEmpty

import javax.servlet.http.HttpSession
import me.miximixi.tunami.service.LoginService

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class EntranceController @Autowired() (loginService: LoginService) extends UsefulController {
  
  @GetMapping(Array("/_", "/login"))
  def login = new ModelAndView("login")

  /**
   * /{username}/{password}
   * @PathVariable
   * 
   * @RequestParam username: String, @RequestParam password: String, 
   */
  @PostMapping(Array("/ajaxLogin"))
  def ajaxLogin(@RequestBody body: JsonNode): JsonNode =
    ajaxHandler(body) { json =>
      (json \ "account_name", json \ "password") match {
        case (JString(account_name), JString(password)) =>
          if (nonEmpty(account_name) && nonEmpty(password)) {
            val optionUser = loginService.bizCheckin(account_name)
            optionUser match {
              case None => ($verify -> false) ~ ($message -> "用户名不存在！")
              case Some(_) => {
                if (md5(password) == optionUser.get.password) {
                  session.setAttribute($SESSION_PRINCIPAL, account_name)
                  ($verify -> true) ~ ($message -> JNull)
                } else
                  ($verify -> false) ~ ($message -> "用户名或密码错误！")
              }
            }
          } else
            ($verify -> false) ~ ($message -> "用户名和密码不能为空！")
        case _ => ($verify -> false) ~ ($message -> "非法参数！")
      }
    }
  
  @GetMapping(Array("/doLogout"))
  def doLogout = {
    session.removeAttribute($SESSION_PRINCIPAL)
    new ModelAndView(s"${$REDIRECT}/_")
  }

  @GetMapping(Array("/"))
  def / = dispatch("index")

  @GetMapping(Array("/t"))
  def demo = {
     new ModelAndView("table-dynamic")
  }

//  @Autowired
//  override def setSession(session: HttpSession) {
//    this.session = session
//  }
}