package com.yejin.portfolio.domain.entity

import jakarta.persistence.*


@Entity
class Introduction(
    content: String,  //새롭게 추가할 필드들
    isActive: Boolean  //새롭게 추가할 필드들
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "introduction_id")
    var id: Long? = null

    var content: String = content

    var isActive: Boolean = isActive
}