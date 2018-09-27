package me.miximixi.tunami.persistence

import java.sql.Date
import java.sql.PreparedStatement

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.stereotype.Repository

import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Gospel

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class GospelDao extends AbstractQueryDao[Gospel] {

  def query(date: Date): Option[Gospel] =
    query(s"select id, content, chapter, date from $table where date=? limit 1", date) 
      { (rs, i) => buildBean(classOf[Gospel], rs).setId(rs.getInt(1)) }.headOption
    
  def insert(seq: Seq[Gospel]): Int =
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $table (content, date, chapter, timestamp)
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
    
}