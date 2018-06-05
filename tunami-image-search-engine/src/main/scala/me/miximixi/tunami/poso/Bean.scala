package me.miximixi.tunami.poso

import scala.beans.BeanProperty

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:25:12 PM
 * @Description
 */
class User extends Serializable {
  @BeanProperty
  var id: Integer = _

  @BeanProperty
  var username: String = _
  
    @BeanProperty
  var password: String = _
}