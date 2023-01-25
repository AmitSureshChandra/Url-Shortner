package com.github.amitsureshchandra.urlshortner.util

import com.tngtech.keycloakmock.api.KeycloakMock
import com.tngtech.keycloakmock.api.ServerConfig.aServerConfig
import com.tngtech.keycloakmock.api.TokenConfig.aTokenConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import javax.annotation.PreDestroy


@Service
class KeycloakMockUtil(
    @Value("\${admin-role}") val adminRole: String,
    @Value("\${keycloak.realm}") val REALM:String,
    @Value("\${keycloak.realm}") val DEFAULT_REALM: String,
    @Value("\${keycloak.port}") val PORT: Int,
    @Value("\${keycloak.host}") val HOST: String,

) {

    @PreDestroy
    fun preExit(){
        mockStop()
    }

    private val mock = KeycloakMock(aServerConfig().withDefaultHostname(HOST).withPort(PORT).withDefaultRealm(DEFAULT_REALM).build())

    fun mockStart() {
        mock.start()
    }

    fun mockStop() {
        mock.stop()
    }

    @Bean
    fun getKeycloakMock(): KeycloakMock{
		mockStart();
        return mock
    }

    fun getToken(roles: MutableList<String>): String {
        return "Bearer " + mock.getAccessToken(
            aTokenConfig().withEmail("example@email.com").withRealm(REALM)
                .withRealmRoles(roles).withPreferredUsername("foo").build()
        )
    }

    fun getToken(): String {
        return "Bearer " + mock.getAccessToken(
            aTokenConfig().withEmail("example@email.com").withRealm(REALM)
                .withRealmRoles(arrayListOf("user")).withPreferredUsername("foo").build()
        )
    }

    fun getAdminToken(): String? {
        return "Bearer " + mock.getAccessToken(
            aTokenConfig().withEmail("example@email.com").withRealm(REALM)
                .withRealmRole(adminRole).withPreferredUsername("foo").build()
        )
    }
}