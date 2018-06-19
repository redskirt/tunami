package me.miximixi.tunami.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import me.miximixi.tunami.persistence.PrincipalDao
import me.miximixi.tunami.poso.Principal


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:42:43 PM
 * @Description 
 */
@Service
class LoginService {
  
  @Autowired
  private var principalDao: PrincipalDao = _
  
  def bizCheckin(accountName: String): Option[Principal] = 
    principalDao.query(accountName)
  
}