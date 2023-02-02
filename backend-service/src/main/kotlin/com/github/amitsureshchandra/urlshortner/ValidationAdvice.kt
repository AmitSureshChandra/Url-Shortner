package com.github.amitsureshchandra.urlshortner

import com.github.amitsureshchandra.urlshortner.dto.RespMsg
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ValidationAdvice {
    @ExceptionHandler
    fun validationAdvice(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, String>>{
        val errors: MutableMap<String, String> = HashMap()
        exception.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage: String = error.defaultMessage.orEmpty()
            errors[fieldName] = errorMessage
        }
        return ResponseEntity(errors,HttpStatus.BAD_REQUEST)
    }
}