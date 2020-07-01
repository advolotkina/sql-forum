package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.NewComment
import com.advolotkina.sql.forum.dto.UpdateUser
import com.advolotkina.sql.forum.entities.ThemeEntity
import com.advolotkina.sql.forum.entities.UserEntity
import com.advolotkina.sql.forum.repository.TopicRepository
import com.advolotkina.sql.forum.repository.UserRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import com.advolotkina.sql.forum.response.SuccessfulSigninResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1.0/users")
class UsersController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<*>{
        val userCandidate: Optional<UserEntity> = userRepository.findById(id)
        if(userCandidate.isPresent){
            val user = userCandidate.get()
            return ResponseEntity.ok(UpdateUser(user.login, user.name, user.email, user.user_pic,
                                                user.registration_date))
        }
        return ResponseEntity(ResponseMessage("User not found"),
                HttpStatus.BAD_REQUEST)
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    fun updateUser(@PathVariable id: Int, @RequestBody updateUser: UpdateUser): ResponseEntity<*>{
        val userCandidate: Optional<UserEntity> = userRepository.findById(id)
        if(userCandidate.isPresent){
            try {
                val user = userCandidate.get()
                if(updateUser.login != null){
                    if(loginExists(updateUser.login!!)){
                        return ResponseEntity(ResponseMessage("Login exists"),
                                HttpStatus.BAD_REQUEST)
                    }
                    user.login = updateUser.login!!
                }
                if(updateUser.email != null){
                    if(emailExists(updateUser.email!!)){
                        return ResponseEntity(ResponseMessage("Email exists"),
                                HttpStatus.BAD_REQUEST)
                    }
                    user.email = updateUser.email!!
                }
                if(updateUser.name != null){
                    user.name = updateUser.name!!
                }
                if(updateUser.userPic != null){
                    user.user_pic = updateUser.userPic
                }
                userRepository.save(user)
                return ResponseEntity.ok(UpdateUser(user.login, user.name, user.email, user.user_pic,
                 user.registration_date))
            } catch (e: Exception) {
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }
        }
        return ResponseEntity(ResponseMessage("User not found"),
                HttpStatus.BAD_REQUEST)
    }

    private fun loginExists(login: String): Boolean {
        return userRepository.findByLogin(login).isPresent
    }
    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }
//    @PostMapping("/")
//    fun createUser() = userRepository.save()

//    @PostMapping("/{id}")
//    fun createTopic(@PathVariable id: Int) =
}