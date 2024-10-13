package com.yejin.portfolio.presentation.dto


//DTO - 데이터 트랜스퍼 오브젝트. 테이터를 담는 통 같은 역할을 하는 자바객체.
//클라이언트에다가 데이터 전달을 할 때 엔티티를 직접 두는게 아니고 dto에다가 엔티티에 있는 데이터를 담아 가지고 전달을 할 것.
//이유 1. 엔티티를 클라이언트에 바로 전달하는 것은 일반적으로 좋은 방법이 아님.
//전달하고자 하는 데이터만 dto에 담아가지고 전달해서 레이어를 분리시킴.
//이유 2. jpa같은 경우 open session inview라는 옵션이있음. 설정에 따라서 브라우저에서 데이터를 수정을 한게 개발자가 의도하지않은 방식대로 db에 수정이 될 수 있는 것을 방지하기 위함
//엔티티는 서비스내부적으로만 사용하고, 외부로 노출되는 것은 dto에 담아서 전달하는 구조

//data calss - Kotlin에서 정말 dto목적으로 사용하는 클래스를 좀 더 정확히, 좀 더 유용하게 사용할 수 있도록 해주는 것.
//기능 - to string(?)을 했을 때 보통 자바 객체를 string으로 하면 레퍼런스, 참조값을 준다. but 데이터 클래스에서 to string을 쓰면 데이터의 필드들이 담고 있는 내용을 key Value형식으로 프린트 해준다. (오버라이딩 불필요)
data class AchievementDTO(
    val title: String,
    val description: String,
    val host: String,
    val achievedDate: String?
)