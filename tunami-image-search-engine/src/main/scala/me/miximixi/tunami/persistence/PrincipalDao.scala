package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository

import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.persistence.QueryProperty.attr_principal
import me.miximixi.tunami.poso.Principal


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class PrincipalDao extends AbstractQueryDao[Principal]  with JdbcTemplateHandler {
   
  def query(accountName: String): Option[Principal] =
    query(s"select id, account_name, password from $attr_principal where account_name = ?", accountName) 
      {  (rs, i) => buildBean(classOf[Principal], rs).setId(rs.getInt("id")) }.headOption

}