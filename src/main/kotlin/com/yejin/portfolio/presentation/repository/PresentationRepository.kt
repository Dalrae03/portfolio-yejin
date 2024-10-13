package com.yejin.portfolio.presentation.repository

import com.yejin.portfolio.domain.entity.*
import com.yejin.portfolio.domain.repository.*
import org.springframework.stereotype.Repository

//프레젠테이션 레포지토리는 디자인 패턴 중에 일종의 퍼사드 패턴의 기능을 하는 그런 클래스
//(퍼사드 패턴 - 복잡한 시스템을 단순화하여 클라이언트가 쉽게 접근할 수 있도록 인터페이스를 제공하는 디자인 패턴)
//전에 먼저 만들었던 repository파일 안의 리포지토리들은 데이터베이스랑 지접적으로 가장 단순한 상호작용을 하는 그런 코어적 기능
//presentation 레이어 안의 레포지토리(이거)는 도메인의 기능들을 활용하여 프레젠테이션 레이어에 필요한 데이터베이스, 조회, 저장같은 기능들을 한번 더 랩핑을 해서 사용을 하는 그런 목적
//프레젠테이션 레이어에 필요한 기능들을 여기에서 묶어가지고 관리하도록 함
//복잡한 데이터 액세스 로직을 단순화하고, 프레젠테이션 레이어에서 필요한 데이터를 손쉽게 조회할 수 있도록 도와주는 것
//레포지토리를 랩핑한 이 boxed(?)레포지토리를 만듦
@Repository
class PresentationRepository(
    private val achievementRepository: AchievementRepository,  //의존성 주입 6개
    private val experienceRepository: ExperienceRepository,
    private val introductionRepository: IntroductionRepository,
    private val linkRepository: LinkRepository,
    private val projectRepository: ProjectRepository,
    private val skillRepository: SkillRepository
) {
    fun getActiveAchievements(): List<Achievement> {
        return achievementRepository.findAllByIsActive(true)
    }

    fun getActiveExperiences(): List<Experience> {
        return experienceRepository.findAllByIsActive(true)
    }

    fun getActiveIntroductions(): List<Introduction> {
        return introductionRepository.findAllByIsActive(true)
    }

    fun getActiveLinks(): List<Link> {
        return linkRepository.findAllByIsActive(true)
    }

    fun getActiveProjects(): List<Project> {
        return projectRepository.findAllByIsActive(true)
    }

    fun getActiveSkills(): List<Skill> {
        return skillRepository.findAllByIsActive(true)
    }
}