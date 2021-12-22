package com.village.soonyee.service;

import com.village.soonyee.configuration.security.jwt.TokenProvider;
import com.village.soonyee.domain.Member;
import com.village.soonyee.dto.MemberDto;
import com.village.soonyee.dto.StatusMessageDto;
import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.MemberNotExistsException;
import com.village.soonyee.exception.exception.UserNotFoundException;
import com.village.soonyee.repository.MemberRepository;
import com.village.soonyee.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    public Long join(MemberDto memberDto){
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = memberDto.toEntity();
        validDuplicateCheck(member);
        return memberRepository.save(member);
    }
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).get(0);
    }
    public Member findById(Long memberIdx){
        Member byId = memberRepository.findById(memberIdx);
        if(byId==null){
            throw new MemberNotExistsException("Member doesn't exist",ErrorCode.MEMBER_NOT_EXISTS);
        }
        return byId;
    }
    public Map<String,String> login(String id,String password){
        List<Member> byEmail = memberRepository.findByEmail(id);
        if(byEmail.size()==0){throw new UserNotFoundException("Can't find user", ErrorCode.USER_NOT_FOUND);}//해당 id를 가진 유저가 존재하는지 확인

        Member member = byEmail.get(0);
        boolean check = passwordEncoder.matches(password, member.getPassword());
        if(!check){throw new UserNotFoundException("Can't find user",ErrorCode.USER_NOT_FOUND);}

        member.updateCoin(10);

        //토큰 발급
        final String accessToken=tokenProvider.generateAccessToken(member.getEmail());
        final String refreshToken=tokenProvider.generateRefreshToken(member.getEmail());

        //토큰 유효기간 설정
        redisUtil.setDataExpire(refreshToken,member.getEmail(), TokenProvider.REFRESH_TOKEN_EXPIRE_TIME);

        Map<String,String> map=new HashMap<>();
        map.put("id",member.getEmail());
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        return map;
    }
    public void logOut(){
        String userEmail = this.getUserEmail();
        redisUtil.deleteData(userEmail);
    }
    public void validDuplicateCheck(Member member){
        List<Member> byEmail = memberRepository.findByEmail(member.getEmail());
        if(!byEmail.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    public void updateStatusMessage(StatusMessageDto statusMessageDto){
        Member member = memberRepository.findByEmail(getUserEmail()).get(0);
        memberRepository.updateMessage(member,statusMessageDto.getMessage());
    }
    public void uploadProfile(String fileName){
        List<Member> byEmail = memberRepository.findByEmail(getUserEmail());
        if(byEmail.isEmpty()){
            throw new MemberNotExistsException("Member doesn't exists",ErrorCode.MEMBER_NOT_EXISTS);
        }
        memberRepository.updateProfile(fileName, byEmail.get(0));
    }
    static public String getUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }

    public void buyThing(int cost){
        List<Member> byEmail = memberRepository.findByEmail(getUserEmail());
        if(byEmail.isEmpty()){
            throw new UserNotFoundException("please try after login",ErrorCode.USER_NOT_FOUND);
        }
        Member member = byEmail.get(0);
        member.updateCoin(-cost);
    }
}
