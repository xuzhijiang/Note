package com.springboot.web.restful.curd.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/download")
public class DowloadController {

    @GetMapping("/video")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
        // 1. 得到要下载的文件流
        ServletContext servletContext = request.getServletContext();
        // 获取文件的路径
        // String realPath = servletContext.getRealPath("asserts/js/bootstrap.min.js");

        // 把文件读取到字节数组
        FileInputStream is = new FileInputStream("D:\\test1.html");
        byte[] tmp = new byte[is.available()];
        is.read(tmp);
        is.close();

        // 2. 将要下载的文件流返回
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + "bootstrap.min.js");

        return new ResponseEntity<>(tmp, headers, HttpStatus.OK);
    }
}
