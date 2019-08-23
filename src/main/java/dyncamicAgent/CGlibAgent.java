package dyncamicAgent;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlibAgent implements MethodInterceptor {
    private Object proxy;
    private Object getInstance(Object proxy){
        this.proxy = proxy;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.proxy.getClass());
        // 回调方法
        enhancer.setCallback(this);
        //创建代理方法
        return enhancer.create();
    }

    public static void main(String[] args) {
        CGlibAgent cGlibAgent = new CGlibAgent();
        Bnana bnaba = (Bnana) cGlibAgent.getInstance(new Bnana());
//        bnaba.show();
        bnaba.sum(1,2);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>before invoking");
        Object ret = methodProxy.invokeSuper(o,objects);
        System.out.println(">>>>after invoking");
        System.out.println("Object ret ->"+ret);
        return ret;
    }
}
