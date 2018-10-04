package me.miximixi.tunami.persistence

import java.sql.PreparedStatement

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.stereotype.Repository

import com.sasaki.packages.constant.JInt
import com.sasaki.packages.constant.JList

import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Prophet

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 17, 2018 8:41:16 PM
 * @Description
 */
@Repository
class ProphetDao extends AbstractQueryDao[Prophet] {

  def insert(seq: Seq[Prophet]) =
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $table (
        content, 
        category,
        chapter
        ) values (?, ?, ?)
       """, new BatchPreparedStatementSetter() {

      override def setValues(ps: PreparedStatement, i: Int) = {
        ps.setString(1, seq(i).getContent())
        ps.setString(2, seq(i).getCategory())
        ps.setString(3, seq(i).getChapter())
      }
      override def getBatchSize() = seq.size
    }).reduce(_ + _)

  def listCategory: JList[String] =
    queryJList(s"select distinct category from $table") { (rs, i) => rs.getString(1) }

  def list(minId: JInt = 0, category: String = __): JList[Prophet] = {
    val sql = s"""
      select 
        id,
        content,
        category,
        chapter
      from $table
      where true
      ${if (0 != minId) "and id<?" else and_?}
      ${and("category", category)}
      order by id desc
      limit ${if (0 != minId) 5 /*增量加载数*/ else 20 /*初始化加载数*/ }
      """
    queryJList(sql, minId, category) { (rs, i) =>
      buildBean(classOf[Prophet], rs, parseQueryColumn(sql))
    }
  }

  def update(ids: Array[Object]) = jdbcTemplate.update(s"""
		  update $table
		  set see = see + 1
		  where id in (${ids.map(o => "?").mkString(", ")})
  """, ids: _*)

  def queryMaxDate: Option[String] =
    query(s"select max(date) from $table")((rs, i) => rs.getString(1)).headOption
}