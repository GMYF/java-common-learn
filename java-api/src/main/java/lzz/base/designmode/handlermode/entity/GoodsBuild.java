package lzz.base.designmode.handlermode.entity;

public class GoodsBuild {
    private String goodsName;
    private String goodsNo;
    private String classPath;
    private Integer goodsHandlerId;
    private Integer goodsPrevHandlerId;
    private Integer goodsNextHandlerId;

    public GoodsBuild(Builder builder) {
        this.goodsName = builder.goodsName;
        this.goodsNo = builder.goodsNo;
        this.classPath = builder.classPath;
        this.goodsHandlerId = builder.goodsHandlerId;
        this.goodsPrevHandlerId = builder.goodsPrevHandlerId;
        this.goodsNextHandlerId = builder.goodsNextHandlerId;
    }

    public static void main(String[] args) {
        GoodsBuild goodsBuild = GoodsBuild.builder()
                .setGoodsName("不锈钢")
                .setGoodsNo("bxg001")
                .setGoodsHandlerId(1)
                .setGoodsPrevHandlerId(0)
                .setGoodsPrevHandlerId(2).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String goodsName;
        private String goodsNo;
        private String classPath;
        private Integer goodsHandlerId;
        private Integer goodsPrevHandlerId;
        private Integer goodsNextHandlerId;

        public GoodsBuild build() {
            return new GoodsBuild(this);
        }

        public Builder setGoodsName(String goodsName) {
            this.goodsName = goodsName;
            return this;
        }

        public Builder setClassPath(String classPath) {
            this.classPath = classPath;
            return this;
        }

        public Builder setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
            return this;
        }

        public Builder setGoodsHandlerId(Integer goodsHandlerId) {
            this.goodsHandlerId = goodsHandlerId;
            return this;
        }

        public Builder setGoodsPrevHandlerId(Integer goodsPrevHandlerId) {
            this.goodsPrevHandlerId = goodsPrevHandlerId;
            return this;
        }

        public Builder setGoodsNextHandlerId(Integer goodsNextHandlerId) {
            this.goodsNextHandlerId = goodsNextHandlerId;
            return this;
        }
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public Integer getGoodsHandlerId() {
        return goodsHandlerId;
    }

    public void setGoodsHandlerId(Integer goodsHandlerId) {
        this.goodsHandlerId = goodsHandlerId;
    }

    public Integer getGoodsPrevHandlerId() {
        return goodsPrevHandlerId;
    }

    public void setGoodsPrevHandlerId(Integer goodsPrevHandlerId) {
        this.goodsPrevHandlerId = goodsPrevHandlerId;
    }

    public Integer getGoodsNextHandlerId() {
        return goodsNextHandlerId;
    }

    public void setGoodsNextHandlerId(Integer goodsNextHandlerId) {
        this.goodsNextHandlerId = goodsNextHandlerId;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public String toString() {
        return "生产线" + this.getGoodsName() + "流程开始";
    }
}
