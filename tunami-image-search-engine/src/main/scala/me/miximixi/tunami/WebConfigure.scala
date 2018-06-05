package me.miximixi.tunami

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.thymeleaf.spring5.SpringTemplateEngine
import org.springframework.context.annotation.Bean
import org.thymeleaf.dialect.IDialect
import org.thymeleaf.dialect.AbstractDialect
import nz.net.ultraq.thymeleaf.LayoutDialect

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 5:26:38 PM
 * @Description
 */

@SpringBootApplication // @EnableConfigurationProperties(value = Array(classOf[PhantomjsConfig],classOf[DomainConfig]))
@ComponentScan(basePackages = Array("me.miximixi.tunami"))
class WebConfigure  
{

  @Bean
  def templateEngine(): SpringTemplateEngine = {
    val templateEngine = new SpringTemplateEngine()
    templateEngine.addDialect(new LayoutDialect())
    templateEngine
  }
  
} 

object Launcher extends App {
  
  SpringApplication.run(classOf[WebConfigure])
}