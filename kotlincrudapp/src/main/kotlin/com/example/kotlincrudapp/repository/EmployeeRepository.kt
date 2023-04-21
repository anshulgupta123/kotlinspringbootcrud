package com.example.kotlincrudapp.repository

import com.example.kotlincrudapp.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByEmail(email: String): Employee?

    @Query(
        value = "select id,address,dept,email,name from employee where (name like %?1% or email like %?1% or address like %?1% or dept like %?1%)",
        nativeQuery = true
    )
    fun findEmployeesBySearchParam(searchParam: String): List<Array<Any>>

}