package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.NewComment
import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.entities.CommentEntity
import com.advolotkina.sql.forum.entities.ThemeEntity
import com.advolotkina.sql.forum.entities.TopicEntity
import com.advolotkina.sql.forum.entities.UserEntity
import com.advolotkina.sql.forum.repository.CommentRepository
import com.advolotkina.sql.forum.repository.TopicRepository
import com.advolotkina.sql.forum.repository.UserRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1.0/themes/{themeId}/topics")
class TopicsController {
    @Autowired
    lateinit var topicRepository: TopicRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var commentRepository: CommentRepository

    @GetMapping("/{id}")
    fun getTopic(@PathVariable id: Int) = topicRepository.findById(id)

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    fun createComment(@PathVariable id: Int, @RequestBody newComment: NewComment): ResponseEntity<*>  {
        val topicCandidate: Optional<TopicEntity> = topicRepository.findById(id)
        if (topicCandidate.isPresent) {
            val userCandidate: Optional<UserEntity> = userRepository.findByLogin(newComment.userName)
            if(userCandidate.isPresent){
                try{
                    val comment = CommentEntity(
                            0,
                            comment_text = newComment.commentText,
                            topic = topicCandidate.get(),
                            commentAuthor = userCandidate.get()
                    )
                    commentRepository.save(comment)
                }catch (e: Exception) {
                    return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                            HttpStatus.SERVICE_UNAVAILABLE)
                }
            }else{
                return ResponseEntity(ResponseMessage("User doesn't exist"),
                        HttpStatus.BAD_REQUEST)
            }
            return ResponseEntity(ResponseMessage("Comment created"), HttpStatus.OK)
        }else{
            return ResponseEntity(ResponseMessage("Topic doesn't exist"),
                    HttpStatus.BAD_REQUEST)
        }
    }

}