package com.github.amitsureshchandra.urlshortner.advices

import com.github.amitsureshchandra.urlshortner.dto.RespMsg
import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.exception.ValidationException
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

    @ExceptionHandler
    fun validationAdvice(exception: ValidationException): ResponseEntity<RespMsg>{
        return ResponseEntity(RespMsg(exception.message),HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun authAdvice(exception: AccessDeniedException): ResponseEntity<RespMsg>{
        return ResponseEntity(RespMsg(exception.message),HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun notFoundAdvice(exception: NotFoundException): ResponseEntity<RespMsg>{
        return ResponseEntity(RespMsg(exception.message),HttpStatus.BAD_REQUEST)
    }
}