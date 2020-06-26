package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.repository.CommentRepository
import com.advolotkina.sql.forum.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1.0/users/{userId}/comments")
class CommentsController {
    @Autowired
    lateinit var commentRepository: CommentRepository

//    @GetMapping("")
//    fun getComments() = commentRepository.findAllByCommentAuthor()
}