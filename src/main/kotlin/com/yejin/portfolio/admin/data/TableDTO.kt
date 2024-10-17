package com.yejin.portfolio.admin.data

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

//어드민 페이지에서 그리드에 뿌려줄 데이터를 담는 내용
data class TableDTO(
    //사용할 javascript라이브러리가 여기에서 컬럼 정보를 읽어가지고, 표의 맨 위쪽에 컬럼의 이름들을 뿌려줌.
    //List<List<String>>의 List<String>리스트 한줄이 각 칼럼에 해당하는 한 레코드의 데이터. 그 레코드들이 이제 리스트로 또 들어오는 것 (이중 리스트) (이거 가지고 자바스크립트로 테이블 초기화작업할 것)
    val name: String,
    val columns: List<String>,
    val records: List<List<String>>
) {
    companion object {
        //classInfo - 클래스 정보 파라미터
        //vararg - 리스트 형태를 받을 수 있음. 리스트를 만들어서 ["a", "b"] 이렇게 파라멘트로 안넣어도 "a", "b"이렇게 넣기만 해도 받아올 수 있도록 함
        fun <T : Any> from(classInfo: KClass<T>, entities: List<Any>, vararg filterings: String)
                : TableDTO {
            val name = classInfo.simpleName ?: "Unknown"  //없으면 unknown기본값
            val columns = createColumns(
                classInfo,
                *filterings
            )  //filterings에 들어올 것들 - 엔티티의 칼럼들을 화면에서 뿌려 줄 건데 그중에서 제외하고싶은 칼럼의 이름을 넣어줄 것
            val records = entities.map { entity ->
                columns.map { column ->  //엔티티 하나하나에 대해 이 로직(columns.map)을 돌린다. 칼람에 대해 하나하나 돌리면서 똑같은 컬럼명을 찾는 것.
                    classInfo.memberProperties  //memberProperties - java.declaredFields와 거의 같은 기능. 저건 코틀린에서 제공.
                        .find { column.equals(it.name) }
                        ?.getter
                        ?.call(entity)  //프로퍼티(칼럼을 말하는 것 같다)에 해당하는 값을 가지고 오는건데 entity정보를 가지고 와야한다. 칼럼명이 일치하면 그 이름을 가지고 이 엔티티에서 값을 가지고 오는 로직
                        .toString()  //가져온 값들을 to string
                }
                    .toList()  //entity 한개의 value들이 들어있는 string List가 만들어지고 그것들을 각각의 entity에서 실행해서 (각 컬럼에 대해서 모아온 값들을 리스트로 만들고)
            }.toList()  //나온 결과를 또 모아주는 것 (다시 리스트로 취합)
            return TableDTO(name = name, columns = columns, records = records)
        }

        //엔티티가 가지고 있는 칼럼 정보를 빼오기 위함
        private fun <T : Any> createColumns(classInfo: KClass<T>, vararg filterings: String)
                : MutableList<String> {
            val mainColumns = classInfo.java.declaredFields  //자바클래스가 가지고있는 필드들을 가지고 올 수 있다.
                .filter { !filterings.contains(it.name) }
                .map { it.name }  //스트링형태의 이름만 가지고 올 것
                .toMutableList()  //나중에 동적으로 데이터 수정 위해서
            val baseColumns =
                classInfo.java.superclass.declaredFields  //엔티티들이 base엔티티 상속을 받아서 거기에 있는 내용을 가지고오기 위한 그런 로직. superclass - 상위클래스 가져옴
                    .map { it.name }
                    .toMutableList()

            return (mainColumns + baseColumns).toMutableList()
        }
    }
}
