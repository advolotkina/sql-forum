package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.lang.Nullable
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "themes")
class ThemeEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false, unique = true)
        var name: String,

        @Column
        var comments_count: Int = 0,

        @Column
        var last_comment: Timestamp = Timestamp(System.currentTimeMillis()),

        @Column
        var url: String = "/themes/$id",

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_id")
        var group: GroupEntity,

        @JsonManagedReference
        @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
        var topics: MutableList<TopicEntity> = mutableListOf(),

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "theme_admins",
                joinColumns = arrayOf(JoinColumn(name = "theme_id", referencedColumnName = "id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "admin_id", referencedColumnName = "id")))
        var themeAdmins: MutableList<UserEntity> = mutableListOf()

//        @ManyToMany(mappedBy = "themesFavedByUser", fetch=FetchType.LAZY)
//        var users_faved_this_theme: MutableList<UserEntity> = mutableListOf()
)