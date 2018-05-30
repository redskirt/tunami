package me.miximixi.tunami

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.SpringApplication

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 5:26:38 PM
 * @Description
 */

@SpringBootApplication // @EnableConfigurationProperties(value = Array(classOf[PhantomjsConfig],classOf[DomainConfig]))
@ComponentScan(basePackages = Array("me.miximixi.tunami"))
class Launcher

object Launcher extends App {
  
  SpringApplication.run(classOf[Launcher])
}