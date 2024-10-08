package com.yejin.portfolio.domain.entity

import jakarta.persistence.*


@Entity
class Project(
    name: String,
    description: String,
    startYear: Int,
    startMonth: Int,
    endYear: Int?,
    endMonth: Int?,  //물음표는 null도 된다는 뜻
    isActive: Boolean
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    var id: Long? = null

    var name: String = name
    var description: String = description
    var startYear: Int = startYear
    var startMonth: Int = startMonth
    var endYear: Int? = endYear
    var endMonth: Int? = endMonth
    var isActive: Boolean = isActive

    @OneToMany(
        targetEntity = ProjectDetail::class,
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST]
    )  //프로젝트가 저장되거나 삭제될 때, 연관된 모든 ProjectDetail들도 함께 저장되거나 삭제
    @JoinColumn(name = "project_id")
    var details: MutableList<ProjectDetail> = mutableListOf()

    //mappedby 프로젝트에 의해서 맵핑이 되는 거고 맵핑을 하는 친구는 프로젝트 스킬인것이다. 프로젝트 스킬이 주인
    @OneToMany(
        mappedBy = "project",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST]
    )  //mappedBy - 양방향 연관관계에서의 주인을 지정을 할 때 사용. 프로젝트 스킬 안에다 프로젝트라는 것을 추가할건데 그안에 있을 프로젝트를 통해서 맵핑이 된다는 의미.
    var skills: MutableList<ProjectSkill> = mutableListOf()
    fun getEndYearMonth(): String {  //화면에 표시할 종료연월을 표시, 가지고 올 때 이 함수 호출
        if (endYear == null || endMonth == null) {
            return "Present"
        }
        return "${endYear}.${endMonth}"
    }

    fun update(
        name: String,
        description: String,
        startYear: Int,
        startMonth: Int,
        endYear: Int?,
        endMonth: Int?,
        isActive: Boolean
    ) {
        this.name = name
        this.description = description
        this.startYear = startYear
        this.startMonth = startMonth
        this.endYear = endYear
        this.endMonth = endMonth
        this.isActive = isActive
    }  //JPA에서는 업데이트 해서 엔티티에다 데이터를 바꾸기만 해도 트랜잭션 끝날 때 알아서 JPA가 처음 데이터 가지고 올 때의 백업 스냡샷과 현 엔티티의 상태를 비교해가지고 수정된 부분이 있으면 알아서 업데이트 날려줌

    //null 체크를 포함한 기본 방어 로직을 포함해 가지고 사용하는 쪽에서 좀 더 깔끔하게 디테일 데이터를 추가할 수 있다.
    fun addDetails(details: MutableList<ProjectDetail>?) {
        if (details != null) {
            this.details.addAll(details)
        }
    }
}