package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;

public class ClientStrongholdCache {
    private static ChunkPosition cachedPos = null;

    // 由网络包接收器调用
    public static void updateStrongholdPos(ChunkPosition pos) {
        cachedPos = pos;
    }

    // 由渲染线程（指南针更新方法）调用
    public static ChunkPosition getStrongholdPos() {
        return cachedPos;
    }

}
