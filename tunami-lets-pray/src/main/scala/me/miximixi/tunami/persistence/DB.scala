package me.miximixi.tunami.persistence

import com.sasaki.packages.constant.JInt

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:15:36 PM
 * @Description 
 */
trait DB {

  protected val __ = "__"
  protected val and_? = " and ? is false " // 若有多余参数但不需要赋值，则用该变量占位
  protected val count_from = "select count(0) from "
  protected val limit_? = " limit ?,? "
  
  protected val attr_principal = "attr_principal"
  protected val attr_metadata = "attr_metadata"
  protected val attr_gospel = "attr_gospel"
  protected val attr_prophet = "attr_prophet"
  protected val bhvr_prayer = "bhvr_prayer"
  protected val attr_anthem = "attr_anthem"
  
  protected def and(column: String, value: String): String = if(__ != value) s"and $column = ?\n" else and_?
  protected def and(column: JInt): String = if(0 != column) s"and $column = ?\n" else and_?
  protected def not_null(column: String): String = s"and $column is not null\n"
}