package com.village.soonyee.service;

import com.village.soonyee.domain.Diary;
import com.village.soonyee.domain.Member;
import com.village.soonyee.dto.DiaryDto;
import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.DiaryNotExistsException;
import com.village.soonyee.exception.exception.MemberNotExistsException;
import com.village.soonyee.repository.DiaryRepository;
import com.village.soonyee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    public Long join(DiaryDto diaryDto){
        Diary diary = diaryDto.toEntity();
        List<Member> byEmail = memberRepository.findByEmail(MemberService.getUserEmail());
        diary.setWriter(byEmail.get(0));
        diaryRepository.save(diary);
        return diary.getId();
    }
    public List<Diary> findByUser(Long memberIdx){
        Member byId = memberRepository.findById(memberIdx);
        if(byId==null){
            throw new MemberNotExistsException("Can't find Member by Id", ErrorCode.MEMBER_NOT_EXISTS);
        }
        return diaryRepository.findByMember(byId);
    }
    public Diary findById(Long id){
        Diary byId = diaryRepository.findById(id);
        if(byId==null){
            throw new DiaryNotExistsException("Can't find Diary by Id", ErrorCode.DIARY_NOT_EXISTS);
        }
        return byId;
    }
}
