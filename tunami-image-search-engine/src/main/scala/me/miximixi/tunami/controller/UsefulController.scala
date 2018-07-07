package me.miximixi.tunami.controller

import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode

import com.fasterxml.jackson.databind.JsonNode
import javax.servlet.http.HttpSession
import org.springframework.web.servlet.ModelAndView
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.persistence.VshViewMapDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 4:06:33 PM
 * @Description
 */
trait UsefulController { self =>

//  final val GET = RequestMethod.GET
//  final val POST = RequestMethod.POST
//  final val PUT = RequestMethod.PUT
//  final val DELETE = RequestMethod.DELETE

  protected val SESSION_PRINCIPAL = "SESSION_PRINCIPAL"
  protected val _REDIRECT = "redirect:"

  protected implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)
  
  protected def dispatch(url: String)(implicit session: HttpSession) =
    if (null == session.getAttribute(SESSION_PRINCIPAL))
      new ModelAndView(s"${_REDIRECT}/_")
    else
      new ModelAndView(url)
    
  // 提供一个隐式Session
  protected implicit var session: HttpSession = _
  
  // 运行时Session注入
  @Autowired
  def setSession(session: HttpSession) = self.session = session
  
}
