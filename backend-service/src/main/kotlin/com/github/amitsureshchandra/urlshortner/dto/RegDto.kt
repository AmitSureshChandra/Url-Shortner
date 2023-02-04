package com.github.amitsureshchandra.urlshortner.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegDto(
    @JsonProperty("username") val userName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("mobile") val mobile: String
)