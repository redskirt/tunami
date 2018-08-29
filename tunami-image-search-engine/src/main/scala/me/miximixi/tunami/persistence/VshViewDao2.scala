package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.Bristol
import org.springframework.stereotype.Repository

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class VshViewDao2 extends JdbcTemplateHandler with DB {
  
  def count(keyword: String = __): Option[Int] = 
    query(s"""
      $count_from
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
      limit._2) { (rs, i) =>

      val o = new Bristol
      o.setId(rs.getInt(1))
      o.setOriginal_image_name(rs.getString(2))
      o.setTitle(rs.getString(3))
      o.setCollection(rs.getString(4))
      o.setEstimated_date(rs.getString(5))
      o.setNote(rs.getString(6))
      o.setIdentifier(rs.getString(7))
      o.setCopyright(rs.getString(8))
      o.setMedia(rs.getString(9))
      o.setTag(rs.getString(10))
      o.setRemark(rs.getString(11))
      o
    }

  def update(o: Bristol): Int =
    jdbcTemplate.update(s"""
        update $attr_bristol
        set remark=?
        where true
        and id=?
        """, o.id)
}