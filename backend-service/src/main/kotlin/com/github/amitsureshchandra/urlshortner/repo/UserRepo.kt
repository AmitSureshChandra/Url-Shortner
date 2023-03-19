package com.github.amitsureshchandra.urlshortner.repo

import com.github.amitsureshchandra.urlshortner.dto.UserDto
import com.github.amitsureshchandra.urlshortner.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepo : JpaRepository<User, UUID?> {
    @Query("SELECT new com.github.amitsureshchandra.urlshortner.dto.UserDto(u.email, u.name, u.mobile) FROM User u WHERE u.email = :email")
    fun findDtoByEmail(email: String): UserDto?
    fun findByEmail(email: String): User?

    fun existsUserByEmail(authEmail: String): Boolean
    fun findByEmailOrName(email: String, userName: String):  Optional<User>
}