package com.advolotkina.sql.forum.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "comment_attachments")
class CommentAttachmentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false)
        val url: String,

        @JsonManagedReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id")
        val comment: CommentEntity
)