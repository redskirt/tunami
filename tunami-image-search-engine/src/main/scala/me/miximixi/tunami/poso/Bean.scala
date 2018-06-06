package me.miximixi.tunami.poso

import scala.beans.BeanProperty
import java.sql.{ Timestamp => JTimestamp }

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:25:12 PM
 * @Description
 */

class C {

  def this($id: Integer) {
    this()
    this.id = $id
  }

  def this($id: Integer, $timestamp: JTimestamp) {
    this($id)
    this.timestamp = $timestamp
  }

  @BeanProperty
  var id: Integer = _

  @BeanProperty
  var timestamp: JTimestamp = _
}

class Principal extends C {

  @BeanProperty
  var accountName: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var `type`: String = _

  @BeanProperty
  var status: String = _

}