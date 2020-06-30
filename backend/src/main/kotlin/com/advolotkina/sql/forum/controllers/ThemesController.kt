package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.NewTopic
import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.entities.GroupEntity
import com.advolotkina.sql.forum.entities.ThemeEntity
import com.advolotkina.sql.forum.entities.TopicEntity
import com.advolotkina.sql.forum.entities.UserEntity
import com.advolotkina.sql.forum.repository.GroupRepository
import com.advolotkina.sql.forum.repository.ThemeRepository
import com.advolotkina.sql.forum.repository.TopicRepository
import com.advolotkina.sql.forum.repository.UserRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1.0/themes")
class ThemesController {
    @Autowired
    lateinit var themeRepository: ThemeRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var topicRepository: TopicRepository

    @GetMapping("")
    fun getThemes() = themeRepository.findAll()

    @GetMapping("/{id}")
    fun getTheme(@PathVariable id: Int) = themeRepository.findById(id)

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun updateTheme(@PathVariable id: Int, @RequestBody updateTheme: SaveThemeRequest): ResponseEntity<*> {
        val themeCandidate: Optional<ThemeEntity> = themeRepository.findById(id)
        if (themeCandidate.isPresent) {
            val groupCandidate: Optional<GroupEntity> = groupRepository.findById(updateTheme.groupId)
            if (groupCandidate.isPresent) {
                try {
                    val theme = themeCandidate.get()
                    theme.name = updateTheme.name
                    themeRepository.save(theme)
                } catch (e: Exception) {
                    return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                            HttpStatus.SERVICE_UNAVAILABLE)
                }
                return ResponseEntity(ResponseMessage("Theme updated"), HttpStatus.OK)
            }
            return ResponseEntity(ResponseMessage("Group not found"),
                    HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ResponseMessage("Theme not found"),
                HttpStatus.BAD_REQUEST)
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun deleteTheme(@PathVariable id: Int): ResponseEntity<*> {
        val themeCandidate: Optional<ThemeEntity> = themeRepository.findById(id)
        if(themeCandidate.isPresent){
            try{
                themeRepository.delete(themeCandidate.get())
            } catch (e: Exception) {
                println(e.message)
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }
            return ResponseEntity(ResponseMessage("Theme deleted"), HttpStatus.OK)
        }
        return ResponseEntity(ResponseMessage("Theme doesn't exist"),
                    HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/{id}")
    fun createTopic(@PathVariable id: Int, @RequestBody newTopic: NewTopic): ResponseEntity<*>
    {
        val themeCandidate: Optional<ThemeEntity> = themeRepository.findById(id)
        if(themeCandidate.isPresent){
            val userCandidate: Optional<UserEntity> = userRepository.findByLogin(newTopic.userName)
            if(userCandidate.isPresent){
                try{
                    val topic = TopicEntity(
                            0,
                            name = newTopic.name,
                            description = newTopic.description,
                            topicAuthor = userCandidate.get(),
                            theme = themeCandidate.get()
                    )
                    topicRepository.save(topic)
                } catch (e: Exception) {
                    return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                            HttpStatus.SERVICE_UNAVAILABLE)
                }
            }else{
                return ResponseEntity(ResponseMessage("User doesn't exist"),
                        HttpStatus.BAD_REQUEST)
            }
            return ResponseEntity(ResponseMessage("Topic created"), HttpStatus.OK)
        } else{
            return ResponseEntity(ResponseMessage("Theme doesn't exist"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    fun createTheme(@RequestBody newTheme: SaveThemeRequest): ResponseEntity<*> {
        val themeCandidate: Optional<ThemeEntity> = themeRepository.findByName(newTheme.name!!)
        println(newTheme.groupId)
        println(newTheme.name)
        if (!themeCandidate.isPresent) {
            if (nameExists(newTheme.name!!)) {
                return ResponseEntity(ResponseMessage("This theme name exists"),
                        HttpStatus.BAD_REQUEST)
            }
            val groupEntity = groupRepository.findById(newTheme.groupId)
            if (groupEntity.isPresent){
            try {
                val theme = ThemeEntity(
                        0,
                        newTheme.name!!,
                        group = groupEntity.get()
                )
                themeRepository.save(theme)
            } catch (e: Exception) {
                return ResponseEntity(ResponseMessage("Server error. Please, contact site owner"),
                        HttpStatus.SERVICE_UNAVAILABLE)
            }
            }else{
                println("Group with this ID doesn't exist")
                return ResponseEntity(ResponseMessage("Group with this ID doesn't exist"),
                        HttpStatus.BAD_REQUEST)
            }

            return ResponseEntity(ResponseMessage("Theme created"), HttpStatus.OK)
        }else{
            return ResponseEntity(ResponseMessage("Theme exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    private fun nameExists(name: String): Boolean {
        return themeRepository.findByName(name).isPresent
    }

}