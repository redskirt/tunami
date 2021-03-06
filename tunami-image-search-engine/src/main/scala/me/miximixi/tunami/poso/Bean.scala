package me.miximixi.tunami.poso

import scala.beans.BeanProperty

import org.springframework.context.annotation.Bean

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp May 30, 2018 4:25:12 PM
 * @Description
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

class ImageContent extends PrimaryBean {

  @BeanProperty
  var dir: String = _

  @BeanProperty
  var file_name: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var descript: String = _

  @BeanProperty
  var city: String = _

  @BeanProperty
  var `type`: String = _

}

class VshViewMap extends PrimaryBean {
  
  @BeanProperty
  var remark: String = _
  
  @BeanProperty
  var retitle: String = _
  
  @BeanProperty
  var image_name: String = _
  
  @BeanProperty
  var image_id: String = _
  
  @BeanProperty
  var city: String = _
  
  @BeanProperty
  var page_id: String = _
  
  @BeanProperty
  var original_title: String = _
  
  @BeanProperty
  var transliteration: String = _
  
  @BeanProperty
  var alternative_original_title: String = _
  
  @BeanProperty
  var collection: String = _
  
  @BeanProperty
  var digtized_file: String = _
  
  @BeanProperty
  var map_type: String = _
  
  @BeanProperty
  var authors: String = _
  
  @BeanProperty
  var year: String = _
  
  @BeanProperty
  var size: String = _
  
  @BeanProperty
  var map_support: String = _
  
  @BeanProperty
  var place_of_publication: String = _
  
  @BeanProperty
  var repository: String = _
  
  @BeanProperty
  var publishers: String = _
  
  @BeanProperty
  var source: String = _
  
}

class VshView extends PrimaryBean {
  
  @BeanProperty
  var remark: String = _
  
  @BeanProperty
  var retitle: String = _
  
  @BeanProperty
  var page_id: String = _

  @BeanProperty
  var image_id: String = _

  @BeanProperty
  var image_name: String = _

  @BeanProperty
  var city: String = _

  @BeanProperty
  var title: String = _

  @BeanProperty
  var collection: String = _

  @BeanProperty
  var location: String = _

  @BeanProperty
  var extent: String = _

  @BeanProperty
  var year: String = _

  @BeanProperty
  var date: String = _

  @BeanProperty
  var photographer: String = _

  @BeanProperty
  var estimated_date: String = _

  @BeanProperty
  var image_type: String = _

  @BeanProperty
  var material_form_of_image: String = _

  @BeanProperty
  var private_repository: String = _

  @BeanProperty
  var notes: String = _

  @BeanProperty
  var keywords_en: String = _

  @BeanProperty
  var keywords_fr: String = _

  @BeanProperty
  var street_name: String = _

  @BeanProperty
  var repository: String = _

  @BeanProperty
  var building: String = _

  @BeanProperty
  var related_image: String = _
  
  @BeanProperty
  var source: String = _
  
  @BeanProperty
  var indentifier: String = _
  
  @BeanProperty
  var copyright: String = _
  
  @BeanProperty
  var dimensions: String = _
  
  @BeanProperty
  var media: String = _
  
  @BeanProperty
  var tags: String = _
}

class Bristol extends PrimaryBean {
  
  @BeanProperty
  var original_image_name: String = _
  
  @BeanProperty
  var title: String = _
  
  @BeanProperty
  var collection: String = _
  
  @BeanProperty
  var estimated_date: String = _
  
  @BeanProperty
  var identifier: String = _
  
  @BeanProperty
  var copyright: String = _
  
  @BeanProperty
  var media: String = _
  
  @BeanProperty
  var tag: String = _
  
  @BeanProperty
  var note: String = _
  
  @BeanProperty
  var remark: String = _
}

class Joseph extends PrimaryBean {

  @BeanProperty
  var title: String = _

  @BeanProperty
  var location: String = _

  @BeanProperty
  var date: String = _

  @BeanProperty
  var original_caption_by_joseph_needham: String = _

  @BeanProperty
  var photographer: String = _

  @BeanProperty
  var classmark: String = _
  
  @BeanProperty
  var remark: String = _

}

class HarvardYenching extends PrimaryBean {

  @BeanProperty
  var title: String = _

  @BeanProperty
  var author_or_creator: String = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  var notes: String = _

  @BeanProperty
  var related_work: String = _

  @BeanProperty
  var use_restrictions: String = _

  @BeanProperty
  var create_date: String = _

  @BeanProperty
  var hollis_number: String = _

  @BeanProperty
  var permalink: String = _

  @BeanProperty
  var source: String = _
}

class Yenching extends PrimaryBean {
  
  @BeanProperty
  var page: Int = _
  
  @BeanProperty
  var work_id: Int = _
  
  @BeanProperty
  var source_id: Int = _
  
  @BeanProperty
  var image_name: String = _
  
  @BeanProperty
  var title: String = _
  
  @BeanProperty
  var author_or_creator: String = _
  
  @BeanProperty
  var description: String = _
  
  @BeanProperty
  var dimensions: String = _
  
  @BeanProperty
  var notes: String = _
  
  @BeanProperty
  var creation_date: String = _
  
  @BeanProperty
  var repository: String = _
  
  @BeanProperty
  var permalink: String = _
  
  @BeanProperty
  var remark: String = _
  
}

class WeiChat extends PrimaryBean {

  @BeanProperty
  var original_title: String = _
  
  @BeanProperty
  var image_name: String = _
  
  @BeanProperty
  var source: String = _
  
  @BeanProperty
  var remark: String = _
  
}
