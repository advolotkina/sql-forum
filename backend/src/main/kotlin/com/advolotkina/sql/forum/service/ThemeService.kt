package com.advolotkina.sql.forum.service

import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.entities.ThemeEntity
import java.util.*

interface ThemeService {
    fun findAll(): List<ThemeEntity>

    fun findById(id: Int): ThemeEntity

    fun create(request: SaveThemeRequest)

    fun update(id: Int, request: SaveThemeRequest)

    fun delete(id: Int)
}