package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.repository.CommentRepository
import com.advolotkina.sql.forum.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1.0/themes/{themeId}/topics/{topicId}/comments")
class CommentsController {
    @Autowired
    lateinit var commentRepository: CommentRepository

//    @GetMapping("")
//    fun getComments() = commentRepository.findAllByCommentAuthor()

    @PostMapping("")
    fun createComment(@RequestBody newTheme: SaveThemeRequest){

    }
}