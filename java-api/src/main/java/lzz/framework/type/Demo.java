package lzz.framework.type;


public class Demo {
    public <T> T getInfo(TypeExtendsDemo<? super T> data) {
        System.out.println(data.getData());
        return null;
    }

    public static void main(String[] args) {
        TypeExtendsDemo<Women> demo = new TypeExtendsDemo.Builder<Women>(new Women("黄色", 24, "丽丽")).build();
        new Demo().getInfo(demo);
    }
}
