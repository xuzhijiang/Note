package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-13 10:07
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FreemarkerTest {

    /**
     * 基于freemarker模板生成静态化文件
     */
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        // 获取类路径
        String classpath = this.getClass().getResource("/").getPath();
        log.info("=================>>>>>>>classpath====>>> " + classpath);
        //设置模板路径
        configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模板
        Template template = configuration.getTemplate("test1.ftl");

        //数据模型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        // 静态化内容
        System.out.println("静态化内容========>>>" + content);

        InputStream inputStream = IOUtils.toInputStream(content);
        //输出文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/test1.html"));
        int copy = IOUtils.copy(inputStream, fileOutputStream);
    }

    //基于模板字符串生成静态化文件
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //获取模板内容
        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString="" +
                "<html>\n" +
                "    <head></head>\n" +
                "    <body>\n" +
                "    名称：${name}\n" +
                "    </body>\n" +
                "</html>";

        //加载模板
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template","utf-8");

        // 获取数据
        Map map = getMap();
        // 获取静态化内容
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        // 静态化内容
        System.out.println("静态化内容========>>>" + content);

        // 输出静态化内容到文件
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/test2.html"));
        IOUtils.copy(inputStream, fileOutputStream);
    }

    // 准备数据
    private Map getMap(){
        Student stu1 = new Student("小明", 18, new Date(), 1000.86f);
        Student stu2 = new Student("小红", 19, new Date(), 200.1f, Arrays.asList(stu1), stu1);
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);

        // 向数据模型放数据
        Map<String, Object> map = new HashMap<>();
        map.put("name","自由开发者");
        map.put("stus", Arrays.asList(stu1, stu2));
        map.put("stu1",stu1);
        map.put("stuMap",stuMap);
        return map;
    }
}
