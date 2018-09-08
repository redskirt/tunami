package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import me.miximixi.tunami.poso.Bristol
import org.springframework.stereotype.Repository
import me.miximixi.tunami.poso.Joseph

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class JosephDao extends JdbcTemplateHandler with QueryHelper[Joseph] {
  
  def count(keyword: String = __): Option[Int] = 
    query(s"""
      $count_from
        $attr_joseph
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              	title like ?
              	or location like ?
              	or date like ?
              	or original_caption_by_joseph_needham like ?
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
    
  def list(keyword: String = __, limit: Tuple2[JInt, JInt]): JList[Joseph] =
    queryJList(s"""
      select 
        id,
      		title,
      		location,
      		date,
      		original_caption_by_joseph_needham,
      		photographer,
      		classmark,
      		remark
      from $attr_joseph
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }\n${ and_? }"
        else """
            	and (
              title like ?
              	or location like ?
              	or date like ?
              	or original_caption_by_joseph_needham like ?
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

      val o = new Joseph
      o.setId(rs.getInt(1))
      o.setTitle(rs.getString(2))
      o.setLocation(rs.getString(3))
      o.setDate(rs.getString(4))
      o.setOriginal_caption_by_joseph_needham(rs.getString(5))
      o.setPhotographer(rs.getString(6))
      o.setClassmark(rs.getString(7))
      o.setRemark(rs.getString(8))
      o
    }

  def update(o: Joseph): Int =
    jdbcTemplate.update(s"""
        update $attr_joseph
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)
}