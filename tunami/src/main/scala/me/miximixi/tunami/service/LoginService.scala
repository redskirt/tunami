package me.miximixi.tunami.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import me.miximixi.tunami.poso.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import org.springframework.jdbc.core.RowCallbackHandler
import me.miximixi.tunami.kit.JdbcTemplateUtils
import me.miximixi.tunami.kit.JdbcTemplateUtils._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:42:43 PM
 * @Description 
 */
@Service
class LoginService extends JdbcTemplateUtils {
  
//  @Autowired
//  private var jdbcTemplate: JdbcTemplate = _
  
  @Autowired
  def setJdbcTemplate(jdbcTemplate: JdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate
  }
  
  val attr_user = "attr_user"
  val sql = s"select id, username, password from $attr_user where username=?"
  
  
  def queryUser(username: String): User = {
    query(sql, username) { (rs, i) =>
      val user = new User()
      user.id = rs.getInt(1)
      user.username = rs.getString(2)
      user.password = rs.getString(3)
      user
    }.head
  }
  
}