package me.miximixi.tunami.controller.frontend

import com.sasaki.packages.constant._
import org.json4s.JsonAST.{ JString, JInt => JSONInt, JNull }
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods.render
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode
import com.sasaki.packages.independent.TODAY

import me.miximixi.tunami.persistence.GospelDao
import me.miximixi.tunami.persistence.PrayerDao
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.ui.Model
import scala.beans.BeanProperty
import me.miximixi.tunami.poso.Prayer
import me.miximixi.tunami.service.PrayerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class HomeController @Autowired() (gospelDao: GospelDao, prayerDao: PrayerDao, prayerService: PrayerService) extends me.miximixi.tunami.controller.UsefulController {

  @GetMapping(Array("/"))
  def /(model: Model) = {
    val list = prayerService.bizBuildPrayerDTO(0)
    val list_ = scala.collection.JavaConversions.seqAsJavaList(list)
    model.addAttribute("prayers", list_)
    model.addAttribute("gospelContent", gospelDao.query(TODAY).getOrElse({
      val o = new me.miximixi.tunami.poso.Gospel
      o.setContent("")
      o
    }).content)
    new ModelAndView("frontend/index")
  }
  
  @GetMapping(Array("/frame_prayer"))
  def frame_prayer(model: Model) = {
		  val list = prayerService.bizBuildPrayerDTO(0)
				  val list_ = scala.collection.JavaConversions.seqAsJavaList(list)
				  model.addAttribute("prayers", list_)
				  model.addAttribute("gospelContent", gospelDao.query(TODAY).getOrElse({
					  val o = new me.miximixi.tunami.poso.Gospel
							  o.setContent("")
							  o
				  }).content)
				  new ModelAndView("frontend/frame_prayer")
  }
  

  @GetMapping(Array("/ajaxListPrayers_{minId}"))
  def ajaxListPrayers(@PathVariable minId: JInt): JsonNode = {
    val list = prayerService.bizBuildPrayerDTO(minId) 
    render(list.map { o => (
        ("id" -> o.id) ~
        ("content" -> o.content) ~
        ("see" -> o.see) ~
        ("info" -> o.info))
    })
  }
  
  @PostMapping(Array("/ajaxSubmitPrayer"))
  def ajaxSubmitPrayer(@RequestBody body: JsonNode): JsonNode =
    ajaxHandler(body) { json =>
      (json \ "content", //
        json \ "location", //
        json \ "gender", //
        json \ "target") match {
          case (JString(content), //
            JString(location), //
            JString(gender), //
            JString(target)) =>
            if (content.length() >= 20 && content.length() <= 200) {
              val o = new Prayer
              o.setContent(content)
              o.setLocation(location)
              o.setGender(gender)
              o.setTarget(target)
              val result = prayerDao.insert(o)
              if(1 == result)
                ($verify -> true) ~ ($message -> JNull)
              else
                ($verify -> false) ~ ($message -> "处理异常。")
            } else
              ($verify -> false) ~ ($message -> "正文内容请输入20字以上200字以内。")
          case _ => ($verify -> false) ~ ($message -> "提交内容有误。")
        }
    }
}
