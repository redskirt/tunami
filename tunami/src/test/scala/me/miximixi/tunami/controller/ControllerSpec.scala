package me.miximixi.tunami.controller

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

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

  val loginService = stub[LoginService]
  val entranceController = new EntranceController(loginService)
  
  "/doLogin/{username}/{password}" should "return true" in {
    val user = new User()
    user.username = "redskirt"
    user.password = "000000"
    (loginService.queryUser _).when("redskirt").returns(user)
    val result = fromJsonNode(entranceController.doLogin(user.username, user.password, null))
    val expected = render("verify" -> true)
    
    assert(expected == result)
  }

}