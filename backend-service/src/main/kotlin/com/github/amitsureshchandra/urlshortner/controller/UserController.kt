package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.UserDto
import com.github.amitsureshchandra.urlshortner.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(val userService: UserService) {
    @GetMapping("/logged")
    fun getUser(): UserDto {
        return userService.getAuthUser();
    }
}