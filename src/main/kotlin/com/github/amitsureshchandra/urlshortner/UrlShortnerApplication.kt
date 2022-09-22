package com.github.amitsureshchandra.urlshortner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrlShortnerApplication

fun main(args: Array<String>) {
	runApplication<UrlShortnerApplication>(*args)
}
