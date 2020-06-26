package com.advolotkina.sql.forum.service


import com.advolotkina.sql.forum.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(login: String): UserDetails {
        val user = userRepository.findByLogin(login).get()



        return org.springframework.security.core.userdetails.User
                .withUsername(login)
                .password(user.password_hash)
                .roles(user.userRole!!.name)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
    }
}