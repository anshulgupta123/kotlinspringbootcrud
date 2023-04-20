package com.example.kotlincrudapp.exception

import java.lang.RuntimeException

class EmployeeException(override var message: String) : RuntimeException(message) {
    var exceptionMessage: String = message

}