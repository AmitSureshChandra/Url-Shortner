package com.github.amitsureshchandra.urlshortner

import com.github.amitsureshchandra.urlshortner.controller.UrlController
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlShortnerApplicationTests(@Autowired var urlController: UrlController) {
	@Test
	fun contextLoads() {
		assertNotNull(urlController)
	}
}
