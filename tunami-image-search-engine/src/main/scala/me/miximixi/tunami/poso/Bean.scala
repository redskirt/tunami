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
  
  val self = this

  def this($id: JInt) {
    this()
    this.id = $id
  }

  def this($id: Integer, $timestamp: JTimestamp) {
    this($id)
    this.timestamp = $timestamp
  }

  var id: JInt = _

  var timestamp: JTimestamp = _

  def getId: JInt = self.id
  
  def getTimestamp: JTimestamp = self.timestamp
    
  def setId($id: JInt): self.type = {
    self.id = $id
    self
  }
  
  def setTimestamp($timestamp: JTimestamp): self.type = {
    self.timestamp = $timestamp
    self
  }
}

class Metadata extends C {
  
  @BeanProperty // -> image_repo
  var imageRepo: String = _
  
}

class Principal extends C {

  @BeanProperty
  var account_name: String = _

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

  @BeanProperty
  var file_name: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var descript: String = _

  @BeanProperty
  var city: String = _

  @BeanProperty
  var `type`: String = _

}

class VshViewMap extends C {
  
  @BeanProperty
  var remark: String = _
  
  @BeanProperty
  var image_name: String = _
  
  @BeanProperty
  var image_id: String = _
  
  @BeanProperty
  var city: String = _
  
  @BeanProperty
  var page_id: String = _
  
  @BeanProperty
  var original_title: String = _
  
  @BeanProperty
  var transliteration: String = _
  
  @BeanProperty
  var alternative_original_title: String = _
  
  @BeanProperty
  var collection: String = _
  
  @BeanProperty
  var digtized_file: String = _
  
  @BeanProperty
  var map_type: String = _
  
  @BeanProperty
  var authors: String = _
  
  @BeanProperty
  var year: String = _
  
  @BeanProperty
  var size: String = _
  
  @BeanProperty
  var map_support: String = _
  
  @BeanProperty
  var place_of_publication: String = _
  
  @BeanProperty
  var repository: String = _
  
  @BeanProperty
  var publishers: String = _
  
}
