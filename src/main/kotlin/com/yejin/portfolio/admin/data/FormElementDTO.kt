package com.yejin.portfolio.admin.data


//FormElementDTO - 데이터 클래스. 이것을 상속하여 텍스트, 데이트, 셀렉트 등 각 유형의 그 html form에 대응 할 수 있도록 구체적인 클래스를 만들 것
//abstract - 따라서 이것만 가지고 그대로 사용할 일이 없어 abstract로 추상 클래스를 만들어 준다
abstract class FormElementDTO(
    val name: String,
    val size: Int,
    val type: String
    //이 세개를 가지고 어드밋 페이지 화면에서 데이터를 넣는 form의 크기라던가 이름이라던가 종류등을 결정하게 해줄 것.
)


//html의 다양한 input폼 형태의 유형을 정해주는 것.
//서버에서 필요한 폼들의 리스트를 던져주면 그 리스트 안의 내용들이 상단의 name, size, type가 될거고 프론트에서 이 개체안에 있는 3가지 것들을 가지고 type을 구분하여 각각의 경우에 맞게 폼을 그려줄 수 있게 선택을 하는 것
class TextFormElementDTO(
    name: String,
    size: Int
) : FormElementDTO(name = name, size = size, type = "text")  //FormElementDTO 상속받음

class DateFormElementDTO(
    name: String,
    size: Int
) : FormElementDTO(name = name, size = size, type = "date")

class SelectFormElementDTO(
    name: String,
    size: Int,
    options: List<Any>
) : FormElementDTO(name = name, size = size, type = "select") {
    val options: List<Any> = options
}