package com.github.amitsureshchandra.urlshortner.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    var name: String,
    var email: String,
    var mobile: String,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID?
)