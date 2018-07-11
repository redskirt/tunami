package me.miximixi.tunami.controller.frontend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:58:47 PM
 * @Description
 */
@RestController
class HomeController extends me.miximixi.tunami.controller.UsefulController {

  @GetMapping(Array("/"))
  def / = new ModelAndView("frontend/index")
}