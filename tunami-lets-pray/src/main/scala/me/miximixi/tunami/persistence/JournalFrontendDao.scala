package me.miximixi.tunami.persistence

import me.miximixi.tunami.poso.JournalFrontend
import me.miximixi.tunami.kit.AbstractQueryDao
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import java.sql.PreparedStatement
import org.springframework.stereotype.Repository

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Oct 3, 2018 5:32:07 PM
 * @Description
 */
@Repository
class JournalFrontendDao extends AbstractQueryDao[JournalFrontend] {

  def insert(o: JournalFrontend): Int = insert(Seq(o))
  
  def insert(seq: Seq[JournalFrontend]): Int = 
    super.insert(s"""
       insert into 
       $table (
          uri,
          url,
          http_method,
          ip,
          method,
          args,
          timestamp
       )
       values (?, ?, ?, ?, ?, ?, ?)
       """, seq) { (ps, i) ⇒
       ps.setString(1, seq(i).getUri())
       ps.setString(2, seq(i).getUrl())
       ps.setString(3, seq(i).getHttp_method())
       ps.setString(4, seq(i).getIp())
       ps.setString(5, seq(i).getMethod())
       ps.setString(6, seq(i).getArgs())
       ps.setTimestamp(7, seq(i).getTimestamp)
    }
}