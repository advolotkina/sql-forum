package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.ThemeEntity
import com.advolotkina.sql.forum.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<UserEntity, Int> {
    fun findByLogin(@Param("login") login: String): Optional<UserEntity>

    fun findByEmail(@Param("email") email: String): Optional<UserEntity>
}