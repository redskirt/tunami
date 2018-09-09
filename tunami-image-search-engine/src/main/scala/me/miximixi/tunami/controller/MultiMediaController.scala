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
import me.miximixi.tunami.persistence.VshViewDao2
import me.miximixi.tunami.persistence.VshViewMapDao
import me.miximixi.tunami.poso.VshViewMap
import me.miximixi.tunami.service.VshViewMapService
import org.springframework.core.io.InputStreamResource
import me.miximixi.tunami.poso.VshView
import me.miximixi.tunami.persistence.JosephDao
import me.miximixi.tunami.poso.Bristol
import me.miximixi.tunami.poso.Joseph

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 6, 2018 2:30:25 PM
 * @Description
 */
@RestController
@RequestMapping(Array("/media"))
class MultiMediaController extends UsefulController with PaginationHandler {

  final val class_mapping                 = "/media"
  
  final val $mapping_photo_list           = "/photo_list_{current}_{size}"
  final val $mapping_photo_list_bristol   = "/photo_list_bristol_{current}_{size}"
  final val $mapping_photo_list_joseph    = "/photo_list_joseph_{current}_{size}"
  final val $mapping_map_list             = "/map_list_{current}_{size}"
  
  final val photo_list                    = "photo_list"
  final val photo_list_bristol            = "photo_list_bristol"
  final val photo_list_joseph             = "photo_list_joseph"
  final val map_list                      = "map_list"

  @Autowired
  var vshViewMapService: VshViewMapService = _
  @Autowired
  var vshViewMapDao: VshViewMapDao = _
  @Autowired
  var vshViewDao: VshViewDao = _
  @Autowired
  var vshViewDao2: VshViewDao2 = _
  @Autowired
  var josephDao: JosephDao = _

  @GetMapping(Array($mapping_photo_list))
  def photo_list(model: Model, @PathVariable current: Int, @PathVariable size: Int): ModelAndView =
    photo_list(model, __ trim, __ trim, current, size)

  @PostMapping(Array($mapping_photo_list))
  def photo_list(
    model:                 Model,
    @RequestParam keyword: String,
    @RequestParam city:    String,
    @PathVariable current: Int,
    @PathVariable size:    Int): ModelAndView = {
    
    val count = vshViewDao.count(city, keyword).getOrElse(0)
    val page = new Pagination(count, current, size)
    val list = vshViewDao.list(city, keyword, page.limit)
    val html_pagination = buildPaginateTag(s"$class_mapping/$photo_list", page)

    model.addAttribute("size", size)
    model.addAttribute("city", city)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("list", list)
    model.addAttribute("html_pagination", html_pagination)

    dispatch(photo_list)
  }

  @GetMapping(Array($mapping_photo_list_bristol))
  def photo_list_bristol(
    model:                 Model,
    @PathVariable current: Int,
    @PathVariable size:    Int): ModelAndView =
    photo_list_bristol(model, __ trim, current, size)

  @PostMapping(Array($mapping_photo_list_bristol))
  def photo_list_bristol(
    model:                 Model,
    @RequestParam keyword: String,
    @PathVariable current: Int,
    @PathVariable size:    Int): ModelAndView = {
    
    val count = vshViewDao2.count(keyword).getOrElse(0)
    val page = new Pagination(count, current, size)
    val html_pagination = buildPaginateTag(s"$class_mapping/$photo_list_bristol", page)
    val list = vshViewDao2.list(keyword, page.limit)

    model.addAttribute("size", size)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("list", list)
    model.addAttribute("html_pagination", html_pagination)

    dispatch(photo_list_bristol)
  }

  @GetMapping(Array($mapping_photo_list_joseph))
  def photo_list_joseph(
    model:                 Model,
    @PathVariable current: Int,
    @PathVariable size:    Int): ModelAndView =
    photo_list_joseph(model, __ trim, current, size)

  @PostMapping(Array($mapping_photo_list_joseph))
  def photo_list_joseph(
    model:                 Model,
    @RequestParam keyword: String,
    @PathVariable current: Int,
    @PathVariable size:    Int): ModelAndView = {
    
    val count = josephDao.count(keyword).getOrElse(0)
    val page = new Pagination(count, current, size)
    val list = josephDao.list(keyword, page.limit)
    val html_pagination = buildPaginateTag(s"$class_mapping/$photo_list_joseph", page)

    model.addAttribute("size", size)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("list", list)
    model.addAttribute("html_pagination", html_pagination)

    dispatch(photo_list_joseph)
  }

  @PostMapping(Array($mapping_map_list))
  def map_list(
      model: Model, 
      @RequestParam keyword: String, 
      @RequestParam city: String, 
      @PathVariable current: Int, 
      @PathVariable size: Int): ModelAndView = {
    val count = vshViewMapDao.count(city, keyword).getOrElse(0)
    val page = new Pagination(count, current, size)
    val html_pagination = buildPaginateTag(s"$class_mapping/$map_list", page)
    val list = vshViewMapDao.list(city, keyword, page.limit)

    model.addAttribute("size", size)
    model.addAttribute("city", city)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("list", list)
    model.addAttribute("html_pagination", html_pagination)

    dispatch(map_list)
  }

  @GetMapping(Array($mapping_map_list))
  def map_list(
      model: Model, 
      @PathVariable current: Int, 
      @PathVariable size: Int): ModelAndView =
    map_list(model, __ trim, __ trim, current, size)

  @org.springframework.beans.factory.annotation.Value("${value.repository}")
  private var repository: String = _

  @GetMapping(Array("/download_{dir}_{filename}"))
  def download(
    @PathVariable dir:      String,
    @PathVariable filename: String): ResponseEntity[InputStreamResource] =
    download(s"$repository${|}${dir.replace("|", "/")}${|}$filename")

  @PostMapping(Array("/ajaxRemark"))
  def ajaxRemark(@RequestBody body: JsonNode): JsonNode =
    ajaxHandler(body) { json =>
      (json \ "id", json \ "remark", json \ "remark_", json \ "type") match {
        case (JInt(id), JString(remark), JString(remark_), JString(tipe)) => {
          val result = if (remark != remark_) {
            tipe match {
              case "map" =>
                val o = new VshViewMap
                o.setId(id.toInt)
                o.setRemark(remark_)
                vshViewMapDao.update(o)
              case "city" =>
                val o = new VshView
                o.setId(id.toInt)
                o.setRemark(remark_)
                vshViewDao.update(o)
              case "bristol" =>
                val o = new Bristol
                o.setId(id.toInt)
                o.setRemark(remark_)
                vshViewDao2.update(o)
              case "joseph" =>
                val o = new Joseph
                o.setId(id.toInt)
                o.setRemark(remark_)
                josephDao.update(o)
              case _ => 0
            }
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

  @deprecated
  private def defaultMapping[T](
    forwardOrRedirect: String,
    model:             Model,
    current:           Int,
    count:             Int,
    size:              Int,
    keyword:           String = __)(fx: () => Unit = () => Unit): ModelAndView = {

    val page = new Pagination(count, current, size /*, total*/ )
    val html_pagination = buildPaginateTag(s"$class_mapping/$forwardOrRedirect", page)

    model.addAttribute("size", size)
    model.addAttribute("keyword", if (__ == keyword) "" else keyword)
    model.addAttribute("html_pagination", html_pagination)
    fx()

    dispatch(forwardOrRedirect)
  }

}
