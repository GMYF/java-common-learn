package lzz.spring.base.controller;

import lzz.spring.base.service.DemoService;
import lzz.spring.build.annotation.ReAutoWired;
import lzz.spring.build.annotation.ReController;
import lzz.spring.build.annotation.ReRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ReController
@ReRequestMapping("/demo")
public class DemoController {
    @ReAutoWired("demoService")
    private DemoService demoService;
    @ReRequestMapping("/show")
    public void showLog(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException {
        String log = demoService.showLog(userName);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.getWriter().write(log);
    }
}
