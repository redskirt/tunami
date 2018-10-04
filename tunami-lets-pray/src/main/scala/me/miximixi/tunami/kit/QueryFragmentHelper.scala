package me.miximixi.tunami.kit

import com.sasaki.packages.constant._
import com.sasaki.packages.independent

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:15:36 PM
 * @Description
 */
trait QueryFragmentHelper {

  private final val * = "*"
  
  protected final val __ = "__"
  protected final val and_? = " and ? is false " // 若有多余参数但不需要赋值，则用该变量占位
  protected final val from_* = select(*)
  protected final val from_count = select("count(0)")
  protected final val limit_? = " limit ?,? "

  private def select(column: String) = s"select $column from "
   
  protected def and(column: String, value: String): String = 
    if (__ != value) s"and $column = ?\n" else and_?
    
  protected def and(column: JInt): String = 
    if (0 != column) s"and $column = ?\n" else and_?
    
  protected def not_null(column: String): String = 
    s"and $column is not null\n"
    
  protected def like(keyword: String): String = 
    s"%$keyword%"

  protected def parseQueryColumn(sql: String): Seq[String] = {
    val array = sql
      .toLowerCase
      .substring(sql.lastIndexOf("select") + 6, sql.lastIndexOf("from"))
      .split(',')
      .map(_.trim.replace(" ", ""))

    independent.invokeVerify(array.nonEmpty, s">> Illegal sql statement: $sql") { () =>
      if (* == array(0)) 
        ??? // select * from 语句此处考虑反射列，因该查询模式存在效率问题暂不做开发
      else 
        array
    }
  }
}

