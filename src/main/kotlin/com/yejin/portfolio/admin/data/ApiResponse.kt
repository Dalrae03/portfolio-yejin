package com.yejin.portfolio.admin.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


//ApiResponse는 generic.
//만들 컨트롤러에서 주는 응답의 포맷
class ApiResponse<T>(status: HttpStatus) : ResponseEntity<T>(status) {

    //스테틱메소드를 만들기 위해서 companion object정의. 여기에 정의되는 메소드들이 스테틱 메소드가 되는 것
    companion object {
        fun successCreate(): ResponseEntity<Any> {  //<Any>모든 타입 다받
            return ok("데이터가 저장되었습니다.")  //200응답 준다. 바디에 들어온 내용을 받아가지고 어드민 페이지에서 저장버튼을 누를 때 성공했으면 이 메세지를 띄워주는 것
            //실패하면 전에 만든 컨트롤러 어드바이스에서 정의했던 e.message의 내용이 들어갈 것
        }

        fun successUpdate(): ResponseEntity<Any> {
            return ok("데이터가 수정되었습니다.")
        }

        fun successDelete(): ResponseEntity<Any> {
            return ok("데이터가 삭제되었습니다.")
        }
    }
}