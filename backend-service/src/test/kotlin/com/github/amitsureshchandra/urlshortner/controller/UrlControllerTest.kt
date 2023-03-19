package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.request.RegDto
import com.github.amitsureshchandra.urlshortner.dto.request.UrlCreateDto
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.util.RouteBuilderUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlControllerTest(@Autowired val userRepo: UserRepo, @Autowired var authController: AuthController, @Autowired val routeBuilderUtil: RouteBuilderUtil, @Autowired val mvc: MockMvc) {
    @Test
    fun test_create_url_map_working(){

        val urlMap = UrlCreateDto("https://github.com/AmitSureshChandra", "github")

        mvc.perform(routeBuilderUtil.setUp(MockMvcRequestBuilders.post("/api/v1/routes"), urlMap))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("msg").isString)

        mvc.perform(routeBuilderUtil.setUp(MockMvcRequestBuilders.post("/api/v1/routes"),
                UrlCreateDto("https://github.com/AmitSureshChandra")
            ))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("msg").isString)
    }

    fun validation_test(){
        // unauth
        mvc.perform(routeBuilderUtil.setUpWithoutToken(MockMvcRequestBuilders.post("/api/v1/routes"), UrlCreateDto()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)

        mvc.perform(routeBuilderUtil.setUp(MockMvcRequestBuilders.post("/api/v1/routes"), UrlCreateDto()))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}