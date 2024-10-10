package com.yejin.portfolio.presentation.dto

//DTO 에 들어가는 자료형들은 스트링이나 인티저 같은 기본적인 문자, 숫자 이런것들을 넣어주는 것이 좋다.
//우리는 이것들을 json으로 바꾸기 때문에 json에서 지원하는 포맷들로 바꾸는 것이 제일 좋다.
data class ExperienceDTO(
    val title: String,
    val description: String,
    val startYearMonth: String?,
    val endYearMonth: String?,
    val details: List<String>
)