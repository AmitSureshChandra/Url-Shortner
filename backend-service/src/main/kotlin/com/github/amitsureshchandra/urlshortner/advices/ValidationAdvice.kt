package com.github.amitsureshchandra.urlshortner.advices

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.github.amitsureshchandra.urlshortner.dto.response.RespMsg
import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.exception.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ValidationAdvice {

    val logger: Logger = LoggerFactory.getLogger(ValidationAdvice::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validationAdvice(exception: MethodArgumentNotValidException): ResponseEntity<MutableMap<String, Any>>{
        val errors: MutableMap<String, Any> = HashMap()
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

    @ExceptionHandler(UnrecognizedPropertyException::class)
    fun handleUnrecognizedPropertyException(ex: UnrecognizedPropertyException): ResponseEntity<RespMsg> {
        val errorMessage = "Unrecognized property: ${ex.propertyName}"
        return ResponseEntity.badRequest().body(RespMsg(errorMessage))
    }

    @ExceptionHandler(ValueInstantiationException::class)
    fun handleValueInstantiationException(ex: ValueInstantiationException): ResponseEntity<RespMsg> {
        val errorMessage = "Failed to instantiate class: ${ex.type.rawClass.simpleName}"
        logger.error(errorMessage)
        return ResponseEntity.badRequest().body(RespMsg("failed to read req body"))
    }
}