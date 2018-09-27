package me.miximixi.tunami.persistence

import java.sql.PreparedStatement

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.stereotype.Repository

import com.sasaki.packages.constant.JInt

import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Prayer

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class PrayerDao extends AbstractQueryDao[Prayer] {

  def list(minId: JInt = 0): List[Prayer] =
    query(s"""
      select 
        id, 
        content, 
        location,
        gender,
        target,
        see,
        digg
      from $table 
      where true
      ${ if(0 != minId) "and id<?" else and_? }
      order by id desc
      limit ${ if(0 != minId) 5/*增量加载数*/ else 20/*初始化加载数*/ }
      """, minId) { (rs, i) => buildBean(classOf[Prayer], rs).setId(rs.getInt(1)) }
  
  def insert(seq: Seq[Prayer]): Int =
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $table (
        content, 
        location,
        gender,
        target,
        see,
        digg
        ) values (?, ?, ?, ?, ?, ?)
       """, new BatchPreparedStatementSetter() {

      override def setValues(ps: PreparedStatement, i: Int) = {
        ps.setString(1, seq(i).getContent())
        ps.setString(2, seq(i).getLocation())
        ps.setString(3, seq(i).getGender())
        ps.setString(4, seq(i).getTarget())
        ps.setInt(5, seq(i).getSee())
        ps.setInt(6, seq(i).getDigg())
      }

      override def getBatchSize() = seq.size
    }).reduce(_ + _)
    
  /**
   * 新增相应id的浏览数
   */
  def update(ids: Array[Object]) = 
    jdbcTemplate.update(s"""
      update $table
      set see = see + 1
      where id in (${ ids.map(o => "?").mkString(", ") })
      """, ids:_*)
}

