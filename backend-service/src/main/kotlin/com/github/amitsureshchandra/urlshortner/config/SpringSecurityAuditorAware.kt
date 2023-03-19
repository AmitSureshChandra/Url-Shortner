package com.github.amitsureshchandra.urlshortner.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpringSecurityAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication == null ||
            !authentication.isAuthenticated || authentication.principal == "anonymousUser"
        ) Optional.of(
            ANONYMOUS_USER
        ) else Optional.of(
            "auth-user"
        )
    }

    companion object {
        private const val ANONYMOUS_USER = "service"
    }
}