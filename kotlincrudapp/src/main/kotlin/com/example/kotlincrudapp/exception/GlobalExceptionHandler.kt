package com.example.kotlincrudapp.exception

import com.example.kotlincrudapp.dto.Response
import com.example.kotlincrudapp.utility.Constants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
public class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    val logger = LoggerFactory.getLogger(javaClass);

    @Autowired
    lateinit var environment: Environment



    @ExceptionHandler
    fun ResponseEntityExceptionHandler(ex: Exception, webRequest: WebRequest): ResponseEntity<Any> {
        logger.info("Inside ResponseEntityExceptionHandler of GlobalExceptionHandler");
        var errorDetails = ErrorDetails(environment.getProperty(Constants.EXCEPTION_CODE)!!, ex.message!!)
        return ResponseEntity(
            Response(
                environment.getProperty(Constants.EXCEPTION_OCCURED)!!,
                environment.getProperty(Constants.FAILURE_CODE)!!,
                errorDetails
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )


    }


}
