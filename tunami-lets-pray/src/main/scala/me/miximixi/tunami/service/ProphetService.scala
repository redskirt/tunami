package me.miximixi.tunami.service

import me.miximixi.tunami.persistence.ProphetDao
import me.miximixi.tunami.poso.Prophet
import com.sasaki.packages.constant._

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
  
  def bizListProphet(minId: JInt, category: String): JList[Prophet] = {
    val list = prophetDao.list(minId, category)
    prophetDao.update({
      {
        for(i <- 0 until list.size()) yield list.get(i).id.asInstanceOf[Object]
      } toArray
    })
    list
  }
}