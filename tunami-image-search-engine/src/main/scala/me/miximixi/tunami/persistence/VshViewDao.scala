package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.VshView
import org.springframework.stereotype.Repository
import me.miximixi.tunami.kit.QueryFragmentHelper
import me.miximixi.tunami.persistence.QueryProperty._
import me.miximixi.tunami.kit.AbstractQueryDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class VshViewDao extends AbstractQueryDao[VshView] {
  
  def count(keyword: String): Option[Int] = None
  
  def list(keyword: String, limit: Tuple2[JInt, JInt]): JList[VshView] = null
    
  def count(city: String = __, keyword: String = __): Option[Int] = 
    query(s"""
      $from_count
        $table
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
      
  def list(city: String = __, keyword: String = __, limit: Tuple2[JInt, JInt]): JList[VshView] = {
    
    val sql = s"""
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
      """
    queryJList(sql, 
      city, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      limit._1, 
      limit._2) { (rs, i) => buildBean(classOf[VshView], rs, parseQueryColumn(sql)) }
  }

  def update(o: VshView): Int =
    jdbcTemplate.update(s"""
        update $attr_vsh_view
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)
}