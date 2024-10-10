package com.yejin.portfolio.presentation.dto

import com.yejin.portfolio.domain.entity.Introduction

data class IntroductionDTO(
    val content: String
) {
    //생성자 한번 오버라이딩 (보다는 추가적으로 만들어주는 것이긴 한데)
    //엔티티를 그대로 받아옴
    //achievementdto와 experiencedto에서 안하는 이유는 resumedto에서 한번 사용할 것이라서 안사용하는 것 (저 둘도 엔티티 그대로 받아와도 되는것같이 말씀)
    constructor(introduction: Introduction) : this(
        content = introduction.content
    )
}