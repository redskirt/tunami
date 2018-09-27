package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository

import com.sasaki.packages.constant.JInt
import com.sasaki.packages.constant.JList

import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.kit.QueryDao
import me.miximixi.tunami.persistence.QueryProperty.attr_harvard_yenching
import me.miximixi.tunami.poso.Yenching

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Sep 25, 2018 8:45:57 AM
 * @Description 
 */
@Repository
class YenchingDao extends QueryDao[Yenching] {

  def count(keyword: String = __): Option[Int] =
    query(
      s"""
      $from_count
        $attr_harvard_yenching
      where true
      ${
      if (__ == keyword)
        s"${and_?}\n${and_?}\n${and_?}\n${and_?}\n${and_?}"
      else """
            	and (
              	title like ?
              	or image_name like ?
              	or author_or_creator like ?
              	or notes like ?
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

  def list(keyword: String = __, limit: Tuple2[JInt, JInt]): JList[Yenching] =
    queryJList(
      s"""
      select 
        id,
      		image_name,
      		title,
      		author_or_creator,
      		description,
      		dimensions,
      		notes,
      		creation_date,
      		repository,
      		permalink,
      		remark
      from $attr_harvard_yenching
      where true
      ${
      if (__ == keyword)
        s"${and_?}\n${and_?}\n${and_?}\n${and_?}\n${and_?}"
      else """
            	and (
              title like ?
              	or image_name like ?
              	or author_or_creator like ?
              	or notes like ?
              	or remark like ?
            )
            """
    }
      order by id asc
      ${limit_?} 
      """,
      like(keyword),
      like(keyword),
      like(keyword),
      like(keyword),
      like(keyword),
      limit._1,
      limit._2)((rs, i) => buildBean(classOf[Yenching], rs).setId(Int.box(rs.getInt(0))))

  def update(o: Yenching): Int =
    jdbcTemplate.update(s"""
        update $attr_harvard_yenching
        set remark=?
        where true
        and id=?
        """, o.remark, o.id)

}