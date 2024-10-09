package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.Experience
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface ExperienceRepository : JpaRepository<Experience, Long> {

    //JPQL - 자바의 객체지향적인 쿼리. sql과 비슷, 좀 더 객체의 관점에서 작성할 수 있는 sql. jpa에서 jpql을 가지고 실제로 dbms에 맞는 query로 바꿔가지고 db로 query를 내보냄
    //Query 뒤 괄호가 JPQL넣음
    //Fetch Join - 묶어가지고 한번에 보내주는 것 같다...
    //Fetch Join하면 기본 정책이 inner join(교집합)이다. / inner join(교집합) - 양쪽에 다 데이터가 있어야지 이 엔티티를 가지고 올 수 있다.
    //left join fetch - 디테일을 굳이 안써도, 간단하게 한 줄 설명만 있어도 프레젠테이션 레이어에서 보여주고 싶을 수 있기 때문에, 이것만 있어도 데이터를 가지고 올 수 있도록 하기 위해 left추가.
    //left join은 조인된 데이터가 없어도 Project 데이터를 가져오며, 만약 Skill이 없는 Project도 조회
    @Query("select e from Experience e left join fetch e.details where e.isActive = :isActive")
    fun findAllByIsActive(isActive: Boolean): List<Experience>

    @Query("select e from Experience e left join fetch e.details where e.id = :id")
    override fun findById(id: Long): Optional<Experience>  //Experience return

}