package me.miximixi.tunami.kit

import reflect.runtime.universe.TypeTag

import com.sasaki.packages.{ constant => cons }
import com.sasaki.packages.{ reflect => ref }

import me.miximixi.tunami.kit.JdbcTemplateHandler.mapRow
import me.miximixi.tunami.persistence.QueryProperty.mappingTable

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Sep 26, 2018 11:28:22 PM
 * @Description
 */
private[me] abstract class AbstractQueryDao[T: TypeTag] extends JdbcTemplateHandler with QueryFragmentHelper { self =>
   
  protected lazy val table = mappingTable[T]

  /**
   * 由 Java Reflect 获得泛型的类型 Class[T]
   */
  private lazy val clazz = ref.extractTypeClass[T](self)
  
  protected def count: Option[Int] =
    query(s"""$from_count $table""") { (rs, i) => rs.getInt(1) }.headOption

  protected def list: cons.JList[T] =
    queryJList(s"""${ from_* } $table""") { (rs, i) => buildBean(clazz, rs) }

  def list(limit: Tuple2[cons.JInt, cons.JInt]): cons.JList[T] = 
    queryJList(s"""${ from_* } $table ${ limit_? } """, limit._1, limit._2) { (rs, i) => buildBean(clazz, rs) }
  
//  protected def insert(t: T): Option[Int] = ??? 
//  
//  protected def update(t: T): Option[Int] = ???
//  
//  protected def delete(t: T): Option[Int] = ???

}

