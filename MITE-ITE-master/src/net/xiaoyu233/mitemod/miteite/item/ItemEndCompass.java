package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.EntityBlizzard;
import net.xiaoyu233.mitemod.miteite.entity.EntityFinalZombieBoss;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.util.ClientStrongholdCache;
import net.xiaoyu233.mitemod.miteite.world.WorldGenBlizzardCave;

import java.util.Iterator;
import java.util.Random;

public class ItemEndCompass extends Item {
    protected ItemEndCompass(int par1, Material material) {
        super(par1, material, "end_compass");
        this.setCraftingDifficultyAsComponent(100.0F);
        this.setCreativeTab(CreativeModeTab.tabTools);
    }
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if(player.onServer()){
            player.sendChatToPlayer(ChatMessage.createFromText("已更新末地指南针。"));
            WorldServer server = player.getWorldServer();
            ChunkPosition strongholdPos = server.findClosestStructure("Stronghold", (int)player.posX, 64, (int)player.posZ);
            if(strongholdPos != null){
                player.sendChatToPlayer(ChatMessage.createFromText("已定位最近的末地要塞"));
                ClientStrongholdCache.updateStrongholdPos(strongholdPos);
                double sqrX = Math.pow(strongholdPos.x - player.getBlockPosX(), 2);
                double sqrZ = Math.pow(strongholdPos.z - player.getBlockPosZ(), 2);
                double sqrDis = sqrX + sqrZ;
                int Dis = (int) Math.sqrt(sqrDis);
                player.sendChatToPlayer(ChatMessage.createFromText("位于" + Dis + "m外。"));
            }else {
                player.sendChatToPlayer(ChatMessage.createFromText("未找到末地要塞，请重试。"));
            }
        } else {
            player.bobItem();
            player.swingArm();
        }
        return true;
    }
}
