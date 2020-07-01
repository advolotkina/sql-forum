package com.advolotkina.sql.forum.dto

import java.sql.Timestamp

data class UpdateUser(
        var login: String?,
        var name: String?,
        var email: String?,
        var userPic: String?,
        val registrationDate: Timestamp?
)