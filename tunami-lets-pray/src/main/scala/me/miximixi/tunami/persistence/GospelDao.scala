package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository

import com.sasaki.chain.ScalaEntity

import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Gospel
import me.miximixi.tunami.kit.AbstractQueryHander
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import java.sql.PreparedStatement
import java.sql.Date

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class GospelDao extends AbstractQueryHander[Gospel] with JdbcTemplateHandler with DB with ScalaEntity {

  def query(date: Date): Option[Gospel] =
    query(s"select id, content, chapter, date from $attr_gospel where date=? limit 1", date) { (rs, i) =>

      val o = new Gospel
      o.setId(Int.box(rs.getInt(1)))
      setMultiple(o, Array(
        ("content", rs.getString(2)),
        ("chapter", rs.getString(3)),
        ("date", rs.getDate(4))))
    }.headOption
    
  override def insert(seq: Seq[Gospel]): Int =
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $attr_gospel (content, date, chapter, timestamp)
       values (?, ?, ?, ?)
       """, new BatchPreparedStatementSetter() {

      override def setValues(ps: PreparedStatement, i: Int) = {
        ps.setString(1, seq(i).getContent())
        ps.setDate(2, seq(i).getDate())
        ps.setString(3, seq(i).getChapter())
        ps.setTimestamp(4, seq(i).getTimestamp)
      }

      override def getBatchSize() = seq.size
    }).reduce(_ + _)
    
  override def list = ???
  
  override def update(o: Gospel) = ???
}