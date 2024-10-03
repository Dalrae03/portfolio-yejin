package com.yejin.portfolio.domain.entity

import jakarta.persistence.*


@Entity
class Experience(
    title: String,
    description: String,
    startYear: Int,
    startMonth: Int,
    endYear: Int?,
    endMonth: Int?,  //물음표는 null도 된다는 뜻
    isActive: Boolean
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    var id: Long? = null

    var title: String = title

    var description: String = description

    var startYear: Int = startYear

    var startMonth: Int = startMonth

    var endYear: Int? = endYear

    var endMonth: Int? = endMonth

    var isActive: Boolean = isActive


    //Experience Entitiy 같은 경우, Experience Detail이랑 1대 n관계를 가지고 있다.
    //Experiecne instance 한 개에 여러개의 Experience Detail이 들어갈 수 있는 구조
    //JPA에서 List로 n쪽에 해당하는 엔티티를 필드로 가져올 수 있다.
    //앞의 one이 experience가 되고 many 쪽이 experience detail이 된다. 그래서 그 두개의 관계가 1대다의 관계를 가지고 있다고 JPA에 알리는 기능을 함
    @OneToMany(
        targetEntity = ExperienceDetail::class,
        fetch = FetchType.LAZY, //Fetch type eager 도 있는데 이건 부모에 문제가 있으면 자식까지 싸그리 잡아서 뒤지는 타입이라 걍 쓰지마라 너무 비효율적이다
        cascade = [CascadeType.ALL]
    )  //영속성 콘텍스트와 관련있다. 이 엔티티에서 영속성 콘텍스트에 들어가거나 빠지거나 변화가 있었을 때 그 자식엔티티도 같이 넣어주냐 빼주냐 정하는 옵션 (ALL은 모든 변화에다가 자식엔티티도 똑같이 적용한다고 하는 것)
    @JoinColumn(name = "experience_id")  //맵핑의 기준이 뭔지, 그 맵핑의 기준이 되는 컬럼을 알려주는 기능을 함
    var details: MutableList<ExperienceDetail> = mutableListOf()  //처음 초기값 빈 리스트. experience detail에 접근할 수 있도록 함
    //mutable - 변화할 수 있다라는 의미

    fun getEndYearMonth(): String {  //화면에 표시할 종료연월을 표시, 가지고 올 때 이 함수 호출
        if (endMonth == null || endYear == null) {
            return "Present"
        }

        return "${endYear}.${endMonth}"  //2023.11
    }

    //받아온 데이터를 필드에다가 업데이트 해주는 기능
    //데이터 수정할 때 서비스 단에서 타이틀, 디스크립션 하나하나 호출할 필요 없이 update라고 선언하는 것 만으로 필드에있는 데이터를 바꿔 줄 수 있도록 했다.
    fun update(
        title: String,
        description: String,
        startYear: Int,
        startMonth: Int,
        endYear: Int?,
        endMonth: Int?,
        isActive: Boolean
    ) {
        this.title = title
        this.description = description
        this.startYear = startYear
        this.startMonth = startMonth
        this.endYear = endYear
        this.endMonth = endMonth
        this.isActive = isActive
    }  //JPA에서는 업데이트 해서 엔티티에다 데이터를 바꾸기만 해도 트랜잭션 끝날 때 알아서 JPA가 처음 데이터 가지고 올 때의 백업 스냡샷과 현 엔티티의 상태를 비교해가지고 수정된 부분이 있으면 알아서 업데이트 날려줌

    //null 체크를 포함한 기본 방어 로직을 포함해 가지고 사용하는 쪽에서 좀 더 깔끔하게 디테일 데이터를 추가할 수 있다.
    fun addDetails(details: MutableList<ExperienceDetail>?) {
        if (details != null) {
            this.details.addAll(details)
        }
    }
}