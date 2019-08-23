package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 注解的学习，主要是类，方法，参数注解
 * @author 李振振
 * @version 1.0
 * @date 2019/8/23 14:49
 */

public class AnnotationDemo {
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface Key{
        String key();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Name{
        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Params{
       String param();
    }

    @Key(key = "myKey")
    public static class AnnotationTest{
        @Name(name = "mysql")
        public String getName(@Params(param = "aaa") String a){
            return a;
        }
    }

    public static void main(String[] args) {
        test(AnnotationTest.class);
    }

    public static void test(Class<?> t){
        // 验证注解是否被装载
        if(t.isAnnotationPresent(Key.class)){
            // 获取该注解
            Key key = t.getAnnotation(Key.class);
            System.out.println(key.key());
        }
        try {
            Method method = t.getDeclaredMethod("getName",String.class);
            try {
                if (method!=null){
                    if(method.isAnnotationPresent(Name.class)){
                        Name name = method.getAnnotation(Name.class);
                        System.out.println(name.name());
                        if("mysql".equals(name.name())){
                            System.out.println("\"方法被注解了\"");
                        }
                        // 这里获取入参中的注解
                        Parameter[] pList = method.getParameters();
                        for(Parameter p : pList ){
                            Params ps =  p.getAnnotation(Params.class);
                            System.out.println(method.invoke(new AnnotationTest(),ps.param()));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static class InvokeClass{
        public static void main(String[] args) {
            InvokeClass invokeClass = new InvokeClass();
            invokeClass.invoke();
        }
        public void invoke(){
            Class instance = null;
            try {
                instance =  Class.forName("test.annotation.AnnotationDemo");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // 直接实例化，然后去调用main方法
            Class anotationClass = null;
            Method main = null;
            String [] args = null;
            try {
                main = instance.getDeclaredMethod("main",String[].class);
                args = new String[]{"1"};
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                // 这里需要将参数变为Object对象，
                main.invoke(null,(Object)args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
