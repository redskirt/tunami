package me.miximixi.tunami.poso

import scala.beans.BeanProperty
import com.sasaki.packages.constant._
import com.sasaki.chain.ScalaEntity

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:25:12 PM
 * @Description 业务模式 Bean
 */

class Metadata extends PrimaryBean {

  @BeanProperty // -> image_repo
  var imageRepo: String = _
}

class Principal extends PrimaryBean {

  @BeanProperty
  var account_name: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var `type`: String = _
}

/**
 * 今日福音
 */
class Gospel extends PrimaryBean {

  def this(content: String, date: java.sql.Date, chapter: String) = {
    this()
    setContent(content)
    setDate(date)
    setChapter(chapter)
  }

  @BeanProperty
  var content: String = _

  @BeanProperty
  var date: java.sql.Date = _
  //  var date: JDate = _

  @BeanProperty
  var chapter: String = _

  @BeanProperty
  @transient
  lazy val chapterInfo: String = {
    val o = this.chapter.split('|')
    s"${o(0)}，${o(1)}章${o(2)}节"
  }
}

class Prayer extends PrimaryBean {

  @BeanProperty
  var content: String = _

  @BeanProperty
  var location: String = _

  @BeanProperty
  var gender: String = _ // 0: female, 1: male

  @BeanProperty
  var target: String = _

  //  @BeanProperty
  //  var relation: String = _

  @BeanProperty
  var see: JInt = 0

  @BeanProperty
  var digg: JInt = 0

  @transient
  lazy val genderInfo = if ("0" == gender) "姊妹" else "弟兄"
}

/**
 * 神谕
 */
class Prophet extends PrimaryBean {

  @BeanProperty
  var category: String = _

  @BeanProperty
  var content: String = _

  @BeanProperty
  var chapter: String = _

  @BeanProperty
  var see: JInt = 0

  @transient
  lazy val chapterO: Tuple3[String, Int, Int] =
    if (chapter == null)
      ("", 0, 0)
    else {
      val o = chapter.split('|')
      (o(0), o(1) toInt, o(2) toInt)
    }
}

class Anthem extends PrimaryBean {
  @BeanProperty
  var name: String = _

  @BeanProperty
  var artist: String = _

  @BeanProperty
  var order: JInt = _
}

final object BibleChapter {

  val o_1 = ("创", "创世记")
  val o_2 = ("出", "出埃及记")
  val o_3 = ("利", "利未记")
  val o_4 = ("民", "民数记")
  val o_5 = ("申", "申命记")
  val o_6 = ("书", "约书亚记")
  val o_7 = ("士", "士师记")
  val o_8 = ("得", "路得记")
  val o_9 = ("撒上", "撒母耳记上")
  val o_10 = ("撒下", "撒母耳记下")
  val o_11 = ("王上", "列王纪上")
  val o_12 = ("王下", "列王纪下")
  val o_13 = ("代上", "历代志上")
  val o_14 = ("代下", "历代志下")
  val o_15 = ("拉", "以斯拉记")
  val o_16 = ("尼", "尼希米记")
  val o_17 = ("斯", "以斯帖记")
  val o_18 = ("伯", "约伯记")
  val o_19 = ("诗", "诗篇")
  val o_20 = ("箴", "箴言")
  val o_21 = ("传", "传道书")
  val o_22 = ("歌", "雅歌")
  val o_23 = ("赛", "以赛亚书")
  val o_24 = ("耶", "耶利米书")
  val o_25 = ("哀", "耶利米哀歌")
  val o_26 = ("结", "以西结书")
  val o_27 = ("但", "但以理书")
  val o_28 = ("何", "何西阿书")
  val o_29 = ("珥", "约珥书")
  val o_30 = ("摩", "阿摩司书")
  val o_31 = ("俄", "俄巴底亚书")
  val o_32 = ("拿", "约拿书")
  val o_33 = ("弥", "弥迦书")
  val o_34 = ("鸿", "那鸿书")
  val o_36 = ("哈", "哈巴谷书")
  val o_37 = ("番", "西番雅书")
  val o_38 = ("该", "哈该书")
  val o_39 = ("亚", "撒迦利亚书")
  val o_40 = ("玛", "玛拉基书")

  val n_1 = ("太", "马太福音")
  val n_2 = ("可", "马可福音")
  val n_3 = ("路", "路加福音")
  val n_4 = ("约", "约翰福音")
  val n_5 = ("徒", "使徒行传")
  val n_6 = ("罗", "罗马书")
  val n_7 = ("林前", "哥林多前书")
  val n_8 = ("林后", "哥林多后书")
  val n_9 = ("加", "加拉太书")
  val n_10 = ("弗", "以弗所书")
  val n_11 = ("腓", "腓利比书")
  val n_12 = ("西", "歌罗西书")
  val n_13 = ("帖前", "帖撒罗尼迦前书")
  val n_14 = ("帖后", "帖撒罗尼迦后书")
  val n_15 = ("提前", "提摩太前书")
  val n_16 = ("提后", "提摩太后书")
  val n_17 = ("多", "提多书")
  val n_18 = ("门", "腓利门书")
  val n_19 = ("来", "希伯来书")
  val n_20 = ("雅", "雅各书")
  val n_21 = ("彼前", "彼得前书")
  val n_22 = ("彼后", "彼得后书")
  val n_23 = ("约一", "约翰壹书")
  val n_24 = ("约二", "约翰贰书")
  val n_25 = ("约三", "约翰参书")
  val n_26 = ("犹", "犹大书")
  val n_27 = ("启", "启示录")

  def short2fullName(short: String) =
    short match {
      case o_1._1  => o_1._2
      case o_2._1  => o_2._2
      case o_3._1  => o_3._2
      case o_4._1  => o_4._2
      case o_5._1  => o_5._2
      case o_6._1  => o_6._2
      case o_7._1  => o_7._2
      case o_8._1  => o_8._2
      case o_9._1  => o_9._2
      case o_10._1 => o_10._2
      case o_11._1 => o_11._2
      case o_12._1 => o_12._2
      case o_13._1 => o_13._2
      case o_14._1 => o_14._2
      case o_15._1 => o_15._2
      case o_16._1 => o_16._2
      case o_17._1 => o_17._2
      case o_18._1 => o_18._2
      case o_19._1 => o_19._2
      case o_20._1 => o_20._2
      case o_21._1 => o_21._2
      case o_22._1 => o_22._2
      case o_23._1 => o_23._2
      case o_24._1 => o_24._2
      case o_25._1 => o_25._2
      case o_26._1 => o_26._2
      case o_27._1 => o_27._2
      case o_28._1 => o_28._2
      case o_29._1 => o_29._2
      case o_30._1 => o_30._2
      case o_31._1 => o_31._2
      case o_32._1 => o_32._2
      case o_33._1 => o_33._2
      case o_34._1 => o_34._2
      case o_36._1 => o_36._2
      case o_37._1 => o_37._2
      case o_38._1 => o_38._2
      case o_39._1 => o_39._2
      case o_40._1 => o_40._2
      case n_1._1  => n_1._2
      case n_2._1  => n_2._2
      case n_3._1  => n_3._2
      case n_4._1  => n_4._2
      case n_5._1  => n_5._2
      case n_6._1  => n_6._2
      case n_7._1  => n_7._2
      case n_8._1  => n_8._2
      case n_9._1  => n_9._2
      case n_10._1 => n_10._2
      case n_11._1 => n_11._2
      case n_12._1 => n_12._2
      case n_13._1 => n_13._2
      case n_14._1 => n_14._2
      case n_15._1 => n_15._2
      case n_16._1 => n_16._2
      case n_17._1 => n_17._2
      case n_18._1 => n_18._2
      case n_19._1 => n_19._2
      case n_20._1 => n_20._2
      case n_21._1 => n_21._2
      case n_22._1 => n_22._2
      case n_23._1 => n_23._2
      case n_24._1 => n_24._2
      case n_25._1 => n_25._2
      case n_26._1 => n_26._2
      case n_27._1 => n_27._2
      case _       => ???
    }
}

final object Bible extends Enumeration {
  type Bible = Value
  val //
  创世记, //
  出埃及记, //
  利未记, //
  民数记, //
  申命记, //
  约书亚记, //
  士师记, //
  路得记, //
  撒母耳记上, //
  撒母耳记下, //
  列王记上, //
  列王记下, //
  历代志上, //
  历代志下, //
  以斯拉记, //
  尼希米记, //
  以斯帖记, //
  约伯记, //
  诗篇, //
  箴言, //
  传道书, //
  雅歌, //
  以赛亚书, //
  耶利米书, //
  耶利米哀歌, //
  以西结书, //
  但以理书, //
  何西阿书, //
  约珥书, //
  阿摩司书, //
  俄巴底亚书, //
  约拿书, //
  弥迦书, //
  那鸿书, //
  哈巴谷书, //
  西番雅书, //
  哈该书, //
  撒迦利亚书, //
  玛拉基书, //
  马太福音, //
  马可福音, //
  路加福音, //
  约翰福音, //
  使徒行传, //
  罗马书, //
  哥林多前书, //
  哥林多后书, //
  加拉太书, //
  以弗所书, //
  腓立比书, //
  歌罗西书, //
  帖撒罗尼迦前书, //
  帖撒罗尼迦后书, //
  提摩太前书, //
  提摩太后书, //
  提多书, //
  腓利门书, //
  希伯来书, //
  雅各书, //
  彼得前书, //
  彼得后书, //
  约翰壹书, //
  约翰贰书, //
  约翰叁书, //
  犹大书, //
  启示录 = Value //

  def name(o: Bible.Value) = o.toString()
}

@transient
case class O(@transient id: Int) {
  @BeanProperty
  @transient
  @throws
  var attr1: String = _
  @transient
  var attr2: String = _
}

object APP extends App {

  //  println(Bible.name(Bible.创世记))
  //  println(Bible.创世记.id)

  import com.sasaki.packages.{ reflect => ref, constant => cons }

  import scala.reflect.runtime.universe._

  val tpe: Type = typeOf[O]
  //    val symbol: Symbol = tpe.decl(TermName("id_ ")) //获取字段符号信息
  //    val annotation: Annotation = symbol.annotations.head
  //    val tree: Tree = annotation.tree
  // println(showRaw(tree)) //打印语法树
  //println(symbol.annotations(0).tree.tpe =:= typeOf[BeanProperty] )
  //  val Apply(_, Literal(Constant(name: String)) :: Literal(Constant(num: Int)) :: Nil) = tree
  //  println(s"Annotation args: name -> $name, num -> $num")

  //println(ref.existsAnnotationFromField[O, transient](classOf[O], "id"))
  //println(ref.extractField2Annotations[Anthem])
  //println(ref.existsAnnotationFromField[AA, BeanProperty]("id"))

  //  val g = new Gospel("亚伯拉罕的后裔，大卫的子孙，耶稣基督...", null, "马太福音|1|1")
  //  println(g.chapterInfo)

  //  import scala.tools.reflect.ToolBox
  //
  //  val o = new Gospel
  //  o.setChapter("x$1wqer")
  //
  //  //  val tb = scala.reflect.runtime.currentMirror.mkToolBox()
  //  val tb = scala.reflect.runtime.universe.runtimeMirror(getClass.getClassLoader).mkToolBox()
  //  //  val a = tb.eval(q"me.miximixi.tunami.poso.APP.o.getChapter").asInstanceOf[String]
  //  //  println(a)
  //
  //  val tree = tb.parse(s"""
  //    import me.miximixi.tunami.poso.APP.o
  //    o.getChapter
  //    """.stripMargin)
  //
  //  val result = tb.eval(tree).asInstanceOf[String]
  //  println(result)

}

