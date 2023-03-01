package lzz.base.designmode.handlermode.factory;

import lzz.base.designmode.handlermode.handler.GoodBuildHandler;
import lzz.base.designmode.handlermode.dao.HandlerDao;
import lzz.base.designmode.handlermode.dao.HandlerDaoImpl;
import lzz.base.designmode.handlermode.entity.GoodsBuild;

public class GoodBuildEnumFactory {
    private static HandlerDao handlerDao = new HandlerDaoImpl();

    public static GoodBuildHandler getFirstGoodBuildHandler() {
        GoodsBuild firstGoodBuild = handlerDao.getFirstHandler();
        GoodBuildHandler firstGoodBuildHandler = newGoodBuildHandler(firstGoodBuild);
        if (firstGoodBuildHandler == null) {
            return null;
        }
        firstGoodBuildHandler.setGoodsBuild(firstGoodBuild);

        GoodsBuild tempGoodBuild = firstGoodBuild;
        Integer nextHandlerId = null;
        GoodBuildHandler tempGoodBuildHandler = firstGoodBuildHandler;
        while ((nextHandlerId = tempGoodBuild.getGoodsNextHandlerId()) != null) {
            GoodsBuild nextGoodsBuild = handlerDao.getGoodBuild(nextHandlerId);
            GoodBuildHandler nextGoodBuildHandler = newGoodBuildHandler(nextGoodsBuild);
            if (nextGoodBuildHandler == null) {
                return null;
            }
            nextGoodBuildHandler.setGoodsBuild(nextGoodsBuild);
            
            tempGoodBuildHandler.setNext(nextGoodBuildHandler);
            tempGoodBuildHandler = nextGoodBuildHandler;
            tempGoodBuild = nextGoodsBuild;
        }
        // 返回第一个handler
        return firstGoodBuildHandler;
    }

    /**
     * 反射实体化具体的处理者
     * @param goodsBuild
     * @return
     */
    private static GoodBuildHandler newGoodBuildHandler(GoodsBuild goodsBuild) {
        // 获取全限定类名
        String className = goodsBuild.getClassPath();
        System.out.println(className);
        try {
            // 根据全限定类名，加载并初始化该类，即会初始化该类的静态段
            Class<?> clazz = Class.forName(className);
            return (GoodBuildHandler) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
