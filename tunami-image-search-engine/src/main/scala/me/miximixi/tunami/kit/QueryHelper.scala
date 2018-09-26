package me.miximixi.tunami.kit

import com.sasaki.packages.constant._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:15:36 PM
 * @Description 
 */
trait QueryHelper[T] { 

  def count(keyword: String): Option[Int]
  def list(keyword: String, limit: Tuple2[JInt, JInt]): JList[T]
   
  final val __ = "__"
  final val and_? = " and ? is false " // 若有多余参数但不需要赋值，则用该变量占位
  final val count_from = "select count(0) from "
  final val limit_? = " limit ?,? "
  
  protected def and(column: String, value: String): String = if(__ != value) s"and $column = ?\n" else and_?
  protected def and(column: JInt): String = if(0 != column) s"and $column = ?\n" else and_?
  protected def not_null(column: String): String = s"and $column is not null\n"
  protected def like(keyword: String): String = s"%$keyword%"

}

