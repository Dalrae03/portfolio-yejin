package com.yejin.portfolio.admin.data


//FormElementDTO - 데이터 클래스. 이것을 상속하여 텍스트, 데이트, 셀렉트 등 각 유형의 그 html form에 대응 할 수 있도록 구체적인 클래스를 만들 것
//abstract - 따라서 이것만 가지고 그대로 사용할 일이 없어 abstract로 추상 클래스를 만들어 준다
abstract class FormElementDTO(
    val name: String,
    val size: Int,
    val type: String
    //이 세개를 가지고 어드밋 페이지 화면에서 데이터를 넣는 form의 크기라던가 이름이라던가 종류등을 결정하게 해줄 것.
)