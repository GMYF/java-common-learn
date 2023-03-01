package lzz.base.designmode.handlermode.client;

import lzz.base.designmode.handlermode.factory.GoodBuildEnumFactory;
import lzz.base.designmode.handlermode.handler.GoodBuildHandler;

public class GoodBuildClient {
    public static void main(String[] args) {
        GoodBuildHandler firstGoodBuildHandler = GoodBuildEnumFactory.getFirstGoodBuildHandler();
        firstGoodBuildHandler.service();
    }
}
