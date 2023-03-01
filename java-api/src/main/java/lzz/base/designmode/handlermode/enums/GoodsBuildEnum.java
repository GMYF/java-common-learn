package lzz.base.designmode.handlermode.enums;

import lzz.base.designmode.handlermode.entity.GoodsBuild;

public enum GoodsBuildEnum {

    TRANSPORT_MATERIAL(GoodsBuild.builder()
            .setGoodsName("原材料运输")
            .setGoodsNo("001")
            .setGoodsHandlerId(1)
            .setGoodsPrevHandlerId(null)
            .setGoodsNextHandlerId(2)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.TransportHandler")
            .build()),
    CHECK_ORIGIN_MATERIAL(GoodsBuild.builder()
            .setGoodsName("原材料实体检查")
            .setGoodsNo("002")
            .setGoodsHandlerId(2)
            .setGoodsPrevHandlerId(1)
            .setGoodsNextHandlerId(3)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.InspectionHandler")
            .build()),
    PAY_MATERIAL_COST(GoodsBuild.builder()
            .setGoodsName("支付原材料费用")
            .setGoodsNo("003")
            .setGoodsHandlerId(3)
            .setGoodsPrevHandlerId(2)
            .setGoodsNextHandlerId(4)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.PayCostHandler")
            .build()),
    GOODS_PRODUCTION(GoodsBuild.builder()
            .setGoodsName("商品生产")
            .setGoodsNo("004")
            .setGoodsHandlerId(4)
            .setGoodsPrevHandlerId(3)
            .setGoodsNextHandlerId(5)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.ProductionHandler")
            .build()),
    GOODS_CHECK(GoodsBuild.builder()
            .setGoodsName("商品检查")
            .setGoodsNo("005")
            .setGoodsHandlerId(5)
            .setGoodsPrevHandlerId(4)
            .setGoodsNextHandlerId(6)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.CheckHandler")
            .build()),
    GOODS_SELL(GoodsBuild.builder()
            .setGoodsName("商品销售")
            .setGoodsNo("006")
            .setGoodsHandlerId(6)
            .setGoodsPrevHandlerId(5)
            .setGoodsNextHandlerId(null)
            .setClassPath("com.lzz.base.designmode.handlermode.handler.SellHandler")
            .build());

    private GoodsBuild goodsBuild;
    GoodsBuildEnum(GoodsBuild goodsBuild) {
        this.goodsBuild = goodsBuild;
    }

    public GoodsBuild getGoodsBuild() {
        return goodsBuild;
    }

    public void setGoodsBuild(GoodsBuild goodsBuild) {
        this.goodsBuild = goodsBuild;
    }

}
