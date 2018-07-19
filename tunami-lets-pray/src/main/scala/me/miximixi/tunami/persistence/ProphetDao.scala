package me.miximixi.tunami.persistence

import com.sasaki.chain.ScalaEntity
import me.miximixi.tunami.kit.AbstractQueryHander
import org.springframework.stereotype.Repository
import me.miximixi.tunami.kit.JdbcTemplateHandler
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Prophet
import com.sasaki.packages.constant.{ JInt, JList }
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import java.sql.PreparedStatement


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 17, 2018 8:41:16 PM
 * @Description
 */
@Repository
class ProphetDao extends AbstractQueryHander[Prophet] with JdbcTemplateHandler with DB with ScalaEntity {

  def insert(seq: Seq[Prophet]) = 
    jdbcTemplate.batchUpdate(s"""
       insert into 
       $attr_prophet (
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
    queryJList(s"select distinct category from $attr_prophet") { (rs, i) =>
      rs.getString(1)
    }
  
  def list = ???

  def list(minId: JInt = 0, category: String = __): List[Prophet] =
    query(s"""
      select 
        id,
        content,
        category,
        chapter
      from $attr_prophet
      where true
      ${ if(0 != minId) "and id<?" else and_? }
      ${ and("category", category) }
      order by id desc
      limit ${ if(0 != minId) 5/*增量加载数*/ else 20/*初始化加载数*/ }
      """, minId, category) { (rs, i) =>

      val o = new Prophet
      o.setId(rs.getInt(1))
      setMultiple_3(o, Seq(
        ("content", rs.getString(2), CLASS_STRING),
        ("category", rs.getString(3), CLASS_STRING),
        ("chapter", rs.getString(4), CLASS_STRING)))
    }
    
  def update(o: Prophet) = ???
      
  def update(ids: Array[Object]) = jdbcTemplate.update(s"""
		  update $attr_prophet
		  set see = see + 1
		  where id in (${ ids.map(o => "?").mkString(", ") })
  """, ids:_*)
}