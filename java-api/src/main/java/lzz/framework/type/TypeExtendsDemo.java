package lzz.framework.type;

import java.util.List;

public class TypeExtendsDemo<T> {
    public TypeExtendsDemo(Builder<T> builder) {
        this.data = builder.data;
    }

    /**
     * Builder模式，初始化属性
     * @param <T>
     */
    public static class Builder<T> {
        public Builder(T data) {
            this.data = data;
        }

        private T data;

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public TypeExtendsDemo<T> build() {
            return new TypeExtendsDemo<>(this);
        }
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 使用固定上边界的通配符的泛型, 就能够接受指定类及其子类类型的数据。
     * @param peopleList
     */
    public void getInfo(List<? extends T> peopleList) {
        peopleList.forEach(System.out::println);
    }

    /**
     * 使用固定下边界的通配符的泛型, 就能够接受指定类及其父类类型的数据.。
     * @param peopleList
     */
    public void getInfo2(List<? super T> peopleList) {
        peopleList.forEach(System.out::println);
    }

    public void getInfo3(List<?> peopleList) {

    }

    /**
     * 返回泛型，必须指定<T>
     * @param data
     * @param <T>
     * @return
     */
    public <T> T getObj(T data) {
        System.out.println(data);
        return data;
    }
}
