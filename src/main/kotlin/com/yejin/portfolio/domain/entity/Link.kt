package com.yejin.portfolio.domain.entity

//import jakarta.persistence.*
import jakarta.persistence.*


@Entity
class Link(
    name: String,
    content: String, //새롭게 추가할 필드들
    isActive: Boolean
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    var id: Long? = null

    var name: String = name
    var content: String = content

    //is_Active 같은 jpa에서 알아서 맵핑될 수 있는 칼럼을 찾아가지고 설정한다.
    var isActive: Boolean = isActive
}