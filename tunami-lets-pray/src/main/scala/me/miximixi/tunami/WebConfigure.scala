package me.miximixi.tunami

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 5:26:38 PM
 * @Description
 */

@SpringBootApplication // @EnableConfigurationProperties(value = Array(classOf[PhantomjsConfig],classOf[DomainConfig]))
@ComponentScan(basePackages = Array("me.miximixi.tunami"))
class WebConfigure 

//extends WebMvcConfigurerAdapter {
//  
//  override def addResourceHandlers(registry: ResourceHandlerRegistry) {
//    registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/")
//  }
//} 

