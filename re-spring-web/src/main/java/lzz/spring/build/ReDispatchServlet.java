package lzz.spring.build;

import cn.hutool.json.JSONUtil;
import lzz.spring.build.annotation.ReAutoWired;
import lzz.spring.build.annotation.ReController;
import lzz.spring.build.annotation.ReRequestMapping;
import lzz.spring.build.annotation.ReService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ReDispatchServlet extends HttpServlet {
    private Properties properties = new Properties();
    private List<String> beanFileList = new ArrayList<>();
    private Map<String, Object> IoCMap = new HashMap<>();
    private Map<String, Method> routeMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 6. 方法调用 invoke
        try {
            this.invoke(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 INNER Exception" + JSONUtil.toJsonStr(Arrays.stream(e.getStackTrace()).collect(Collectors.toList())));
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1. 加载配置文件，读取bean，返回List<String> 类名集合
        this.loadConfig(config);

        // 2. 扫描
        this.scanBean(properties.getProperty("scanDir"));
        // 3. 读取bean，存放IoC容器  返回Map<String,Object>
        this.loadBean();
        // 4. 依赖注入（DI）
        this.bindBean();
        // 5. url - method 绑定
        this.bindMethod();
        System.out.println("server started up");
    }

    private void scanBean(String path) {
        String filePath = "/" + path.replaceAll("\\.","/");
        URL url = this.getClass().getClassLoader().getResource(filePath);
        if (url == null) {
            throw new RuntimeException("bean目录异常");
        }
        File file = new File(url.getFile());
        for (File listFile : file.listFiles()) {
            String fullName;
            String fileName = "";
            if (listFile.isDirectory()) {
                scanBean(path + "." + listFile.getName());
            } else {
                if (!listFile.getName().endsWith(".class")) {
                    continue;
                }
                fileName = listFile.getName().replaceAll(".class", "");
                fullName = path + "." + fileName;
                beanFileList.add(fullName);
            }
        }
    }

    private void invoke(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
        if (!routeMap.containsKey(url)) {
            resp.getWriter().write("404 not found!!");
            return;
        }
        Method method = routeMap.get(url);
        Map<String,String[]> map = req.getParameterMap();
        method.invoke(IoCMap.get(transferFirstLowerCaseString(method.getDeclaringClass().getSimpleName())),new Object[] {req,resp,map.get("userName")[0]});
    }

    private void bindMethod() {
        for (Map.Entry<String, Object> entry : IoCMap.entrySet()) {
            if (!entry.getValue().getClass().isAnnotationPresent(ReRequestMapping.class)) {
                continue;
            }
            ReRequestMapping reRequestMapping = entry.getValue().getClass().getAnnotation(ReRequestMapping.class);

            // 获取url一级路由
            String routeHeadStr = reRequestMapping.value();
            // 获取url二级路由
            Method[] methods = entry.getValue().getClass().getMethods();
            Arrays.stream(methods).forEach(method -> {
                if (!method.isAnnotationPresent(ReRequestMapping.class)) {
                    return;
                }
                ReRequestMapping methodAnnotation = method.getAnnotation(ReRequestMapping.class);
                String secondRouteStr = methodAnnotation.value();
                String routeUrl = "/" + routeHeadStr + "/" + secondRouteStr;
                routeUrl = routeUrl.replaceAll("/+", "/");
                routeMap.put(routeUrl, method);
            });
        }
    }

    /**
     * 依赖注入
     * Map key bean的名称， value bean的实例
     */
    private void bindBean() {
        for (Map.Entry<String, Object> entry : IoCMap.entrySet()) {
            // 获取到依赖注入的注解
            //getDeclaredFields 获取到public、private、projected修饰
            // getFields 获取public
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(ReAutoWired.class)) {
                    continue;
                }
                ReAutoWired autoWired = field.getAnnotation(ReAutoWired.class);
                String beanName = autoWired.value().trim();
                if (isEmpty(beanName)) {
                    beanName = field.getType().getName();
                }

                // 设置为可见
                field.setAccessible(true);
                // ?
                try {
                    field.set(entry.getValue(), IoCMap.get(beanName));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * IoC容器加载bean
     */
    private void loadBean() {
        beanFileList.forEach(beanFileName -> {
            Class<?> clazzClass;
            Object object;
            try {
                clazzClass = Class.forName(beanFileName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // 被spring注入的bean才需要实例化
            if (clazzClass.isAnnotationPresent(ReController.class) || clazzClass.isAnnotationPresent(ReService.class)) {
                try {
                    object = clazzClass.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                String beanName  = clazzClass.getSimpleName();
                if (clazzClass.isAnnotationPresent(ReService.class)) {
                   ReService reService = clazzClass.getAnnotation(ReService.class);
                   String serviceName = reService.value();
                   if (!isEmpty(serviceName)) {
                       beanName = serviceName;
                   }
                   IoCMap.put(beanName, object);
                    for (Class<?> anInterface : clazzClass.getInterfaces()) {
                        if (IoCMap.containsKey(anInterface.getName())) {
                            throw new RuntimeException("has mutl Bean impl");
                        }
                        IoCMap.put(anInterface.getSimpleName(), object);
                    }
                }else {
                    // IoC的Bean名称，第一个名称小写
                    IoCMap.put(transferFirstLowerCaseString(beanName), object);
                }
            }
        });
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    private String transferFirstLowerCaseString(String beanFileName) {
        if (isEmpty(beanFileName)) {
            throw new RuntimeException("beanFileName is Empty");
        }
        char[] chars = beanFileName.toCharArray();
        chars[0] += 32;
        return new String(chars, 0, chars.length);
    }

    /**
     * 读取配置文件
     *
     * @param config
     * @throws IOException
     */
    private void loadConfig(ServletConfig config) {
        String configLocation = config.getInitParameter("configContextLocation");
        try (InputStream ins = this.getClass().getClassLoader().getResourceAsStream(configLocation);) {
            properties.load(ins);
        } catch (Exception e) {

        }
    }
}
