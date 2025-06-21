package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.OssUtil;
import com.sky.utils.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/admin/common")
@Slf4j
@RestController
public class CommonController {
    @Autowired
    private OssUtil ossUtil;
    @PostMapping("/upload")
    public Result<String> uploadController(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file);
        //构建一个不会重复的文件名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String uploadPath = ossUtil.upload(file.getBytes(), originalFilename);
        return Result.success(uploadPath);
    }
}
