package me.miximixi.tunami.kit

import java.sql.{ ResultSet, Timestamp }
import java.time.{ ZoneId, Instant, LocalDateTime }

import com.sasaki.packages.constant._
import scala.collection.JavaConverters._

import org.springframework.jdbc.core.{ RowMapper, JdbcTemplate }

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
    def apply[T](f: (ResultSet, Int) => T)(implicit ev: ((ResultSet, Int) => T) => RowMapper[T]): JList[T] = 
      jdbcTemplate.query(sql, args.toArray, ev(f))//.asScala.toList
  }

  protected def query(sql: String, args: Object*) = new {
    def apply[T](f: (ResultSet, Int) => T)(implicit ev: ((ResultSet, Int) => T) => RowMapper[T]): List[T] =
      jdbcTemplate.query(sql, args.toArray, ev(f)).asScala.toList
  }

//  protected implicit def list2JavaList[T](list: List[T]): com.sasaki.packages.constant.JList[T] = 
//    list.asJava

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