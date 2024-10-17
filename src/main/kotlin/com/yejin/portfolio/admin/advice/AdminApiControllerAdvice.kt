package com.yejin.portfolio.admin.advice

import com.yejin.portfolio.admin.exception.AdminException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//인터셉터와 같은 개념인데 예외 처리에 특화된 그런 인터셉터
//컨트롤러가 예외를 던졌을 때 그걸 잡아 가지고 그 예외에 대한 대응을 해주는 그런 기능
//ControllerAdvice - ExceptionHandler을 모아가지고 공통으로 처리하는 기능

//오류가 발생해서 예외를 던졌는데 그 예외에 대해서 트라이캐치해서 핸들링을 해놨으면 컨드롤러까지 오기 전에 예외가 먹혀서 처리가 되어 서비스 상에 이상이 없을텐데,
//그런 예외처리를 하지 못한경우, 아니면 서비스가 너무 복잡해 가지고 하나하나 예외처리를 해주기 복잡한 경우에
// 컨트롤러까지 와가지고 컨트롤러에서 예외를 던지게 하면 결국 이 컨트롤러 어드바이스라는 곳에서(정확히는 Exception Handler)여기에서 처리가 될 거니까 예외에 대한 관리를 더욱더 용이하게 할 수 있다.

@RestControllerAdvice
class AdminApiControllerAdvice {
    //로그를 코틀린에서 가져오려면 로거 팩토리에서 Get Logger라는 것에 사용하는 클래스 정보를 넣어줌
    val log = LoggerFactory.getLogger(AdminApiControllerAdvice::class.java)

    //@ExceptionHandler - 메소드 레벨에 붙이는 것. 컨트롤러 안에 넣어도 됨. 컨트롤러 안에 넣으면 해당 컨트롤러에 있는 메소드들이 예외를 던지면 이 하단 함수가 잡아서 그 예외에 대한 처리를 해줌
    @ExceptionHandler
    fun handleException(e: AdminException): ResponseEntity<String> {  //ResponseEntity<String> - string을 받는 리턴타입
        log.info(
            e.message,
            e
        )  //e 예외가 가지고있는 오류가(message)가 출력이 되고, 오류의 스택트레이스(오류가 어디에서 발생했는지 그 라인바이 라인으로 찍어주는 로그. / 후반의 ', e' 역할)를 찍어줄 것

        return ResponseEntity.status(e.httpStatus).body(e.message)  //.status - http상태코드 넣기 가능
    }  //ResponseEntity후반함수로 미리 정의된http상태를 줄 수 있도록 되어있다

    //MethodArgumentNotValidException - validation에서 던지는 에러
    //validation에 데이터 검증 역할을 위임을 해서, validation에서 오류가 발생하면 던지는 예외, 그 예외에 대해서 처리를 해주기위한 목적
    @ExceptionHandler
    fun handleExcpetion(e: MethodArgumentNotValidException): ResponseEntity<String> {
        log.info(e.message, e)
        //bindingResult 안에 어떤 검증에서 오류가발생했는지 정보를 들고있다ㅣ.
        val fieldError = e.bindingResult.fieldErrors[0]
        val message = "[${fieldError.field}] ${fieldError.defaultMessage}"  //어떤 필드, 어떤 오류

        //이 함수같은경우는 데이터검증을 실패한 거니까 클라이언트이 오류일 가능성이 높기 때문에, 클라이어ㅓㄴ트의 오류라 가정을 하고 bedRequest응답을 줄것
        return ResponseEntity.badRequest().body(message)
    }

    //예상하지못한 오류가 발생했을 때 그것까지도 잡아 가지고 항상 공통된 응답관리를 해주기위한 목적
    //-> 서버문제의 가능성이 높다고 보고 인터널 서버 에러 응답을 줌
    @ExceptionHandler
    fun handleException(e: Exception): ResponseEntity<String> {
        log.info(e.message, e)

        return ResponseEntity.internalServerError().body("시스템 오류가 발생했습니다.")
    }
}