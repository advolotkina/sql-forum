package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.GroupEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository: CrudRepository<GroupEntity, Int> {
}