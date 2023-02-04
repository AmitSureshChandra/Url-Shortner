package com.github.amitsureshchandra.urlshortner.filter

import com.auth0.jwt.exceptions.JWTVerificationException
import com.github.amitsureshchandra.urlshortner.service.UrlUserDetailService
import com.github.amitsureshchandra.urlshortner.service.util.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(val urlUserDetailService: UrlUserDetailService, val jwtUtil: JwtUtil): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if(authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return
        }

        val jwt = authHeader.substring(7)
        if(jwt.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token")
            return
        }

        try {
            val email = jwtUtil.validateTokenAndRetrieveSub(jwt)

            val userDetails = urlUserDetailService.loadUserByUsername(email)

            val authToken = UsernamePasswordAuthenticationToken(email, userDetails.password, userDetails.authorities)

            if(SecurityContextHolder.getContext().authentication == null) {
                SecurityContextHolder.getContext().authentication = authToken
            }
        }catch(exc: JWTVerificationException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token")
        }

        filterChain.doFilter(request, response);
    }
}