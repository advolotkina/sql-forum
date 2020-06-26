package com.advolotkina.sql.forum.controllers

import com.advolotkina.sql.forum.dto.SaveThemeRequest
import com.advolotkina.sql.forum.entities.ThemeEntity
import com.advolotkina.sql.forum.repository.GroupRepository
import com.advolotkina.sql.forum.repository.ThemeRepository
import com.advolotkina.sql.forum.response.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1.0/themes")
class ThemesController {
    @Autowired
    lateinit var themeRepository: ThemeRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @GetMapping("")
    fun getThemes() = themeRepository.findAll()

    @GetMapping("/{id}")
    fun getTheme(@PathVariable id: Int) = themeRepository.findById(id)

    @PostMapping("")
    fun createTheme(@RequestBody newTheme: SaveThemeRequest): ResponseEntity<*> {
        val themeCandidate: Optional<ThemeEntity> = themeRepository.findByName(newTheme.name!!)
        if (!themeCandidate.isPresent) {
            if (nameExists(newTheme.name!!)) {
                return ResponseEntity(ResponseMessage("This theme name exists"),
                        HttpStatus.BAD_REQUEST)
            }
            val groupEntity = groupRepository.findById(newTheme.groupId)
            if (groupEntity.isPresent){
            try {
                // Creating user's account
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