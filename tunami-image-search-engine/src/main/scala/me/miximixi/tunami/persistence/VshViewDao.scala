package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.VshView
import org.springframework.stereotype.Repository

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class VshViewDao extends JdbcTemplateHandler with QueryHelper[VshView] {
  
  def count(keyword: String): Option[Int] = None
  
  def list(keyword: String, limit: Tuple2[JInt, JInt]): JList[VshView] = null
    
  def count(city: String = __, keyword: String = __): Option[Int] = 
    query(s"""
      $count_from
        $attr_vsh_view
      where true
      ${ and("city", city) }
      ${ not_null("image_id") }
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	title like ?
              	or notes like ?
              	or year like ?
              	or image_id like ?
              	or remark like ?
            )
            """
      }    
      """, 
      city, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword)) { (rs, i) => rs.getInt(1) }.headOption
      
  def list(city: String = __, keyword: String = __, limit: Tuple2[JInt, JInt]): JList[VshView] =
    queryJList(s"""
      select 
        id,
        remark,
        retitle,
        page_id,
        image_id,
        image_name,
        city,
        title,
        collection,
        location,
        extent,
        year,
        date,
        photographer,
        estimated_date,
        image_type,
        material_form_of_image,
        private_repository,
        notes,
        keywords_en,
        keywords_fr,
        street_name,
        repository,
        building,
        related_image
      from $attr_vsh_view
      where true
      ${ and("city", city) }
      ${ not_null("image_id") }
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	title like ?
              	or notes like ?
              	or year like ?
              	or image_id like ?
              	or remark like ?
            )
            """
      }
      order by SUBSTR(image_id, 4) + 0 asc
      ${ limit_? } 
      """, 
      city, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      limit._1, 
      limit._2) { (rs, i) =>

      val map = new VshView
      map.setId(Int.box(rs.getInt(1)))
      map.setRemark(rs.getString(2))
      map.setRetitle(rs.getString(3))
      map.setPage_id(rs.getString(4))
      map.setImage_id(rs.getString(5))
      map.setImage_name(rs.getString(6))
      map.setCity(rs.getString(7))
      map.setTitle(rs.getString(8))
      map.setCollection(rs.getString(9))
      map.setLocation(rs.getString(10))
      map.setExtent(rs.getString(11))
      map.setYear(rs.getString(12))
      map.setDate(rs.getString(13))
      map.setPhotographer(rs.getString(14))
      map.setEstimated_date(rs.getString(15))
      map.setImage_type(rs.getString(16))
      map.setMaterial_form_of_image(rs.getString(17))
      map.setPrivate_repository(rs.getString(18))
      map.setNotes(rs.getString(19))
      map.setKeywords_en(rs.getString(20))
      map.setKeywords_fr(rs.getString(21))
      map.setStreet_name(rs.getString(22))
      map.setRepository(rs.getString(23))
      map.setBuilding(rs.getString(24))
      map.setRelated_image(rs.getString(25))
      map
    }

  def update(o: VshView): Int =
    jdbcTemplate.update(s"""
        update $attr_vsh_view
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)
}