package com.example.kotlincrudapp.service

import com.example.kotlincrudapp.dto.EmployeeDto
import org.springframework.stereotype.Service

@Service
interface EmployeeService {
    fun addEmployee(employeeDto: EmployeeDto): Any
    fun getAllEmployee(): Any
    fun getEmployeeById(employeeId: Long): Any
    fun deleteEmployee(employeeId: Long): Any
    fun updateEmployee(employeeDto: EmployeeDto): Any
    fun getAllEmployeesBySerach(searchParam: String): Any
}