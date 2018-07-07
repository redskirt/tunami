package me.miximixi.tunami.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import me.miximixi.tunami.persistence.VshViewMapDao

/**
 * @Author Sasaki
 * @Mail redskirt@outlook.com
 * @Timestamp Jul 7, 2018 4:57:34 PM
 * @Description 
 */
@Service
class VshViewMapService {
  
  @Autowired
  var vshViewMapDao: VshViewMapDao = _
  
}