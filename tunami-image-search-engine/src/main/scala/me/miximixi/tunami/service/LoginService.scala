package me.miximixi.tunami.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import me.miximixi.tunami.poso.Principal

import com.sasaki.chain.ScalaEntity


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:42:43 PM
 * @Description 
 */
@Service
class LoginService extends ScalaEntity {
  
//  @Autowired
//  private var jdbcTemplate: JdbcTemplate = _
  
  def queryPrincipal(accountName: String): Option[Principal] = 
    ???
  
}