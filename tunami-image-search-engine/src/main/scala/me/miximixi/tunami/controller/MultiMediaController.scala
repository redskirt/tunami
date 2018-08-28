package me.miximixi.tunami.controller

import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

import com.fasterxml.jackson.databind.JsonNode

import me.miximixi.tunami.kit.PaginationHandler
import me.miximixi.tunami.persistence.VshViewDao
import me.miximixi.tunami.persistence.VshViewMapDao
import me.miximixi.tunami.poso.VshViewMap
import me.miximixi.tunami.service.VshViewMapService
import org.springframework.core.io.InputStreamResource
import me.miximixi.tunami.poso.VshView

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
    photo_list(model, __ trim, __ trim, current, size, countPage)
    
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
  
  @GetMapping(Array("/photo_list2_{current}_{size}_{countPage}")) 
  def photo_list2(model: Model, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = 
    photo_list2(model, __ trim, __ trim, current, size, countPage)
  
  @PostMapping(Array("/photo_list2_{current}_{size}_{countPage}"))
  def photo_list2(model: Model, @RequestParam keyword: String, @RequestParam city: String, @PathVariable current: Int, @PathVariable size: Int, @PathVariable countPage: Int): ModelAndView = {
    val count = vshViewDao.count(city, keyword).getOrElse(0)
    val page = new Pagination(count, current, size, countPage)
    val html_pagination = buildPaginateTag("/media/photo_list2", page)
    val list = vshViewDao.list(city, keyword, page.limit)

    model.addAttribute("city", city)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("list", list)
    model.addAttribute("html_pagination", html_pagination)

    dispatch("photo_list2")
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
    map_list(model, __ trim, __ trim, current, size, countPage)
  
  @org.springframework.beans.factory.annotation.Value("${value.repository}")
  private var repository: String = _
  
  @GetMapping(Array("/{type}/download_{dir}_{filename}"))
  def download(
      @PathVariable dir: String, 
      @PathVariable filename: String, 
      @PathVariable `type`: String): ResponseEntity[InputStreamResource] = 
        download(s"$repository${|}${`type`}${|}$dir${|}$filename")
      
  @PostMapping(Array("/ajaxRemark"))
  def ajaxRemark(@RequestBody body: JsonNode): JsonNode =
    ajaxHandler(body) { json =>
      (json \ "id", json \ "remark", json \ "remark_", json \ "type") match {
        case (JInt(id), JString(remark), JString(remark_), JString(tipe)) => {
          val result = if (remark != remark_) {
            if("map" == tipe) {
              val o = new VshViewMap
              o.setId(id.toInt)
              o.setRemark(remark_)
              vshViewMapDao.update(o)
            } else if ("city" == tipe) {
              val o = new VshView
              o.setId(id.toInt)
              o.setRemark(remark_)
              vshViewDao.update(o)
            } else 
              0
          } else
            1

          if (0 != result)
            reply(true, "修改成功。")
          else
            reply(false, "修改异常！")
        }
        case _ => reply(false, "修改异常！")
      }
    }
}
