package me.miximixi.tunami.poso

import scala.beans.BeanProperty
import com.sasaki.packages.constant._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:25:12 PM
 * @Description
 */

class C {

  def this($id: JInt) {
    this()
    this.id = $id
  }

  def this($id: Integer, $timestamp: JTimestamp) {
    this($id)
    this.timestamp = $timestamp
  }

  @BeanProperty
  var id: JInt = _

  @BeanProperty
  var timestamp: JTimestamp = _
}

class Metadata extends C {
  
  @BeanProperty // -> image_repo
  var imageRepo: String = _
  
  
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

class ImageContent extends C {

  @BeanProperty
  var dir: String = _

  @BeanProperty // -> file_name
  var fileName: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var descript: String = _

  @BeanProperty
  var city: String = _

  @BeanProperty
  var `type`: String = _

}

