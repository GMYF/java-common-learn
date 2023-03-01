package lzz.base.designmode.handlermode.handler;

public class TransportHandler extends GoodBuildHandler {

    @Override
    public void service() {
        System.out.println(this.getGoodsBuild().toString());
        if (this.next != null) {
            this.next.service();
        }
    }
}
