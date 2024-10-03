package com.yejin.portfolio.domain.entity

import jakarta.persistence.*


@Entity
class ProjectDetail(content: String, url: String?, isActive: Boolean) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_detail_id")
    var id: Long? = null

    var content: String = content

    var url: String? = url

    //is_Active 같은 jpa에서 알아서 맵핑될 수 있는 칼럼을 찾아가지고 설정한다.
    var isActive: Boolean = isActive

    fun update(content: String, url: String?, isActive: Boolean) {
        this.content = content
        this.url = url
        this.isActive = isActive
    }

}