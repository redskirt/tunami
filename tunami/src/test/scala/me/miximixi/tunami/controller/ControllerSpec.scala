package me.miximixi.tunami.controller

import org.json4s.JsonAST.JNull
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL.boolean2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.JsonDSL.string2jvalue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.json4s.jackson.JsonMethods.fromJsonNode
import org.json4s.jackson.JsonMethods.pretty
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.scalatest.Matchers

import com.fasterxml.jackson.databind.JsonNode

import me.miximixi.tunami.poso.Principal
import me.miximixi.tunami.service.LoginService

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
    val accountName_ = "tunami"
    val password_ = "000000"
    
    val body = fromJsonNode(("username" -> accountName_) ~ ("password" -> password_))
    
    val mockExistUser = new Principal()
    mockExistUser.account_name = accountName_
    mockExistUser.password = md5(password_)
    (loginService.bizCheckin _).when(accountName_).returns(Some(mockExistUser))
    
    val result = pretty(fromJsonNode(entranceController.doLogin(body)))
    val expected = pretty(("verify" -> true) ~ ("message" -> JNull))
    
    assert(expected == result)
  }

}