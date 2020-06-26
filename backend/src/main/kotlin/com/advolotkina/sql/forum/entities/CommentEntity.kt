package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "comments")
class CommentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        @Column(nullable = false)
        var comment_text: String,

        @Column(nullable = false)
        var datetime: Timestamp,

        @Column(nullable = false)
        var url: String,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "topic_id")
        val topic: TopicEntity,

        @JsonManagedReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        val commentAuthor: UserEntity,

        @JsonBackReference
        @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY)
        var commentAttachments: MutableList<CommentAttachmentEntity> = mutableListOf()
)