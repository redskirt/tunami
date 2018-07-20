package me.miximixi.tunami

import org.springframework.boot.SpringApplication

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 20, 2018 8:14:12 AM
 * @Description 
 */
class Launcher { /*必需，防止打包异常*/  } 

object Launcher extends App {
  
  SpringApplication.run(classOf[WebConfigure])
}