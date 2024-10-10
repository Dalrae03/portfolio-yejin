package com.yejin.portfolio.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//spring framework에서 웹 애플리케이션의 api를 개발할 때 사용되는 어노테이션
@RestController  //Spring에서 RESTful 웹 서비스를 개발할 때 사용. 클래스가 HTTP 요청을 처리하고, 그 결과를 JSON 또는 XML 형식으로 반환하는 컨트롤러임을 나타냄. @Controller와 @ResponseBody를 합친 역할, 따라서 각 메서드의 반환값이 자동으로 JSON 형식으로 변환
//return하는 것을 그대로 http리스폰스바디에 넣어가지고 돌려줌

@RequestMapping("/api")  //특정 URL 경로에 매핑되는 HTTP 요청을 처리하는 역할. 클래스 또는 메서드에 붙일 수 있으며, 이 경우 모든 API 요청이 /api 경로로 시작하는 URL에 매핑. ex) /api/test로 요청이 들어오면 해당 경로를 처리할 수 있는 메서드를 찾음
///api로 시작하는 HTTP 요청을 처리하는 REST API를 구현하고 있다.
class PresentationApiController {
    @GetMapping("/test")
    fun test(): String {
        return "OK"
    }
}