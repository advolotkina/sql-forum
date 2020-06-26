package com.advolotkina.sql.forum.entities
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    var name: String,

    @JsonBackReference
    @OneToMany(mappedBy = "userRole", fetch=FetchType.EAGER)
    var users: MutableList<UserEntity> = mutableListOf()
)