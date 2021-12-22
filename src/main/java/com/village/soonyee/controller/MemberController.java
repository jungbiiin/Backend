package com.village.soonyee.controller;

import com.village.soonyee.domain.Member;
import com.village.soonyee.dto.MemberDto;
import com.village.soonyee.dto.SignInDto;
import com.village.soonyee.dto.StatusMessageDto;
import com.village.soonyee.response.ResponseService;
import com.village.soonyee.response.result.CommonResult;
import com.village.soonyee.response.result.SingleResult;
import com.village.soonyee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MemberController {
    private final MemberService memberService;
    private final ResponseService responseService;
    @PostMapping("/member")
    public CommonResult join(@RequestBody MemberDto memberDto){
        memberService.join(memberDto);
        return responseService.getSuccessResult();
    }
    @GetMapping("/member/{memberIdx}")
    public SingleResult<Member> findById(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.findById(memberIdx));
    }
    @PostMapping("/login")
    public SingleResult<Map<String,String>> login(@RequestBody SignInDto signInDto){
        return responseService.getSingleResult(memberService.login(signInDto.getEmail(),signInDto.getPassword()));
    }
    @PostMapping("/logout")
    public CommonResult logout(){
        memberService.logOut();
        return responseService.getSuccessResult();
    }
    @PatchMapping("/member/message")
    public CommonResult updateMessage(@RequestBody StatusMessageDto statusMessageDto){
        memberService.updateStatusMessage(statusMessageDto);
        return responseService.getSuccessResult();
    }
    @PostMapping("/buy/{cost}")
    public CommonResult buyGift(@PathVariable int cost){
        memberService.buyThing(cost);
        return responseService.getSuccessResult();
    }
}
