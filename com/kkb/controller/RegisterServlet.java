package com.kkb.controller;

import com.kkb.dao.UserDao;
import com.kkb.exception.LoginException;
import com.kkb.pojo.User;
import com.kkb.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        User user = new User(userName, password);

        UserService userService = new UserService();
        boolean b = false;
        try {
            b = userService.registerUser(user);
            if(b) {
                req.setAttribute("msg","注册成功！");
                resp.sendRedirect("login.jsp");
            } else {
                req.setAttribute("msg","注册失败");
                req.getRequestDispatcher("register.jsp").forward(req,resp);
            }
        } catch (LoginException e) {
            req.setAttribute("msg","注册失败");
            req.getRequestDispatcher("register.jsp").forward(req,resp);
        }

    }
}
