package lzz.base.designmode.handlermode.dao;

import lzz.base.designmode.handlermode.entity.GoodsBuild;
import lzz.base.designmode.handlermode.enums.GoodsBuildEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HandlerDaoImpl implements HandlerDao{
    private static Map<Integer, GoodsBuild> goodsBuildMap = new HashMap<>();

    static {
        Arrays.stream(GoodsBuildEnum.values()).forEach(goodsBuildEnum -> {
            GoodsBuild goodsBuild = goodsBuildEnum.getGoodsBuild();
            goodsBuildMap.put(goodsBuild.getGoodsHandlerId(), goodsBuild);
        });
    }
    @Override
    public GoodsBuild getGoodBuild(int handlerId) {
        return goodsBuildMap.get(handlerId);
    }

    @Override
    public GoodsBuild getFirstHandler() {
        for (Map.Entry<Integer, GoodsBuild> goodsBuildEntry : goodsBuildMap.entrySet()) {
            GoodsBuild currGoodBuild = goodsBuildEntry.getValue();
            if (currGoodBuild.getGoodsPrevHandlerId() == null) {
                return currGoodBuild;
            }
        }
        return null;
    }
}
