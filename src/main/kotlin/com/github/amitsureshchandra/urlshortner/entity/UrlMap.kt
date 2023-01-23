package com.github.amitsureshchandra.urlshortner.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class UrlMap(
    var shortUrl: String,
    var fullUrl: String,

    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID?,

    @ManyToOne
    var user: User,
)