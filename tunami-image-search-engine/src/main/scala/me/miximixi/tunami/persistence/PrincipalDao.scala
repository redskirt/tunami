package me.miximixi.tunami.persistence

import com.sasaki.packages.constant.JInt
import com.sasaki.packages.constant.JList
import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.poso.Principal
import me.miximixi.tunami.kit.JdbcTemplateHandler._
import com.sasaki.chain.ScalaEntity


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class PrincipalDao extends JdbcTemplateHandler with QueryHelper[Principal] {
  
  def count(keyword: String = __): Option[Int] = None
  
  def list(keyword: String = __, limit: Tuple2[JInt, JInt]): JList[Principal] = null

  def query(accountName: String): Option[Principal] =
    query(s"select id, account_name, password from $attr_principal where account_name = ?", accountName) 
      {  (rs, i) => buildBean(classOf[Principal], rs).setId(rs.getInt("id")) }.headOption

}