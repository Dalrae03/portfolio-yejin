package com.yejin.portfolio.admin.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

//ApiResponse는 generic.
class ApiResponse<T>(status: HttpStatus) : ResponseEntity<T>(status)