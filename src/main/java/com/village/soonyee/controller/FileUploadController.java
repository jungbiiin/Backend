package com.village.soonyee.controller;

import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.FileNotExistsException;
import com.village.soonyee.exception.exception.WrongPathException;
import com.village.soonyee.response.ResponseService;
import com.village.soonyee.response.result.CommonResult;
import com.village.soonyee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class FileUploadController {
    private final ResponseService responseService;
    private final MemberService memberService;
    @Value("${file.upload.location}")
    private String fileDir;
    @PatchMapping("/upload")
    public CommonResult saveFile(@RequestParam MultipartFile file){
        if(file.isEmpty()){
            throw new FileNotExistsException("File doesn't exist", ErrorCode.FILE_NOT_EXIST);
        }
        String fullPath = fileDir + file.getOriginalFilename();
        try{
            file.transferTo(new File(fullPath));
        }catch (IOException e){
            throw new WrongPathException("Path isn't right",ErrorCode.WRONG_PATH);
        }
        memberService.uploadProfile(fullPath);
        return responseService.getSuccessResult();
    }
    @GetMapping("/profile/{memberIdx}")
    public ResponseEntity<Resource> viewImg(@PathVariable Long memberIdx) throws IOException{
        String profile = memberService.findById(memberIdx).getProfile();
        Path path=new File(profile).toPath();
        FileSystemResource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .body(resource);
    }
}
