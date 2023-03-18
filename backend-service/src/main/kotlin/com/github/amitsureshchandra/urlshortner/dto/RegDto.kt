package com.github.amitsureshchandra.urlshortner.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class RegDto(
    @field:NotNull(message = "required") @JsonProperty("username") val userName: String? = "",
    @field:NotNull(message = "required") @field:Email(message = "invalid email") @JsonProperty("email") val email: String? = "",
    @field:NotNull(message = "required") @JsonProperty("password") val password: String? = "",
    @JsonProperty("mobile") val mobile: String? = ""
)