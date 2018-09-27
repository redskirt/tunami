package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.Bristol
import org.springframework.stereotype.Repository
import me.miximixi.tunami.kit.QueryHelper
import me.miximixi.tunami.persistence.QueryProperty._
import me.miximixi.tunami.kit.QueryDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class VshViewDao2 extends QueryDao[Bristol] {
  
  def count(keyword: String = __): Option[Int] = 
    query(s"""
      $from_count
        $attr_bristol
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	title like ?
              	or original_image_name like ?
              	or note like ?
              	or tag like ?
              	or remark like ?
            )
            """
      }    
      """, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword)) { (rs, i) => rs.getInt(1) }.headOption
    
  def list(keyword: String = __, limit: Tuple2[JInt, JInt]): JList[Bristol] =
    queryJList(s"""
      select 
        id,
        original_image_name,
      		title,
      		collection,
      		estimated_date,
      		note,
      		identifier,
      		copyright,
      		media,
      		tag,
      		remark
      from $attr_bristol
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              title like ?
              	or original_image_name like ?
              	or note like ?
              	or tag like ?
              	or remark like ?
            )
            """
      }
      order by id asc
      ${ limit_? } 
      """, 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      like(keyword), 
      limit._1, 
      limit._2) { (rs, i) => buildBean(classOf[Bristol], rs).setId(rs.getInt("id")) }

  def update(o: Bristol): Int =
    jdbcTemplate.update(s"""
        update $attr_bristol
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)
}