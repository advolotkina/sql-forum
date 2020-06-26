package com.advolotkina.sql.forum.dto

import com.advolotkina.sql.forum.entities.GroupEntity

data class SaveThemeRequest (
        val name: String,
        var groupId: Int
)