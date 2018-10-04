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

  def f_x: (java.sql.PreparedStatement, Int) => Unit = (ps: PreparedStatement, i: Int) => {

  }

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
       """, seq) { (ps, i) =>

      setParameter(classOf[JournalFrontend], ps, seq(i),
        "uri",
        "url",
        "http_method",
        "ip",
        "method",
        "args", "timestamp")
    }

}
