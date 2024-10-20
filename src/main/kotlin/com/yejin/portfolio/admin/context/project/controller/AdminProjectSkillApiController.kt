package com.yejin.portfolio.admin.context.project.controller

import com.yejin.portfolio.admin.context.project.form.ProjectSkillForm
import com.yejin.portfolio.admin.context.project.service.AdminProjectSkillService
import com.yejin.portfolio.admin.data.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


//ProjectSkill 테이블의 삽입 수정기능
@RestController
@RequestMapping("/admin/api/projects/skills")
class AdminProjectSkillApiController(
    private val adminProjectSkillService: AdminProjectSkillService
) {
    @PostMapping
    fun postProjectSkill(@RequestBody @Validated form: ProjectSkillForm): ResponseEntity<Any> {
        adminProjectSkillService.save(form)
        return ApiResponse.successCreate()
    }

    @DeleteMapping("/{id}")
    fun deleteProjectSkill(@PathVariable id: Long): ResponseEntity<Any> {
        adminProjectSkillService.delete(id)
        return ApiResponse.successDelete()
    }
}