package com.village.soonyee.service;

import com.village.soonyee.domain.Member;
import com.village.soonyee.domain.Posting;
import com.village.soonyee.dto.PostingDto;
import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.MemberNotSameException;
import com.village.soonyee.repository.MemberRepository;
import com.village.soonyee.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostingService {
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    public Long write(PostingDto postingDto){
        Member member = memberRepository.findByEmail(MemberService.getUserEmail()).get(0);
        Posting posting = postingDto.toEntity();
        posting.setWriter(member);
        postingRepository.save(posting);
        return posting.getId();
    }
    public List<Posting> findAll(){
        return postingRepository.findAll();
    }
    public Posting findOne(Long postingIdx){
        return postingRepository.findById(postingIdx).get();
    }
    public void deleteOne(Long postingIdx){
        Posting byId = postingRepository.getById(postingIdx);
        if(memberRepository.findByEmail(MemberService.getUserEmail()).get(0)!=byId.getWriter()){
            throw new MemberNotSameException("Member isn't same", ErrorCode.MEMBER_NOT_SAME);
        }
        postingRepository.deleteById(postingIdx);
    }
    public void update(Long postingIdx,PostingDto postingDto){
        Posting byId = postingRepository.getById(postingIdx);
        if(memberRepository.findByEmail(MemberService.getUserEmail()).get(0)!=byId.getWriter()){
            throw new MemberNotSameException("Member isn't same", ErrorCode.MEMBER_NOT_SAME);
        }
        byId.update(postingDto);
    }
}
