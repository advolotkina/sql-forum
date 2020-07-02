package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "comments")
class CommentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(name="comment_text", nullable = false)
        var commentText: String,

        @Column
        var datetime: Timestamp? = Timestamp(System.currentTimeMillis()),

        @Column
        var url: String? = "",

        @Column(name="is_modified")
        var isModified: Boolean? = false,

        @Column(name="modification_date")
        var modificationDate: Timestamp? = null,

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