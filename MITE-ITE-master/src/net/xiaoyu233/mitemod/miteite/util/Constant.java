package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.Objects;
import java.util.Random;

public class Constant {
    public static final double[] ENHANCE_FACTORS;
    public static final bjo icons_ite = new bjo("textures/gui/icons_ite.png");
    public static final String MITE_ITE_VERSION = "v1.5B-patch11";
    public static final int MITE_ITE_VER_NUM = 15111;
    public static final bjo RES_VIBRANIUM_SINGLE = new bjo("textures/entity/chest/vibranium_single.png");
    public static int nextItemID = 2024;
    public static int nextBlockID = 160;
    public static int nextEnchantmentID = 96;
    public static int nextAchievementID = 136;
    public static ItemArmor[] HELMETS = null;
    public static ItemArmor[] CHESTPLATES = null;
    public static ItemArmor[] LEGGINGS = null;
    public static ItemArmor[] BOOTS = null;
    public static Item[] SWORDS = null;
    public static Item[] TOOLS = null;
    public static Item[][] WEAPONS = null;
    public static ItemArmor[][] ARMORS = null;
    public static BlockBed [] bedBlockTypes= null;
    public static Random GARandom = new Random();
    public static float getNormalMobModifier(String method,int day){
        return getNormalMobModifier(method,day,true);
    }
    public static float getEliteMobModifier(String method,int day){
        return getEliteMobModifier(method,day,true);
    }
    public static float getNormalMobModifier(String method, int day, boolean isOverworld){
        if(Objects.equals(method, "Health")){
            return (isOverworld ? 1.0F : 1.5F) + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 128.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Damage")){
            return (isOverworld ? 1.0F : 1.25F) + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 256.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Speed")){
            return 1.0F + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 2048.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else {
            return 1.0F;
        }
    }
    public static float getEliteMobModifier(String method,int day, boolean isOverworld){
        if(Objects.equals(method, "Health")){
            return (isOverworld ? 1.0F : 1.5F) + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 96.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Damage")){
            return (isOverworld ? 1.0F : 1.25F) + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 192.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Speed")){
            return 1.0F + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 2048.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else {
            return 1.0F;
        }
    }
    public static float getBossMobModifier(String method,int day){
        if(Objects.equals(method, "Health")){
            return 1.0F + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 80.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Damage")){
            return 1.0F + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 160.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else if(Objects.equals(method, "Speed")){
            return 1.0F + Math.min(day,Configs.wenscConfig.EnhanceLimit.ConfigValue) / 2048.0F * Configs.wenscConfig.enhanceMultiply.ConfigValue;
        }else {
            return 1.0F;
        }
    }

    static {
        ENHANCE_FACTORS = new double[Short.MAX_VALUE];
        for (int i = 0, enhance_factorsLength = ENHANCE_FACTORS.length; i < enhance_factorsLength; i++) {
            ENHANCE_FACTORS[i] = Math.pow(1.045, i*2.2) - 1;
        }
    }

    public static int getNextItemID() {
        return Constant.nextItemID++;
    }

    public static int getFoliageColorMaple()
    {
        // 纯白色
        return 16777215;
    }

    public static int getFoliageColorCherry()
    {
        // 纯白色
        //0xFFFFFF
        return 16777215;
    }

    public static ItemStack getBlockComponentWithNewWood(int metadata) {
        Material tool_material = BlockWorkbench.getToolMaterial(metadata);
        if (tool_material == Material.flint) {
            return new ItemStack(Blocks.wood1, 1, metadata);
        } else {
            return tool_material == Material.obsidian ? new ItemStack(Blocks.wood1, 1, metadata - 11) : null;
        }
    }

    public static void initItemArray() {
        HELMETS = new ItemArmor[]{Item.helmetLeather, Item.helmetChainCopper, Item.helmetChainIron, Item.helmetChainAncientMetal, Item.helmetRustedIron, Item.helmetCopper, Item.helmetIron, Item.helmetAncientMetal, Item.helmetChainMithril, Item.helmetMithril, Item.helmetAdamantium, Items.VIBRANIUM_HELMET};
        CHESTPLATES = new ItemArmor[]{Item.plateLeather, Item.plateChainCopper, Item.plateChainIron, Item.plateChainAncientMetal, Item.plateRustedIron, Item.plateCopper, Item.plateIron, Item.plateAncientMetal, Item.plateChainMithril, Item.plateMithril, Item.plateAdamantium, Items.VIBRANIUM_CHESTPLATE};
        LEGGINGS = new ItemArmor[]{Item.legsLeather, Item.legsChainCopper, Item.legsChainIron, Item.legsChainAncientMetal, Item.legsRustedIron, Item.legsCopper, Item.legsIron, Item.legsAncientMetal, Item.legsChainMithril, Item.legsMithril, Item.legsAdamantium, Items.VIBRANIUM_LEGGINGS};
        BOOTS = new ItemArmor[]{Item.bootsLeather, Item.bootsChainCopper, Item.bootsChainIron, Item.bootsChainAncientMetal, Item.bootsRustedIron, Item.bootsCopper, Item.bootsIron, Item.bootsAncientMetal, Item.bootsChainMithril, Item.bootsMithril, Item.bootsAdamantium, Items.VIBRANIUM_BOOTS};
        ARMORS = new ItemArmor[][]{HELMETS, CHESTPLATES, LEGGINGS, BOOTS};
        SWORDS = new Item[]{Item.daggerRustedIron, Item.swordRustedIron, Item.swordCopper, Items.clubIron, Item.swordIron, Item.swordAncientMetal, Items.clubMithril, Item.swordMithril, Items.clubAdamantium, Item.swordAdamantium, Items.clubVibranium, Items.VIBRANIUM_SWORD};
        TOOLS = new Item[]{Item.shovelRustedIron, Item.axeRustedIron, Item.battleAxeRustedIron, Item.shovelCopper, Item.battleAxeCopper, Item.shovelIron, Item.axeIron, Item.warHammerAncientMetal, Item.battleAxeMithril, Item.shovelMithril, Item.warHammerMithril, Items.VIBRANIUM_BATTLE_AXE};
        WEAPONS = new Item[][]{SWORDS,TOOLS};
        bedBlockTypes = new BlockBed[] {Blocks.blackBed,
                Blocks.redBed,Blocks.greenBed,Blocks.brownBed,Blocks.blueBed,Blocks.purpleBed,Blocks.cyanBed,Blocks.silverBed,Blocks.grayBed,Blocks.pinkBed,Blocks.limeBed,Blocks.yellowBed
                ,Blocks.lightBlueBed,Blocks.magentaBed,Blocks.orangeBed, Blocks.bed};
    }
}
