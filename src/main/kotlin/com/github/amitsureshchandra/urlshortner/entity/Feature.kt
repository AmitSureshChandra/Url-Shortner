package com.github.amitsureshchandra.urlshortner.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Feature(
    var name: String,
    var code: String,
    @Id @GeneratedValue var id: Long = 0
)