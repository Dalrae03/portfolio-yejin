package com.yejin.portfolio.presentation.interceptor

import com.yejin.portfolio.domain.entity.HttpInterface
import com.yejin.portfolio.domain.repository.HttpInterfaceRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

//spring에서 제공하는 interceptor 기능을 사용할 거라서 스프링 빈으로 등록이 되어야하기 때문에 component 선언
@Component
//인터셉터의 기능 - 이 페이지(사이트)에 들어오는 모든 방문자의 클라이언트 정보를 저장한다.
class PresentationInterceptor(
    private val httpInterfaceRepository: HttpInterfaceRepository  //HttpInterfaceRepository - 이것을 사용하여 그 인터셉트 기능 구현
) : HandlerInterceptor {
    //사용자가 우리의 사이트에 접속을 할 때마다 (즉 컨트롤러로 요청을 보낼 때마다) 하단의 함수 실행됨
    //컨트롤러가 요청을 처리하고 그 다음에 응답을 할 때 그 시점에서 이 기능이 동작.
    //최종적으로 어떤 요청이 들어왔는지 알 수 있고 원한다면 어떤 응답을 줬는지도 저장을 할 수가 있음.
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val httpInterface = HttpInterface(request)
        httpInterfaceRepository.save(httpInterface)  //.save - 단권의 엔티티를 저장을 할 때 사용
    }

}
//여기서 오버라이드 메소드 할 수 있는 것들
//preHandle - 컨트롤러까지 요청이 가기 전에 동작을 함.
//postHandle - 요청이 간 후에 동작을 함. (응답을 준 후). 컨트롤러가 리턴을 한 다음에 동작을 하는 그런 기능
//afterCompletion - 컨트롤러가 응답을 준 다음에 동작을 하는 그런 기능.
//postHandle, afterCompletion 둘의 차이
//(postHadle - 컨트롤러가 제대로 리턴을 못하고 예외를 던지면 동작하지 않음.
//afterCompletion - 예외를 던지더라도 항상 동작을 함. 컨트롤러에 대해서 예외의 발생에 관계없이 동작해야 되는 기능을 개발을 한다면 이거 사용)