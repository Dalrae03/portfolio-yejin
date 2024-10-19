package com.yejin.portfolio.admin.context.achievement.form

import com.yejin.portfolio.domain.entity.Achievement
import jakarta.validation.constraints.NotBlank  //constraints에 다양한 데이터 검증 기능을 제공. -> 로직단에서 모든 데이터 체크필요없이 annotation을 추가하는 것만으로 간단하게 클라이언트에서 넘어온 데이터를 검증 가능
import java.time.LocalDate


//일종의 DTO클래스. 기능에 집중해서 Form이라고 명명
data class AchievementForm(
    //지난번에 추가했던 vaildation Library사용을 할것이라 field선언
    @field:NotBlank(message = "필수값입니다.")
    val title: String,  //이 데이터가 들어올 때 타이틀이 블랭크인지 아닌지를 판단을 하고 블랭크면 에러를 던진다. 그때 예외 메세지(상단에 필수값~)를 지정
    @field:NotBlank(message = "필수값입니다.")
    val description: String,
    @field:NotBlank(message = "필수값입니다.")
    val host: String,
    @field:NotBlank(message = "필수값입니다.")
    val achievedDate: String,
    val isActive: Boolean
) {
    fun toEntity(): Achievement {  //achievement엔티티를 리턴하는 메소드.
        //DTO를 기반으로 DTO에 해당하는 achievement entity를 만들어 주는 것
        return Achievement(
            title = this.title,
            description = this.description,
            host = this.host,
            achievedDate = LocalDate.parse(this.achievedDate),  //.parse - 스트링 받아서 문자열로 리턴해주는 것
            isActive = this.isActive
        )
    }

    //엔티티 수정할 때 사용
    fun toEntity(id: Long): Achievement {
        val achievement = this.toEntity()
        achievement.id = id
        return achievement
    }
}