package com.example.kotlincrudapp.dto

data class EmployeeDto(
    var id: Long,
    var name: String,
    var email: String,
    var dept: String,
    var address: String
)