package com.village.soonyee.service

import com.village.soonyee.domain.GuestBook
import com.village.soonyee.dto.GuestBookDto
import com.village.soonyee.dto.GuestBookUpdateDto
import com.village.soonyee.exception.ErrorCode
import com.village.soonyee.exception.exception.NotSameTargetException
import com.village.soonyee.exception.exception.UserNotFoundException
import com.village.soonyee.repository.GuestBookRepository
import com.village.soonyee.repository.MemberRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class GuestBookService(
        private val memberRepository: MemberRepository,
        private val guestBookRepository: GuestBookRepository
) {
    fun findByTarget(memberIdx: Long): List<GuestBook> {
        return guestBookRepository.findByTarget(memberIdx)
    }

    fun join(memberIdx: Long, guestBookDto: GuestBookDto): Long {
        return guestBookRepository.save(guestBookDto.toEntity().apply {
            writer = memberRepository.findByEmail(MemberService.getUserEmail())[0]
            target_id = memberIdx
        })
    }

    fun delete(guestBookId: Long) {
        val byId = guestBookRepository.findById(guestBookId)
        val byEmail = memberRepository.findByEmail(MemberService.getUserEmail())
        if (byId.target_id != byEmail[0].id) {
            throw NotSameTargetException("target isn't same to user", ErrorCode.NOT_SAME_TARGET)
        }
        guestBookRepository.deleteGuestBook(guestBookId)
    }

    @Transactional
    open fun update(guestBookIdx: Long, guestBookUpdateDto: GuestBookUpdateDto) {
        val byId = guestBookRepository.findById(guestBookIdx)
        if (byId.writer !== memberRepository.findByEmail(MemberService.getUserEmail())[0]) {
            throw UserNotFoundException("User isn't same", ErrorCode.USER_NOT_FOUND)
        }
        byId.update(guestBookUpdateDto)
    }
}