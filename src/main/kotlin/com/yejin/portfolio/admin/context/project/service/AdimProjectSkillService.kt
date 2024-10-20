package com.yejin.portfolio.admin.context.project.service

import com.yejin.portfolio.admin.context.project.form.ProjectSkillForm
import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.admin.exception.AdminBadReqeustException
import com.yejin.portfolio.admin.exception.AdminInternalServerErrorException
import com.yejin.portfolio.domain.entity.ProjectSkill
import com.yejin.portfolio.domain.repository.ProjectRepository
import com.yejin.portfolio.domain.repository.ProjectSkillRepository
import com.yejin.portfolio.domain.repository.SkillRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminProjectSkillService(
    private val projectRepository: ProjectRepository,
    private val skillRepository: SkillRepository,
    private val projectSkillRepository: ProjectSkillRepository
) {
    @Transactional
    fun getProejectSkillTable(): TableDTO {
        val projects = projectRepository.findAll()
        val columns = mutableListOf<String>(  //list<String>
            "id", "projectId", "projectName", "skillId", "skillName",
            "createdDateTime", "updatedDateTime"
            //다대다 구조에서는 엔티티 정보만 가지고 칼럼을 뽑아오기가 조금 복잡해서 직접 만들기함 (필요로한는 칼럼 이르들 정리해줌)
        )

        //이중리스트
        val records = mutableListOf<MutableList<String>>()
        for (project in projects) {  //조회해온 모든 프로젝트에 대해서 순회를 하면서
            project.skills.forEach {  //각 프로젝트 스킬에서 그 프로젝트 스킬이 갖고 있는 스킬 데이터를 하나씩 돌면서 레코드리스트를 만드는 것
                val record = mutableListOf<String>()  //빈리스트 받기. 이렇게 만들어진 record들이 records에 들어갈 것
                record.add(it.id.toString())
                record.add(it.project.id.toString())
                record.add(it.project.name)
                record.add(it.skill.id.toString())
                record.add(it.skill.name)
                record.add(it.createdDateTime.toString())
                record.add(it.updatedDateTime.toString())
                records.add(record)
            }
        }
        return TableDTO(name = "ProjectSkill", columns = columns, records = records)
    }

    //폼에서 보여주기 위한 프로젝트 리스트
    fun getProjectList(): List<String> {
        val projects = projectRepository.findAll()
        return projects.map { "${it.id} (${it.name})" }.toList()  //리스트로 만든게 selectform에 들어갈 것
        //셀렉트 폼에 들어가면 갖고있는 모든 프로젝트를 쭉 릿스트업을 하고 또 스킬도 리스트 업이 될것이고,
        //그래서 맵핑을 할 프로젝트와 스킬을 선택을 해가지고 저장을 누르면 그 두개가 맵핑이 된 프로젝트 스킬데이터가 데이터베이스에 저장이 되도록 나중에 만들어 줄 것
    }

    //폼에서 보여주기 위한 스킬 리스트
    fun getSkillList(): List<String> {
        val skills = skillRepository.findAll()
        return skills.map { "${it.id} (${it.name})" }.toList()
    }

    @Transactional
    fun save(form: ProjectSkillForm) {
        //"id (name)" 이런 형식의문자열 로 들어올 것
        // 이미 매핑된 Project - Skill 여부 검증.
        val projectId = parseId(form.project)
        val skillId = parseId(form.skill)
        projectSkillRepository.findByProjectIdAndSkillId(projectId, skillId)  //중복 매핑을 방지하기 위해 있는 로직
            .ifPresent { throw AdminBadReqeustException("이미 매핑된 데이터입니다.") }  //옵셔널로 받아서 옵셔널에 데이터가 있으면 그때 예외 던질 것

        // 유효한 ProjectSkill 생성
        val project = projectRepository.findById(projectId)
            .orElseThrow { throw AdminBadReqeustException("ID ${projectId}에 해당하는 데이터를 찾을 수 없습니다.") }

        val skill = skillRepository.findById(skillId)
            .orElseThrow { throw AdminBadReqeustException("ID ${skillId}에 해당하는 데이터를 찾을 수 없습니다.") }

        //프로젝트와 스킬을 가지고 왔다면.
        val projectSkill = ProjectSkill(
            project = project,
            skill = skill
        )
        project.skills.add(projectSkill)  //db에서 조회해오면서 영속성에 이미 들어간 프로젝트가 처음에가지고온 스냅샷이랑 지금 현재 상태랑 다르니까 jpa에서 업데이트를 날려줄 것
    }

    //"id (name)" 이 형식에서 id만 추출할 수 있도록 하는 함수
    private fun parseId(line: String): Long {
        try {
            val endIndex = line.indexOf(" ") - 1  //아이디의 마지막 인덱스는 스페이스 바로 앞일테니까 -1
            val id = line.slice(0..endIndex).toLong()
            return id
        } catch (e: Exception) {
            throw AdminInternalServerErrorException("ID 추출 중 오류가 발생했습니다.")
        }
    }

    //삭제
    @Transactional
    fun delete(id: Long) {
        projectSkillRepository.deleteById(id)
    }
}