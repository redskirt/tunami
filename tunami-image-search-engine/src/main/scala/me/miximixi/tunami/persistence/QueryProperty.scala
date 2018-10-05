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
  
  val attr_principal             = "attr_principal"
  val attr_metadata              = "attr_metadata"
  val attr_vsh_view              = "attr_vsh_view"
  val attr_vsh_view_map          = "attr_vsh_view_map"
  val attr_harvard_yenching      = "attr_harvard_yenching"
  val attr_bristol               = "attr_bristol"
  val attr_joseph                = "attr_joseph"
  val attr_weichat               = "attr_weichat"
  
  def mappingTable[T: TypeTag] = 
    typeOf[T] match {
      case __ if __ =:= typeOf[Principal]  => attr_principal
      case __ if __ =:= typeOf[VshView]    => attr_vsh_view
      case __ if __ =:= typeOf[VshViewMap] => attr_vsh_view_map
      case __ if __ =:= typeOf[WeiChat]    => attr_weichat
      case __ if __ =:= typeOf[Bristol]    => attr_bristol
      case __ if __ =:= typeOf[Yenching]   => attr_harvard_yenching
      case __ if __ =:= typeOf[Joseph]     => attr_joseph
      case _                               => ???; println(s"Unexpected type of column: ${ typeOf[T] }.")
    }
}