package me.miximixi.tunami.persistence


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:15:36 PM
 * @Description 
 */
trait DB {

  final val __ = "__"
  final val and_? = " and ? is false " // 若有多余参数但不需要赋值，则用该变量占位
  final val count_from = "select count(0) from "
  final val limit_? = " limit ?,? "
  
  val attr_principal = "attr_principal"
  val attr_metadata = "attr_metadata"
  val attr_image_content = "attr_image_content"
  val attr_vsh_view = "attr_vsh_view"
  val attr_vsh_view_map = "attr_vsh_view_map"
  
  protected def and(column: String): String = if(__ != column) s"and $column = ?\n" else and_?
  protected def not_null(column: String): String = s"and $column is not null\n"
  protected def like(keyword: String): String = s"%$keyword%"
}