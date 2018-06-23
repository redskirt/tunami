package me.miximixi.tunami.controller

import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpSession
import org.springframework.web.servlet.ModelAndView

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 4:06:33 PM
 * @Description
 */
trait UsefulController {

  final val GET = RequestMethod.GET
  final val POST = RequestMethod.POST
  final val PUT = RequestMethod.PUT
  final val DELETE = RequestMethod.DELETE

  protected val SESSION_PRINCIPAL = "SESSION_PRINCIPAL"
  protected val _REDIRECT = "redirect:"

  protected implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)
  
  // 运行时Session注入
  def setSession(session: HttpSession)
  
  // 提供一个隐式Session
  implicit var session: HttpSession = _
  
  protected def dispatch(url: String)(implicit session: HttpSession) = {
    if (null == session.getAttribute(SESSION_PRINCIPAL))
      new ModelAndView(s"${_REDIRECT}/_")
    else
      new ModelAndView(url)
  }
  
}