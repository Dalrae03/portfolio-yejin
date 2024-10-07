package com.yejin.portfolio.domain

import com.yejin.portfolio.domain.constant.SkillType
import com.yejin.portfolio.domain.entity.*
import com.yejin.portfolio.domain.repository.*
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Year

//스프링 IoC 컨테이너에 의해 관리되는 빈(Bean) 객체로 등록하기 위해 클래스 위에 붙이는 어노테이션 (스프링에서 관리하는 인스턴스(객체)를 빈이라고 부른다)
//개발자는 직접 객체 생성(생성자로 인스턴스를만들어서 사용)을 하지 않고도 스프링 컨테이너가 객체를 관리하고, 의존성 주입(Dependency Injection)을 할 수 있게 된다
//스프링이 처음에 실행이 되면서 컴포넌트 스캔이라는 과정을 거치는데 그 과정에서 컴포넌트 어노테이션이 붙은것들을 다 찾아서 인스턴스를 만들어가지고 별도로 관리를 한다
//다른 인스턴스에서 이렇게 만들어진 빈들을 사용하려면 그 인스턴스를 주입받아 사용하면 된다. (의존성 주입 / DI방법)
//Component, Controller, Service, Repository 등이 있다. / Controller, Service, Repository에는 conponent와 똑같은 기능을 하지만, 좀 더 구체적으로 클래스의 기능을 설명해주는 기능을 함.
//스프링이 스캔, 인스턴스 생성, DI로 등록을 함. -> 빈들을 사용할 때 생성자, 세터, 필드 주입 방식을 통해 의존성을 주입받아서 사용할 수 있다.
@Component
@Profile(value = ["default"])  //스프링 제공 어노테이션. 스프링이 빈으로 등록을 할 때, 프로필이 Default일때만 클래스 생성해서 빈으로 등록해라 / Data Initialier -> 프로필이 default일 때만 실행 됨
class DataInitialier(  //괄호안 -> 생성자 만들어주는 것(?)
    private val achievementRepository: AchievementRepository,  //스프링 빈 레포지토리들 (jpa 레포지토리 같다)
    private val introductionRepository: IntroductionRepository,
    private val linkRepository: LinkRepository,
    private val skillRepository: SkillRepository,
    private val projectRepository: ProjectRepository,
    private val experienceRepository: ExperienceRepository
) {
    //메인메소드가 실행이 되면 스프링을 구축할 텐데, 그때 Spring DI가 컴포넌트 스캔을 해 가지고 필요한 것을 찾고 인스턴스를 생성을하고 의존성을 주입을 하는 식으로 스프링픅고젝트를 컨스트럭트를 함
    //이렇게 스프링을 초기화 하는 작업이 완료가 되면은 이렇게 포스트 컨스트럭트가 붙은 메소드를 찾아서 한번 더 실행 -> 실행까지 한 다음에 스프링 실행이 완료되는 것
    //그래서 postConstruct에서 테스트 데이터들을 초기화 시켜주는 것.
    @PostConstruct
    fun initializeData() {
        //println 출력 운영에서 절대 사용 x.
        //대신 Logger 사용
        println("스프링이 실행되었습니다. 테스트 데이터를 초기화합니다.")  //엔티티 만들어서 주입받은 jpa 레포지토리들을 이용해서 데이터베이스에서 데이터를 넣어주는 작업

        //<Achievement> -> 하단의 mutable list는 achievement객체를 받는다는 것을 명시 (엔티티에 선언했던거 말하는 건가봐)
        val achievements = mutableListOf<Achievement>(
                Achievement(
                    title = "Catkao 해커톤 최우수상",
                    description = "고양이 쇼핑몰 검색 서비스의 아키텍처, 데이터 모델링, API 개발 역할 수행",
                    host = "캣카오",
                    achievedDate = LocalDate.of(2022, 8, 1),
                    isActive = true
                ),
                Achievement(
                    title = "정보처리기사",
                    description = "자료구조, 운영체제, 알고리즘, 데이터베이스 등",
                    host = "한국산업인력공단",
                    achievedDate = LocalDate.of(2020, 2, 2),
                    isActive = true
                )
        )
        //achievementRepository -> 생성자에서 주입받은 Spring Bean
        //AchievementRepository, Jpa레포지토리 상속
        //jpa레포지토리 -> 메소드들 많이 정리되어있음.
        //=> 스프링이 실행 되면서 만든 인터페이스, 그 인터페이스들이 상속을 하는 인터페이스들을 가지고 실제로 동작을 하는 기능을 가지고 있는 그런 코드들을 가지고 있는 레포지토리 클래스를 만들어줌
        //그 클래스가 빈으로 등록이 되는 것
        achievementRepository.saveAll(achievements)

        val introductions = mutableListOf<Introduction>(
            Introduction(content = "주도적으로 문제를 찾고, 해결하는 고양이입니다.", isActive = true),
            Introduction(content = "기술을 위한 기술이 아닌, 비즈니스 문제를 풀기 위한 기술을 추구합니다.", isActive = true),
            Introduction(content = "기존 소스를 리팩토링하여 더 좋은 구조로 개선하는 작업을 좋아합니다.", isActive = true)
        )
        //.saveAll() -> 여러 엔티티를 한 번에 저장하는 기능을 제공. 이 메서드는 리스트나 컬렉션 형태의 객체를 받아 데이터베이스에 저장하거나 업데이트하는 작업을 수행
        //여러 엔티티를 저장, 새 엔티티 저장 및 기존 엔티티 업데이트 하는 기능 수행
        introductionRepository.saveAll(introductions)

        val links = mutableListOf<Link>(
            Link(name = "Github", content = "https://github.com/infomuscle", isActive = true),
            Link(name = "Linkedin", content = "https://www.linkedin.com/in/bokeunjeong", isActive = true)
        )
        linkRepository.saveAll(links)

        val experience1 = Experience(
            title = "캣홀릭대학교(CatHolic Univ.)",
            description = "컴퓨터공학 전공",
            startYear = 2018,
            startMonth = 9,
            endYear = 2022,
            endMonth = 8,
            isActive = true
        )
        experience1.addDetails(
            mutableListOf(
                ExperienceDetail(content = "GPA 4.3/4.5", isActive = true),
                ExperienceDetail(content = "소프트웨어 연구 학회 활동", isActive = true)
            )
        )
        val experience2 = Experience(
            title = "주식회사 캣카오(Catkao Corp.)",
            description = "소셜서비스팀 백엔드 개발자",
            startYear = 2022,
            startMonth = 9,
            endYear = null,
            endMonth = null,
            isActive = true
        )
        experience2.addDetails(
            mutableListOf(
                ExperienceDetail(content = "유기묘 위치 공유 서비스 개발", isActive = true),
                ExperienceDetail(content = "신입 교육 프로그램 우수상 수상", isActive = true)
            )
        )
        experienceRepository.saveAll(mutableListOf(experience1, experience2))

        val java = Skill(name = "Java", type = SkillType.lANGUAGE.name, isActive = true)
        val kotlin = Skill(name = "Kotlin", type = SkillType.lANGUAGE.name, isActive = true)
        val python = Skill(name = "Python", type = SkillType.lANGUAGE.name, isActive = true)
        val spring = Skill(name = "Spring", type = SkillType.FRAMEWORK.name, isActive = true)
        val django = Skill(name = "Django", type = SkillType.FRAMEWORK.name, isActive = true)
        val mysql = Skill(name = "MySQL", type = SkillType.DATABASE.name, isActive = true)
        val redis = Skill(name = "Redis", type = SkillType.DATABASE.name, isActive = true)
        val kafka = Skill(name = "Kafka", type = SkillType.TOOL.name, isActive = true)
        skillRepository.saveAll(mutableListOf(java, kotlin, python, spring, django, mysql, redis, kafka))

        val project1 = Project(
            name = "유기묘 발견 정보 공유 서비스",
            description = "유기묘 위치의 실시간 공유, 임시보호까지 연결해주는 서비스. 구글 맵스를 연동하여 유기묘 위치 정보를 직관적으로 파악할 수 있도록 하는 사용자 경험 개선 작업.",
            startYear = 2022,
            startMonth = 9,
            endYear = 2022,
            endMonth = 12,
            isActive = true
        )
        project1.addDetails(
            mutableListOf(
                ProjectDetail(content = "구글 맵스를 활용한 유기묘 발견 지역 정보 제공 API 개발", url = null, isActive = true),
                ProjectDetail(content = "Redis 적용하여 인기 게시글의 조회 속도 1.5초 → 0.5초로 개선", url = null, isActive = true)
            )
        )
        project1.skills.addAll(
            mutableListOf(
                ProjectSkill(project = project1, skill = java),
                ProjectSkill(project = project1, skill = spring),
                ProjectSkill(project = project1, skill = mysql),
                ProjectSkill(project = project1, skill = redis)
            )
        )
        val project2 = Project(
            name = "반려동물 홈 카메라 움직임 감지 분석 모듈",
            description = "카메라에서 서버로 전달되는 신호를 분석하여 움직임이 감지될 경우 클라이언트에게 알림 발송 작업.",
            startYear = 2022,
            startMonth = 12,
            endYear = null,
            endMonth = null,
            isActive = true
        )
        project2.addDetails(
            mutableListOf(
                ProjectDetail(content = "PIL(Pillow) 활용하여 이미지 분석 기능 개발", url = null, isActive = true),
                ProjectDetail(content = "알림 발송을 비동기 처리하여 이미지 분석 - 알림 발송 기능간 의존도 감소", url = null, isActive = true),
                ProjectDetail(content = "Github Repository", url = "https://github.com/infomuscle", isActive = true)
            )
        )
        project2.skills.addAll(
            mutableListOf(
                ProjectSkill(project = project2, skill = python),
                ProjectSkill(project = project2, skill = django),
                ProjectSkill(project = project2, skill = kafka)
            )
        )
        projectRepository.saveAll(mutableListOf(project1, project2))
    }
}

