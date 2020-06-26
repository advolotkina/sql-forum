package com.advolotkina.sql.forum.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class NewUser : Serializable {

    @JsonProperty("login")
    var login: String? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("password")
    var password: String? = null


    constructor() {}

    constructor(login: String, name: String, email: String, password: String) {
        this.login = login
        this.name = name
        this.email = email
        this.password = password
    }

    companion object {

        private const val serialVersionUID = -1764970284520387975L
    }
}