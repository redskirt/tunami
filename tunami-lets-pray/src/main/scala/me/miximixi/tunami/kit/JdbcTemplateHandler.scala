package me.miximixi.tunami.kit

import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

import scala.collection.JavaConverters.asScalaBufferConverter
import reflect.runtime.universe.TypeTag

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

import com.sasaki.packages.{ independent, reflect => ref, constant => cons }
import java.sql.PreparedStatement
import me.miximixi.tunami.poso.PrimaryBean

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 11:19:43 AM
 * @Description
 * Ref: https://github.com/j-shepard/java-vs-scala
 */
trait JdbcTemplateHandler { self =>

  protected var jdbcTemplate: JdbcTemplate = _

  @org.springframework.beans.factory.annotation.Autowired
  protected def setJdbcTemplate(jdbcTemplate: JdbcTemplate) = self.jdbcTemplate = jdbcTemplate

  /**
   * Generates a function that accepts a closure matching the signature of RowMapper.mapRow
   * The function returned will call JdbcTemplate.query
   * Examples:
   * 1. query("select * from names")({ (rs, rowNum) => <your builder code here> })
   * 2. query("select * from names") { (rs, rowNum) => <your builder code here> }
   *
   * 3. val allNames = query("select * from names")
   *    allNames { (rs, rowNum) => <your builder code here> }
   */
  //  def query(sql: String) = new {
  //    def apply[T](f: (ResultSet, Int) => T)(implicit ev: ((ResultSet, Int) => T) => RowMapper[T]): List[T] = jdbcTemplate.query(sql, ev(f)).asScala.toList
  //  }

  protected def queryJList(sql: String, args: Object*) = new {
    def apply[T](f: (ResultSet, Int) => T)(implicit ev: ((ResultSet, Int) => T) => RowMapper[T]): cons.JList[T] =
      jdbcTemplate.query(sql, args.toArray, ev(f)) //.asScala.toList
  }

  protected def query(sql: String, args: Object*) = new {
    def apply[T](f: (ResultSet, Int) => T)(implicit ev: ((ResultSet, Int) => T) => RowMapper[T]): List[T] =
      jdbcTemplate.query(sql, args.toArray, ev(f)).asScala.toList
  }

  //  protected implicit def list2JavaList[T](list: List[T]): com.sasaki.packages.constant.JList[T] =
  //    list.asJava

  /**
   *
   */
  protected def buildBean[T <: PrimaryBean: TypeTag](clazz: Class[T], rs: ResultSet, queryColumns: Seq[String]): T = {
    lazy val o = clazz.getConstructor().newInstance()
    lazy val fields = ref.extractFieldsAll(clazz)

    for (i <- 0 until queryColumns.size) {
      fields.find(_.getName == queryColumns(i)).get match {
          case f if f.getType.equals(classOf[Int])                 => f.set(o, Int.box(rs.getInt(queryColumns(i))))
          case f if f.getType.equals(classOf[cons.JInt])           => f.set(o, Int.box(rs.getInt(queryColumns(i))))
          case f if f.getType.equals(classOf[Long])                => f.set(o, rs.getLong(queryColumns(i)))
          case f if f.getType.equals(classOf[cons.JLong])          => f.set(o, rs.getLong(queryColumns(i)))
          case f if f.getType.equals(classOf[String])              => f.set(o, rs.getString(queryColumns(i)))
          case f if f.getType.equals(classOf[Boolean])             => f.set(o, rs.getBoolean(queryColumns(i)))
          case f if f.getType.equals(classOf[cons.JDate])          => f.set(o, rs.getDate(queryColumns(i)))
          case f if f.getType.equals(classOf[cons.JTimestamp])     => f.set(o, rs.getTimestamp(queryColumns(i)))
          case _                                                   => throw new Exception("From me.miximixi.tunami.kit.buildBean method，未知的类型。")
      }
    }
    o
  }

  protected def setParameter[T <: PrimaryBean: TypeTag](clazz: Class[T], ps: PreparedStatement, t: T, queryColumns: String*): Unit = {
    lazy val fields = ref.extractFieldsAll(clazz)

    for (i <- 0 until queryColumns.size) {
      val index = i + 1

      fields.find(_.getName == queryColumns(i)).get match {
        case f if f.getType.equals(classOf[Int])             => ps.setInt(index, f.getInt(t))
        case f if f.getType.equals(classOf[cons.JInt])       => ps.setInt(index, Int.box(f.getInt(t)))
        case f if f.getType.equals(classOf[String])          => ps.setString(index, f.get(t).asInstanceOf[String])
        case f if f.getType.equals(classOf[cons.JTimestamp]) => ps.setTimestamp(index, f.get(t).asInstanceOf[cons.JTimestamp])
        case _                                               => throw new Exception("From me.miximixi.tunami.kit.setParameter method，未知的类型。")
      }
    }
  }
}

object JdbcTemplateHandler {

  /** Performs implicit conversion from a closure to RowMapper[T]: Note the closure matches the signature of RowMapper.mapRow */
  implicit def mapRow[T](rowMapper: (ResultSet, Int) => T): RowMapper[T] = {
    new RowMapper[T] {
      override def mapRow(rs: ResultSet, rowNum: Int): T = rowMapper(rs, rowNum)
    }
  }

  implicit def timestampToLocalDateTime(timestamp: Timestamp): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault())

  implicit def resultSetToRichResultSet(rs: ResultSet): RichResultSet = new RichResultSet(rs)
}

/** Wrapper class for ResultSet that adds helper methods */
final class RichResultSet(rs: ResultSet) {
  def getLocalDateTime(colIndex: Int): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp(colIndex).getTime()), ZoneId.systemDefault())
}

class ResultsNonSingleException extends Exception("Returned too many results.")
