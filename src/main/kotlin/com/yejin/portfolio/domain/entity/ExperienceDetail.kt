package com.yejin.portfolio.domain.entity

import jakarta.persistence.*


@Entity
class ExperienceDetail(content: String, isActive: Boolean) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_detail_id")
    var id: Long? = null

    var content: String = content

    //is_Active 같은 jpa에서 알아서 맵핑될 수 있는 칼럼을 찾아가지고 설정한다.
    var isActive: Boolean = isActive

    fun update(content: String, isActive: Boolean) {
        this.content = content
        this.isActive = isActive
    }
}