package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import com.sasaki.chain.ScalaEntity
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.VshViewMap
import org.springframework.stereotype.Repository
import me.miximixi.tunami.kit.QueryFragmentHelper
import me.miximixi.tunami.persistence.QueryProperty._
import me.miximixi.tunami.kit.AbstractQueryDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 23, 2018 9:35:18 AM
 * @Description 
 */
@Repository
class VshViewMapDao extends AbstractQueryDao[VshViewMap] {

  def count(keyword: String): Option[Int] = None
  
  def list(keyword: String, limit: Tuple2[JInt, JInt]): JList[VshViewMap] = null
  
  def count(city: String = __, keyword: String = __): Option[Int] = 
    query(s"""
      $from_count
        $attr_vsh_view_map
      where true
      ${ and("city", city) }
      ${ not_null("image_id") }
      ${
        if (__ == keyword || "" == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	original_title like ?
              	or transliteration like ?
              	or alternative_original_title like ?
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
      like(keyword),
      like(keyword)) { (rs, i) => rs.getInt(1) }.headOption
    
  def list(city: String = __, keyword: String = __, limit: Tuple2[JInt, JInt]): JList[VshViewMap] =
    queryJList(s"""
      select 
        id,
      		image_name,
        image_id,
        city,
        original_title,
    		  transliteration,
    		  alternative_original_title,
        collection,
      		map_type,
        year,
    		  size,
    		  map_support,
        authors,
        publishers,
        repository,
    		  place_of_publication,
        retitle,
      		remark
      from $attr_vsh_view_map
      where true
      ${ and("city", city) }
      ${ not_null("image_id") }
      ${
        if (__ == keyword || "" == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	original_title like ?
              	or transliteration like ?
              	or alternative_original_title like ?
        	      or year like ?
        	      or image_id like ?
              	or remark like ?
            )
            """
      }
      order by id asc
      ${ limit_? } 
      """, 
      city, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      limit._1, 
      limit._2) { (rs, i) => buildBean(classOf[VshViewMap], rs).setId(rs.getInt("id")) }
      
    def update(o: VshViewMap): Int = 
      jdbcTemplate.update(s"""
        update $attr_vsh_view_map
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)

}