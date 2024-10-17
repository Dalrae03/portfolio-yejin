package com.yejin.portfolio.admin.data

//어드민 페이지에서 그리드에 뿌려줄 데이터를 담는 내용
data class TableDTO(
    //사용할 javascript라이브러리가 여기에서 컬럼 정보를 읽어가지고, 표의 맨 위쪽에 컬럼의 이름들을 뿌려줌.
    //List<List<String>>의 List<String>리스트 한줄이 각 칼럼에 해당하는 한 레코드의 데이터. 그 레코드들이 이제 리스트로 또 들어오는 것 (이중 리스트) (이거 가지고 자바스크립트로 테이블 초기화작업할 것)
    val name: String,
    val columns: List<String>,
    val records: List<List<String>>
)