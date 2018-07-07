package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository
import com.sasaki.chain.ScalaEntity
import com.sasaki.packages.independent._
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import me.miximixi.tunami.poso.ImageContent


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 19, 2018 7:43:45 AM
 * @Description 
 */
@Repository
class ImageContentDao extends JdbcTemplateHandler with DB with ScalaEntity {
  
  def list(city: String = __, `type`: String = __): Seq[ImageContent] = {
    val sql = s"""
        select id, dir, file_name, name, descript, city, type
        from $attr_image_content
        where true
        ${ and(city); and(`type`) }
      """
        
        
    query(sql, city, `type`) { (rs, i) =>
      setMultiple(new ImageContent, Array(
        ("id", Int.box(rs.getInt(1))),
        ("dir", rs.getString(2)),
        ("fileName", rs.getString(3)),
        ("name", rs.getString(4)),
        ("descript", rs.getString(5)),
        ("city", rs.getString(6)),
        ("type", rs.getString(7))))
    }.toSeq
  }
  
  def add(image: ImageContent) = {
    val sql = s"insert into $attr_image_content values "
//    jdbcTemplate.update(x$1, x$2)
  }
  
//    @Autowired
//  def setJdbcTemplate(jdbcTemplate: JdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate
//  }
    
}