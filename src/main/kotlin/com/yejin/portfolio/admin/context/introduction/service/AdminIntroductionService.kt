package com.yejin.portfolio.admin.context.introduction.service

import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.domain.entity.Introduction
import com.yejin.portfolio.domain.repository.IntroductionRepository
import org.springframework.stereotype.Service

@Service
class AdminIntroductionService(
    private val introductionRepository: IntroductionRepository
) {
    fun getIntroductionTable(): TableDTO {
        val classInfo = Introduction::class
        val entities = introductionRepository.findAll()
        return TableDTO.from(classInfo, entities)
    }
}