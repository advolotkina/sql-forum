package com.advolotkina.sql.forum.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ThemeNotFoundException(id: Int): RuntimeException("Theme with id=$id not found")