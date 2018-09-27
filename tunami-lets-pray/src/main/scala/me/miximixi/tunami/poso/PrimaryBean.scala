package me.miximixi.tunami.poso

import com.sasaki.packages.constant._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 4:23:56 PM
 * @Description Constant
 */
class PrimaryBean { self =>

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

  def setId($id: JInt): self.type = {
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
