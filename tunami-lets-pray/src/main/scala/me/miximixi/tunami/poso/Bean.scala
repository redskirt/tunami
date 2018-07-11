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

