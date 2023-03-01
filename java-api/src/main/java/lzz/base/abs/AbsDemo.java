package lzz.base.abs;

public abstract class AbsDemo {
    public abstract void test();
    public void sout() {
        System.out.println("sout");
    }
    public static class AsbImpl extends AbsDemo {
        @Override
        public void test() {

        }

    }
}
