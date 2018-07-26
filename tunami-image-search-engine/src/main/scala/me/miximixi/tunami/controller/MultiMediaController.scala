package me.miximixi.tunami.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.json4s.JsonAST.JNull
import org.json4s.JsonAST.JString
import org.json4s.JsonDSL.boolean2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.JsonDSL.string2jvalue
import org.json4s.jackson.JsonMethods.fromJsonNode
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import me.miximixi.tunami.poso.VshViewMap
import org.json4s.JsonAST.JInt
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import me.miximixi.tunami.persistence.VshViewDao


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
  @Autowired
  var vshViewDao: VshViewDao = _
  
  @GetMapping(Array("/photo_gallery"))
  def photo_gallery = dispatch("photo_gallery")

  @GetMapping(Array("/photo_list_{current}_{size}_{countPage}")) 
  def photo_list(model: Model, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = 
    photo_list(model, __, __, current, size, countPage)
    
  @PostMapping(Array("/photo_list_{current}_{size}_{countPage}"))
  def photo_list(model: Model, @RequestParam keyword: String, @RequestParam city: String, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = {
    val count = vshViewDao.count(city, keyword).getOrElse(0)
    val page = new Pagination(count, current, size, countPage)
    val html_pagination = buildPaginateTag("/media/photo_list", page)
    val list = vshViewDao.list(city, keyword, page.limit)
    
    model.addAttribute("city", city)
    model.addAttribute("keyword", if(__ == keyword) "" else keyword)
    model.addAttribute("list", list)  
    model.addAttribute("html_pagination", html_pagination)
    
    dispatch("photo_list")
  } 
  
  @PostMapping(Array("/map_list_{current}_{size}_{countPage}")) 
  def map_list(model: Model, @RequestParam keyword: String, @RequestParam city: String, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = {
    val count = vshViewMapDao.count(city, keyword).getOrElse(0)
    val page = new Pagination(count, current, size, countPage)
    val html_pagination = buildPaginateTag("/media/map_list", page)
    val list = vshViewMapDao.list(city, keyword, page.limit)
    
    model.addAttribute("city", city)
    model.addAttribute("keyword", if(__ == keyword) "" else keyword)
    model.addAttribute("list", list)  
    model.addAttribute("html_pagination", html_pagination)
    
    dispatch("map_list")
  }
  
  @GetMapping(Array("/map_list_{current}_{size}_{countPage}")) 
  def map_list(model: Model, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = 
    map_list(model, __, __, current, size, countPage)
  
  @GetMapping(Array("/{type}/download_{dir}_{filename}"))
  def download(@PathVariable dir: String, @PathVariable filename: String, @PathVariable `type`: String): ResponseEntity[InputStreamResource] = 
      super.download(s"$repository/${`type`}/$dir/$filename")
      
  @PostMapping(Array("/ajaxRemark"))
  def ajaxRemark(@RequestBody body: JsonNode): JsonNode =
    ajaxHandler(body) { json =>
      (json \ "id", json \ "remark", json \ "remark_") match {
        case (JInt(id), JString(remark), JString(remark_)) => {
          if (remark != remark_) {
            val o = new VshViewMap
            o.id = id.toInt
            o.remark = remark
            vshViewMapDao.update(o)
          }
          reply(true, "修改成功。")
        }
        case _ => reply(false, "修改异常！")
      }
    }
}