package me.miximixi.tunami.compoment.aop

import org.aspectj.lang.{ JoinPoint, ProceedingJoinPoint }
import org.aspectj.lang.annotation._
import org.springframework.stereotype.Component
import org.springframework.web.context.request.{ RequestContextHolder, ServletRequestAttributes }
import org.slf4j.LoggerFactory
import me.miximixi.tunami.poso.JournalFrontend
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.persistence.JournalFrontendDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Oct 2, 2018 6:27:54 PM
 * @Description AOP 日志
 * @see 示例参考 https://www.cnblogs.com/bigben0123/p/7779357.html
 */
@Aspect
@Component
class JournalAspectComponent {

  import JournalAspectComponent._
  
  @Autowired
  var journalFrontendDao: JournalFrontendDao = _
  
  @Pointcut($poingcut)
  def journal() {}

  @Before($pointcut_method)
  def before(point: JoinPoint) = {

    val attributes = RequestContextHolder.getRequestAttributes.asInstanceOf[ServletRequestAttributes]
    val request = attributes.getRequest
    val uri = request.getRequestURI
    val url = request.getRequestURL.toString
    val http_method = request.getMethod
    val ip = request.getRemoteAddr
    val method = point.getSignature.getDeclaringTypeName + "." + point.getSignature.getName
    val args = java.util.Arrays.toString(point.getArgs)
//    println(uri)         // /
//    println(url)         // http://localhost/
//    println(ip)          // 127.0.0.1
//    println(method)      // me.miximixi.tunami.controller.frontend.FrontendController.$div
//    println(args)        // [{}]
    
    val o = new JournalFrontend
    o.setUri(uri)
    o.setUrl(url)
    o.setHttp_method(http_method)
    o.setIp(ip)
    o.setMethod(method)
    o.setArgs(args)
    journalFrontendDao.insert(o)
  }

  /**
   * 处理完请求，返回内容
   * 方法返回结果值
   */
//  @AfterReturning(returning = "returning", pointcut = $pointcut_method)
//  def afterReturning(returning: Object) = println("after returning the method: " + returning)

  /**
   * 后置异常通知
   */
//  @AfterThrowing(pointcut = "journal()", throwing = "e")
//  def afterThrowing(point: JoinPoint, e: Exception) = {
//    println(e.getMessage)
//    println(e.getStackTrace)
//    println("afterThrowing.")
//  }

  /**
   * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
   */
//  @After($pointcut_method)
//  def after(point: JoinPoint) = println("after the method: " + $pointcut_method)

  /**
   * 环绕通知,环绕增强，相当于MethodInterceptor
   */
//  @Around($pointcut_method)
//  def arround(point: ProceedingJoinPoint): Object = {
//    val o = point.proceed()
//    println("arround the method: " + $pointcut_method + ", " + o)
//    o
//  }
}

object JournalAspectComponent {
  
  final val logger = LoggerFactory.getLogger(classOf[JournalAspectComponent]); 
  final val $poingcut = "execution(public * me.miximixi.tunami.controller.frontend.*.*(..))"
  final val $pointcut_method = "journal()"
}