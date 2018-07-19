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
import me.miximixi.tunami.poso.Anthem


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 17, 2018 8:41:16 PM
 * @Description
 */
@Repository
class AnthemDao extends JdbcTemplateHandler with DB {

  def list: JList[Anthem] = 
    queryJList(s"""select `name`, artist, `order` from $attr_anthem order by `order`""") { (rs, i) =>
      val o = new Anthem
      o.setName(rs.getString(1))
      o.setArtist(rs.getString(2))
      o.setOrder(rs.getInt(3))
      o
    }

}