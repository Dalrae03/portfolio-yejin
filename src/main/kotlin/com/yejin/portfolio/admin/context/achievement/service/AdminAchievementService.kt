package com.yejin.portfolio.admin.context.achievement.service

import com.yejin.portfolio.admin.context.achievement.form.AchievementForm
import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.domain.entity.Achievement
import com.yejin.portfolio.domain.repository.AchievementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminAchievementService(
    private val achievementRepository: AchievementRepository  //도메인에 있는 achievement리포지토리
) {
    fun getAchievementTable(): TableDTO {
        val classInfo = Achievement::class
        val entities = achievementRepository.findAll()
        return TableDTO.from(classInfo, entities)
    }

    @Transactional
    fun save(form: AchievementForm) {  //컨트롤러에서 받은 form받음
        val achievement = form.toEntity()  //엔티티만들기
        achievementRepository.save(achievement)  //넘겨주면인서트된다.
    }

    @Transactional
    fun update(id: Long, form: AchievementForm) {
        val achievement = form.toEntity(id)  //아이디가 포함된 엔티티여야지 레포지토리가 넘길때 인서트가 아니고 업데이트가 실행
        achievementRepository.save(achievement)
    }
}