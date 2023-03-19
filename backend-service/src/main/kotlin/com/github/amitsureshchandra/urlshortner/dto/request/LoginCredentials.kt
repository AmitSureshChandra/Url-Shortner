package com.github.amitsureshchandra.urlshortner.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class LoginCredentials(
    @field:NotNull(message = "required")
    @field:NotBlank(message = "required")
    @JsonProperty("email")
    val email:String? = null,

    @field:NotNull(message = "required")
    @field:NotBlank(message = "required")
    @JsonProperty("password")
    val password: String? = null
)