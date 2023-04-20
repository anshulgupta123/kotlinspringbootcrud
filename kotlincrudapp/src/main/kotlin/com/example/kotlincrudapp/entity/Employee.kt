package com.example.kotlincrudapp.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var email: String,
    var dept: String,
    var address: String

) {
    constructor(name: String, email: String, dept: String, address: String) : this(0, name, email, dept, address) {

    }
}