package com.github.amitsureshchandra.urlshortner.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    var name: String,
    var email: String,
    var mobile: String,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID?,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "user")
    val urls: MutableList<UrlMap>
)