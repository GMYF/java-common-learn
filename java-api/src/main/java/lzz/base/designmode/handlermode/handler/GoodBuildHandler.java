package lzz.base.designmode.handlermode.handler;

import lzz.base.designmode.handlermode.entity.GoodsBuild;

public abstract class GoodBuildHandler {
    private GoodsBuild goodsBuild;

    public GoodsBuild getGoodsBuild() {
        return goodsBuild;
    }

    public void setGoodsBuild(GoodsBuild goodsBuild) {
        this.goodsBuild = goodsBuild;
    }

    protected GoodBuildHandler next;

    public void setNext(GoodBuildHandler next) {
        this.next = next;
    }
    public abstract void service() ;
}
