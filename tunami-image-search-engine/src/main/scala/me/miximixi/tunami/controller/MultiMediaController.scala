package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpSession

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 6, 2018 2:30:25 PM
 * @Description
 */
@RestController
class MultiMediaController extends UsefulController {

  @GetMapping(Array("/photo_gallery"))
  def photo_gallery(session: HttpSession) = dispatchWithSession("photo_gallery", session)

  
}