package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.request.LoginCredentials
import com.github.amitsureshchandra.urlshortner.dto.request.RegDto
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.util.RouteBuilderUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest(@Autowired val userRepo: UserRepo ,@Autowired var authController: AuthController, @Autowired val routeBuilderUtil: RouteBuilderUtil, @Autowired val mvc: MockMvc) {

    @Test
    fun test_register_working(){
        val regDto = RegDto("ak","ak@example.com", "password", "1234567890")

        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/register"), regDto))
            .andExpect(status().isOk)
            .andExpect(jsonPath("user.email").value(regDto.email))
            .andExpect(jsonPath("user.mobile").value(regDto.mobile))
            .andExpect(jsonPath("user.name").value(regDto.userName))
            .andExpect(jsonPath("jwt-token").isString)
    }

    @Test
    fun test_register_validation(){
        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/register")))
            .andExpect(status().isBadRequest)

        val regDto = RegDto()
        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/register"), regDto))
            .andDo{ println(it.response.contentAsString) }
            .andExpect(status().isBadRequest)
    }

    @Test
    fun test_login_working(){
        val regDto = RegDto("ak2","ak2@example.com", "password", "1234567890")
        val dto = LoginCredentials("ak2@example.com", "password")

        // unauth
        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/login"), dto))
            .andExpect(status().isBadRequest)

        // unauth
        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/login"), LoginCredentials()))
            .andExpect(status().isBadRequest)

        authController.registerHandler(regDto);

        // logged in
        mvc.perform(routeBuilderUtil.setUpWithoutToken(post("/api/v1/auth/login"), dto))
            .andExpect(status().isOk)
            .andExpect(jsonPath("user.email").value(regDto.email))
            .andExpect(jsonPath("user.mobile").value(regDto.mobile))
            .andExpect(jsonPath("user.name").value(regDto.userName))
            .andExpect(jsonPath("jwt-token").isString)
    }
}