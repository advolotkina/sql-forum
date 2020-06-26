package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "topics")
class TopicEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var description: String,

        @Column(nullable = false)
        val creation_datetime: Timestamp = Timestamp(Instant.now().epochSecond),

        @Column(nullable = false)
        var views: Int = 0,

        @Column(nullable = true)
        var last_comment: Timestamp?,

        @Column(nullable = false)
        var comments_count: Int = 0,

        @Column(nullable = false)
        val url: String = "/themes/topics/$id",

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "theme_id")
        val theme: ThemeEntity,

        @JsonManagedReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        val topicAuthor: UserEntity,

        @JsonManagedReference
        @OneToMany(mappedBy = "topic", fetch=FetchType.LAZY)
        var comments: MutableList<CommentEntity> = mutableListOf()
)