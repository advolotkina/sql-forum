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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false, unique = true)
        var login: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var password_hash: String,

        @Column(nullable = false)
        var name: String,

        @Column
        val registration_date: Timestamp? = Timestamp(System.currentTimeMillis()),

        @Column
        var is_banned: Boolean? = false,

        @Column
        var comments_count: Int? = 0,

        @Column
        val last_comment: Timestamp? = null,

        @Column
        var timezone_offset: Int? = 0,

        @Column
        var location: String? = "",

        @Column
        var profession: String? = "",

        @Column
        var extra_info: String? = "",

        @Column
        var signature: String? = "",

        @Column
        var user_pic: String? = "",

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id")
        var userRole: RoleEntity? = null,

        @JsonBackReference
        @OneToMany(mappedBy = "topicAuthor", fetch=FetchType.EAGER)
        var topicsCreatedByUser: MutableList<TopicEntity> = mutableListOf(),

        @Transient
        @JsonBackReference
        @OneToMany(mappedBy = "commentAuthor", fetch=FetchType.LAZY)
        var commentsCreatedByUser: MutableList<CommentEntity> = mutableListOf(),

        @Transient
        @ManyToMany(mappedBy = "themeAdmins", fetch=FetchType.EAGER)
        var themesAdminedByUser: MutableList<ThemeEntity> = mutableListOf(),

        @Transient
        @ManyToMany(fetch=FetchType.EAGER)
        @JoinTable(name = "user_fav_themes",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "theme_id", referencedColumnName = "id")))
        var themesFavedByUser: MutableList<ThemeEntity> = mutableListOf()
)