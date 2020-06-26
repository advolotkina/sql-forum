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
    val name: String

//    @JsonBackReference
//    @OneToMany(mappedBy = "userRole", fetch=FetchType.LAZY)
//    var users: MutableList<UserEntity> = mutableListOf()
)