package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.repository.TopicRepository
import com.advolotkina.sql.forum.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1.0/users")
class UsersController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int) = userRepository.findById(id)

//    @PostMapping("/")
//    fun createUser() = userRepository.save()

//    @PostMapping("/{id}")
//    fun createTopic(@PathVariable id: Int) =
}