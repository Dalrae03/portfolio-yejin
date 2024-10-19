package com.yejin.portfolio.admin.context.project.service

import com.yejin.portfolio.admin.data.TableDTO
import com.yejin.portfolio.domain.repository.ProjectRepository
import com.yejin.portfolio.domain.repository.SkillRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminProjectSkillService(
    private val projectRepository: ProjectRepository,
    private val skillRepository: SkillRepository
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
}