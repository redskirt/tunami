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

import com.sasaki.packages.{ reflect => ref, constant => cons }

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
      jdbcTemplate.query(sql, args.toArray, ev(f))//.asScala.toList
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
  protected def buildBean[T: TypeTag](clazz: Class[T], rs: ResultSet, attrs: String*): T = {
    val constructor = clazz.getConstructor()
    val oT = constructor.newInstance()
    val fields_ = clazz.getDeclaredFields.filterNot(_.getName.contains("$")/*含 $ 字符的无效属性*/)
    
      {
        if (null == attrs || attrs.isEmpty || fields_.size == attrs.size)
          fields_
        else
          fields_.filter { o => attrs.contains(o.getName) }
      }
      .filterNot { o =>
        // 排除 @transient 标记属性
        ref.existsAnnotationFromField[T, transient](clazz, o.getName)
      }
      .map { o => (o.getName, o.getType) }
      .foreach { o =>
        val attr = o._1
        val field = clazz.getDeclaredField(attr)
        field.setAccessible(true)

        o._2 match {
          case _type if _type.equals(classOf[Int])               => field.set(oT, Int.box(rs.getInt(attr)))
          case _type if _type.equals(classOf[cons.JInt])         => field.set(oT, Int.box(rs.getInt(attr)))
          case _type if _type.equals(classOf[Long])              => field.set(oT, rs.getLong(attr))
          case _type if _type.equals(classOf[cons.JLong])        => field.set(oT, rs.getLong(attr))
          case _type if _type.equals(classOf[String])            => field.set(oT, rs.getString(attr))
          case _type if _type.equals(classOf[Boolean])           => field.set(oT, rs.getBoolean(attr))
          case _type if _type.equals(classOf[cons.JDate])        => field.set(oT, rs.getDate(attr))
          case _type if _type.equals(classOf[cons.JTimestamp])   => field.set(oT, rs.getTimestamp(attr))
          case _ => throw new Exception("From me.miximixi.tunami.kit.buildBean method，未知的类型。")
        }
      }
      
    oT
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
