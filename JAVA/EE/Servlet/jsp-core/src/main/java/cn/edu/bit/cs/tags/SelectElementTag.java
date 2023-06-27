package cn.edu.bit.cs.tags;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SelectElementTag extends SimpleTagSupport {
    private String[] countries = {"Australia", "Brazil", "China" };

    public void doTag() throws IOException, JspException {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();
        out.print("<select>\n");
        for (int i=0; i<3; i++) {
            getJspContext().setAttribute("value", countries[i]);
            getJspContext().setAttribute("text", countries[i]);
            //getJspBody()方法返回一个JspFragment对象
            //依据用户定义的内容模板，将上述定义好的属性值填入，生成最终的HTML代码
            getJspBody().invoke(null);
        }
        out.print("</select>\n");
    }
}
