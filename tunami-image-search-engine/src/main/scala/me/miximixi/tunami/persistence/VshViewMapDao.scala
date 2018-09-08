package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import com.sasaki.chain.ScalaEntity
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.VshViewMap
import org.springframework.stereotype.Repository

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 23, 2018 9:35:18 AM
 * @Description 
 */
@Repository
class VshViewMapDao extends JdbcTemplateHandler with QueryHelper[VshViewMap] with ScalaEntity {

  def count(keyword: String): Option[Int] = None
  
  def list(keyword: String, limit: Tuple2[JInt, JInt]): JList[VshViewMap] = null
  
  def count(city: String = __, keyword: String = __): Option[Int] = 
    query(s"""
      $count_from
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
      limit._2) { (rs, i) =>

      val map = new VshViewMap
      map.setId(Int.box(rs.getInt(1)))

      setMultiple_3(map, Array(
        ("image_name", rs.getString(2), CLASS_STRING),
        ("image_id", rs.getString(3), CLASS_STRING),
        ("city", rs.getString(4), CLASS_STRING),
        ("original_title", rs.getString(5), CLASS_STRING),
        ("transliteration", rs.getString(6), CLASS_STRING),
        ("alternative_original_title", rs.getString(7), CLASS_STRING),
        ("collection", rs.getString(8), CLASS_STRING),
        ("map_type", rs.getString(9), CLASS_STRING),
        ("year", rs.getString(10), CLASS_STRING),
        ("size", rs.getString(11), CLASS_STRING),
        ("map_support", rs.getString(12), CLASS_STRING),
        ("authors", rs.getString(13), CLASS_STRING),
        ("publishers", rs.getString(14), CLASS_STRING),
        ("repository", rs.getString(15), CLASS_STRING),
        ("place_of_publication", rs.getString(16), CLASS_STRING),
        ("retitle", rs.getString(16), CLASS_STRING),
        ("remark", rs.getString(18), CLASS_STRING)))
    }
      
    def update(o: VshViewMap): Int = 
      jdbcTemplate.update(s"""
        update $attr_vsh_view_map
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)

}