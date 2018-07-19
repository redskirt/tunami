package me.miximixi.tunami.poso

import scala.beans.BeanProperty
import com.sasaki.packages.constant._
import com.sasaki.chain.ScalaEntity

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
    setId($id)
  }

  def this($id: Integer, $timestamp: JTimestamp) {
    this($id)
    setTimestamp($timestamp)
  }
  
  def this($id: Integer, $timestamp: JTimestamp, $status: String) {
    this($id, $timestamp)
    setStatus($status)
  }

  var id: JInt = _
  
  var status: String = "0" // 非0不被查询

  var timestamp: JTimestamp = new JTimestamp(com.sasaki.packages.independent.TIME_MULLIONS)

  def getId: JInt = self.id
  
  def getStatus: String = self.status

  def setStatus($status: String): self.type = {
    self.status = $status
    self
  }
    
  def setId($id: JInt): C = {
    self.id = $id
    self
  }
  
  def getTimestamp: JTimestamp = self.timestamp
  
  def setTimestamp($timestamp: JTimestamp): self.type = {
    self.timestamp = $timestamp
    self
  }
  
  def setTimestamp($timestamp: JLong): self.type = {
  		self.timestamp = new JTimestamp($timestamp)
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
}

class Gospel extends C {
  
  @BeanProperty
  var content: String = _
  
  @BeanProperty
  var date: java.sql.Date = _
//  var date: JDate = _
}

class Prayer extends C {

  @BeanProperty
  var content: String = _
  
  @BeanProperty
  var location: String = _
  
  @BeanProperty
  var gender: String = _ // 0: female, 1: male
  
  @BeanProperty
  var target: String = _
  
//  @BeanProperty
//  var relation: String = _
  
  @BeanProperty
  var see: JInt = 0
  
  @BeanProperty
  var digg: JInt = 0
  
  val genderInfo = if("0" == gender) "姊妹" else "弟兄"
}

class Prophet extends C {
  
  @BeanProperty 
  var category: String = _
  
  @BeanProperty
  var content: String = _
  
  @BeanProperty
  var chapter: String = _

  @BeanProperty
  var see: JInt = 0
  
  lazy val chapterO: Tuple3[String, Int, Int] =
    if (chapter == null)
      ("", 0, 0)
    else {
      val o = chapter.split('|')
      (o(0), o(1) toInt, o(2) toInt)
    }
}

class Anthem extends C {
  @BeanProperty
  var name: String = _

  @BeanProperty
  var artist: String = _

  @BeanProperty
  var order: JInt = _
}

final object Bible extends Enumeration {
  type Bible = Value
  val //
  创世记, //
  出埃及记, //
  利未记, //
  民数记, //
  申命记, //
  约书亚记, //
  士师记, //
  路得记, //
  撒母耳记上, //
  撒母耳记下, //
  列王记上, //
  列王记下, //
  历代志上, //
  历代志下, //
  以斯拉记, //
  尼希米记, //
  以斯帖记, //
  约伯记, //
  诗篇, //
  箴言, //
  传道书, //
  雅歌, //
  以赛亚书, //
  耶利米书, //
  耶利米哀歌, //
  以西结书, //
  但以理书, //
  何西阿书, //
  约珥书, //
  阿摩司书, //
  俄巴底亚书, //
  约拿书, //
  弥迦书, //
  那鸿书, //
  哈巴谷书, //
  西番雅书, //
  哈该书, //
  撒迦利亚书, //
  玛拉基书, //
  马太福音, //
  马可福音, //
  路加福音, //
  约翰福音, //
  使徒行传, //
  罗马书, //
  哥林多前书, //
  哥林多后书, //
  加拉太书, //
  以弗所书, //
  腓立比书, //
  歌罗西书, //
  帖撒罗尼迦前书, //
  帖撒罗尼迦后书, //
  提摩太前书, //
  提摩太后书, //
  提多书, //
  腓利门书, //
  希伯来书, //
  雅各书, //
  彼得前书, //
  彼得后书, //
  约翰壹书, //
  约翰贰书, //
  约翰叁书, //
  犹大书, //
  启示录 = Value //

  def name(o: Bible.Value) = o.toString()
}

object APP extends App{
  
  println(Bible.name(Bible.创世记))
  println(Bible.创世记.id)
}