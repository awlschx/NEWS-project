package com.chx.controller;

import com.chx.common.Result;
import com.chx.entity.NewsUser;
import com.chx.service.NewsUserService;
import com.chx.utils.WebUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/user/*")
public class UserController extends HttpServlet {

    @Autowired
    private NewsUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if ("/logout".equals(path)) {
            req.getSession().invalidate();
            WebUtil.writeJson(resp, Result.success("退出成功", null));
            return;
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        switch (path == null ? "" : path) {
            case "/login" -> {
                String username = req.getParameter("username");
                String userPwd = req.getParameter("userPwd");
                NewsUser user = userService.login(username, userPwd);
                if (user != null) {
                    req.getSession().setAttribute("user", user);
                    WebUtil.writeJson(resp, Result.success(user));
                } else {
                    WebUtil.writeJson(resp, Result.error("用户名或密码错误"));
                }
            }
            case "/register" -> {
                NewsUser user = new NewsUser(null,
                        req.getParameter("username"),
                        req.getParameter("userPwd"),
                        req.getParameter("nickName"));
                boolean ok = userService.register(user);
                WebUtil.writeJson(resp, ok ? Result.success("注册成功", null) : Result.error("用户名已存在"));
            }
            default -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
