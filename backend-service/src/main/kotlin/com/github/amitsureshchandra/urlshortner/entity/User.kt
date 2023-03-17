package com.github.amitsureshchandra.urlshortner.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    var name: String,
    var email: String,
    var mobile: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String
): BaseEntity(){
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: UUID? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "user")
    private val urls: MutableList<UrlMap> = mutableListOf()

    fun getUrls(): MutableList<UrlMap>{
        return urls
    }

    fun getPass(): String{
        return password
    }
}