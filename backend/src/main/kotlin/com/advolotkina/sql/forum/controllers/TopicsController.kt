package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1.0/themes/{themeId}/topics")
class TopicsController {
    @Autowired
    lateinit var topicRepository: TopicRepository

    @GetMapping("/{id}")
    fun getTopic(@PathVariable id: Int) = topicRepository.findById(id)

//    @PostMapping("/{id}")
//    fun createTopic(@PathVariable id: Int) =
}