package com.yejin.portfolio.presentation.service

import com.yejin.portfolio.presentation.dto.IntroductionDTO
import com.yejin.portfolio.presentation.dto.LinkDTO
import com.yejin.portfolio.presentation.dto.ProjectDTO
import com.yejin.portfolio.presentation.dto.ResumeDTO
import com.yejin.portfolio.presentation.repository.PresentationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PresentationService(
    private val presentationRepository: PresentationRepository  //생성자 의존성 주입
) {
    //인덱스 페이지, 메인 페이지를 호출하는 컨트롤러에서 호출.
    @Transactional(readOnly = true)  //메소드 안에 있는 모든 동작들이 한 개의 트랜잭셔너로 묶임 / readOnly - 스냅샷 뜨는 작업 생략. 업데이트 안되기 때문. 성능의 이점으로 조회성 메소드들은 옵션을 넣는 것이 좋음
    fun getIntroductions(): List<IntroductionDTO> {  //introductions - ntroduction데이터를 조회해옴
        val introductions = presentationRepository.getActiveIntroductions()  //인트로덕션 엔티티의 리스트
        return introductions.map { IntroductionDTO(it) }  //리스트에서 각각의 인트로덕션을 여기 넣어가지고 만들어진 dto의 리스트를 return해주는 것
    }

    @Transactional(readOnly = true)
    fun getLinks(): List<LinkDTO> {
        val links = presentationRepository.getActiveLinks()
        return links.map { LinkDTO(it) }
    }

    //Resume페이지에서 사용
    @Transactional(readOnly = true)
    fun getResume(): ResumeDTO {
        val experiences = presentationRepository.getActiveExperiences()
        val achievements = presentationRepository.getActiveAchievements()
        val skills = presentationRepository.getActiveSkills()
        return ResumeDTO(
            experiences = experiences,
            achievements = achievements,
            skills = skills
        )
    }

    //프로젝트페이지에서 사용
    @Transactional(readOnly = true)
    fun getProjects(): List<ProjectDTO> {
        val projects = presentationRepository.getActiveProjects()
        return projects.map { ProjectDTO(it) }  //각각의 프로젝트 엔티티를 project.dto로 바꿔가지고 그 리스트 반환
    }

}