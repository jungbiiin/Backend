package com.village.soonyee.domain

import com.village.soonyee.dto.DiaryDto
import com.village.soonyee.util.entity.IEntityStruct
import javax.persistence.*

//만약 이렇게 DTO 클래스를 상속을 받을 수 있으면 toEntity 제거 가능
//근데 실행을 안 시켜봐서 모르겠음...ㅋㅋ
@Entity
class Diary : DiaryDto(), IEntityStruct<DiaryDto> {
    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    var writer: Member? = null
}