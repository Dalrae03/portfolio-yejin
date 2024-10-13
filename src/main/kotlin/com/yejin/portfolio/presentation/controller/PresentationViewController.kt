package com.yejin.portfolio.presentation.controller

import com.yejin.portfolio.domain.constant.SkillType
import com.yejin.portfolio.presentation.service.PresentationService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

//viewController - 서버사이드 렌더링에서 사용
//서버에서 프론트 작업까지 다 해놓고 결과물인 html파일만 전달하는 것
//요즘, 확실히 대 고객 서비스는 프론트와 백을 분리해서 프론트랑 백이 api를 통해서 통신하는 방식을 많이 사용 (서버사이드 렌더링을 많이 안쓴다는 것 같다)
//프론트도 서버사이드 렌더링으로 개발해가지고 좀 더 백엔드 개발이라는 목적에 맞출 수 있다.

//MVC 패턴에서 컨트롤러 역할을 하는 클래스를 정의할 때 사용.(웹 페이지 렌더링) 이 어노테이션이 붙은 클래스는 HTTP 요청을 처리하고, 그에 맞는 뷰(view / html파일)를 반환
//@Controller는 주로 웹 애플리케이션에서 HTML 뷰나 JSP 같은 템플릿을 렌더링하는 데 사용. @RestController와 달리, 메서드의 반환값이 뷰 이름.
@Controller
class PresentationViewController(
    private val presentationService: PresentationService
) {
    //HTTP GET 요청을 처리하는 메서드를 정의할 때 사용되는 어노테이션
    //@RequestMapping(method = [RequestMethod.GET], name = "/test") 와 같은 기능을 한다.
    @GetMapping("/test")
    fun test(): String {
        return "test"  //return 하는 string과 같은 이름의 html파일을 찾아서 반환
    }


    @GetMapping("/")
    fun index(model: Model): String {  //index페이지에서는 두개의 테이블을 사용하니까 (introductions, links)
        val introductions = presentationService.getIntroductions()  //인트로덕션 디테일을 조회함
        //model.addAttribute(key, value) - 컨트롤러에서 데이터를 모델에다가 전달. view에서는 컨트롤러에서 직접 데이터를 받는게 아니고 그 모델에 들어가 있는 데이터를 꺼내 와서 자기의 뷰를 만들어서 클라이언트에게 전달이 되는 것.
        model.addAttribute("introductions", introductions)

        val links = presentationService.getLinks()
        model.addAttribute("links", links)
        return "presentation/index"  //presentation directory안에 있는 index를 찾아가지고 view resolver가 html파일을 전달할 것.
    }

    @GetMapping("/resume")
    fun resume(model: Model): String {
        val resume = presentationService.getResume()
        model.addAttribute("resume", resume)
        model.addAttribute("skillTypes", SkillType.values())  //.values() - 프론트에서 레즈메 페이지에서 스킬타입별로 스킬을 구분해가지고 보여줄 수 있다.
        return "presentation/resume"
    }

    @GetMapping("/projects")
    fun projects(model: Model): String {
        val projects = presentationService.getProjects()
        model.addAttribute("projects", projects)
        return "presentation/projects"  //return은 다른 확장자도 쓸 수 있지만, 기본적인 설정값은 html이다.
    }

}

//컨트롤러는 HTML 페이지를 사용자에게 보여주기 위한 역할을 하며, 웹 애플리케이션의 UI를 제공하는 데 사용. 주로 서버 측에서 템플릿 엔진(Thymeleaf, JSP 등)을 사용해 동적으로 HTML을 생성.
//사용자 인터페이스에서 데이터를 요청할 때는 PresentationApiController의 API를 사용하고, 사용자에게 보여줄 웹 페이지는 PresentationViewController를 통해 제공하는 방식