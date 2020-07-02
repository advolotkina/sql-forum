package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.NewComment
import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.dto.UpdateComment
import com.advolotkina.sql.forum.entities.CommentEntity
import com.advolotkina.sql.forum.entities.TopicEntity
import com.advolotkina.sql.forum.entities.UserEntity
import com.advolotkina.sql.forum.repository.CommentRepository
import com.advolotkina.sql.forum.repository.TopicRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import com.advolotkina.sql.forum.response.SuccessfulSigninResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.util.*

@RestController
@RequestMapping("/api/v1.0/comments")
class CommentsController {
    @Autowired
    lateinit var commentRepository: CommentRepository

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun updateComment(@PathVariable id: Int, @RequestBody updateComment: UpdateComment): ResponseEntity<*> {
        val commentCandidate: Optional<CommentEntity> = commentRepository.findById(id)
        if(commentCandidate.isPresent){
            var comment = commentCandidate.get()
            try {
                comment.commentText = updateComment.commentText
                comment.isModified = true
                comment.modificationDate = Timestamp(System.currentTimeMillis())
                commentRepository.save(comment)
            } catch (e: Exception){
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }
            return ResponseEntity.ok(UpdateComment(comment.commentText))
        }
        return ResponseEntity(ResponseMessage("Comment doesn't exist"),
                HttpStatus.BAD_REQUEST)
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun deleteComment(@PathVariable id: Int):ResponseEntity<*>{
        val commentCandidate: Optional<CommentEntity> = commentRepository.findById(id)
        if(commentCandidate.isPresent){
            val comment = commentCandidate.get()
            try {
                commentRepository.delete(comment)
            } catch (e: Exception){
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }
            return ResponseEntity(ResponseMessage("Comment deleted"), HttpStatus.OK)
        }
        return ResponseEntity(ResponseMessage("Comment doesn't exist"),
                HttpStatus.BAD_REQUEST)
    }
}