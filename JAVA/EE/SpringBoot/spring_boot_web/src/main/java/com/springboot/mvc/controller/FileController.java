package com.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

// 上传成功之后，在upload文件夹中出现了上传的文件。
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    // 上传文件的核心类型是Spring MVC所提供的MultipartFile类，
    // 它完成了所有困难的工作，我们只需要调用它的几个方法就可以实现文件上传功能。
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> uploadMultipartFile(MultipartFile file) {// 使用Spring MVC的MultipartFile类作为参数
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

    /**
     * 多文件上传(需完善)
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadMultipleFileHandler(@RequestParam("name") String[] names,
                                     @RequestParam("file") MultipartFile[] files) {

        if (files.length != names.length)
            return "Mandatory information missing";

        String message = "";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = names[i];
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                message = message + "You successfully uploaded file=" + name
                        + "<br />";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        return message;
    }
}

