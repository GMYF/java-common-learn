package lzz.spring.base.service.impl;

import lzz.spring.base.service.DemoService;
import lzz.spring.build.annotation.ReService;

@ReService("demoService")
public class DemoServiceImpl implements DemoService {
    @Override
    public String showLog(String userName) {
        String log = "操作人：[" + userName +"]，DemoService被调用";
        System.out.println(log);
        return log;
    }
}
