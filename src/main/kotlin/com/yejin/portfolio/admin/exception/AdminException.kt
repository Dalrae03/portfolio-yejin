package com.yejin.portfolio.admin.exception

import org.springframework.http.HttpStatus


//http상태에 따라 응답을 줘야하는데, 오류의 유형별로ㅗ http상태를 지정할 수 있게 하기 위해서 adminException에다가 어떤 상태값을 줄지 미리 정할 것
//그 상태갑을 기반으로 리스폰스 엔티티에서 http상태를 세팅햇서 클라이언트에게 응답을 주도록 하는 구조를 잡을 것
//직접 사용하지 않을 것. abstract 추상클래스 선언. 형태코드 유형별로 exception을 다 분리할 것
abstract class AdminException(
    httpStatus: HttpStatus,
    message: String  //커스텀마이징된 메세지 줄 수 있도록 함
) : RuntimeException(message) {
    val httpStatus: HttpStatus = httpStatus  //생성자에서 받은 값을 넣을 수 있도록 할 것
}