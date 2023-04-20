package com.example.kotlincrudapp.repository

import com.example.kotlincrudapp.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
     fun findByEmail(email: String) :Employee?

}