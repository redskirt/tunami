package me.miximixi.tunami.kit

import reflect.runtime.universe.TypeTag

import com.sasaki.packages.{ constant => cons }
import com.sasaki.packages.{ reflect => ref }

import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.persistence.QueryProperty.mappingTable
import me.miximixi.tunami.poso.PrimaryBean
import java.sql.PreparedStatement
import org.springframework.jdbc.core.BatchPreparedStatementSetter

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Sep 26, 2018 11:28:22 PM
 * @Description
 */
private[me] abstract class AbstractQueryDao[T <: PrimaryBean: TypeTag] extends JdbcTemplateHandler with QueryFragmentHelper { self =>

  protected lazy val table = mappingTable[T]

  /**
   * 由 Java Reflect 获得泛型类型 Class[T]
   */
  private lazy val clazz = ref.extractTypeClass[T](self)

  def count: Option[Int] =
    query(s"""$from_count $table""") { (rs, i) => rs.getInt(1) }.headOption

  @deprecated("select * 语句暂未处理。")
  def list: cons.JList[T] = {
    val sql = s"${from_*} $table"
    queryJList(sql) { (rs, i) => buildBean(clazz, rs, parseQueryColumn(sql)) }
  }

  @deprecated("select * 语句暂未处理。")
  def list(limit: Tuple2[cons.JInt, cons.JInt]): cons.JList[T] = {
    val sql = s"${from_*} $table ${limit_?} "
    queryJList(sql, limit._1, limit._2) { (rs, i) => buildBean(clazz, rs, parseQueryColumn(sql)) }
  }

  /**
   * 带函数插入模板，批量
   */
  protected def insert(sql: String, seq: Seq[T])(f_x: (PreparedStatement, Int) => Unit): Int =
    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter {

      override def setValues(ps: PreparedStatement, i: Int) = f_x(ps, i)

      override def getBatchSize() = seq.size

    }).reduce(_ + _)

  /**
   * 带函数插入模板，单条
   */
  protected def insert(sql: String, t: T)(f_x: (PreparedStatement, Int) => Unit): Int =
    insert(sql, Seq(t))(f_x)
  //  protected def insert(columns: Seq[String]): Int = ???
  //
  //  protected def update(t: T): Option[Int] = ???
  //
  //  protected def delete(t: T): Option[Int] = ???

}
