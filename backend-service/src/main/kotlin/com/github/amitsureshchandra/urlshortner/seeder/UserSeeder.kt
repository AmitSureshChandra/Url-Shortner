package com.github.amitsureshchandra.urlshortner.seeder

import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class UserSeeder(val userRepo: UserRepo, val passwordEncoder: PasswordEncoder) {

    val logger: Logger = LoggerFactory.getLogger(UserSeeder::class.java)

    @PostConstruct
    fun init(){
        createUser()
    }

    private fun createUser() {
        if(!userRepo.existsUserByEmail("amit@example.com"))
        {
            val user = User(
                "amit",
                "amit@example.com",
                "1234567890",
                passwordEncoder.encode("password"),
            )
            userRepo.save(user)
            logger.info("amit user created")
        }

        if(!userRepo.existsUserByEmail("suraj@example.com")){
            val user2 = User(
                "suraj",
                "suraj@example.com",
                "1234567890",
                passwordEncoder.encode("password"),
            )
            userRepo.save(user2)
            logger.info("suraj user created")
        }
    }
}