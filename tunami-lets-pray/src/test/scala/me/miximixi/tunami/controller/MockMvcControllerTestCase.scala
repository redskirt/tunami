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
  
//  @RequestMapping(value="/sample/insertBoard.do")
//public ModelAndView insertBoard(CommandMap commandMap,HttpServletRequest request) throws Exception{
//    ModelAndView mv = ModelAndView("redirect:/sample/openBoardList.do");
//    sampleService.insertBoard(commandMap.getMap(),request);
//    return mv;
//}
//  
//  @Test
//public void testInsertBoard() throws Exception{
//    File fis = new File("c:\\users\\aaa.jpg");
//    FileInputStream fi1 = new FileInputStream(fis);
//    MockMultipartFile file = new MockMultipartFile("file",fis.getName(),"multipart/form-data",fi1);
//
//    this.mockMvc.perform(MockMvcRequestBuilders.fileupload("/sample/insertBoard.do"))
//            .file(file)
//            .param("title","title_test")
//            .param("contents","contents_test")
//            .contentType(MediaType.MULTIPART_FORM_DATA)
//            .andExpect(redirectedUrl("/sample/openBoardList.do"));
//}
  
  @Test
  def test_/(): Unit = {
    //    .andExpect(status().isOk())
    //      .andExpect(content().contentType("application/json;charset=UTF-8"))
    // "text/html;charset=UTF-8"
    //		  .andExpect(redirectedUrl("/_"))

    //        println(">> Without session:")
    //        mockMvc.perform(get("/"))
    //          .andExpect(status.isOk())
    //          .andDo(print())
    import org.json4s.jackson.JsonMethods.render
    import org.json4s.JsonDSL._
    val body = ("content" -> "大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！大卫之子，和撒那！赞美主！") ~ ("location" -> "上海") ~ ("gender" -> "1") ~ ("target" -> "国家")
    println(">> With mock session:")
    mockMvc.perform(post("/ajaxSubmitPrayer").requestAttr("body", body))
      .andExpect(status.isOk())
      .andDo(print)
  }
  
//  @Test
//  def text_download(): Unit = {
//    println(">> With mock session:")
//    mockMvc.perform(get("/media/download_SZU_SZU0001.jpg").session(mockSession))
//      .andExpect(status.isOk())
//      .andDo(print)
//  }
  
  
  
}