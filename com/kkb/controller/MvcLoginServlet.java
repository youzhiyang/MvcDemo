package com.kkb.controller;

import com.kkb.pojo.User;
import com.kkb.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/doLogin")
public class MvcLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        User user = new User(userName,password);
        UserService userService = new UserService();
        try {
            //验证登入
            User user2 = userService.checkLogin(user);
            if(user2 != null) {
                req.getSession().setAttribute("user",user2);
                req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req,resp);
            } else {
                req.getSession().setAttribute("error","用户名和密码不存在！");
                resp.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error",e.getMessage());
            resp.sendRedirect("login.jsp");
        }
    }
}
