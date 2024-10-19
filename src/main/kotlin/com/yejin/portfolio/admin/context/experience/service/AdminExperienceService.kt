package com.yejin.portfolio.admin.context.experience.service

import com.yejin.portfolio.admin.context.experience.form.ExperienceForm
import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.admin.exception.AdminBadReqeustException
import com.yejin.portfolio.domain.entity.Experience
import com.yejin.portfolio.domain.entity.ExperienceDetail
import com.yejin.portfolio.domain.repository.ExperienceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun save(form: ExperienceForm) {
        val experienceDetails = form.details
            ?.map { detail -> detail.toEntity() }  //null이 아닐 경우에 맵핑을 해주는데 detail들을 다 엔티티로 바꿔준다
            ?.toMutableList()  //이것이 또 null이 아니라면 Mutable List로 바꿔준다
        val experience = form.toEntity()
        experience.addDetails(experienceDetails)
        experienceRepository.save(experience)  //인서트. 이 experience가 영속성에 들어갈 때, details도 cascade타입이 all로 되어 있기 때문에 같이 들어갈 것.
        //experience Dettail Repository를 굳이 호출 안해줘도 experienceRepository.save(experience)이거 호출만으로도 둘 다 저장이 된다.
        //experience안에 캐스케이드 타입이 details도 포함이 되어있다.
    }

    //업데이트
    @Transactional
    fun update(id: Long, form: ExperienceForm) {
        val experience = experienceRepository.findById(id)
            .orElseThrow { throw AdminBadReqeustException("ID ${id}에 해당하는 데이터를 찾을 수 없습니다.") }  //아이디가 없다면 던지는 예외
        experience.update(
            title = form.title,
            description = form.description,
            startYear = form.startYear,
            startMonth = form.startMonth,
            endYear = form.endYear,
            endMonth = form.endMonth,
            isActive = form.isActive
        )

        //조회해온 experience에 있는 디테일을 가지고 {(그 디테일의)id: expereinceDetail} 형식으로 맵을 만들어 준다. 그 맵에다가 폼에 있는 데이터를 찾아 가지고 업데이트를 해줄 것
        //db에서 조회해온 디테일의 엔티티가 맵으로 들어가 있다.
        val detailMap = experience.details.map { it.id to it }.toMap()

        //form에서 받아온 디테일폼을 가지고
        form.details?.forEach {
            val entity = detailMap.get(it.id)  //그 form의 id를 가지고 거기에 해당하는 엔티티를 찾는다
            if (entity != null) {
                entity.update(
                    content = it.content,
                    isActive = it.isActive
                )
            } else {
                experience.details.add(it.toEntity())  //form을 엔티티로 바꿔서 추가해줌.
                //굳이 save호출 안해도 트랜지션이 끝나면서 처음에 조회해온 엔티티와 지금 업데이트가 된 엔티티의 상태를 비교해가지고 jpa가 알아서 업데이트 쿼리를 날려줌
            }
        }
    }
}