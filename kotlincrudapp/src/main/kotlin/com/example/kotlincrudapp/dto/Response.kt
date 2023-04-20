package com.example.kotlincrudapp.dto

data class Response<T>( var description: String,var code: String,var data: T) {

}