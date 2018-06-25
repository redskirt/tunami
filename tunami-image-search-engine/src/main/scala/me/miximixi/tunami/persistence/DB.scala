package me.miximixi.tunami.persistence


/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jun 18, 2018 11:15:36 PM
 * @Description 
 */
trait DB {
    
  val and_? = " and ? != ''"
  
  val attr_principal = "attr_principal"
  val attr_metadata = "attr_metadata"
  val attr_image_content = "attr_image_content"
  val attr_vsh_view = "attr_vsh_view"
  val attr_vsh_view_map = "attr_vsh_view_map"
  
}