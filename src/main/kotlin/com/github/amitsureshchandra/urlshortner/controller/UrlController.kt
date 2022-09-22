package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.RespMsg
import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

@RestController
class UrlController {
    var urlMap: HashMap<String?, String?> = HashMap();

    @PostMapping("/api/v1/routes")
    fun createRoute(@RequestBody dto: UrlCreateDto): ResponseEntity<RespMsg>{
        urlMap[UUID.randomUUID().toString().subSequence(0,7).toString()] = dto.url.orEmpty()
        return ResponseEntity.ok(RespMsg("OK"));
    }

    @GetMapping("/api/v1/routes")
    fun getRoutes(): ResponseEntity<HashMap<String?, String?>>{
        return ResponseEntity.ok(urlMap);
    }

    @RequestMapping("/{url}")
    fun redirect(@PathVariable @NotNull url:String?,  httpServletResponse: HttpServletResponse){
        if(!urlMap.containsKey(url)){
            httpServletResponse.setStatus(404);
        }else{
            httpServletResponse.setHeader("Location", urlMap[url]);
            httpServletResponse.setStatus(302);
        }
    }
}