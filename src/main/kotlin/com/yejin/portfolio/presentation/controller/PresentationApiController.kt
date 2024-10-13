package com.yejin.portfolio.presentation.controller

import com.yejin.portfolio.presentation.dto.IntroductionDTO
import com.yejin.portfolio.presentation.dto.LinkDTO
import com.yejin.portfolio.presentation.dto.ProjectDTO
import com.yejin.portfolio.presentation.dto.ResumeDTO
import com.yejin.portfolio.presentation.service.PresentationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


//apiController - 데이터의 전달에 집중
//요즘 대중적인 방식 - 클라이언트 사이드 렌더링
//클라이언트 사이드 렌더링을 할 때 프론트엔드 개발자가 프론트엔드 쪽 프로그램을 별도로 만들고 서버 쪽은 데이터의 처리에만 집중을 해서 프론트랑 백이랑 데이터만 주고받기함
//=>프론트는 백에서 받은 데이터를 본인들의 프로그램에서 보여주는 그런걸 개발하는 것.

//spring framework에서 웹 애플리케이션의 api를 개발할 때 사용되는 어노테이션
//이 어노테이션은 해당 클래스가 RESTful API 요청을 처리할 때 사용. 즉, HTTP 요청을 받고, 그 응답을 JSON, XML 등과 같은 데이터 형식으로 반환하는 데 사용. 일반적으로 API를 제공할 때 사용.
@RestController  //Spring에서 RESTful 웹 서비스를 개발할 때 사용. 클래스가 HTTP 요청을 처리하고, 그 결과를 JSON 또는 XML 형식으로 반환하는 컨트롤러임을 나타냄. @Controller와 @ResponseBody를 합친 역할, 따라서 각 메서드의 반환값이 자동으로 JSON 형식으로 변환
//return하는 것을 그대로 http리스폰스바디에 넣어가지고 돌려줌

@RequestMapping("/api")  //특정 URL 경로에 매핑되는 HTTP 요청을 처리하는 역할. 클래스 또는 메서드에 붙일 수 있으며, 이 경우 모든 API 요청이 /api 경로로 시작하는 URL에 매핑. ex) /api/test로 요청이 들어오면 해당 경로를 처리할 수 있는 메서드를 찾음
///api로 시작하는 HTTP 요청을 처리하는 REST API를 구현하고 있다.
class PresentationApiController(
    private val presentationService: PresentationService
) {
    @GetMapping("/test")  //값이 json으로 반환
    fun test(): String {
        return "OK"
    }

    //브라우저에서는 주소창에 입력을 하면 항상 http-get으로 간다
    //-> get-mapping이 이 주소를 찾아, PresentationApiController에서 getIntroductions() 호출을 하고 .getIntroductions()이게 호출이 되고, 그 결과가 반환이 되는데
    //스프링부터에서 알아서 List<IntroductionDTO>을 제이슨 포멧의 스트링으로 바꿔 가지고 바디에 넣어주는 것.
    //브라우저에서는 바디에 있는 내용을 그대로 보여줌.
    @GetMapping("/v1/introductions")  //api관리위해 버전링을 많이 함
    fun getIntroductions(): List<IntroductionDTO> {
        return presentationService.getIntroductions()  //중요한 작업들은 service에서 해줌
    }

    @GetMapping("/v1/links")
    fun getLinks(): List<LinkDTO> {
        return presentationService.getLinks()
    }

    @GetMapping("/v1/resume")
    fun getResume(): ResumeDTO {
        return presentationService.getResume()
    }

    @GetMapping("/v1/projects")
    fun getProjects(): List<ProjectDTO> {
        return presentationService.getProjects()  //각각 뒤에있는것들(ex.ProjectDTO) 조회해서 리턴하는 역할들
    }
}


//일반적으로 서버랑 서버 간의 통신을 하거나 아니면 이제 클라이언트 사이드 렌더링 방식을 사용을 해서 프론트와 백이 통신을 할 때는 json으로 데이터를 주고받음

//이 컨트롤러는 HTML이나 뷰를 반환하는 대신, 데이터를 클라이언트에 제공하는 데 중점. 보통 프론트엔드 애플리케이션(React, Vue.js 같은 SPA)에서 백엔드 API와 통신할 때 사용.