package com.github.amitsureshchandra.urlshortner.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegDto(
    @field:NotNull(message = "required")
    @field:NotBlank(message = "required")
    @JsonProperty("username")
    val userName: String? = null,

    @field:NotNull(message = "required")
    @field:NotBlank(message = "required")
    @field:Email(message = "invalid email")
    @JsonProperty("email")
    val email: String? = null,

    @field:NotNull(message = "required")
    @field:NotBlank(message = "required")
    @JsonProperty("password")
    val password: String? = null,

    @JsonProperty("mobile")
    val mobile: String? = null
)