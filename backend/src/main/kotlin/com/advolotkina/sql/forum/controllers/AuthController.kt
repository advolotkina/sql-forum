package com.advolotkina.sql.forum.controllers


import com.advolotkina.sql.forum.dto.LoginUser
import com.advolotkina.sql.forum.dto.NewUser
import com.advolotkina.sql.forum.entities.UserEntity
import com.advolotkina.sql.forum.jwt.JwtProvider
import com.advolotkina.sql.forum.repository.RoleRepository
import com.advolotkina.sql.forum.repository.UserRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import com.advolotkina.sql.forum.response.SuccessfulSigninResponse
import java.util.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["http://localhost:8888", "http://localhost:8081"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/auth")
class AuthController() {

    @Value("\${authCookieName}")
    lateinit var authCookieName: String

    @Value("\${isCookieSecure}")
    var isCookieSecure: Boolean = true

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginUser, response: HttpServletResponse): ResponseEntity<*> {

        val userCandidate: Optional<UserEntity> = userRepository.findByLogin(loginRequest.login!!)

        if (userCandidate.isPresent) {
            val user: UserEntity = userCandidate.get()
            try{
                val authentication = authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(loginRequest.login, loginRequest.password))
                SecurityContextHolder.getContext().setAuthentication(authentication)
                val jwt: String = jwtProvider.generateJwtToken(user.login!!)

                val cookie: Cookie = Cookie(authCookieName, jwt)
                cookie.maxAge = jwtProvider.jwtExpiration!!
                cookie.secure = isCookieSecure
                cookie.isHttpOnly = true
                cookie.path = "/"
                response.addCookie(cookie)
                val role = user.userRole
                return ResponseEntity.ok(SuccessfulSigninResponse(user.login, role!!.name, user.id.toString()))
            } catch (e: AuthenticationException){
                return ResponseEntity(ResponseMessage("Authentication failed"),
                        HttpStatus.UNAUTHORIZED)
            }
        } else {
            return ResponseEntity(ResponseMessage("User not found!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody newUser: NewUser): ResponseEntity<*> {

        val userCandidate: Optional <UserEntity> = userRepository.findByLogin(newUser.login!!)

        if (!userCandidate.isPresent) {
            if (loginExists(newUser.login!!)) {
                return ResponseEntity(ResponseMessage("Username is already taken!"),
                        HttpStatus.BAD_REQUEST)
            } else if (emailExists(newUser.email!!)) {
                return ResponseEntity(ResponseMessage("Email is already in use!"),
                        HttpStatus.BAD_REQUEST)
            }

            // Creating user's account
            var user = UserEntity(
                    0,
                    newUser.login!!,
                    newUser.email!!,
                    encoder.encode(newUser.password),
                    newUser.name!!
            )

            user.userRole = roleRepository.findByName("user").get()
            userRepository.save(user)

            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(ResponseMessage("User already exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<*> {
        val cookie: Cookie = Cookie(authCookieName, null)
        cookie.maxAge = 0
        cookie.secure = isCookieSecure
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("Successfully logged"))
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun loginExists(login: String): Boolean {
        return userRepository.findByLogin(login).isPresent
    }

}