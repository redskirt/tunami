package me.miximixi.tunami.persistence

import org.springframework.stereotype.Repository

import com.sasaki.packages.constant.JList

import me.miximixi.tunami.kit.AbstractQueryDao
import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.poso.Anthem


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 17, 2018 8:41:16 PM
 * @Description
 */
@Repository
class AnthemDao extends AbstractQueryDao[Anthem] {

  override def list: JList[Anthem] = 
    queryJList(s"""select `name`, artist, `order` from $table order by `order`""") { (rs, i) => buildBean(classOf[Anthem], rs) }

}