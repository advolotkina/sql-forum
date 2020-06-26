package com.advolotkina.sql.forum.repository

import com.advolotkina.sql.forum.entities.ThemeEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ThemeRepository: CrudRepository<ThemeEntity, Int> {
    fun findByName(@Param("name") name: String): Optional<ThemeEntity>
//    fun findAllByOrderByName(): List<ThemeEntity>
//
//    fun getById(id: Int): ThemeEntity?
}