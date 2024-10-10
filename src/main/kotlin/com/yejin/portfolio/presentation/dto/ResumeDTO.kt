package com.yejin.portfolio.presentation.dto

import com.yejin.portfolio.domain.entity.Achievement
import com.yejin.portfolio.domain.entity.Experience
import com.yejin.portfolio.domain.entity.Skill
import java.time.format.DateTimeFormatter


//이 페이지를 만든 이유 - resume페이지를 개발을 할 때, experience, achievement, skill, dto를 각각 가져와서 꺼내와서 보여주는 코드를 짜는 것 보다는 resumedto라는 통에 한꺼번에 넣어가지고 html로 주는게 코드가 구조상 간결한 것 같아 만듬
class ResumeDTO(
    experiences: List<Experience>,
    achievements: List<Achievement>,
    skills: List<Skill>
) {
    //ExperienceDTO 객체를 요소로 가지는 리스트
    //map 함수는 Kotlin에서 리스트나 컬렉션의 각 요소를 특정 변환 함수를 적용해 새로운 리스트로 변환할 때 사용
    var experiences: List<ExperienceDTO> = experiences.map {
        ExperienceDTO(
            title = it.title,
            description = it.description,
            startYearMonth = "${it.startYear}.${it.startMonth}",
            endYearMonth = it.getEndYearMonth(),
            details = it.details.filter { it.isActive }.map { it.content }
        )
    }
    var achievements: List<AchievementDTO> = achievements.map {
        AchievementDTO(
            title = it.title,
            description = it.description,
            host = it.host,
            achievedDate = it.achievedDate  //로컬date?. null아니면 포맷을해줄것임
                ?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                ?.replace("-", ".")
        )
    }
    var skills: List<SkillDTO> = skills.map { SkillDTO(it) }

}