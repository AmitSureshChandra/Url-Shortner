package com.github.amitsureshchandra.urlshortner.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder


@Service
class RouteBuilderUtil(val objectMapper: ObjectMapper) {

    fun setUp(builder: MockHttpServletRequestBuilder): MockHttpServletRequestBuilder {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getToken())
    }

    @Throws(JsonProcessingException::class)
    fun setUp(builder: MockHttpServletRequestBuilder, body: Any?): MockHttpServletRequestBuilder {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getToken()).content(objectMapper.writeValueAsString(body))
    }

    fun setUpWithoutToken(builder: MockHttpServletRequestBuilder): MockHttpServletRequestBuilder {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
    }

    @Throws(JsonProcessingException::class)
    fun setUpWithoutToken(builder: MockHttpServletRequestBuilder, body: Any?): MockHttpServletRequestBuilder {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body))
    }

    @Throws(JsonProcessingException::class)
    fun setUpWithoutToken(
        builder: MockHttpServletRequestBuilder,
        body: Any?,
        sigHeader: String?
    ): MockHttpServletRequestBuilder {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body))
            .header("stripe-signature", sigHeader)
    }

    private fun getToken(): String {
//        return keycloakMockUtil.getToken()
        return "";
    }
}