package me.miximixi.tunami.controller

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.scalatest.Matchers

import me.miximixi.tunami.service.LoginService

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 1, 2018 6:05:19 PM
 * @Description
 */
class ControllerSpec extends FlatSpec with Matchers with MockFactory {

  "" should "" in {
    val loginService = stub[LoginService]
    val user = loginService.queryUser("redskirt")
    println(user)
    
    val test = new EntranceController(loginService)
    println(test.login)
  }
  
}