package dyncamicAgent;

class Bnana implements Fruit {

    @Override
    public void show() {
        System.out.println("当前类[" + Bnana.class.getName() + "]is invoked!");
    }

    @Override
    public int sum(int widht, int height) {
        return 0;
    }
}