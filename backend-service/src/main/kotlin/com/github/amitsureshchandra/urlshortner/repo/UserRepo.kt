package com.github.amitsureshchandra.urlshortner.repo

import com.github.amitsureshchandra.urlshortner.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepo : JpaRepository<User, UUID?> {
    fun findByEmail(authEmail: String): User
    fun existsUserByEmail(authEmail: String): Boolean
}