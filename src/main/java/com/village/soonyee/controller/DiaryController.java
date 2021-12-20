package com.village.soonyee.controller;

import com.village.soonyee.domain.Diary;
import com.village.soonyee.dto.DiaryDto;
import com.village.soonyee.response.ResponseService;
import com.village.soonyee.response.result.CommonResult;
import com.village.soonyee.response.result.ListResult;
import com.village.soonyee.response.result.SingleResult;
import com.village.soonyee.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;
    private final ResponseService responseService;
    @PostMapping("/diary")
    public CommonResult join(@RequestBody DiaryDto diaryDto){
        diaryService.join(diaryDto);
        return responseService.getSuccessResult();
    }
    @GetMapping("/diary/member/{memberIdx}")
    public ListResult<Diary> findByUser(@PathVariable Long memberIdx){
        return responseService.getListResult(diaryService.findByUser(memberIdx));
    }
    @GetMapping("/diary/{diaryIdx}")
    public SingleResult<Diary> findById(@PathVariable Long diaryIdx){
        return responseService.getSingleResult(diaryService.findById(diaryIdx));
    }
}
