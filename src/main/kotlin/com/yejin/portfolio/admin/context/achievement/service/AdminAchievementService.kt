package com.yejin.portfolio.admin.context.achievement.service

import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.domain.entity.Achievement
import com.yejin.portfolio.domain.repository.AchievementRepository
import org.springframework.stereotype.Service

@Service
class AdminAchievementService(
    private val achievementRepository: AchievementRepository  //도메인에 있는 achievement리포지토리
) {
    fun getAchievementTable(): TableDTO {
        val classInfo = Achievement::class
        val entities = achievementRepository.findAll()
        return TableDTO.from(classInfo, entities)
    }
}