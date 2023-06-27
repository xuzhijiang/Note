package com.springboot.web.restful.curd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    // 单文件上传: 使用Spring MVC的MultipartFile类作为参数
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> uploadMultipartFile(MultipartFile file) {
        if (!file.isEmpty()) {
            // 获取上传上来的文件名
            String fileName = file.getOriginalFilename();
            System.out.println("文件名: " + fileName);
            File dest = new File(fileName);
            try {
                file.transferTo(dest);
            } catch (Exception e) {
                e.printStackTrace();
                return dealResultMap(false, "上传失败");
            }
            return dealResultMap(true, "上传成功");
        } else {
            return dealResultMap(false, "上传失败,因为文件为空 ");
        }
    }

    // 处理上传文件结果
    private Map<String, Object> dealResultMap(boolean success, String msg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("msg", msg);
        return result;
    }
}

