package me.miximixi.tunami.controller

import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.web.bind.annotation.RequestMethod

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

  protected val SESSION_USER = "SESSION_USER"
  protected val _REDIRECT = "redirect:"

  protected implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)

}