package lzz.base.designmode.handlermode.handler;

public class PayCostHandler extends GoodBuildHandler {
    @Override
    public void service() {
        System.out.println(this.getGoodsBuild().toString());
        if (this.next != null) {
            this.next.service();
        }
    }
}
