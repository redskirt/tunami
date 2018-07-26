package me.miximixi.tunami.kit

import com.sasaki.packages.constant._

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 8, 2018 12:59:36 PM
 * @Description 后端渲染分页，使用时仅需构造 Pagination 后调用 buildPaginateTag
 */
trait PaginationHandler {
  
  /**
      <ul>
        	<li class="footable-page-arrow"><a href="/map_list_1_10_10">«</a></li>
        	<li class="footable-page-arrow"><a href="/map_list_2_10_10">‹</a></li>
        	<li class="footable-page"><a href="/map_list_1_10_10">1</a></li>
        	<li class="footable-page active"}"><a href="#">...</a></li>
        	<li class="footable-page active"><a href="/map_list_3_10_10">3</a></li>
        	<li class="footable-page"><a href="/map_list_4_10_10">4</a></li>
        	<li class="footable-page"><a href="/map_list_5_10_10">5</a></li>
        	<li class="footable-page"><a href="/map_list_6_10_10">6</a></li>
        	<li class="footable-page"><a href="/map_list_7_10_10">7</a></li>
        	<li class="footable-page active"}"><a href="#">...</a></li>
        	<li class="footable-page"><a href="/map_list_10_10_10">10</a></li>
        	<li class="footable-page-arrow"><a href="/map_list_4_10_10">›</a></li>
        	<li class="footable-page-arrow"><a href="/map_list_10_10_10">»</a></li>
      </ul>
      
    		function postPagination(url) {
    			$("#form-pagination").attr("action", url);
    			$("#form-pagination").submit();
    		}
  */
  def buildPaginateTag(prefix: String, page: Pagination): String = {
    
    val to = (destnation: Int) => s"${ prefix }_${ destnation }_${ page.size }_${ page.total }"

    val first = to(1)
    val last = to(page.total)
    val previous = to(page.current - 1)
    val subsequent = to(page.current + 1)
    
    val href = (destnation: String) => s" href=$destnation"
    val onclick = (destnation: String) => s""" onclick="javascript:postPagination('$destnation');""""
    
    val builder = new StringBuilder
    builder
      .append("<ul>\n")
//      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a${ if(page.current > 1) s" href=$first" else "" }>«</a></li>\n""")
//      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a${ if(page.current > 1) s" href=$previous" else "" }>‹</a></li>\n""")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a${ if(page.current > 1) onclick(first) else "" }>«</a></li>\n""")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current <= 1) " disabled" else "" }"><a${ if(page.current > 1) onclick(previous) else "" }>‹</a></li>\n""")
      .append({
//        val str_i = (current: Int, i: Int) => s"""\t<li class="footable-page${if (page.current == i) " active" else ""}"><a${ if(page.current != i) s" href=${to(i)}" else "" }>$i</a></li>\n"""
      		val str_i = (current: Int, i: Int) => s"""\t<li class="footable-page${if (page.current == i) " active" else ""}"><a${ if(page.current != i) onclick(to(i)) else "" }>$i</a></li>\n"""
      		val str_~ = s"""\t<li class="footable-page active"><a>...</a></li>\n"""
        
        var j = page.current

        for (i <- 1 to page.total) yield {
          if (i == 1)
            str_i(page.current, i)
          else if ((i >= j && j < page.span + page.current) /*游标前部*/ ||
            (page.total - i < page.span && j - page.current < page.span)) /*游标后部*/ {
            j += 1
            str_i(page.current, i)
          } else if (i == page.total)
            str_i(page.current, i)
          else {
            if (i == 2 && page.current >= 2 && page.count > page.span)
              str_~
            else if (i == j && page.count > page.span)
              str_~
            else
              ""
          }
        }
      } mkString)
      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a${ if(page.current < page.total) onclick(subsequent) else "" }>›</a></li>\n""")
      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a${ if(page.current < page.total) onclick(last) else "" }>»</a></li>\n""")
//      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a${ if(page.current < page.total) s" href=$subsequent" else "" }>›</a></li>\n""")
//      .append(s"""\t<li class="footable-page-arrow${ if(page.current == page.total) " disabled" else "" }"><a${ if(page.current < page.total) s" href=$last" else "" }>»</a></li>\n""")
      .append("</ul>")
    
    builder toString
  }
  
  final class Pagination(val $count: Int, val $current: Int = 0, val $size: Int = 0, val $total: Int = 0, val $span: Int = 5) {

    lazy val count = $count
    lazy val current: Int = if (0 == $current) 1 else $current
    lazy val size: Int = if (0 == $size) 15 /*default size*/ else $size
    lazy val total: Int =
      if (0 == $total)
        if (0 == count % size)
          count / size
        else
          count / size + 1
      else
        $total
    lazy val span: Int = $span
    lazy val from: Int = (current - 1) * size
    lazy val to: Int = size
    lazy val limit: Tuple2[JInt, JInt] = (from, to)
    
  }
}

//object Main extends PaginationHandler {
//
//  def main(args: Array[String]): Unit = {
//    val page = new Pagination(100, 4, 10, 0)
//    println(buildPaginateTag("/media/map_list", page))
//  }
//}
