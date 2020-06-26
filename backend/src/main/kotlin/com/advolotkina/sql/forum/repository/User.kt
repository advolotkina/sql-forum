package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<UserEntity, Int> {

}