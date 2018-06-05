package me.miximixi.tunami.controller

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.junit.Test
import com.fasterxml.jackson.databind.JsonNode
import javax.servlet.http.HttpSession
import org.springframework.web.servlet.ModelAndView
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.junit.Before
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.result.MockMvcResultHandlers._
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._ 

import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 5, 2018 10:27:44 PM
 * @Description
 */
@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MockMvcControllerTestCase {
  
  @Autowired
  var webApplicationContext: WebApplicationContext = _
  @Autowired
  var restTemplate: TestRestTemplate = _
  @Autowired
  var session: HttpSession = _

  var mockMvc: MockMvc = _
  var mockSession: MockHttpSession = _
  
  val SESSION_USER = "SESSION_USER"
  
  @Before
  def setup {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build()
    this.mockSession = new MockHttpSession
    mockSession.putValue(SESSION_USER, "tunami")
  }
  
  @Test
  def test_/(): Unit = {
    //    .andExpect(status().isOk())
    //      .andExpect(content().contentType("application/json;charset=UTF-8"))
    // "text/html;charset=UTF-8"
    println(">> Without session:")
    mockMvc.perform(get("/"))
      .andExpect(redirectedUrl("/_"))
      .andExpect(status().is(302))
    //        .andDo(print())

    println(">> With mock session:")
    mockMvc.perform(get("/").session(mockSession))
      .andExpect(status.isOk())
      .andDo(print)
  }
}