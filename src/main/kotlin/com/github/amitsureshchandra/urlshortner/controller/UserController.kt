package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.UserUrl
import com.github.amitsureshchandra.urlshortner.entity.UrlMap
import com.github.amitsureshchandra.urlshortner.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(val userService: UserService) {

    @GetMapping("/routes")
    fun routes(): List<UserUrl>{
        return userService.authUserRoutes();
    }
}