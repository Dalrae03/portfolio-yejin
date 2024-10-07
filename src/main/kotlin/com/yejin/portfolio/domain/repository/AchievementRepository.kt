package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.Achievement
import org.springframework.data.jpa.repository.JpaRepository

//interface -> 인터페이스를 정의하는 키워드로, 클래스가 반드시 구현해야 하는 메서드의 계약(Contract)을 정의하는 역할
//자바나 코틀린에서 인터페이스는 객체 지향 프로그래밍의 중요한 개념 중 하나로, 특정 기능이나 메서드를 명시하고, 이를 상속받는 클래스가 그 메서드를 구현하도록 강제한다
//AchievementRepository는 JpaRepository를 상속받는 인터페이스.
//즉, 데이터베이스와 상호작용할 수 있는 레포지토리를 정의하는 것이며, Achievement 엔티티와 관련된 데이터 접근 작업을 수행하는 역할
interface AchievementRepository : JpaRepository<Achievement, Long> {
    //jpa가 만들어주는 쿼리 - select * from achievement where is_Active = :isActive
    //Achievement 엔티티에 대한 레포지토리 -> Achievement에서 조회한다
    //모든데이터 조회 -> (findAll, select *), 어떤컬럼에 대한 조회인지 알 수 있음 -> IsActive
    //리스트로 조회함을 알 수 있음 -> List<Achievement>
    //단건을 조사할 경우 그냥 Achievement or Optional<Achievement> 하지만 2건 이상이면 오류발생.
    fun findAllByIsActive(isActive: Boolean): List<Achievement>
}