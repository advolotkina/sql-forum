package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*
import javax.swing.text.StyledEditorKit

@Entity
@Table(name = "topics")
class TopicEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var description: String,

        @Column
        val creation_datetime: Timestamp? = Timestamp(System.currentTimeMillis()),

        @Column
        var views: Int? = 0,

        @Column
        var last_comment: Timestamp? = Timestamp(System.currentTimeMillis()),

        @Column
        var comments_count: Int? = 0,

        @Column(name="is_modified")
        var isModified: Boolean = false,

        @Column(name="modification_date")
        var modificationDate: Timestamp? = null,

        @Column
        val url: String? = "/themes/topics/$id",

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