package com.github.amitsureshchandra.urlshortner.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
	private static final String ANONYMOUS_USER = "service";

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) return null;
		if (authentication.getPrincipal().equals("anonymousUser")) return Optional.of(ANONYMOUS_USER);
		return Optional.of("auth-user");
	}
}
