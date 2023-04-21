package com.example.kotlincrudapp.serviceimpl

import com.example.kotlincrudapp.dto.EmployeeDto
import com.example.kotlincrudapp.dto.Response
import com.example.kotlincrudapp.entity.Employee
import com.example.kotlincrudapp.exception.EmployeeException
import com.example.kotlincrudapp.repository.EmployeeRepository
import com.example.kotlincrudapp.service.EmployeeService
import com.example.kotlincrudapp.utility.Constants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.text.MessageFormat
import java.util.Optional


@Service
class addEmployee : EmployeeService {

    var log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var employeeRepository: EmployeeRepository;

    @Autowired
    lateinit var enviroment: Environment;

    override fun addEmployee(employeeDto: EmployeeDto): Any {
        log.info("Inside addEmployee of EmployeeServiceImpl")
        try {
            if (addEmployeeValidation(employeeDto)) {
                log.info("Getting Invalid data in request");
                throw EmployeeException(enviroment.getProperty(Constants.INVALID_DATA)!!);
            }
            var employeeExist: Boolean = employeeExistByEmail(employeeDto.email)
            if (employeeExist) {
                throw EmployeeException(enviroment.getProperty(Constants.EMPLOYEE_ALREADY_EXIST)!!);
            }
            val employee = Employee(employeeDto.name, employeeDto.email, employeeDto.address, employeeDto.dept)
            val savedEmployee = employeeRepository.save(employee)
            return Response(
                enviroment.getProperty(Constants.EMPLOYEE_SAVED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                savedEmployee
            )
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format("Exception caught in addEmployee of EmployeeServiceImpl:{0}", e.message);
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    override fun getAllEmployee(): Any {
        try {
            log.info("Inside getAllEmployee of EmployeeServiceImpl")
            var employeesFromRepo: List<Employee>? = employeeRepository.findAll()
            val responseList: MutableList<EmployeeDto> = mutableListOf()
            if (employeesFromRepo != null) {
                for (employee in employeesFromRepo) {
                    responseList.add(getPopulateEmployeeDtoFromEmployee(employee))
                }
            }
            return Response(
                enviroment.getProperty(Constants.EMPLOYEES_FETCHED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                responseList
            );
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format("Exception caught in addEmployee of EmployeeServiceImpl:{0}", e.message);
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    override fun getEmployeeById(employeeId: Long): Any {
        try {
            log.info("Inside getEmployeeById of EmployeeServiceImpl")
            if (employeeId == null) {
                throw EmployeeException(enviroment.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID)!!);
            }
            var employee: Optional<Employee> = employeeRepository.findById(employeeId)
            if (employee.isEmpty()) {
                throw EmployeeException(enviroment.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST)!!);
            }
            return Response(
                enviroment.getProperty(Constants.EMPLOYEE_FETCHED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                getPopulateEmployeeDtoFromEmployee(employee.get())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format("Exception caught in getEmployeeById of EmployeeServiceImpl:{0}", e.message);
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    override fun deleteEmployee(employeeId: Long): Any {
        try {
            log.info("Inside deleteEmployee of EmployeeServiceImpl")
            if (employeeId == null) {
                throw EmployeeException(enviroment.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID)!!);
            }
            var employee: Optional<Employee> = employeeRepository.findById(employeeId)
            if (employee.isEmpty()) {
                throw EmployeeException(enviroment.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST)!!);
            }
            employeeRepository.deleteById(employeeId);
            return Response(
                enviroment.getProperty(Constants.EMPLOYEE_DELETED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                employeeId!!
            );
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format("Exception caught in deleteEmployee of EmployeeServiceImpl:{0}", e.message);
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    override fun updateEmployee(employeeDto: EmployeeDto): Any {
        log.info("Inside updateEmployee of EmployeeServiceImpl")
        try {
            if (employeeDto.id == null) {
                throw EmployeeException(enviroment.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID)!!);
            }
            var employee: Optional<Employee> = employeeRepository.findById(employeeDto.id)
            if (employee.isEmpty()) {
                throw EmployeeException(enviroment.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST)!!);
            }
            val employeeFromRepo = employee.get()
            employeeFromRepo.id = employeeDto.id
            employeeFromRepo.name = employeeDto.name
            employeeFromRepo.email = employeeDto.email
            employeeFromRepo.address = employeeDto.address
            employeeFromRepo.dept = employeeDto.dept
            val updatedEmployee = employeeRepository.save(employeeFromRepo)
            return Response(
                enviroment.getProperty(Constants.EMPLOYEE_UPDATED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                updatedEmployee
            )
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format("Exception caught in updateEmployee of EmployeeServiceImpl:{0}", e.message);
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    override fun getAllEmployeesBySerach(searchParam: String): Any {
        log.info("Inside getAllEmployeesBySerach of EmployeeServiceImpl")
        try {
            val responseList = mutableListOf<EmployeeDto>()
            val dataFromRepo: List<Array<Any>> = employeeRepository.findEmployeesBySearchParam(searchParam)
            for (array in dataFromRepo) {
                var employeeId: Long = 0;
                var employeeName = ""
                var employeeDept = ""
                var employeeEmail = ""
                var employeeAddress = ""
                if (array[0] is BigDecimal) {
                    var empId = array[0] as BigDecimal
                    employeeId = empId.longValueExact()
                }
                if (array[1] is String) {
                    employeeAddress = array[1].toString()
                }
                if (array[2] is String) {
                    employeeDept = array[2].toString()
                }
                if (array[3] is String) {
                    employeeEmail = array[3].toString()
                }
                if (array[4] is String) {
                    employeeName = array[4].toString()
                }
                responseList.add(EmployeeDto(employeeId, employeeName, employeeEmail, employeeDept, employeeAddress))
            }
            return Response(
                enviroment.getProperty(Constants.EMPLOYEES_FETCHED_SUCCESSFULLY)!!,
                enviroment.getProperty(Constants.SUCCESS_CODE)!!,
                responseList
            )
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage: String? = null;
            if (e is EmployeeException) {
                errorMessage = e.exceptionMessage;
            } else {
                errorMessage =
                    MessageFormat.format(
                        "Exception caught in getAllEmployeesBySerach of EmployeeServiceImpl:{0}",
                        e.message
                    );
            }
            log.error(errorMessage);
            throw EmployeeException(errorMessage!!);
        }
    }

    fun addEmployeeValidation(employeeDto: EmployeeDto): Boolean {
        log.info("Inside addEmployeeValidation of EmployeeServiceImpl")
        if (employeeDto.name == null || employeeDto.name.isEmpty() || employeeDto.address == null || employeeDto.address.isEmpty() || employeeDto.dept == null ||
            employeeDto.dept.isEmpty() || employeeDto.email == null || employeeDto.email.isEmpty()
        ) {
            return true
        }
        return false
    }

    fun employeeExistByEmail(email: String): Boolean {
        log.info("Inside employeeExistByEmail of EmployeeServiceImpl")
        var employee: Employee? = employeeRepository.findByEmail(email)
        if (employee == null) {
            return false
        } else {
            return true
        }
    }

    fun getPopulateEmployeeDtoFromEmployee(employee: Employee): EmployeeDto {
        log.info("Inside getPopulateEmployeeDtoFromEmployee of EmployeeServiceImpl")
        return EmployeeDto(employee.id, employee.name, employee.email, employee.dept, employee.address)
    }
}