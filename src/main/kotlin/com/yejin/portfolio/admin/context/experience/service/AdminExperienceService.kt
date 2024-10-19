package com.yejin.portfolio.admin.context.experience.service

import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.admin.exception.AdminBadReqeustException
import com.yejin.portfolio.domain.entity.Experience
import com.yejin.portfolio.domain.entity.ExperienceDetail
import com.yejin.portfolio.domain.repository.ExperienceRepository
import org.springframework.stereotype.Service

@Service
class AdminExperienceService(
    private val experienceRepository: ExperienceRepository
) {
    //기능 두 개 - 1. experiecne Table조회
    fun getExperienceTable(): TableDTO {
        val classInfo = Experience::class  //클래스 인포는 experience entity 이거의 정보를 주고
        val entities = experienceRepository.findAll()  //experienceRepository에서 findall 해서 엔티티 리스트를 만들고
        return TableDTO.from(
            classInfo,
            entities,
            "details"
        )  //Table DTO로 넘기면 끝. 디테일 데이터를 보기위해 따로 하단에 디테일 전용테이블을 따로 만들어 줄 거기 때문에 details는 필터링
    }

    //2. experienceDetailTable조회
    fun getExperienceDetailTable(id: Long?): TableDTO {  //id를 받고 TableDTO 리턴
        //아이디 받는 이유 - 처음부터 모든 experienceDetail 데이터를 다 조회해와가지고 화면에 뿌려주기에는 화면구조상 어렵기 때문에
        //상세조회 익스피리언스의 레코드가 줄별로 보이고 줄마다 상세 조회버튼을 가지고 있을테니 컨트롤러에서 넣어준 hasDetail값이 true False에 따라 상세조회 버튼이 노출이 되냐 안되냐 결정이 될 거고,
        //특정 엔티티의 상세 조회 버튼을 누르면 그 엔티티의 아이디를 여기로 넘겨가지고 아이디를 받아와서 그 아이디에 맞는 디테일 정보를 찾아오고
        //걔를 테이블 디테일을 만들어서 클라이언트로 넘기면 이제 화면에서 다시 보여주는 것
        //널러블의 이유 - 처음에 그 페이지에 접속을 했을 때 아직 어떤 상세적인 버튼도 누르기 전이기 때문에 그때 그냥 빈 리스트를 먼저주기 위해 id가 널인 케이스도 받는 것
        val classInfo = ExperienceDetail::class  //ExperienceDetail의 class정보를 가지고 옴
        val entities = if (id != null) experienceRepository.findById(id)
            .orElseThrow { throw AdminBadReqeustException("ID ${id}에 해당하는 데이터를 찾을 수 없습니다.") }  //오류발생하여 잘못된아이디가 들어왔을때 저 메세지가 컨트롤러 어드바이스의 .body(e.message)에 들어감. 그 바디에 있는 내용을 프론트에서 alert로 띄워 줄 것.
            //orElseThrow - 옵셔널에서 객체를 뽑아와서 그 객체를 준다. (옵셔널 experience가 아니고 experience가 나옴)
            .details else emptyList()  //그 객체에서 디테일즈를 가지고 올 것. 객체 없으면 빈리스트
        return TableDTO.from(classInfo, entities)
    }
}