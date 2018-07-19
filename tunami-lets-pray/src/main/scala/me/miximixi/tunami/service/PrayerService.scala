package me.miximixi.tunami.service

import me.miximixi.tunami.persistence.PrayerDao
import me.miximixi.tunami.poso.PrayerDTO
import com.sasaki.packages.independent._
import java.text.SimpleDateFormat

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 14, 2018 5:55:25 PM
 * @Description 
 */
@org.springframework.stereotype.Service
class PrayerService {
  
  @org.springframework.beans.factory.annotation.Autowired
  private var prayerDao: PrayerDao = _
  
  def bizBuildPrayerDTO(minId: com.sasaki.packages.constant.JInt): List[PrayerDTO] = {
    val list = prayerDao.list(minId).map { o =>
      PrayerDTO(
          o.getId, 
          o.getContent, 
          o.getSee, 
          s"${ new SimpleDateFormat(TimePattern.PATTERN_DATE.toString()).format(o.timestamp) } 来自${o.location}的${o.genderInfo}为${o.target}祷告")
    }
    prayerDao.update(list.map(o => o.id.asInstanceOf[Object]).toArray)
    list
  }
}