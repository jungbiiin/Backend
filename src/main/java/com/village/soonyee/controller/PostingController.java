package com.village.soonyee.controller;

import com.village.soonyee.domain.Posting;
import com.village.soonyee.dto.PostingDto;
import com.village.soonyee.response.ResponseService;
import com.village.soonyee.response.result.CommonResult;
import com.village.soonyee.response.result.ListResult;
import com.village.soonyee.response.result.SingleResult;
import com.village.soonyee.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingController {
    private final PostingService postingService;
    private final ResponseService responseService;

    @PostMapping("/posting")
    public CommonResult write(@RequestBody PostingDto postingDto){
        postingService.write(postingDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/posting")
    public ListResult<Posting> findAll(){
        return responseService.getListResult(postingService.findAll());
    }

    @GetMapping("/posting/{postingIdx}")
    public SingleResult<Posting> findOne(@PathVariable Long postingIdx){
        return responseService.getSingleResult(postingService.findOne(postingIdx));
    }

    @DeleteMapping("/posting/{postingIdx}")
    public CommonResult delete(@PathVariable Long postingIdx){
        postingService.deleteOne(postingIdx);
        return responseService.getSuccessResult();
    }

    @PutMapping("/posting/{postingIdx}")
    public CommonResult update(@PathVariable Long postingIdx,@RequestBody PostingDto postingDto){
        postingService.update(postingIdx,postingDto);
        return responseService.getSuccessResult();
    }
}
