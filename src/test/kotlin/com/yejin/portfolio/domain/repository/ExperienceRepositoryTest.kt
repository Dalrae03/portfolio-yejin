package com.yejin.portfolio.domain.repository

import com.yejin.portfolio.domain.entity.Experience
import com.yejin.portfolio.domain.entity.ExperienceDetail
//import org.assertj.core.api.Assertions
//import org.assertj.core.api.Assertions.*  //이거 왜 저절로 안생기지... 이거 있으면 Assertions.assertThat()을 assertThat()만으로도 간결하게 사용할 수 있는 것 같다
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest  //jpa관련 테스트를 할 때 사용하는 어노테이션. 이 테스트 코드가 실행이 될 때 jpa사용이 가능한 만큼 스프링 빈을 만들어 준다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  //TestInstance의 Lifecycle이 CLASS단위가 된다. (원래라면 New Experience Repository테스트 해서 메소드 하나씩 이것을 반복하지만, class레벨로 하면 한번 만들어가지고 그 인스턴스를 가지고 이 안에 있는 여러 메소드 들을 수행해주는 것
// 메소드마다 테스트 인스턴스를 만들어 가지고 테스틀 돌리면 장점 -> 메소드 간에 의존적이지 않은 것.(원칙에 더 맞음) / 클래스 간에 돌리면 메소드 간에 의존적이 된다
class ExperienceRepositoryTest(
    //@Autowired - 의존성 주입을 위해 사용하는 어노테이션. Spring이 관리하는 Bean을 자동으로 주입해 주는 역할. 개발자가 직접 객체를 생성하지 않고도 Spring이 자동으로 해당 객체를 인스턴스화하고 주입해 줌.
    @Autowired val experienceRepository: ExperienceRepository  //테스트할 대상을 주입받는다
) {
    val DATA_SIZE = 10  //테스트 데이터를 초기화 할 때, 나중에 결과를 검증할 때도 쓴다.

    //더미 객체 생성
    //테스트 데이터 초기화를 할 때 더미 엔티티를 만들어 주는 기능함
    //받은 n의 개수만큼 이 experience안에 디테일을 넣을 것
    private fun createExperience(n: Int): Experience {  //): 뒤 experience는 return값
        val experience = Experience(
            title = "${n}",  //문자열로 넣음, 데이터 검증할 때 사용
            description = "테스트 설명 {n}",
            startYear = 2023,
            startMonth = 9,
            endYear = 2023,
            endMonth = 9,
            isActive = true
        )
        val details = mutableListOf<ExperienceDetail>()
        for (i in 1..n) {
            val experienceDetail = ExperienceDetail(content = "테스트 ${i}", isActive = true)
            details.add(experienceDetail)
        }
        experience.addDetails(details)
        return experience
    }

    //테스트 데이터 초기화
    @BeforeAll
    fun beforeAll() {
        println("----- 데이터 초기화 이전 조회 시작 -----")
        val beforeInitialize = experienceRepository.findAll()
        assertThat(beforeInitialize).hasSize(0)  //테스트 검증 메소드. beforeInitialize에서 받은 데이터의 사이즈를 체크 (헤즈사이즈가 0이면 테스트 통과, 아니면 실패했다고 반환)
        println("----- 데이터 초기화 이전 조회 종료 -----")
        println("----- 테스트 데이터 초기화 시작 -----")
        val experiences =
            mutableListOf<Experience>()  // if) i=10이면, 타이틀이 10이고, 디테일즈를 10개 가지고있는 엔티티가 만들어져서 experiences로 들어갈 것이다.
        for (i in 1..DATA_SIZE) {
            val experience = createExperience(i)
            experiences.add(experience)
        }
        experienceRepository.saveAll(experiences)
        println("----- 테스트 데이터 초기화 종료 -----")
    }

    //테스트 메소드 작성
    //experience 만들 때 details를 추가해 준 것 만으로 saveall로 영속성에 집어 넣을 때 details도 같이 들어가서 데이터베이스에 들어갔는지 확인 가능
    @Test  //이게 있어야 이 메소드가 테스트 메소드로 인식이 되가지고 실행이 될 수있다.
    fun testFindAll() {
        println("----- findAll 테스트 시작 -----")
        val experiences = experienceRepository.findAll()
        assertThat(experiences).hasSize(DATA_SIZE)  //10개만큼 데이터를 가지고 있는지 검증한다.
        println("experiences.size: ${experiences.size}")
        for (experience in experiences) {
            assertThat(experience.details).hasSize(experience.title.toInt())  //반복하는 각각의 esperience에 대해서 details가 size를 가지고 있어야하는데, 그것이 experience의 title과 같은 숫자일테니 int로 바꿔서(.toInt()) 검증 하는 것이다
            println("experience.details.size: ${experience.details.size}")
        }
        println("----- findAll 테스트 종료 -----")
    }

    @Test
    fun testFindAllByIsActive() {
        println("----- findAllByIsActive 테스트 시작 -----")
        val experiences = experienceRepository.findAllByIsActive(true)  //ture인 것만 조회
        assertThat(experiences).hasSize(DATA_SIZE)
        println("experiences.size: ${experiences.size}")
        for (experience in experiences) {
            assertThat(experience.details).hasSize(experience.title.toInt())
            println("experience.details.size: ${experience.details.size}")
        }
        println("----- findAllByIsActive 테스트 종료 -----")
    }
}