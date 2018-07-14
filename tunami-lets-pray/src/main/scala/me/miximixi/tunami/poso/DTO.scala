package me.miximixi.tunami.poso

import scala.beans.BeanProperty

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 14, 2018 6:32:54 PM
 * @Description 
 */
case class PrayerDTO (
  @BeanProperty id:      Int,
  @BeanProperty content: String,
  @BeanProperty see:     Int,
  @BeanProperty info:    String)