package com.yejin.portfolio.domain.entity

//import jakarta.persistence.*
import com.yejin.portfolio.domain.constant.SkillType
import jakarta.persistence.*


@Entity
class Skill(
    name: String, type: String,  //어드민 프론트에서 데이터 받아서 세팅하는 건데 어드민에서는 타입같은거 알 방법이 없어 문자로 보내서 받는다
    isActive: Boolean
) : BaseEntity() {

    //생성자 내부적으로 타입 스트리에 마즌 스킬 타입을 찾아 필드에 넣을 것

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    var id: Long? = null

    var name: String = name

    @Column(name = "skill_type")
    @Enumerated(value = EnumType.STRING)  //자료형이 인원클래스일 때 쓰는 어밋테이션. 이넘타입쓸때, 항상 string 으로 해주는 게 좋다
    var type: SkillType =
        SkillType.valueOf(type)  //type문자열과 일치하는 enum을 찾아서 반환을 해준다 (따옴표에 둘러싸인 languae라는걸 valueof에 넣으면 enum의 language를 찾아서 리턴해주는 방식)
    var isActive: Boolean = isActive
}