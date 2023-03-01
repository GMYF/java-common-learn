package lzz.framework.dyncamicAgent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 定义动态代理handler,并实现AOP
 */
public class DyncamicAgent {
    public static Object agent(Class interfaceClass, Object proxy,Object handler) {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new MyHandler(proxy,(NotifyerHandler)handler));
    }

    static class MyHandler implements InvocationHandler {
        private Object proxy;

        private NotifyerHandler handler;

        private MyHandler(Object proxy,NotifyerHandler handler) {
            this.handler = handler;
            this.proxy = proxy;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("-------invoke begin-------");
            handler.invoke();
            return method.invoke(this.proxy, args);
        }
    }
}