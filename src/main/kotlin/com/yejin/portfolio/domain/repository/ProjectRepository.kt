package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ProjectRepository : JpaRepository<Project, Long> {

    //fach join의 단점 - 여러개의 엔티티와 관계를 맺고 있을 때 그것들을 다 한꺼번에 조회 불가
    //지금 이건 skill만 가지고 오지 detail은 가지고오지 못했기 때문에 디테일 호출할때마다 레이즈 때문에 매번가지고오게된다.
    //이런경우 네이티브 쿼리로 따로 풀어주거나, 쿼리 DSL이나 다른 방식으로 풀어주어야한다.
    //디폴트 배치 패치 사이즈를 통해 어느정도 성능문제 해결 가능
    //프로젝트와 묶인 스킬 같은 경우에는 스페치 조인으로 한번에 가지고오고, 디테일은 패치조인으로 가지고 올 수 없기 때문에 yml수정으로 한번에 가지고 오는 개수를 지정하는 방식으로 리포지토리의 성능 개선
    @Query("select p from Project p left join fetch p.skills s left join fetch s.skill where p.isActive = :isActive")
    fun findAllByIsActive(isActive: Boolean): List<Project>

    @Query("select p from Project p left join fetch p.details where p.id = :id")
    override fun findById(id: Long): Optional<Project>  //project를 받음
}