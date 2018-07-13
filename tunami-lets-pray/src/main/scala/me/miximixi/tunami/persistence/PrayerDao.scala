package me.miximixi.tunami.persistence

import com.sasaki.packages.constant._
import org.springframework.stereotype.Repository

import com.sasaki.chain.ScalaEntity

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
class PrayerDao extends JdbcTemplateHandler with DB with ScalaEntity {

  def list: JList[Prayer] =
    queryJList(s"""
      select 
        id, 
        content, 
        location,
        gender,
        target,
        see,
        digg
      from $bhvr_prayer 
      order by id desc
      limit 100
      """) { (rs, i) =>

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
}