package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.TopicEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository: CrudRepository<TopicEntity, Int> {

}