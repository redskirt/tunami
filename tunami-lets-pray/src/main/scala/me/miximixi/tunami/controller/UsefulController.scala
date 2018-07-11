package me.miximixi.tunami.controller

import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL.boolean2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.JsonDSL.string2jvalue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode

import javax.servlet.http.HttpSession

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

  protected val $SESSION_PRINCIPAL = "SESSION_PRINCIPAL"
  protected val $REDIRECT = "redirect:"
  
  protected val $verify = "verify"
  protected val $message = "message"
  protected def reply(verify: Boolean, message: String): JsonNode = 
    ($verify -> verify) ~ ($message -> $message)

  protected implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)
  
  protected def dispatch(url: String)(implicit session: HttpSession) =
    if (null == session.getAttribute($SESSION_PRINCIPAL))
      new ModelAndView(s"${$REDIRECT}/_")
    else
      new ModelAndView(url)
    
  // 提供一个隐式Session
  protected implicit var session: HttpSession = _
  
  protected def ajaxHandler(body: JsonNode)(f_x: JValue => JsonNode): JsonNode = {
    val json = org.json4s.jackson.JsonMethods.fromJsonNode(body)
    f_x(json)
  }
  
  // 运行时Session注入
  @Autowired
  protected def setSession(session: HttpSession) = self.session = session
  
  protected def download(fullPath: String): ResponseEntity[InputStreamResource] = {
    val file = new FileSystemResource(fullPath);
    val headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition", s"""attachment; filename="${ file.getFilename() }"""");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");

    return ResponseEntity
      .ok()
      .headers(headers)
      .contentLength(file.contentLength())
      .contentType(MediaType.parseMediaType("application/octet-stream"))
      .body(new InputStreamResource(file.getInputStream()));
  }
}
