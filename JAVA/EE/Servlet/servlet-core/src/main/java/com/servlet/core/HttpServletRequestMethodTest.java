package com.servlet.core;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Content-Type是application/x-www-form-urlencoded时，客户端可以通过body发送键值对数据，例如key1=value1&amp;key2=value2
 * Content-Type是application/json时，客户端可以通过body发送json字符串；如果是application/xml时可以发送xml字符串。
 * Content-Type是application/octet-stream时，客户端可以通过body发送Binary数据，例如文件或者任何能转成流的数据。
 * Content-Type是multipart/form-data时，客户端可以通过body发送一个表单。
 *
 * 上传文件:
 *     <form action="/singleUpload" method="post" enctype="multipart/form-data">
 *         用户头像: <input type="file" name="headerimg"/> <br />
 *         用户名: <input type="text" name="username"/> <br />
 *         <input type="submit"/>
 *     </form>
 */
@WebServlet(urlPatterns = {"/httpServletRequestMethodTest"})
public class HttpServletRequestMethodTest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 测试 HttpServletRequest 的方法
         * request.getRequestURL() http://localhost:8080/jqueryLearn/resources/request.jsp
         * request.getRequestURI()  /jqueryLearn/resources/request.jsp (包含ContextPath,但是不包含ip和port)
         * request.getContextPath() /jqueryLearn (ContextPath上下文路径)
         * request.getServletPath() /resources/request.jsp (不包含ContextPath,ip,和port的路径)
         * 注： resources为WebContext下的目录名, jqueryLearn 为ContextPath,也就是工程名
         */
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("request.getMethod():" + request.getMethod()); // get和post都可用
        System.out.println("request.getContentType(): " + request.getContentType()); //  get和post都可用,例如返回：application/json ，multipart/form-data， application/xml等
        System.out.println("request.getServletPath(): 返回除去host和工程名部份的路径: " + request.getServletPath());
        System.out.println("request.getContextPath(): 返回工程名部份，如果工程映照为/，此处返回则为空: " + request.getContextPath());
        System.out.println("request.getRequestURL(): 返回全路径: " + request.getRequestURL());
        System.out.println("request.getRequestURI(): 返回除去host（域名或ip）部份的路径: " + request.getRequestURI());
        System.out.println("request.getQueryString(): " + request.getQueryString());
        System.out.println("request.getRealPath(\"/\"): " + request.getRealPath("/"));
        System.out.println("getServletContext().getRealPath:"+getServletContext().getRealPath("/"));
        System.out.println("request.getParameterNames(): " + request.getParameterNames()); // get和post都可用，注：不适用contentType为multipart/form-data
        System.out.println("request.getParameter(\"test\"): " + request.getParameter("test")); // get和post都可用，注：不适用contentType为multipart/form-data
        System.out.println("request.getParameterMap():" + request.getParameterMap()); // get和post都可用，注： 不适用contentType为multipart/form-data
        // request.getInputStream() : 适用于如：application/json，xml, multipart/form-data 对应json/xml/表单

        /**
         * 获取参数列表:
         *      1). getQueryString() 只适用于GET,比如客户端发送http://localhost/testServlet?a=b&c=d&e=f,通过request.getQueryString()得到的是a=b&c=d&e=f.
         *
         *      2). getParameter() GET和POST都可以使用, 但如果是POST请求, 要根据 表单提交数据 的 编码方式 来确定 getParameter() 能否使用.
         *          a). 当post编码方式是(application/x-www-form-urlencoded)时才能使用 getParameter().这种编码方式(application/x-www-form-urlencoded)虽然简单，
         *              但对于传输大块的二进制数据显得力不从心.
         *          b). 对于传输大块的二进制数这类数据，浏览器采用了另一种编码方式("multipart/form-data"), 这时 getParameter() 就不可以使用了,
         *              这时就需要使用下面的两种方法: getInputStream()和getReader(), 这两种方法获取的是Http请求包的包体,
         *              因为GET方式请求一般不包含包体.所以这两种方法一般用于POST请求获取参数.需要注意的是：
         *                  1). request.getParameter()、 request.getInputStream()、request.getReader()这三种方法是有冲突的，因为流只能被读一次。
         *                      a). 比如： 当form表单内容采用 enctype=application/x-www-form-urlencoded编码时，先通过调用request.getParameter()方法得到参数后,
         *                      再调用request.getInputStream()或request.getReader()已经得不到流中的内容，
         *                      因为在调用 request.getParameter()时系统可能对表单中提交的数据以流的形式读了一次,反之亦然。
         *                      b). 当form表单内容采用 enctype=multipart/form-data编码时，即使先调用request.getParameter()也得不到数据，
         *                          这个时候调用request.getParameter()是无效的.所以这时调用request.getParameter()方法对 request.getInputStream()或request.getReader()没有冲突，
         *                          即使已经调用了 request.getParameter()方法也可以通过调用request.getInputStream()或request.getReader()得到表单中的数据,
         *
         *                      而request.getInputStream()和request.getReader()在同一个响应中是不能混合使用的,如果混合使用就会抛异常
         */

        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

        // forward, 转发请求到 /xx/xx:
        // request.getRequestDispatcher("/xx/xx").forward(request, response);

        // include:
        // request.getRequestDispatcher("/xx/xx").include(request, response);

        response.setContentType("text/html");
        response.getWriter().write("*********HttpServletRequestMethodTest doGet*********");
    }
}
