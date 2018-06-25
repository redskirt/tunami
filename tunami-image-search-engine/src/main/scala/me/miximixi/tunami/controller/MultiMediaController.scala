package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.ui.Model

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 6, 2018 2:30:25 PM
 * @Description
 */
@RestController
class MultiMediaController extends UsefulController {
  
  @GetMapping(Array("/photo_gallery"))
  def photo_gallery = dispatch("photo_gallery")

  @GetMapping(Array("/photo_list"))
  def photo_list = dispatch("photo_list")
  
  @GetMapping(Array("/map_list")) 
  def map_list(model: Model) = {
    model.addAttribute("list", vshViewMapDao.list)    
    dispatch("map_list")
  }
  
  
}