package me.miximixi.tunami.controller.frontend

import com.sasaki.packages.constant._
import org.json4s.JsonAST.{ JString, JInt => JSONInt, JNull }
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode
import com.sasaki.packages.independent.TODAY

import me.miximixi.tunami.persistence.GospelDao
import me.miximixi.tunami.persistence.ProphetDao
import me.miximixi.tunami.persistence.PrayerDao
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.ui.Model
import scala.beans.BeanProperty
import me.miximixi.tunami.poso.Prayer
import me.miximixi.tunami.service._
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import me.miximixi.tunami.poso.Gospel
import javax.servlet.http.HttpSession
import org.springframework.context.annotation.Scope

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
@Scope("session") 
class FrontendController @Autowired() (
    gospelDao: GospelDao, //
    prayerDao: PrayerDao, //
    prophetDao: ProphetDao, //
    prayerService: PrayerService, //
    prophetService: ProphetService, //
    anthemService: AnthemService
    ) extends me.miximixi.tunami.controller.UsefulController {

  /**
   * 主页
   */
  @GetMapping(Array("/"))
  def /(model: Model) = {

    import scala.collection.JavaConversions.seqAsJavaList

    val list = prayerService.bizBuildPrayerDTO(0)
    val list_ = seqAsJavaList(list)
    model.addAttribute("prayers", list_)
    session.setAttribute("gospel", {
      val o = gospelDao.query(TODAY)
      o match {
        case Some(_)    => o.get
        case None       => new Gospel("亚伯拉罕的后裔，大卫的子孙，耶稣基督...", TODAY, "马太福音|1|1")
      }
    })

    val thumbnails = for (i <- 1 to 60) yield Thumbnail(i, s"title $i", s"description $i")
    session.setAttribute("thumbnails", seqAsJavaList(thumbnails))
    
    new ModelAndView("frontend/index")
  }

  /**
   * 主祷文
   */
  @GetMapping(Array("/lords-prayer"))
  def lords_prayer = new ModelAndView("frontend/lords-prayer")
  
  /**
   * 神谕
   */
  @GetMapping(Array("/prophet"))
  def prophet = {
    session.setAttribute("categories", prophetDao.listCategory)
    new ModelAndView("frontend/prophet")
  }
  
  /**
   * 赞美诗  
   */
  @GetMapping(Array("/anthem"))
  def anthem = new ModelAndView("frontend/anthem")

  /**
   * 事工
   */
  @GetMapping(Array("/holy-orders"))
  def holy_orders = new ModelAndView("frontend/holy-orders")
  
  @GetMapping(Array("/ajaxListProphet/{minId}/{category}"))
  def ajaxListProphet(@PathVariable minId: JInt, @PathVariable category: String): JsonNode =
    render({
      val list = prophetService.bizListProphet(minId, category)
      
      {
        for (i <- 0 until list.size()) yield {
          val o = list.get(i)
          (
            ("id" -> o.id.toInt) ~
            ("content" -> o.content) ~
            ("see" -> o.see.toInt) ~
            ("chapter_1" -> o.chapterO._1) ~
            ("chapter_2" -> o.chapterO._2) ~
            ("chapter_3" -> o.chapterO._3))
        }
      }
    })

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
  
  @GetMapping(Array("/ajaxListAnthem"))
  def ajaxListAnthem: JsonNode = {
    val list = anthemService.bizListAnthem
    render(list.map { o => (
        ("name" -> o.name) ~
        ("artist" -> o.artist) ~
        ("url" -> o.url) ~
        ("cover" -> o.cover) ~
        ("lrc" -> o.lrc) ~
        ("theme" -> o.theme))
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
              
              val result = prayerDao.insert(List(o))
              
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

case class Thumbnail(
  @BeanProperty index: Int,

  @BeanProperty title: String,

  @BeanProperty description: String)