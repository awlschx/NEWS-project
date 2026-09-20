package com.chx.controller;

import com.chx.common.Result;
import com.chx.entity.NewsHeadline;
import com.chx.entity.NewsUser;
import com.chx.service.NewsHeadlineService;
import com.chx.service.NewsTypeService;
import com.chx.utils.WebUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/headline/*")
public class HeadlineController extends HttpServlet {

    @Autowired
    private NewsHeadlineService headlineService;
    @Autowired
    private NewsTypeService typeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        switch (path == null ? "" : path) {
            case "/list" -> {
                String type = req.getParameter("type");
                Integer tid = (type == null || type.isEmpty()) ? null : Integer.valueOf(type);
                int page = toInt(req.getParameter("page"), 1);
                int pageSize = toInt(req.getParameter("pageSize"), 5);
                WebUtil.writeJson(resp, Result.success(headlineService.pageByType(tid, page, pageSize)));
            }
            case "/hot" -> WebUtil.writeJson(resp, Result.success(headlineService.hotList()));
            case "/detail" -> {
                int hid = Integer.parseInt(req.getParameter("hid"));
                WebUtil.writeJson(resp, Result.success(headlineService.detail(hid)));
            }
            case "/types" -> WebUtil.writeJson(resp, Result.success(typeService.listAll()));
            default -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        NewsUser loginUser = (NewsUser) req.getSession().getAttribute("user");
        if (loginUser == null) {
            WebUtil.writeJson(resp, Result.error("请先登录"));
            return;
        }
        String path = req.getPathInfo();
        switch (path == null ? "" : path) {
            case "/add" -> {
                NewsHeadline h = new NewsHeadline();
                h.setTitle(req.getParameter("title"));
                h.setArticle(req.getParameter("article"));
                h.setType(Integer.valueOf(req.getParameter("type")));
                h.setPublisher(loginUser.getUid());
                boolean ok = headlineService.add(h);
                WebUtil.writeJson(resp, ok ? Result.success("发布成功", null) : Result.error("发布失败"));
            }
            case "/update" -> {
                NewsHeadline h = new NewsHeadline();
                h.setHid(Integer.valueOf(req.getParameter("hid")));
                h.setTitle(req.getParameter("title"));
                h.setArticle(req.getParameter("article"));
                h.setType(Integer.valueOf(req.getParameter("type")));
                h.setPublisher(loginUser.getUid());
                boolean ok = headlineService.update(h);
                WebUtil.writeJson(resp, ok ? Result.success("修改成功", null) : Result.error("修改失败"));
            }
            case "/delete" -> {
                int hid = Integer.parseInt(req.getParameter("hid"));
                boolean ok = headlineService.delete(hid);
                WebUtil.writeJson(resp, ok ? Result.success("删除成功", null) : Result.error("删除失败"));
            }
            default -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static int toInt(String s, int def) {
        try {
            return s == null ? def : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
