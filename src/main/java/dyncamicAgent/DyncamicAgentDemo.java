package dyncamicAgent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *
 * @author zz.li
 * @date 2019-03-23
 * @see java.lang.reflect.InvocationHandler
 * @see java.lang.reflect.Proxy
 */
public class DyncamicAgentDemo {


    public static void main(String[]args){
        NotifyerHandler handler = ()->{
            System.out.println("handler has been invoked!!!");
        };
        Fruit fruit = (Fruit) DyncamicAgent.agent(Fruit.class, new Apple("苹果"),handler);
        fruit.show();
        System.out.println(fruit.sum(4,5));
        fruit = (Fruit) DyncamicAgent.agent(Fruit.class, new Bnana(),handler);
        fruit.show();
        fruit.sum(1,2);
    }
}
