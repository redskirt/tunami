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
  
  val SESSION_PRINCIPAL = "SESSION_PRINCIPAL"
  
  @Before
  def setup {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build()
    this.mockSession = new MockHttpSession
    mockSession.putValue(SESSION_PRINCIPAL, "tunami")
  }
  
  @Test
  def test_/(): Unit = {
            println(">> Without session:")
            mockMvc.perform(get("/ajaxListProphet/20/__"))
              .andExpect(status.isOk())
              .andDo(print())
//    import org.json4s.jackson.JsonMethods.render
//    import org.json4s.JsonDSL._
//    val body = ("content" -> "大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！") ~ ("location" -> "上海") ~ ("gender" -> "1") ~ ("target" -> "国家")
//    println(">> With mock session:")
//    mockMvc.perform(get("/"))
//      .andExpect(status.isOk())
//      .andDo(print)
  }
  
  @Test
  def text_download(): Unit = {
//    println(">> With mock session:")
//    mockMvc.perform(get("/media/download_SZU_SZU0001.jpg").session(mockSession))
//      .andExpect(status.isOk())
//      .andDo(print)
  }
  
  
  
}