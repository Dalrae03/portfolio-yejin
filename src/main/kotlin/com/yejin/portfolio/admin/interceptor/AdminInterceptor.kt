package com.yejin.portfolio.admin.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

@Component
class AdminInterceptor : HandlerInterceptor {
    //오류가 발생했을때 실행될 필요가 없어서 postHandle 오버라이딩
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?  //이 인터셉터는 모델만 받지 않고 모델 앤 뷰라고 모델과 뷰 데이터를 같이 갖고있는 객체를 받을 것
    ) {
        val menus = listOf<MenuDTO>(
            MenuDTO(
                name = "Index",
                pages = listOf<PageDTO>(
                    PageDTO(name = "Introduction", url = "/admin/introduction"),
                    PageDTO(name = "Link", url = "/admin/link")
                )
            ),
            MenuDTO(
                name = "Resume",
                pages = listOf<PageDTO>(
                    PageDTO(name = "Experience", url = "/admin/experience"),
                    PageDTO(name = "Achievement", url = "/admin/achievement"),
                    PageDTO(name = "Skill", url = "/admin/skill")
                )
            ),
            MenuDTO(
                name = "Projects",
                pages = listOf<PageDTO>(
                    PageDTO(name = "Project", url = "/admin/project"),
                    PageDTO(name = "ProjectSkill", url = "/admin/project/skill")
                )
            )
        )

        modelAndView?.model?.put("menus", menus)  //이름은 menus, 우리가 만든 menus 이 리스트 넣음
    }

}