package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository

import com.sasaki.packages.constant.JInt
import com.sasaki.packages.constant.JList

import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.persistence.QueryProperty.attr_weichat
import me.miximixi.tunami.poso.WeiChat

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 25, 2018 1:06:19 PM
 * @Description 
 */
@Repository
class WeiChatDao extends AbstractQueryDao[WeiChat] {

  def count(keyword: String = __): Option[Int] = 
    query(s"""
      $from_count
        $table
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }"
        else """
          	and (
            	original_title like ?
            	or source like ? 
            	or remark like ?
          )
          """
      }    
      """, 
      like(keyword), 
      like(keyword), 
      like(keyword)) { (rs, i) => rs.getInt(1) }.headOption
    
  def list(keyword: String = __, limit: Tuple2[JInt, JInt]): JList[WeiChat] =
    queryJList(s"""
      select 
        id,
      		original_title,
      		image_name,
      		source,
      		remark
      from $table
      where true
      ${
        if (__ == keyword)
          s"${ and_? }\n${ and_? }\n${ and_? }"
        else """
          	and (
            	original_title like ?
            	or source like ?
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
      limit._1,
      limit._2) { (rs, i) => buildBean(classOf[WeiChat], rs).setId(rs.getInt("id")) }

  def update(o: WeiChat): Int =
    jdbcTemplate.update(s"""
        update $table
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)
}