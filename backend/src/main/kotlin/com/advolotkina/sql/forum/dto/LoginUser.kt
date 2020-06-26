package com.advolotkina.sql.forum.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class LoginUser : Serializable {

    @JsonProperty("login")
    var login: String? = null

    @JsonProperty("password")
    var password: String? = null

    constructor() {}

    constructor(login: String, password: String) {
        this.login = login
        this.password = password
    }

    companion object {

        private const val serialVersionUID = -1764970284520387975L
    }
}