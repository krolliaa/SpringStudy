package com.zwm.controller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(key);
        webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        String strName = httpServletRequest.getParameter("name");
        String strAge = httpServletRequest.getParameter("age");
        System.out.println("容器信息：" + webApplicationContext);
        httpServletRequest.getRequestDispatcher("/result.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
