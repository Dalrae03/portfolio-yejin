package com.yejin.portfolio.presentation.controller

import org.assertj.core.api.Assertions
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.nio.charset.StandardCharsets

//api컨트롤러 테스트 코드 - 스프링부트를 다 띄운 다음에 그 api를 직접 호출을 해가지고 결과를 검증하는 방식
//jpa테스트일 때 데이터 jpa 테스트를 사용 - jpa테스트에 필요한 것만 초기화 한다
//서비스는 단위 테스트
//데이터 jpa테스트 같은 경우에는 부분적인 통합 테스트 (부분적 모듈 초기화)
//스프링 부트 테스트 - 전체 띄우는 것


@SpringBootTest  //스프링 부트를 실제로 띄운 다음에 테스트 진행 (프로젝트가 동작하는 것과 똑같이 동작한다고 할 수 있다. (그만큼 시간이 많이 걸림))
@AutoConfigureMockMvc  //컨트롤러 단위에서 테스트를 하기 위해서 필요한 기능. 자동적으로 MockMvc관련된 설정들을 세팅해줌
@DisplayName("[API 컨트롤러 테스트]")  //테스트를 돌릴 때마다 뜨는 메소드명 이름 커스터마이징. 이 이름으로 테스트 리스트에 보일 것. (클래스 단위로 지정할 수 있고, 메소드에도 달아줄 수 있다.)
class PresentationApiControllerTest(
    @Autowired private val mockMvc: MockMvc  //의존성주입
) {
    @Test
    @DisplayName("Introductions 조회")
    fun testGetIntroductions() {
        // given
        val uri = "/api/v1/introductions"
        // when
        val mvcResult =
            performGet(uri)  //performGet(uri) 함수가 호출되며, 이 함수는 /api/v1/introductions로 GET 요청을 보내고 그 결과를 mvcResult에 저장. (performGet은 아마도 MockMvc를 이용한 GET 요청을 수행하는 커스텀 함수)
        val contentAsString =
            mvcResult.response.getContentAsString(StandardCharsets.UTF_8)  //응답 퍼싱. StandardCharsets.UTF_8이걸해야지 로그 찍을때 한글이 안깨지고 잘보임 (mvcResult로부터 HTTP 응답의 바디를 가져오는 부분)
        val jsonArray = JSONArray(contentAsString)  //http리스폰스에 있는 body를 string그대로 가지고 오는데, 다루기 편하기 위해서 json배열로 바꿔준 것.
        // then
        Assertions.assertThat(jsonArray.length()).isPositive()  //jsonArray에서 length는 양수인 것만 검증 (즉, 배열에 데이터가 있는지)
    }

    @Test
    @DisplayName("Links 조회")
    fun testGetLinks() {
        // given
        val uri = "/api/v1/links"
        // when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)
        // then
        Assertions.assertThat(jsonArray.length()).isPositive()
    }

    @Test
    @DisplayName("Resume 조회")
    fun testGetResume() {
        // given
        val uri = "/api/v1/resume"
        // when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonObject = JSONObject(contentAsString)  //APiController를 봤을 때 리스트가 아닌 객체를 반환하기 때문에 JSONObject
        // then
        Assertions.assertThat(jsonObject.optJSONArray("experiences").length())
            .isPositive()  //.optJSONArray("experiences") - json array에 있는 experiences키의 벨류값을 가지고 올 것(근데 리스트라서 그것의 길이가 양수인지 검증)
        Assertions.assertThat(jsonObject.optJSONArray("achievements").length()).isPositive()
        Assertions.assertThat(jsonObject.optJSONArray("skills").length()).isPositive()
    }

    @Test
    @DisplayName("Projects 조회")
    fun testGetProjects() {
        // given
        val uri = "/api/v1/projects"
        // when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)
        // then
        Assertions.assertThat(jsonArray.length()).isPositive()
    }

    //호출하는 private 메소드 (MvcResult 객체 리턴)
    private fun performGet(uri: String): MvcResult {
        return mockMvc
            .perform(MockMvcRequestBuilders.get(uri))  //url의 get방식으로 호출해주는 리퀘스트 (MockMvcRequestBuilders 클래스에서 제공하는 GET 요청 빌더. 이 코드는 uri로 GET 방식의 HTTP 요청)
            .andDo(MockMvcResultHandlers.print())  //호출한 다음 MockMvcResultHandlers.print()하면 그 결과를 출력.(이 핸들러는 요청과 응답의 세부 정보를 콘솔에 출력. 디버깅에 유용하며, 요청과 응답의 내용을 로그로 확인)
            // (andDo(): 이 메서드는 요청의 결과에 대해 추가 동작을 정의할 수 있게 해줌)
            .andReturn()  //결과를 리턴하는 것 (MvcResult 객체를 반환)
    }
}