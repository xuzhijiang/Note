package com.spring.mvc.core.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @GetMapping("/singleUpload")
    public String singleUpload() {
        return "single_file_upload";
    }

    /**
     * 单文件上传
     * @param username
     * @param multipartFile
     * @param model
     * @return
     */
    @PostMapping(value="/singleUpload")
    public String testSingleUpload(@RequestParam(value="username",required=false) String username,
                             @RequestParam("headerimg") MultipartFile multipartFile, Model model) {

        System.out.println("用户名为: " + username);
        System.out.println("form表单input的type为file的 这个input的 name属性的值 : "+multipartFile.getName());
        System.out.println("文件的名字 : "+multipartFile.getOriginalFilename());

        // 文件保存
        try {
            multipartFile.transferTo(new File("D:\\" + multipartFile.getOriginalFilename()));
            model.addAttribute("msg", "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "文件上传失败");
        }

        return "single_file_upload";
    }

    @GetMapping("/manyUpload")
    public String manyUpload() {
        return "many_file_upload";
    }

    /**
     * 多文件上传
     * @param username
     * @param multipartFile
     * @param model
     * @return
     */
    @PostMapping(value="/manyUpload")
    public String testManyUpload(@RequestParam(value="username",required=false) String username,
                                   @RequestParam("headerimg") MultipartFile[] multipartFile,
                                  @RequestParam("headerimg05") MultipartFile multipartFile5,
                                 Model model) {

        System.out.println("用户名为: " + username);

        // 通过遍历来保存文件
        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                try {
                    System.out.println("文件的名字 : "+file.getOriginalFilename());
                    file.transferTo(new File("D:\\" + file.getOriginalFilename()));
                    model.addAttribute("msg", "文件上传成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("msg", "文件上传失败");
                }
            }
        }

        try {
            System.out.println("form表单input的type为file的 这个input的 name属性的值 : "+multipartFile5.getName());
            multipartFile5.transferTo(new File("D:\\" + multipartFile5.getOriginalFilename()));
            model.addAttribute("msg", "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "文件上传失败");
        }

        return "many_file_upload";
    }
}
