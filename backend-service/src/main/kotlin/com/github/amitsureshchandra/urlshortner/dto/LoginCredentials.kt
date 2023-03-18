package com.github.amitsureshchandra.urlshortner.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

data class LoginCredentials(
    @field:NotNull(message = "required") @JsonProperty("email") val email:String? = "",
    @field:NotNull(message = "required") @JsonProperty("password") val password: String? = ""
)