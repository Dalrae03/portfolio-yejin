package com.yejin.portfolio.presentation.service

import com.yejin.portfolio.domain.entity.Introduction
import com.yejin.portfolio.domain.entity.Link
import com.yejin.portfolio.presentation.repository.PresentationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

//단위 테스트 - 테스트하고자 하는 대상의 기능에 집중을 해 가지고 그거 하나만 딱 검증을 한다는 개념
//일반적으로 Mockito 라이브러리를 활용. 테스트하고자하는 대상이 의존을 하는 다른것을 모킹하여 테스트 진행

//목(Mock): 목은 테스트에서 사용하는 가짜 객체.
//실제 구현을 호출하지 않고, 미리 정의된 동작을 수행하도록 만들어진 객체.
//ex) 예를 들어, 데이터베이스에 접근하는 Repository를 테스트할 때, 실제 DB에 접근하지 않고 목을 사용하여 원하는 데이터만 반환하도록 할 수 있다. 이로 인해 외부 의존성을 배제하고 비즈니스 로직만 테스트할 수 있게 해줌.

//모킹(Mocking): 모킹은 목 객체의 동작을 정의하는 것을 의미.
//위 코드에서 Mockito.when(presentationRepository.getActiveIntroductions())은 presentationRepository가 호출되었을 때 어떤 데이터를 반환할지 미리 정의하는 모킹 과정. 이렇게 하면 실제 데이터베이스에 접근하지 않고도, 특정 조건에 대한 동작을 정의하여 테스트할 수 있다.

@ExtendWith(MockitoExtension::class)
class PresentationServiceTest {

    //목(모방)을 만들어서, 그 목을 주입을 할 대상을 말한다.
    //목을 주입 받아가지고 동작을 해야 되는 저희가 실제로 테스트를 할 대상.
    @InjectMocks
    lateinit var presentationService: PresentationService
    //lateinit - 원래의 코틀리는 null을 허용x. 초기값이 필요하지만 초기값에서 넣어줄 수 없는 경우 이것을 이용하여 초기화를 늦추는 것.

    @Mock
    lateinit var presentationRepository: PresentationRepository  //이 프레젠테이션 레포지토리는 목이 들어간다.

    //프레젠테이션 서비스가 PresentationRepository이걸 받고 있으니, 이 안에 모킹을 넣어줘 가지고 프레젠테이션 서비스가 제대로 동작을 하는지 검증을 함
    val DATA_SIZE = 9


    @Test
    fun testGetIntroductions() {
        //개발할 때 많이 쓰는 패턴 - given, when, then / 조건이 주어지고 동작을 했을 때 결과가 어떻게 될거냐
        // given
        val introductions = mutableListOf<Introduction>()
        for (i in 1..DATA_SIZE) { // 1, 3, 5, 7, 9 -> false / 2, 4, 6, 8 -> true
            val introduction = Introduction(content = "${i}", isActive = i % 2 == 0)  //홀짝 번갈아가면서 true가 들어감 (짝수에 true)
            introductions.add(introduction)
        }
        val activeIntroductions = introductions.filter { introduction ->
            introduction.isActive
        } // 4 / / introduction -> introduction = it

        //when이 코틀린에서 예약어라 `로 감싸야한다.
        Mockito.`when`(presentationRepository.getActiveIntroductions())
            .thenReturn(activeIntroductions)  //모킹을 하는 건데 getActiveIntroductions호출하면 activeIntroductions을 리턴하도록 모킹하는 것

        // when
        val introductionDTOs = presentationService.getIntroductions()

        // then
        assertThat(introductionDTOs).hasSize(DATA_SIZE / 2)
        for (introductionDTO in introductionDTOs) {
            assertThat(introductionDTO.content.toInt()).isEven()  //짝수인지 검증
            //짝수가 아니다, 어떤 데이터에 테스트가 실패했다 라고 한다면, PresentationService코드에 getIntroductions에 어떤 오류가 있다고 추측가능
        }
    }

    //링크에 대한 테스트
    @Test
    fun testGetLinks() {
        // given
        val links = mutableListOf<Link>()  //임포트
        for (i in 1..DATA_SIZE) {
            val link = Link(name = "${i}", content = "${i}", isActive = i % 2 != 0)  //홀수에 true
            links.add(link)
        }
        val activeLinks = links.filter { link ->
            link.isActive
        }
        Mockito.`when`(presentationRepository.getActiveLinks())  //내부적으로 이걸 호출하면서 이 activeLinks(미리 모킹을 해둔)를 받아올 것
            .thenReturn(activeLinks)

        // when
        val linkDTOs = presentationService.getLinks()

        // then
        var expectedSize = DATA_SIZE / 2
        if (DATA_SIZE % 2 != 0) {
            expectedSize += 1
        }
        assertThat(linkDTOs).hasSize(expectedSize)
        for (linkDTO in linkDTOs) {
            assertThat(linkDTO.content.toInt()).isOdd()  //홀수 검증. 여기서 문제가 생기면 val linkDTOs = presentationService.getLinks()여기에 뭔가 문제가 있다고 판단
        }
    }
}