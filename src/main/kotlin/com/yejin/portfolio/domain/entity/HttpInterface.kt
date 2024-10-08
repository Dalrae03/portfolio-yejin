package com.yejin.portfolio.domain.entity

//import jakarta.persistence.*
import jakarta.persistence.*
import jakarta.servlet.http.HttpServletRequest


//http요청정보를 저장하기 위한 엔티티
@Entity
class HttpInterface(
    httpServletRequest: HttpServletRequest  //스프링에서 요청을 받을 때 리퀘스트에 대한 정보를 여기에 담아준다. 이 안에서 우리가 필요로 하는 클라이언트 정보를 꺼내올것이다.
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "http_interface_id")
    var id: Long? = null

    //cookies의 마지막 물음표 -> null여부 체크 null이면 실행 안되고 null이면 실행 -> nullpoint인셉션 막아줌
    //cookies는 배열. map이 안에있는 것들을 하나씩 순차적으로 돌면서 맵 뒤 중괄호 안에 들어갈 함수대로 반환하는 기능함
    var cookies: String? = httpServletRequest.cookies?.map { "${it.name}:${it.value}" }?.toString()

    //referer - 구글에서 검색을 해 가지고 나온 어떤 사이트에 들어갔을 때 그때 google.com의 도메인이 referer가 되는 것. 요청이 어디에서부터 왔냐를 알려주는 것.
    var referer: String? = httpServletRequest.getHeader("referer")

    //클라이언트와 관련된 ip주소같은 것들 3가지
    var localAddr: String? = httpServletRequest.localAddr

    var remoteAddr: String? = httpServletRequest.remoteAddr

    var remoteHost: String? = httpServletRequest.remoteHost

    //우리 서버에서 어떤 uri로 접속을 했는지 정보
    var requestUri: String? = httpServletRequest.requestURI

    //브라우저 정보
    var userAgent: String? = httpServletRequest.getHeader("user-agent")
}