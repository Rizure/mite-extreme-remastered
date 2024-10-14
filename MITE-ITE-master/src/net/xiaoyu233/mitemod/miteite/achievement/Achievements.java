package net.xiaoyu233.mitemod.miteite.achievement;

import net.minecraft.Achievement;
import net.minecraft.AchievementList;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.trans.entity.EntityBatTrans;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class Achievements {

    public static Achievement vibraniumIngot;
    public static Achievement wearAllVibraniumPlateArmor;
    public static Achievement vibraniumAnvil;
    public static Achievement vibraniumFurnace;

    public static Achievement openShop;
    public static Achievement rideBat;
    public static Achievement spawnBlock;
    public static Achievement gemSettingBlock;

    public static Achievement ringKillerCopper;
    public static Achievement itemDynamicCoreIron;
    public static Achievement killZombieBoss;
    public static Achievement fishFortune;
    public static Achievement razTime;
    public static Achievement Achievement_zhujidan;
    public static Achievement Achievement_x1;
    public static Achievement Achievement_x2;
    public static Achievement Achievement_x3;
    public static Achievement Achievement_x4;
    public static Achievement Achievement_x5;
    public static Achievement Achievement_x6;
    public static Achievement Achievement_x7;
    public static Achievement Achievement_x8;
    public static Achievement Achievement_x9;
    public static Achievement Achievement_x0;
    public static Achievement coldSpiderLeg;
    public static Achievement spicyStrip;
    public static void registerAchievements(){
        vibraniumIngot = new Achievement(getNextAchievementID(),"vibraniumIngot",-2,17, Items.VIBRANIUM_INGOT, AchievementList.adamantiumIngot).registerAchievement();
        wearAllVibraniumPlateArmor = new Achievement(getNextAchievementID(),"vibraniumArmor",12,1,Items.VIBRANIUM_CHESTPLATE,AchievementList.wearAllAdamantiumPlateArmor).setSpecial().registerAchievement();
        vibraniumAnvil = new Achievement(getNextAchievementID(),"vibraniumAnvil",0,17, Blocks.anvilVibranium, vibraniumIngot).setSpecial().registerAchievement();
        vibraniumFurnace = new Achievement(getNextAchievementID(),"vibraniumFurnace",-4,17,Blocks.furnaceVibraniumIdle,vibraniumIngot).setSpecial().registerAchievement();
        openShop = (new Achievement(getNextAchievementID(), "openShop", -4, 2, new ItemStack(Blocks.blockColorful, 1, 1), AchievementList.openInventory)).setIndependent().registerAchievement();
        rideBat = new Achievement(getNextAchievementID(),"rideBat",9,-5, Item.saddle, AchievementList.flyPig).setSpecial().registerAchievement();
        fishFortune = new Achievement(getNextAchievementID(),"fishFortune",12,3, Item.fishingRodIron, AchievementList.fishingRod).registerAchievement();
        spawnBlock = new Achievement(getNextAchievementID(),"spawnBlock",-5,7, Blocks.blockSpawn, AchievementList.diamonds).setFlipped().registerAchievement();
        gemSettingBlock = new Achievement(getNextAchievementID(),"gemSettingBlock",1,5, Blocks.gemSetting, AchievementList.acquireIron).registerAchievement();
        ringKillerCopper = new Achievement(getNextAchievementID(),"ringKillerCopper",-2,3, Items.ringKillerCopper, AchievementList.emeralds).registerAchievement();
        itemDynamicCoreIron = new Achievement(getNextAchievementID(),"itemDynamicCoreIron",3,3, Items.itemDynamicCoreIron, AchievementList.acquireIron).registerAchievement();
        killZombieBoss = new Achievement(getNextAchievementID(),"killZombieBoss",0,11, Items.VIBRANIUM_WAR_HAMMER, AchievementList.portal).registerAchievement();
        razTime = (new Achievement(getNextAchievementID(), "razTime", -6, 2, new ItemStack(Items.c4, 1, 1), AchievementList.openInventory)).setIndependent().registerAchievement();
        Achievement_zhujidan = (new Achievement(getNextAchievementID(), "achievement_zhujidan", 1, -4, Items.chikitan, AchievementList.seeds)).registerAchievement();
        Achievement_x1 = (new Achievement(getNextAchievementID(), "achievement_x1", 1, -5, Items.chikitan, Achievement_zhujidan)).registerAchievement();
        Achievement_x2 = (new Achievement(getNextAchievementID(), "achievement_x2", 0, -5, Items.chikitan, Achievement_x1)).registerAchievement();
        Achievement_x3 = (new Achievement(getNextAchievementID(), "achievement_x3", -1, -5, Items.chikitan, Achievement_x2)).registerAchievement();
        Achievement_x4 = (new Achievement(getNextAchievementID(), "achievement_x4", -2, -5, Items.chikitan, Achievement_x3)).registerAchievement();
        Achievement_x5 = (new Achievement(getNextAchievementID(), "achievement_x5", -3, -5, Items.chikitan, Achievement_x4)).registerAchievement();
        Achievement_x6 = (new Achievement(getNextAchievementID(), "achievement_x6", -4, -5, Items.chikitan, Achievement_x5)).registerAchievement();
        Achievement_x7 = (new Achievement(getNextAchievementID(), "achievement_x7", -5, -5, Items.chikitan, Achievement_x6)).registerAchievement();
        Achievement_x8 = (new Achievement(getNextAchievementID(), "achievement_x8", -6, -5, Items.chikitan, Achievement_x7)).registerAchievement();
        Achievement_x9 = (new Achievement(getNextAchievementID(), "achievement_x9", -7, -5, Items.chikitan, Achievement_x8)).registerAchievement();
        Achievement_x0 = (new Achievement(getNextAchievementID(), "achievement_x0", -8, -5, Items.chikitan, Achievement_x9)).setSpecial().registerAchievement();
        coldSpiderLeg = (new Achievement(getNextAchievementID(), "coldSpiderLeg", -8, 4, new ItemStack(Items.coldSpiderLeg, 1, 1), Achievements.razTime)).setIndependent().registerAchievement();
        spicyStrip = new Achievement(getNextAchievementID(), "spicyStrip", -8, 2, new ItemStack(Items.spicyStrip,1),Achievements.razTime).setIndependent().registerAchievement();
    }
    private static int getNextAchievementID(){
        return Constant.nextAchievementID++;
    }
}
