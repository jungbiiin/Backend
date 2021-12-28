package com.village.soonyee.service

import com.village.soonyee.domain.Diary
import com.village.soonyee.dto.DiaryDto
import com.village.soonyee.exception.ErrorCode
import com.village.soonyee.exception.exception.DiaryNotExistsException
import com.village.soonyee.exception.exception.MemberNotExistsException
import com.village.soonyee.repository.DiaryRepository
import com.village.soonyee.repository.MemberRepository
import com.village.soonyee.util.entity.EntityProvider
import org.springframework.stereotype.Service

@Service
class DiaryService(
    private val diaryRepository : DiaryRepository,
    private val memberRepository : MemberRepository
) {

    fun join(diaryDto: DiaryDto): Long {
        return EntityProvider.loadEntity(Diary::class.java, diaryDto).apply {
            writer = memberRepository.findByEmail(MemberService.getUserEmail())[0]
            diaryRepository.save(this)
        }.id
    }

    fun findByUser(memberIdx: Long): List<Diary> {
        val byId = memberRepository.findById(memberIdx)
                ?: throw MemberNotExistsException("Can't find Member by Id", ErrorCode.MEMBER_NOT_EXISTS)
        return diaryRepository.findByMember(byId)
    }

    fun findById(id: Long): Diary {
        return diaryRepository.findById(id)
                ?: throw DiaryNotExistsException("Can't find Diary by Id", ErrorCode.DIARY_NOT_EXISTS)
    }
}
