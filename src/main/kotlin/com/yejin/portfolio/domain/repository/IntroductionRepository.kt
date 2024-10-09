package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.Introduction
import org.springframework.data.jpa.repository.JpaRepository

interface IntroductionRepository : JpaRepository<Introduction, Long> {
    //spring data jpa 리포지토리 인터페이스 - 데이터베이스와 상호작용하는 계층을 쉽게 관리하기 위해 제공되는 기능. 이 인터페이스를 통해 개발자는 복잡한 SQL 쿼리를 작성하지 않고도 데이터베이스에서 데이터를 조회, 저장, 수정, 삭제하는 작업을 할 수 있다.
    // select * from introduction where is_active = :isActive
    fun findAllByIsActive(isActive: Boolean): List<Introduction>
}