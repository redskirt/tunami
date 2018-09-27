package me.miximixi.tunami.persistence

import me.miximixi.tunami.poso._
import reflect.runtime.universe._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Sep 26, 2018 11:11:34 PM
 * @Description 
 */
object QueryProperty {

  val attr_principal = "attr_principal"
  val attr_metadata = "attr_metadata"
  val attr_gospel = "attr_gospel"
  val attr_prophet = "attr_prophet"
  val bhvr_prayer = "bhvr_prayer"
  val attr_anthem = "attr_anthem"

  def mappingTable[T: TypeTag] =
    typeOf[T] match {
      case __ if __ =:= typeOf[Principal] => attr_principal
      case __ if __ =:= typeOf[Metadata]  => attr_metadata
      case __ if __ =:= typeOf[Gospel]    => attr_gospel
      case __ if __ =:= typeOf[Prophet]   => attr_prophet
      case __ if __ =:= typeOf[Prayer]    => bhvr_prayer
      case __ if __ =:= typeOf[Anthem]    => attr_anthem
      case _                              => "???"
    }
}