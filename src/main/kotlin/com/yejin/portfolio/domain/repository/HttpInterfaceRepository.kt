package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.HttpInterface
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface HttpInterfaceRepository : JpaRepository<HttpInterface, Long> {
    //데시보드에서 날짜별로 방문자 통계 가져올거임
    //CreatedDateTime이 시작 LocalDateTime과 종료 LocalDateTime 사이의 모든 데이터를 세서 넣어주는 것
    fun countAllByCreatedDateTimeBetween(start: LocalDateTime, end: LocalDateTime): Long
}