package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "groups")
class GroupEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false)
        val name: String,

        @JsonManagedReference
        @OneToMany(mappedBy = "group", fetch=FetchType.LAZY)
        var themes: MutableList<ThemeEntity> = mutableListOf()
)