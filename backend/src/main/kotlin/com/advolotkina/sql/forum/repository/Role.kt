package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.RoleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: CrudRepository<RoleEntity, Int> {

}