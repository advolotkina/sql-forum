package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        @Column(nullable = false, unique = true)
        var login: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var password_hash: String,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        val registration_date: Timestamp = Timestamp(Instant.now().epochSecond),

        @Column(nullable = false)
        var is_banned: Boolean = false,

        @Column(nullable = false)
        var comments_count: Int = 0,

        @Column(nullable = true)
        val last_comment: Timestamp,

        @Column(nullable = true)
        var timezone_offset: Int,

        @Column(nullable = true)
        var location: String,

        @Column(nullable = true)
        var profession: String,

        @Column(nullable = true)
        var extra_info: String,

        @Column(nullable = true)
        var signature: String,

        @Column
        var user_pic: String? = null,


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "role_id")
        val userRole: RoleEntity,

        @Transient
        @JsonBackReference
        @OneToMany(mappedBy = "topicAuthor", fetch=FetchType.LAZY)
        var topicsCreatedByUser: MutableList<TopicEntity> = mutableListOf(),

        @Transient
        @JsonBackReference
        @OneToMany(mappedBy = "commentAuthor", fetch=FetchType.LAZY)
        var commentsCreatedByUser: MutableList<CommentEntity> = mutableListOf(),

        @Transient
        @ManyToMany(mappedBy = "themeAdmins", fetch=FetchType.LAZY)
        var themesAdminedByUser: MutableList<ThemeEntity> = mutableListOf()

//        @Transient
//        @ManyToMany(fetch=FetchType.LAZY)
//        @JoinTable(name = "user_fav_themes",
//                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
//                inverseJoinColumns = arrayOf(JoinColumn(name = "theme_id", referencedColumnName = "id")))
//        var themesFavedByUser: MutableList<ThemeEntity> = mutableListOf()
//curl -d '{"name":"AAA", "groupId":"1"}' -H "Content-Type: application/json" -X POST http://localhost:8888/themes
)