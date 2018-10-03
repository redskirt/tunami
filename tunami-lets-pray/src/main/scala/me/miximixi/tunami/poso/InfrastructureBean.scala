package me.miximixi.tunami.poso

import scala.beans.BeanProperty

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Oct 3, 2018 9:07:12 AM
 * @Description 基础组件 Bean
 */
class JournalFrontend extends PrimaryBean {
  
  @BeanProperty
  var uri: String = _
  
  @BeanProperty
  var url: String = _
  
  @BeanProperty
  var http_method: String = _
  
  @BeanProperty
  var ip: String = _
  
  @BeanProperty
  var method: String = _
  
  @BeanProperty
  var args: String = _
  
//  @BeanProperty
//  var exception: String = _
  
}

class JournalPlatform extends PrimaryBean {
  
}