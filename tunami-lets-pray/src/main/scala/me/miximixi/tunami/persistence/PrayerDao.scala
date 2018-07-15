package me.miximixi.tunami.persistence

import java.sql.PreparedStatement

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.stereotype.Repository

import com.sasaki.chain.ScalaEntity
import com.sasaki.packages.constant.JInt

import me.miximixi.tunami.kit.AbstractQueryHander
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Prayer

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:13:50 PM
 * @Description
 */
@Repository
class PrayerDao extends AbstractQueryHander[Prayer] with JdbcTemplateHandler with DB with ScalaEntity {

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
      from $bhvr_prayer 
      where true
      ${ if(0 != minId) "and id<?" else and_? }
      order by id desc
      limit ${ if(0 != minId) 5/*增加加载数*/ else 20/*初始化加载数*/ }
      """, minId) { (rs, i) =>

      val o = new Prayer
      o.setId(Int.box(rs.getInt(1)))
      setMultiple(o, Array(
        ("content", rs.getString(2)),
        ("location", rs.getString(3)),
        ("gender", rs.getString(4)),
        ("target", rs.getString(5)),
        ("see", Int.box(rs.getInt(6))),
        ("digg", Int.box(rs.getInt(7)))))
    }
  
  override def insert(seq: Seq[Prayer]): Int =
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $bhvr_prayer (
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
    
  override def list = ???
  
  override def update(o: Prayer) = ???
  
  /**
   * 新增相应id的浏览数
   */
  def update(ids: Array[Object]) = 
    jdbcTemplate.update(s"""
      update $bhvr_prayer
      set see = see + 1
      where id in (${ ids.map(o => "?").mkString(", ") })
      """, ids:_*)
}

