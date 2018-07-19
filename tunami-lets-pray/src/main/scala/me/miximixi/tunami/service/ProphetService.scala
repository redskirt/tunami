package me.miximixi.tunami.service

import me.miximixi.tunami.persistence.ProphetDao
import me.miximixi.tunami.poso.Prophet

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 14, 2018 5:55:25 PM
 * @Description 
 */
@org.springframework.stereotype.Service
class ProphetService {
  
  @org.springframework.beans.factory.annotation.Autowired
  private var prophetDao: ProphetDao = _
  
  def bizListPrayer(minId: com.sasaki.packages.constant.JInt, category: String): List[Prophet] = {
    val list = prophetDao.list(minId, category)
    prophetDao.update(list.map(o => o.id.asInstanceOf[Object]).toArray)
    list
  }
}