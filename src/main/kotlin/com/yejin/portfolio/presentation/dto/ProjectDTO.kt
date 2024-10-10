package com.yejin.portfolio.presentation.dto

import com.yejin.portfolio.domain.entity.Project

data class ProjectDTO(
    val name: String,
    val description: String,
    val startYearMonth: String,
    val endYearMonth: String?,
    val details: List<ProjectDetailDTO>,
    val skills: List<SkillDTO>?
) {
    constructor(project: Project) : this(
        name = project.name,
        description = project.description,
        startYearMonth = "${project.startYear}.${project.startMonth}", // 2023.9
        endYearMonth = project.getEndYearMonth(),
        //각각의 디테일을 넣어가지고 ProjectDetailDTO를 만들어서 리턴을 할 것. 그럼 리스트로 나옴
        details = project.details.filter { it.isActive }
            .map { ProjectDetailDTO(it) },  //엔티티를 다 필터링을 할 건데, isActive 활성화된것만 필터링을 할 것이다. (true만 통과됨)
        skills = project.skills.map { it.skill }.filter { it.isActive }.map { SkillDTO(it) }
    )
}