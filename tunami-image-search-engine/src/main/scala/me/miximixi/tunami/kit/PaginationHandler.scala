package me.miximixi.tunami.kit

import com.sasaki.packages.constant._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 8, 2018 12:59:36 PM
 * @Description 
 */
trait PaginationHandler {

  
  //  <ul>
  // 		<li class="footable-page-arrow disabled"><a data-page="first" href="#first">«</a></li>
  // 		<li class="footable-page-arrow disabled"><a data-page="prev" href="#prev">‹</a></li>
  // 		<li class="footable-page active"><a data-page="0" href="#">1</a></li>
  // 		<li class="footable-page"><a data-page="1" href="#">2</a></li>
  // 		<li class="footable-page-arrow"><a data-page="next" href="#next">›</a></li>
  // 		<li class="footable-page-arrow"><a data-page="last" href="#last">»</a></li>
  // 	</ul>
  
  def buildPaginateTag(prefix: String, page: Pagination): String = {
    
    val to = (destnation: Int) => s"${ prefix }_${ destnation }_${ page.size }_${ page.total }"
    
    val first = to(1)
    val last = to(page.total)
    val previous = to(page.current - 1)
    val subsequent = to(page.current + 1)
    
    val builder = new StringBuilder
    builder
      .append("<ul>\n")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a href="${ if(page.current <= 1) "#" else first }">«</a></li>\n""")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a href="${ if(page.current <= 1) "#" else previous }">‹</a></li>\n""")
      .append({
        for (i <- 1 to page.total) yield //
          s"""\t<li class="footable-page${ if (page.current == i) " active" else "" }"><a href="${ to(i) }">$i</a></li>\n"""
      } mkString)
      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a href="${ if(page.current == page.total) "#" else subsequent }">›</a></li>\n""")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a href="${ if(page.current == page.total) "#" else last }">»</a></li>\n""")
      .append("</ul>")
    
    builder toString
  }
  
  final class Pagination(val $count: Int, val $current: Int = 0, val $size: Int = 0, val $total: Int = 0) {

    var count = $count
    var current: Int = if (0 == $current) 1 else $current
    var size: Int = if (0 == $size) 15 /*default size*/ else $size
    var total: Int =
      if (0 == $total)
        if (0 == count % size)
          count / size
        else
          count / size + 1
      else
        $total
    var from: Int = (current - 1) * size
    var to: Int = size
    var limit: Tuple2[JInt, JInt] = (from, to)
  }
}

object Main extends PaginationHandler {
  
  def main(args: Array[String]): Unit = {
    val page = new Pagination(31, 4, 10, 0)
    println(buildPaginateTag("/map_list", page))
  }
}
