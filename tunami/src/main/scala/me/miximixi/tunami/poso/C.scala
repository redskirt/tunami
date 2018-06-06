package me.miximixi.tunami.poso

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 2, 2018 4:23:56 PM
 * @Description Constant
 */
object C {
  def main(args: Array[String]): Unit = {
    val process = Runtime.getRuntime().exec("ps -def | grep tomcat | grep -v grep | awk '{print $2}'")
    val reader = new BufferedReader(new InputStreamReader(process.getInputStream))
    var temp: String = null
    
    while ({
      temp = reader.readLine()
      temp != null
    })
      println(new String(temp.getBytes))
    
  }
  
}