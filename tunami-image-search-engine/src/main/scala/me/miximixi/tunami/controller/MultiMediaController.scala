package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import me.miximixi.tunami.service.VshViewMapService
import me.miximixi.tunami.persistence.VshViewMapDao
import me.miximixi.tunami.kit.PaginationHandler
import org.springframework.core.io .InputStreamResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 6, 2018 2:30:25 PM
 * @Description
 */
@RestController
@RequestMapping(Array("/media"))
class MultiMediaController extends UsefulController with PaginationHandler {
  
  @Autowired
  var vshViewMapService: VshViewMapService = _
  @Autowired
  var vshViewMapDao: VshViewMapDao = _
  
  @GetMapping(Array("/photo_gallery"))
  def photo_gallery = dispatch("photo_gallery")

  @GetMapping(Array("/photo_list"))
  def photo_list = dispatch("photo_list")
  
  @GetMapping(Array("/map_list_{current}_{size}_{countPage}")) 
  def map_list(model: Model, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int) = {
     
    val count = vshViewMapDao.count().getOrElse(0)
    val page = new Pagination(count, current, size, countPage)
    val htmlFoot = buildPaginateTag("/map_list", page)
    val list = vshViewMapDao.list("__", page.limit)
    
    model.addAttribute("list", list)  
    model.addAttribute("htmlFoot", htmlFoot)
    
    dispatch("map_list")
  }
  
  @GetMapping(Array("/download_{dir}_{filename}"))
  def download(@PathVariable dir: String, @PathVariable filename: String): ResponseEntity[InputStreamResource] = 
      super.download(s"$repository/map/$dir/$filename")
  
}