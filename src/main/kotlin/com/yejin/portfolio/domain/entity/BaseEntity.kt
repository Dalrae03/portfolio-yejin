package com.yejin.portfolio.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {

    @CreatedDate  //JPA엔티티가 생성된 시간을 자동으로 세팅
    @Column(nullable = false, updatable = false)  //이 필드는 값이 항상 있어야하고, 업데이트가 불가하다. (값이 변경되면 jpa에서 오류가 날것이다.)
    var createdDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate  //JPA엔티티가 이 데이터가 마지막으로 수정된 일시가 언제인지 지정
    @Column(nullable = false)
    var updatedDateTime: LocalDateTime = LocalDateTime.now()

}