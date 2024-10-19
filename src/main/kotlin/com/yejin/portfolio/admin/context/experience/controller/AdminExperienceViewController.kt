package com.yejin.portfolio.admin.context.experience.controller

import com.yejin.portfolio.admin.context.experience.service.AdminExperienceService
import com.yejin.portfolio.admin.data.FormElementDTO
import com.yejin.portfolio.admin.data.SelectFormElementDTO
import com.yejin.portfolio.admin.data.TextFormElementDTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/experience")
//화면 조회하는 것
class AdminExperienceViewController(
    private val adminExperienceService: AdminExperienceService
) {
    @GetMapping
    fun experience(model: Model): String {
        val formElements = listOf<FormElementDTO>(
            TextFormElementDTO("title", 4),
            TextFormElementDTO("description", 8),
            SelectFormElementDTO("startYear", 3, (2010..2030).toList()),
            SelectFormElementDTO("startMonth", 2, (1..12).toList()),
            SelectFormElementDTO("endYear", 3, (2010..2030).toList()),
            SelectFormElementDTO("endMonth", 2, (1..12).toList()),
            SelectFormElementDTO("isActive", 2, listOf(true.toString(), false.toString()))
        )
        model.addAttribute("formElements", formElements)

        //디테일에 대한 form element
        val detailFormElements = listOf<FormElementDTO>(
            TextFormElementDTO("content", 10),
            SelectFormElementDTO("isActive", 2, listOf(true.toString(), false.toString()))
        )
        model.addAttribute("detailFormElements", detailFormElements)

        val table = adminExperienceService.getExperienceTable()
        model.addAttribute("table", table)

        val detailTable =
            adminExperienceService.getExperienceDetailTable(null)  //맨 처음 화면을 조회할 때는 딱히 선택한 experience Entity가 없기 때문에 id는 null로 해서 그냥 빈 리스트 리턴
        model.addAttribute("detailTable", detailTable)

        val pageAttributes = mutableMapOf<String, Any>(
            Pair("menuName", "Resume"),
            Pair("pageName", table.name),
            Pair("editable", true),
            Pair("deletable", false),
            Pair("hasDetails", true),
        )
        model.addAllAttributes(pageAttributes)

        return "admin/page-table"
    }
}