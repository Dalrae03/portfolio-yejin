package com.yejin.portfolio.admin.context.achievement.controller

import com.yejin.portfolio.admin.context.achievement.form.AchievementForm
import com.yejin.portfolio.admin.context.achievement.service.AdminAchievementService
import com.yejin.portfolio.admin.data.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


//Achievement테이블의 삽입 수정기능
@RestController
@RequestMapping("/admin/api/achievements")  //어떤 자원에 대한 url인지 좀 더 명확하게 명칭할 수 있도록 한다.
class AdminAchievementApiController(
    private val adminAchievementService: AdminAchievementService
) {
    //인서트
    @PostMapping
    //@RequestBody파라미터 - http리퀘스트 바디에 있는 내용을 좀 더 쉽게 파싱을 해서 가져올 수 있다.
    //@Validated - 우리가 정의한 validation이 적용될 수 있도록 한다. (검증시 활용)
    //form의 타입 - IntroductionForm
    fun postAchievement(@RequestBody @Validated form: AchievementForm): ResponseEntity<Any> {  //리턴 하는 것이 ResponseEntity, 타입은 Any
        adminAchievementService.save(form)
        return ApiResponse.successCreate()  //응답 주는것을 serviceCreate로 묶어둠
        //이거 처리하다가 오류가 나면 AdminApiControllerAdvice에서 처리가 될 것
    }

    //@PathVariable - 상단의 {id}값을 가지고올 수 있다.
    @PutMapping("/{id}")
    fun putAchievement(@PathVariable id: Long, @RequestBody form: AchievementForm): ResponseEntity<Any> {
        adminAchievementService.update(id, form)
        return ApiResponse.successUpdate()
    }
}