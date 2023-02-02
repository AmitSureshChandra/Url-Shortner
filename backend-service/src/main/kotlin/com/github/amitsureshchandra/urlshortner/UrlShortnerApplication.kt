package com.github.amitsureshchandra.urlshortner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class UrlShortnerApplication

fun main(args: Array<String>) {
	runApplication<UrlShortnerApplication>(*args)
}
