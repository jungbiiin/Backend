package com.village.soonyee.service;

import com.village.soonyee.domain.GuestBook;
import com.village.soonyee.domain.Member;
import com.village.soonyee.dto.GuestBookDto;
import com.village.soonyee.dto.GuestBookUpdateDto;
import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.NotSameTargetException;
import com.village.soonyee.exception.exception.UserNotFoundException;
import com.village.soonyee.repository.GuestBookRepository;
import com.village.soonyee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final MemberRepository memberRepository;
    private final GuestBookRepository guestBookRepository;
    public List<GuestBook> findByTarget(Long memberIdx){
        return guestBookRepository.findByTarget(memberIdx);
    }
    public Long join(Long memberIdx, GuestBookDto guestBookDto){
        GuestBook guestBook = guestBookDto.toEntity();
        guestBook.setWriter(memberRepository.findByEmail(MemberService.getUserEmail()).get(0));
        guestBook.setTarget_id(memberIdx);
        return guestBookRepository.save(guestBook);
    }
    public void delete(Long guestBookId){
        GuestBook byId = guestBookRepository.findById(guestBookId);
        List<Member> byEmail = memberRepository.findByEmail(MemberService.getUserEmail());
        if(byId.getTarget_id()!=byEmail.get(0).getId()){
            throw new NotSameTargetException("target isn't same to user",ErrorCode.NOT_SAME_TARGET);
        }
        guestBookRepository.deleteGuestBook(guestBookId);
    }
    @Transactional
    public void update(Long guestBookIdx, GuestBookUpdateDto guestBookUpdateDto){
        GuestBook byId = guestBookRepository.findById(guestBookIdx);
        if(byId.getWriter()!=memberRepository.findByEmail(MemberService.getUserEmail()).get(0)){throw new UserNotFoundException("User isn't same", ErrorCode.USER_NOT_FOUND);}
        byId.update(guestBookUpdateDto);
    }
}
