package com.github.amitsureshchandra.urlshortner.service.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    @Value("\${jwt_secret}")
    private val secret: String? = null

    private val JWT_SUBJECT = "User Details"
    private val JWT_ISSUER = "URL Shotner"

    fun generateToken(email: String): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withClaim("email", email)
            .withIssuedAt(Date())
            .withIssuer(JWT_ISSUER)
            .sign(Algorithm.HMAC256(secret))
    }
    fun validateTokenAndRetrieveSub(token: String): String {
         val jwtVerifier :JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
             .withSubject(JWT_SUBJECT)
             .withIssuer(JWT_ISSUER)
             .build()

        val jwt: DecodedJWT = jwtVerifier.verify(token)
        return jwt.getClaim("email").asString()
    }
}