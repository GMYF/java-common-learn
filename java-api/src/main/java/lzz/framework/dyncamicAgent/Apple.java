package lzz.framework.dyncamicAgent;

class Apple implements Fruit {
    String name;
    int width;
    int height;

    public Apple(String name) {
        this.name = name;
    }

    @Override
    public void show() {
        System.out.println("当前类[" + Apple.class.getName() + "]is invoked!对应的水果为" + this.name);
    }

    @Override
    public int sum(int width, int height) {
        this.width = width;
        this.height = height;
        System.out.println(this.width + "-" + this.height);
        return this.width * this.height;
    }
}