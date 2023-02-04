package com.github.amitsureshchandra.urlshortner.entity

import java.util.*
import javax.persistence.*

@Entity
data class UrlMap(
    var shortUrl: String,
    var fullUrl: String,
    @ManyToOne
    var user: User,
){
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID = UUID.randomUUID()
}