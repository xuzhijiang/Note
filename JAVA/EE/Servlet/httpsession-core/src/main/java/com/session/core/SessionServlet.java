package com.session.core;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("param");
        if ("getSession".equals(value)) {
            HttpSession session = req.getSession();
            System.out.println("********ccccccc: " + session.getId() + "***********session: " + session);
            resp.getWriter().write(session.getId());
        } else if ("invalidateSession".equals(value)) {
            HttpSession session = req.getSession();
            System.out.println("********BBBBBBBB: " + session.getId());
            // 销毁session
            session.invalidate();
            resp.getWriter().write("session*********invalidate");
        }
    }
}
