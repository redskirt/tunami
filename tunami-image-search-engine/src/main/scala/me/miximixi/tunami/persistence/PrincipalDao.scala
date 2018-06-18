package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import me.miximixi.tunami.kit.JdbcTemplateUtils
import me.miximixi.tunami.poso.Principal
import me.miximixi.tunami.kit.JdbcTemplateUtils.mapRow
import com.sasaki.chain.ScalaEntity


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class PrincipalDao extends JdbcTemplateUtils with DB with ScalaEntity {

  @Autowired
  def setJdbcTemplate(jdbcTemplate: JdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate
  }

  def queryPrincipal(accountName: String): Option[Principal] = 
    query(s"select id, account_name, password from $attr_principal where account_name=?", accountName) { (rs, i) =>
      
    val principal = new Principal()
    //      principal.id = rs.getInt(1)
    //      principal.accountName = rs.getString(2)
    //      principal.password = rs.getString(3)

    //      principal

    setMultiple[Principal](principal, Array(
      ("id", Int.box(rs.getInt(1))),
      ("accountName", rs.getString(2)),
      ("password", rs.getString(3))))
  }.headOption

}