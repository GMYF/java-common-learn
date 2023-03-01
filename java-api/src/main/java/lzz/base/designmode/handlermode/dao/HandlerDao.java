package lzz.base.designmode.handlermode.dao;

import lzz.base.designmode.handlermode.entity.GoodsBuild;

public interface HandlerDao {
    GoodsBuild getGoodBuild(int handlerId);
    GoodsBuild getFirstHandler();
}
