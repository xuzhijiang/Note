package com.jinxuliang.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PreferenceServlet", urlPatterns = { "/preference" })
public class PreferenceServlet extends HttpServlet {
    public static final String MENU =
            "<div style='background:#e8e8e8;"
                    + "padding:15px'>"
                    + "<a href='cookieClass'>Cookie Class</a>&nbsp;&nbsp;"
                    + "<a href='cookieInfo'>Cookie Info</a>&nbsp;&nbsp;"
                    + "<a href='preference'>Preference</a>" + "</div>";

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head>" + "<title>Preference</title>"
                        + "<style>table {" + "font-size:small;"
                        + "background:NavajoWhite }</style>"
                        + "</head><body>"
                        + MENU
                        + "Please select the values below:"
                        + "<form method='post'>"
                        + "<table>"
                        + "<tr><td>Title Font Size: </td>"
                        + "<td><select name='titleFontSize'>"
                        + "<option>large</option>"
                        + "<option>x-large</option>"
                        + "<option>xx-large</option>"
                        + "</select></td>"
                        + "</tr>"
                        + "<tr><td>Title Style & Weight: </td>"
                        +"<td><select name='titleStyleAndWeight' multiple>"
                        + "<option>italic</option>"
                        + "<option>bold</option>"
                        + "</select></td>"
                        + "</tr>"
                        + "<tr><td>Max. Records in Table: </td>"
                        + "<td><select name='maxRecords'>"
                        + "<option>5</option>"
                        + "<option>10</option>"
                        + "</select></td>"
                        + "</tr>"
                        + "<tr><td rowspan='2'>"
                        + "<input type='submit' value='Set'/></td>"
                        + "</tr>"
                        + "</table>" + "</form>" + "</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
    IOException {
        String maxRecords = request.getParameter("maxRecords");
        String[] titleStyleAndWeight = request.getParameterValues("titleStyleAndWeight");
        String titleFontSize =request.getParameter("titleFontSize");

        response.addCookie(new Cookie("maxRecords", maxRecords));
        response.addCookie(new Cookie("titleFontSize",titleFontSize));

        // delete titleFontWeight and titleFontStyle cookies first
        // Delete cookie by adding a cookie with the maxAge = 0;

        // Cookies是一个很小的信息片段，可自动地在浏览器和Web服务器间交互，因此
        // cookies可存储在多个页面间传递的信息。
        // Cookie作为HTTP header的一部 分，其传输由HTTP协议控制。此外，
        // 可以控制cookies的有效时间。浏览器通 常支持每个网站设置20个cookies。

        // 可以通过传递name和value两个参数给Cookie类的构造函数来创建一个
        // cookies： `Cookie cookie = new Cookie(name, value);`
        Cookie cookie = new Cookie("titleFontWeight", "");
        // 创建完一个Cookie对象后，你可以设置domain、 path和maxAge属性。
        // 其中， maxAge属性决定cookie何时过期
        cookie.setMaxAge(0);
        // 要将cookie发送到浏览器，需要调用HttpServletResponse的add方法：
        // `httpServletResponse.addCookie(cookie);`
        response.addCookie(cookie);

        cookie = new Cookie("titleFontStyle", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        if (titleStyleAndWeight != null) {
            for (String style : titleStyleAndWeight) {
                if (style.equals("bold")) {
                    response.addCookie(new
                            Cookie("titleFontWeight", "bold"));
                } else if (style.equals("italic")) {
                    response.addCookie(new Cookie("titleFontStyle",
                            "italic"));
                }
            }
        }
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head>" + "<title>Preference</title>"
                        + "</head><body>" + MENU
                        + "Your preference has been set."
                        + "<br/><br/>Max. Records in Table: " + maxRecords
                        + "<br/>Title Font Size: " + titleFontSize
                        + "<br/>Title Font Style & Weight: ");
        // titleStyleAndWeight will be null if none of the options
        // was selected
        if (titleStyleAndWeight != null) {
            writer.println("<ul>");
            for (String style : titleStyleAndWeight) {
                writer.print("<li>" + style + "</li>");
            }
            writer.println("</ul>");
        }
        writer.println("</body></html>");
    }
}