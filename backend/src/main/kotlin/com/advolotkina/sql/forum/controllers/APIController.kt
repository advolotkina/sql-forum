package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1.0/")
class APIController {
    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @GetMapping("/groups")
    fun getGroups() = groupRepository.findAll()


    @GetMapping("/roles")
    fun getRoles() = roleRepository.findAll()


}