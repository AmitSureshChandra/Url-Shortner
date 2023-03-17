package com.github.amitsureshchandra.urlshortner.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UrlHit(
    var ip: String,
    var shortUrl: String,
    var userAgent: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID = UUID.randomUUID()
}