package me.miximixi.tunami.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import me.miximixi.tunami.kit.JdbcTemplateUtils
import me.miximixi.tunami.kit.JdbcTemplateUtils.mapRow
import me.miximixi.tunami.poso.Principal

import com.sasaki.chain.ScalaEntity


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:42:43 PM
 * @Description 
 */
@Service
class LoginService extends ScalaEntity with JdbcTemplateUtils  {
  
//  @Autowired
//  private var jdbcTemplate: JdbcTemplate = _
  
  @Autowired
  def setJdbcTemplate(jdbcTemplate: JdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate
  }
  
  val attr_principal = "attr_principal"
  
  def queryPrincipal(accountName: String): Option[Principal] = 
    query(s"select id, account_name, password from $attr_principal where account_name=?", accountName) { (rs, i) =>
      val principal = new Principal()
      principal.id = rs.getInt(1)
      principal.accountName = rs.getString(2)
      principal.password = rs.getString(3)
      
//      setMultiple[Principal](principal, Array(
//        ("id", rs.getInt(1).asInstanceOf[Object]), ()    
//      ))
      principal
    }.headOption
  
}