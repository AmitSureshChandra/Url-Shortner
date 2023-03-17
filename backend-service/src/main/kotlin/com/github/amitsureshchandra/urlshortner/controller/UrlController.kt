package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.RespMsg
import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import com.github.amitsureshchandra.urlshortner.dto.UserUrl
import com.github.amitsureshchandra.urlshortner.service.UrlService
import com.github.amitsureshchandra.urlshortner.utils.UrlUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
class UrlController(val urlService: UrlService, val utilService: UrlUtil) {

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

    @GetMapping("/api/v1/routes/users")
    fun getUserRoutes(): ResponseEntity<List<UserUrl>>{
        return ResponseEntity.ok(urlService.getAllUserUrls());
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
