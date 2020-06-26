package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.CommentEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: CrudRepository<CommentEntity, Int> {

}