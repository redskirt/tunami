package me.miximixi.tunami.controller

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST._

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.scalatest.Matchers

import me.miximixi.tunami.service.LoginService
import me.miximixi.tunami.poso.User
import javax.servlet.http.HttpSession
import sun.net.www.protocol.https.HttpsClient
import org.json4s.JsonAST.JValue
import com.fasterxml.jackson.databind.JsonNode

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 1, 2018 6:05:19 PM
 * @Description
 */
class ControllerSpec extends FlatSpec with Matchers with MockFactory {

  import com.sasaki.packages.independent._
  
  val loginService = stub[LoginService]
  val entranceController = new EntranceController(loginService)
  
  implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)
  
  "POST /doLogin" should "return true" in {
    val username_ = "tunami"
    val password_ = "000000"
    
    val body = fromJsonNode(("username" -> username_) ~ ("password" -> password_))
    
    val mockExistUser = new User()
    mockExistUser.username = username_
    mockExistUser.password = md5(password_)
    (loginService.queryUser _).when(username_).returns(Some(mockExistUser))
    
    val result = pretty(fromJsonNode(entranceController.doLogin(body, null)))
    val expected = pretty(("verify" -> true) ~ ("message" -> JNull))
    
    assert(expected == result)
  }

}