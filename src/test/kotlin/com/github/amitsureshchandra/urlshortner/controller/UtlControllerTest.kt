package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.util.KeycloakMockUtil
import com.github.amitsureshchandra.urlshortner.util.RouteBuilderUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UtlControllerTest(@Autowired val routeBuilderUtil: RouteBuilderUtil,@Autowired val mvc: MockMvc) {
    @Test
    fun health_check(){
        mvc.perform(routeBuilderUtil.setUp(MockMvcRequestBuilders.get("/")))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.msg", `is`("short url service")))
    }

    @Test
    fun health_check_unauth(){
        mvc.perform(routeBuilderUtil.setUpWithoutToken(MockMvcRequestBuilders.get("/api/v1/routes")))
            .andExpect(status().isUnauthorized)
    }
}