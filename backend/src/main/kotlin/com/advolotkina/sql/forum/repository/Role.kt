package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.RoleEntity
import com.advolotkina.sql.forum.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: CrudRepository<RoleEntity, Int> {
    fun findByName(@Param("name") name: String): Optional<RoleEntity>
}