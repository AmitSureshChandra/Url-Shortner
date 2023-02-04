package com.github.amitsureshchandra.urlshortner.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginCredentials(@JsonProperty("email") val email:String, @JsonProperty("password") val password: String)