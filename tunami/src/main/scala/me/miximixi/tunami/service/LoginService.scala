package me.miximixi.tunami.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.Long

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:42:43 PM
 * @Description 
 */

@Service
class LoginService {
  
  @Autowired
  var jdbcTemplate: JdbcTemplate = _
  
  def countT = jdbcTemplate.queryForObject("select count(*) from t", classOf[Long])
}