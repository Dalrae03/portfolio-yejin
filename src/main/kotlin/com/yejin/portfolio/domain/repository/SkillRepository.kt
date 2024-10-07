package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.constant.SkillType
import com.yejin.portfolio.domain.entity.Skill
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SkillRepository : JpaRepository<Skill, Long> {
    //select * from skill where is_active = :isActive
    fun findAllByIsActive(isActive: Boolean): List<Skill>

    //select * from skill where lower(name) = lower(:name) and skill_type = :type
    //대소문자를 구분하지 않고 name와 skill_type을 조건으로 Skill 데이터를 조회하는 SQL 쿼리를 실행.
    //이 메서드는 대소문자를 구분하지 않고 name을 검색하고, 동시에 skill_type이 특정 값과 일치하는지 확인하는 쿼리를 실행
    fun findByNameIgnoreCaseAndType(name: String, type: SkillType): Optional<Skill>
}