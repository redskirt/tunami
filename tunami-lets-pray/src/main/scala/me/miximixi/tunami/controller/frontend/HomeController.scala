package me.miximixi.tunami.controller.frontend

import com.sasaki.packages.independent._
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import me.miximixi.tunami.persistence.GospelDao
import me.miximixi.tunami.persistence.PrayerDao
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.databind.JsonNode
import java.sql.Date

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class HomeController @Autowired() (gospelDao: GospelDao, prayerDao: PrayerDao) extends me.miximixi.tunami.controller.UsefulController {

  @GetMapping(Array("/"))
  def / = new ModelAndView("frontend/index")
  
  @GetMapping(Array("/doGospel"))
  def doGospel = session.setAttribute("gospel", gospelDao.query(TODAY))
  
}