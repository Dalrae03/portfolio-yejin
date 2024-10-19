package com.yejin.portfolio.admin.context.project.controller

import com.yejin.portfolio.admin.context.project.form.ProjectForm
import com.yejin.portfolio.admin.context.project.service.AdminProjectService
import com.yejin.portfolio.admin.data.ApiResponse
import com.yejin.portfolio.admin.data.TableDTO
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


//Project 테이블의 삽입 수정기능
@RestController
@RequestMapping("/admin/api/projects")
class AdminProjectApiController(
    private val adminProjectService: AdminProjectService
) {
    @PostMapping
    fun postProject(@RequestBody @Validated form: ProjectForm): ResponseEntity<Any> {
        adminProjectService.save(form)
        return ApiResponse.successCreate()
    }

    @PutMapping("/{id}")
    fun putProject(@PathVariable id: Long, @RequestBody form: ProjectForm): ResponseEntity<Any> {
        adminProjectService.update(id, form)
        return ApiResponse.successUpdate()
    }

    @GetMapping("/{id}/details")
    fun getProjectDetails(@PathVariable id: Long): TableDTO {
        return adminProjectService.getProjectDetailTable(id)
    }
}