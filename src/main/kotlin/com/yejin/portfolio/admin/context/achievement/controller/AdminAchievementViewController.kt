package com.yejin.portfolio.admin.context.achievement.controller

import com.yejin.portfolio.admin.context.achievement.service.AdminAchievementService
import com.yejin.portfolio.admin.data.DateFormElementDTO
import com.yejin.portfolio.admin.data.FormElementDTO
import com.yejin.portfolio.admin.data.SelectFormElementDTO
import com.yejin.portfolio.admin.data.TextFormElementDTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/achievement")
class AdminAchievementViewController(
    private val adminAchievementService: AdminAchievementService
) {  //화면을 리턴하는 컨트롤러
    @GetMapping
    fun achievement(model: Model): String {
        val formElements = listOf<FormElementDTO>(  //현 페이지에서 필요한 html import폼들의 리스트를 정해주는 것
            TextFormElementDTO("title", 4),  //size는 폼의 길이
            TextFormElementDTO(
                "description",
                8
            ),  //한 줄을 12개로 나누어서 (3333 이런식으로 나눠서 레이아웃을 잡을 수 있다) 4와 8합쳐서 12라는 것은 화면상에서 한줄에 타이틀과 디스크립션이 같이나올거라는 의미라고 보면 된다.
            DateFormElementDTO("achievedDate", 5),
            TextFormElementDTO("host", 5),
            SelectFormElementDTO(
                "isActive",
                2,
                listOf(true.toString(), false.toString())
            )  //true.toString(), false.toString() - true, false의 값이 화면에서 선택할 수 있는 셀렉트 형태로 보일 것
        )
        model.addAttribute("formElements", formElements)  //formElements란 이름으로 상단의 리스트 넣기

        val table = adminAchievementService.getAchievementTable()
        model.addAttribute("table", table)
        model.addAttribute("detailTable", null)  //화면 템플릿을 공통으로 쓸 거라서 테이블 디테일도 일단 모델에 추가해줘야 됨

        // hasdetails도 디테일 포함 여부에 따라 가직 약간의 레이아웃이 다를 수 있어가지고 화면과 관련된 설정들을 화면에서 다 다루지 않고 서버에서 컨트롤 할 수 있도록 이것들을 넣어두는 것
        val pageAttributes = mutableMapOf<String, Any>(
            //Key - String, Value - Any
            Pair("menuName", "Resume"),  //Pair - 코틀린에서 맵에 데이터 넣어줄때 씀
            Pair("pageName", table.name),
            //하위 세 개는 화면 단에서 공통적으로 이걸 사용해 가지고 테이블에 편집 버튼을 노출할지 말지, 삭제버튼을 노출할지 말지에 대해 결정하는 것
            Pair("editable", true),
            Pair("deletable", false),
            Pair("hasDetails", false),
        )
        model.addAllAttributes(pageAttributes)  //.addAllAttributes() - 여기에 맵을 넣으면, addAttribute처럼 key와 value를 모델에 넣어줌
        return "admin/page-table"
    }
}