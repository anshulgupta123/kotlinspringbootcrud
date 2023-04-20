package com.example.kotlincrudapp.controller

import com.example.kotlincrudapp.dto.EmployeeDto
import com.example.kotlincrudapp.service.EmployeeService
import com.example.kotlincrudapp.utility.UrlConstants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class EmployeeController {

    var log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var employeeService: EmployeeService;

    @PostMapping(UrlConstants.ADD_EMPLOYEE)
    fun addEmployee(@RequestBody employeeDto: EmployeeDto): ResponseEntity<Any> {
        log.info("Request for addEmployee of EmployeeController :{}", employeeDto)
        return ResponseEntity(employeeService.addEmployee(employeeDto), HttpStatus.OK)
    }

    @GetMapping(UrlConstants.GET_ALL_EMPLOYEE)
    fun getAllEmployee(): ResponseEntity<Any> {
        log.info("Request for getAllEmployee of EmployeeController")
        return ResponseEntity(employeeService.getAllEmployee(), HttpStatus.OK)
    }

    @GetMapping(UrlConstants.GET_EMPLOYEE_BY_ID)
    fun getEmployeeById(@RequestParam employeeId: Long): ResponseEntity<Any> {
        log.info("Request for getEmployeeById of EmployeeController :{}", employeeId)
        return ResponseEntity(employeeService.getEmployeeById(employeeId), HttpStatus.OK)
    }

    @PutMapping(UrlConstants.Update_EMPLOYEE)
    fun updateEmployee(@RequestBody employeeDto: EmployeeDto): ResponseEntity<Any> {
        log.info("Request for updateEmployee of EmployeeController :{}", employeeDto)
        return ResponseEntity(employeeService.updateEmployee(employeeDto), HttpStatus.OK)
    }

    @DeleteMapping(UrlConstants.DELETE_EMPLOYEE)
    fun deleteEmployee(@RequestParam employeeId: Long): ResponseEntity<Any> {
        log.info("Request for updateEmployee of EmployeeController :{}", employeeId)
        return ResponseEntity(employeeService.deleteEmployee(employeeId), HttpStatus.OK)
    }
}