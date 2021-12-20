package com.village.soonyee.controller;

import com.village.soonyee.domain.GuestBook;
import com.village.soonyee.dto.GuestBookDto;
import com.village.soonyee.dto.GuestBookUpdateDto;
import com.village.soonyee.response.ResponseService;
import com.village.soonyee.response.result.CommonResult;
import com.village.soonyee.response.result.ListResult;
import com.village.soonyee.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class GuestBookController {
    private final ResponseService responseService;
    private final GuestBookService guestBookService;
    @PostMapping("/guestBook/{memberIdx}")
    public CommonResult join(@RequestBody GuestBookDto guestBookDto, @PathVariable Long memberIdx){
        guestBookService.join(memberIdx,guestBookDto);
        return responseService.getSuccessResult();
    }
    @GetMapping("/guestBook/{memberIdx}")
    public ListResult<GuestBook> findByTarget(@PathVariable Long memberIdx){
        return responseService.getListResult(guestBookService.findByTarget(memberIdx));
    }
    @DeleteMapping("/guestBook/{guestBookId}")
    public CommonResult delete(@PathVariable Long guestBookId){
        guestBookService.delete(guestBookId);
        return responseService.getSuccessResult();
    }
    @PutMapping("/guestBook/{guestBookId}")
    public CommonResult update(@PathVariable Long guestBookId, @RequestBody GuestBookUpdateDto guestBookUpdateDto){
        guestBookService.update(guestBookId,guestBookUpdateDto);
        return responseService.getSuccessResult();
    }
}
