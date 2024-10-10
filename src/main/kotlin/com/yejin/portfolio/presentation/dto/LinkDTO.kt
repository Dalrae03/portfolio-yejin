package com.yejin.portfolio.presentation.dto

import com.yejin.portfolio.domain.entity.Link

data class LinkDTO(
    val name: String,
    val content: String
) {
    //constructor - Kotlin에서 생성자를 정의할 때 사용하는 키워드. constructor 키워드를 사용해 보조 생성자를 정의
    //생성자는 클래스의 인스턴스를 만들 때 호출되며, 클래스의 초기화를 담당
    constructor(link: Link) : this(
        name = link.name.lowercase(),  //이 네임값으로 부트스트랩 아이콘에서 거기에 해당하는 아이콘 찾아가지고올 것
        content = link.content
    )
}