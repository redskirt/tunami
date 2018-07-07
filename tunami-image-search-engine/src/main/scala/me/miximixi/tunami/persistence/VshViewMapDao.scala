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
class VshViewMapDao extends JdbcTemplateHandler with DB with ScalaEntity {

  def count(city: String = __): Option[Int] = 
    query(s"""
      $count_from
        $attr_vsh_view_map
      where true
      ${ and(city) }
      """, city) { (rs, i) => rs.getInt(1) }.headOption
    
  def list(city: String = __, limit: Tuple2[JInt, JInt]): JList[VshViewMap] =
    queryJList(s"""
      select 
        id,
        image_id,
        city,
        year,
        original_title,
        size,
        authors,
        map_type,
        publishers,
        repository,
        remark
      from $attr_vsh_view_map
      where true
      ${ and(city) }
      order by id asc
      ${ limit_? }
      """, city, limit._1, limit._2) { (rs, i) =>

      val map = new VshViewMap
      map.setId(Int.box(rs.getInt(1)))

      setMultiple_3(map, Array(
        ("image_id", rs.getString(2), CLASS_STRING),
        ("city", rs.getString(3), CLASS_STRING),
        ("year", rs.getString(4), CLASS_STRING),
        ("original_title", rs.getString(5), CLASS_STRING),
        ("size", rs.getString(6), CLASS_STRING),
        ("authors", rs.getString(7), CLASS_STRING),
        ("map_type", rs.getString(8), CLASS_STRING),
        ("publishers", rs.getString(9), CLASS_STRING),
        ("repository", rs.getString(10), CLASS_STRING),
        ("remark", rs.getString(11), CLASS_STRING)))
    }

}