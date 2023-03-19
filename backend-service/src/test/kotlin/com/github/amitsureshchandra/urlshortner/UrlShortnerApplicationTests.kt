package com.github.amitsureshchandra.urlshortner

import com.github.amitsureshchandra.urlshortner.controller.UrlController
import com.github.amitsureshchandra.urlshortner.util.RouteBuilderUtil
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions.assertNotNull
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
class UrlShortnerApplicationTests(@Autowired var urlController: UrlController, @Autowired val routeBuilderUtil: RouteBuilderUtil,@Autowired val mvc: MockMvc) {
	@Test
	fun contextLoads() {
		assertNotNull(urlController)
	}

	@Test
	fun health_check(){
		mvc.perform(routeBuilderUtil.setUp(MockMvcRequestBuilders.get("/")))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.jsonPath("$.msg", CoreMatchers.`is`("short url service")))
	}

	@Test
	fun health_check_unauth(){
		mvc.perform(routeBuilderUtil.setUpWithoutToken(MockMvcRequestBuilders.get("/api/v1/routes")))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized)
	}
}