package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.RespMsg
import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import com.github.amitsureshchandra.urlshortner.dto.UserUrl
import com.github.amitsureshchandra.urlshortner.service.UrlService
import com.github.amitsureshchandra.urlshortner.utils.UrlUtils
import org.jetbrains.annotations.NotNull
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.collections.HashMap

@RestController
class UrlController(val urlService: UrlService, val utilService: UrlUtils) {

    @PostMapping("/api/v1/routes")
    fun createRoute(@RequestBody @Valid dto: UrlCreateDto, httpServletRequest: HttpServletRequest): ResponseEntity<RespMsg>{
        if(dto.url == ""){
            return ResponseEntity.badRequest().body(RespMsg("invalid url"));
        }
        return ResponseEntity.ok(RespMsg( utilService.getServerPath(httpServletRequest) + "/"+ urlService.saveUrl(dto).shortUrl));
    }

    @GetMapping("/api/v1/routes")
    fun getRoutes(): ResponseEntity<List<UserUrl>>{
        return ResponseEntity.ok(urlService.getAllUrls());
    }

    @RequestMapping("/{url}")
    fun redirect(@PathVariable url:String,  httpServletResponse: HttpServletResponse){
        if(!urlService.containsKey(url)){
            httpServletResponse.status = 404;
        }else{
            httpServletResponse.setHeader("Location", urlService.getLongUrl(url));
            httpServletResponse.status = 302;
        }
    }
    
    @GetMapping
    fun home(): ResponseEntity<RespMsg>{
        return ResponseEntity.ok(RespMsg("short url service"))
    }
}
