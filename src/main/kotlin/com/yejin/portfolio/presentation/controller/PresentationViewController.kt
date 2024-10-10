package com.yejin.portfolio.presentation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

//MVC 패턴에서 컨트롤러 역할을 하는 클래스를 정의할 때 사용. 이 어노테이션이 붙은 클래스는 HTTP 요청을 처리하고, 그에 맞는 뷰(view / html파일)를 반환
//@Controller는 주로 웹 애플리케이션에서 HTML 뷰나 JSP 같은 템플릿을 렌더링하는 데 사용. @RestController와 달리, 메서드의 반환값이 뷰 이름.
@Controller
class PresentationViewController {
    //HTTP GET 요청을 처리하는 메서드를 정의할 때 사용되는 어노테이션
    //@RequestMapping(method = [RequestMethod.GET], name = "/test") 와 같은 기능을 한다.
    @GetMapping("/test")
    fun test(): String {
        return "test"  //return 하는 string과 같은 이름의 html파일을 찾아서 반환
    }
}