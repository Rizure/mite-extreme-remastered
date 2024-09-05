package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Configs {
    public static Map <String, ConfigItem> wenscMap = new HashMap<>();

    public static class ConfigItem<T>{
        public String ConfigKey;
        public T ConfigValue;
        public T min;
        public T max;
        public boolean isNeedCompare = false;
        public String ConfigComment;
        ConfigItem(String key, T value, String comment){
            this.ConfigKey = key;
            this.ConfigValue = value;
            this.ConfigComment = comment;
        }
        ConfigItem(String key, T value, String comment, T min, T max){
            this.ConfigKey = key;
            this.ConfigValue = value;
            this.isNeedCompare = true;
            this.min = min;
            this.max = max;
            this.ConfigComment = comment + " [范围："+ min + "-" + max +"]";
        }
        public void setConfigValue(T configValue) {
            ConfigValue = configValue;
        }
        public T getConfigValue() {
            return this.ConfigValue;
        }
    }

    public static class wenscConfig {
        public static ConfigItem <Integer> EnhanceLimit = new ConfigItem("EnhanceLimit", 512, "怪物属性增强天数上限",0,89478);
        public static ConfigItem <Boolean> BlnGravel = new ConfigItem("BlnGravel", true, "是否增加燧石概率");
        public static ConfigItem <Boolean> isOpenStrongBoxBreakRecord = new ConfigItem("isOpenStrongBoxBreakRecord", true, "是否开启私人箱子破坏记录");
        public static ConfigItem <Boolean> isRecipeGATorch = new ConfigItem("isRecipeGATorch", true, "是否有一捆火把配方");
        public static ConfigItem <Boolean> isRecipeRingKiller = new ConfigItem("isRecipeRingKiller", true, "是否有魔灵刃配方");
        public static ConfigItem <Double> steppedMobDamageFactor = new ConfigItem("steppedMobDamageFactor", 1d, "渐进伤害每次增幅点数基础值(小数)", 0d ,100d);
        public static ConfigItem <Double> steppedMobDamageProgressMax = new ConfigItem("steppedMobDamageProgressMax", 4d, "怪物渐进伤害最大加成倍率", 0d,9d);
        public static ConfigItem <Integer> steppedMobDamageProgressIncreaseDay = new ConfigItem("steppedMobDamageProgressIncreaseDay_v0.1.0", 64, "渐进伤害每次增幅每提升100%所需天数(整数)", 32 ,512);
        public static ConfigItem <Integer> fishingXp = new ConfigItem("fishingXp", 5, "钓鱼经验(整数)", 1 ,10);
        public static ConfigItem <Integer> inRainDebuffTime = new ConfigItem("inRainDebuffTime", 3600, "雨中获得负面效果所需时长(整数)", 0 ,1000000);
        public static ConfigItem <Float> timeSpeedInDay = new ConfigItem("timeSpeedInDay", 1.0F, "白天时间流速(小数)", 0.25f ,2.0f);
        public static ConfigItem <Float> timeSpeedInNight = new ConfigItem("timeSpeedInNight", 1.0F, "夜晚时间流速(小数)", 0.25f ,2.0f);
        public static ConfigItem <Integer> playerDefenseCooldown = new ConfigItem("playerDefenseCooldown", 160, "玩家格挡冷却(tick)", 0 ,320);
        public static ConfigItem <Integer> playerDefenceMaxTime = new ConfigItem("playerDefenceMaxTime", 80, "玩家格挡最大时间(tick)", 0 ,160);
        public static ConfigItem <Boolean> playerDisarmPlayer = new ConfigItem("playerDisarmPlayer", true, "玩家缴械玩家");
        public static ConfigItem <Boolean> boostEnhance = new ConfigItem("boostEnhance",true,"怪物强化速度翻倍");
        public static ConfigItem <Boolean> annihilationSkeletonSpawnInLight = new ConfigItem("annihilationSkeletonSpawnInLight", false, "主世界湮灭骷髅在光照处生成");
        public static ConfigItem <Integer> ancientBoneLordSpawnLimitDay = new ConfigItem("ancientBoneLordSpawnLimitDay", 192, "主世界生成远古骷髅公爵所需天数(整数)", 0 ,384);
        public static ConfigItem <Integer> wanderingWitchSpawnChanceOverworld = new ConfigItem("wanderingWitchSpawnChanceOverworld", 25, "主世界流浪女巫生成成功概率百分比(整数)", 0 ,50);
        public static ConfigItem <Integer> wanderingWitchSpawnLimitDayOverworld = new ConfigItem("wanderingWitchSpawnLimitDayOverworld", 96, "主世界流浪女巫生成天数限制(整数)", 96 ,500);
        public static ConfigItem <Integer> wanderingWitchSpawnLimitDayOther = new ConfigItem("wanderingWitchSpawnLimitDayOther", 64, "地狱及地下世界流浪女巫生成天数限制(整数)", 64 ,500);
        public static ConfigItem <Integer> wanderingWitchSpawnChanceUnderworld = new ConfigItem("wanderingWitchSpawnChanceUnderworld", 50, "地下世界流浪女巫生成成功概率百分比(整数)", 0 ,100);
        public static ConfigItem <Boolean> underworldRandomTeleport = new ConfigItem("underworldRandomTeleport", false, "地下世界随机传送(开关)");
        public static ConfigItem <Integer> underworldRandomTeleportTimeNew = new ConfigItem("underworldRandomTeleportTimeNew", 132000, "地下世界随机传送时间(tick)", 0 ,1000000);
        public static ConfigItem <Boolean> underworldDebuff = new ConfigItem("underworldDebuff", false, "地下世界负面效果(开关)");
        public static ConfigItem <Integer> underworldDebuffPeriod1 = new ConfigItem("underworldDebuffPeriod1", 72000, "地下世界负面效果第一阶段触发时间(tick)", 0 ,144000);
        public static ConfigItem <Integer> underworldDebuffPeriod2 = new ConfigItem("underworldDebuffPeriod2", 108000, "地下世界负面效果第二阶段触发时间(tick)", 0 ,216000);
        public static ConfigItem <Boolean> netherrackDamage = new ConfigItem("netherrackDamage", true, "地狱烫脚(开关)");
        public static ConfigItem <Integer> netherrackDamageLimitDay = new ConfigItem("netherrackDamageLimitDay", 96, "地狱烫脚所需天数(整数)", 0 ,192);
        public static ConfigItem <Boolean> netherDebuff = new ConfigItem("netherDebuff", false, "地狱负面效果(开关)");
        public static ConfigItem <Integer> netherDebuffTime = new ConfigItem("netherDebuffTime", 72000, "地狱负面效果触发时间(tick)", 0 ,144000);
        public static ConfigItem <Integer> quartzMaxExpLevel = new ConfigItem("quartzMaxExpLevel", 35, "石英可提供经验最大等级", 0 ,1001);
        public static ConfigItem <Integer> fancyRedExp = new ConfigItem("fancyRedExp", 800, "红钻经验", 400 ,1600);
        public static ConfigItem <Integer> diamondExp = new ConfigItem("diamondExp", 500, "钻石经验", 250 ,1000);
        public static ConfigItem <Integer> emeraldExp = new ConfigItem("emeraldExp", 250, "绿宝石经验", 125 ,500);
        public static ConfigItem <Integer> quartzExp = new ConfigItem("quartzExp", 50, "石英经验", 25 ,100);
        public static ConfigItem <Integer> goldenAppleEatTime = new ConfigItem("goldenAppleEatTime", 32, "金苹果使用耗时（tick）", 16 ,32);
        public static ConfigItem <Integer> enchantedGoldenAppleEatTime = new ConfigItem("enchantedGoldenAppleEatTime", 16, "附魔金苹果使用耗时（tick）", 8 ,32);
        public static ConfigItem <Integer> chikitanEatTime = new ConfigItem("chikitanEatTime", 16, "筑基丹使用耗时（tick）", 8 ,32);
        public static ConfigItem <Double> allProtectionVDefenceFraction = new ConfigItem("allProtectionVDefenceFraction", 0.2d, "全套保护5伤害减免", 0d ,0.4d);
        public static ConfigItem <Boolean> mobSpawnerSpeedUpWithPlayers = new ConfigItem("mobSpawnerSpeedUpWithPlayers", true, "刷怪笼在旁边有玩家时加速生怪");
        public static ConfigItem <Boolean> isSpawnDragger = new ConfigItem("isSpawnDragger", true, "部分怪物是否生成伴随的拖曳骷髅");
        public static ConfigItem <Boolean> boneLordTweak = new ConfigItem("boneLordTweak", true, "骷髅领主增强");
        public static ConfigItem <Boolean> skeletonTripleShot = new ConfigItem("skeletonTripleShot", true, "三发骷髅");
        public static ConfigItem <Integer> compressedSkeletonSpawningWeight = new ConfigItem("compressedSkeletonSpawningWeight", 3, "分裂骷髅生成比重",0,100);
        public static ConfigItem <Integer> compressedSkeletonCrackStandTime = new ConfigItem("compressedSkeletonCrackStandTime", 0, "分裂骷髅分裂后停滞时间(tick)", 0 ,100);
        public static ConfigItem <Integer> compressedSkeletonExpandCount = new ConfigItem("compressedSkeletonExpandCount", 4, "分裂骷髅分裂数量",0, 8);
        public static ConfigItem <Boolean> skeletonForceMeleeAttack = new ConfigItem("skeletonForceMeleeAttack", true, "强制骷髅近战");
        public static ConfigItem <Float> boneLordAndLongdeadChangeWeaponChance = new ConfigItem("boneLordAndLongdeadChangeWeaponChance", 0.25f, "古尸和骷髅领主会切换武器概率",0f, 1f);
        public static ConfigItem <Boolean> batPoisonAttack = new ConfigItem("batPoisonAttack", true, "吸血蝙蝠攻击使玩家中毒");
        public static ConfigItem <Boolean> infernalCreeperBoost = new ConfigItem("infernalCreeperBoost", true, "增强地狱爬行者");
        public static ConfigItem <Boolean> wolvesAttackPlayersWithMeat = new ConfigItem("wolvesAttackPlayersWithMeat", true, "狼攻击快捷栏有肉的玩家");
        public static ConfigItem <Integer> villagerWoolToEmeraldShardCount = new ConfigItem("villagerWoolToEmeraldShardCount", 4, "村民用羊毛换绿宝石碎片-羊毛所需数量", 1 ,8);
        public static ConfigItem <Integer> villagerWoolToEmeraldShardShardCount = new ConfigItem("villagerWoolToEmeraldShardShardCount", 1, "村民用羊毛换绿宝石碎片-绿宝石碎片获得数量", 1 ,9);
        public static ConfigItem <Boolean> canBoostIronGolem = new ConfigItem("canBoostIronGolem", true, "可以强化铁傀儡");
        public static ConfigItem <Boolean> mobDefense = new ConfigItem("mobDefense", true, "怪物格挡");
        public static ConfigItem <Boolean> mobDisarmWhenDefence = new ConfigItem("mobDisarmWhenDefence", false, "怪物格挡时若有缴械附魔有几率缴械");
        public static ConfigItem <Boolean> mobDisarmWhenAttack = new ConfigItem("mobDisarmWhenAttack", true, "怪物攻击时若有缴械附魔有几率缴械");
        public static ConfigItem <Boolean> mobAttackCauseFire = new ConfigItem("mobAttackCauseFire", true, "怪物有几率在手持装备攻击时使目标着火");
        public static ConfigItem <Integer> annihilationSkeletonDespawnTime = new ConfigItem("annihilationSkeletonDespawnTime", 3600, "湮灭骷髅被攻击后消失用时(tick)", 1800 ,7200);
        public static ConfigItem <Float> enderDragonAttackWitherChance = new ConfigItem("enderDragonAttackWitherChance", 0.2f, "末影龙受攻击后给予玩家凋零几率",0f, 1f);
        public static ConfigItem <Float> enderDragonImmuneToArrowHealthPercent = new ConfigItem("enderDragonImmuneToArrowHealthPercent", 0.3f, "末影龙免疫弓箭血量百分比",0f, 1f);
        public static ConfigItem <Boolean> enderDragonAttackSlowness = new ConfigItem("enderDragonAttackSlowness", true, "末影龙攻击玩家给予玩家缓慢效果");
        public static ConfigItem <Float> netherModAttackFireChance = new ConfigItem("netherModAttackFireChance", 0.5f, "地狱生物攻击火焰附加概率",0f, 1f);
        public static ConfigItem <Integer> slimeAttackDamageMultiplier = new ConfigItem("slimeAttackDamageMultiplier", 1, "绿色史莱姆伤害倍数(整数)", 1 ,2);
        public static ConfigItem <Float> slimeAttackDamageBonus = new ConfigItem("slimeAttackDamageBonus", 1f, "绿色史莱姆伤害加成", 0f ,8f);
        public static ConfigItem <Integer> giantZombieSpawnZombieCooldown = new ConfigItem("giantZombieSpawnZombieCooldown", 300, "巨型僵尸生成僵尸间隔(tick)", 20 ,1200);
        public static ConfigItem <Integer> animalIllToDeathTime = new ConfigItem("animalIllToDeathTime", 144000, "生物生病死亡时间(tick)", 72000 ,288000);
        public static ConfigItem <Integer> breedXpChicken = new ConfigItem("breedXpChicken", 5, "鸡繁殖获得经验", 1 ,10);
        public static ConfigItem <Integer> breedXpSheep = new ConfigItem("breedXpSheep", 5, "羊繁殖获得经验", 1 ,10);
        public static ConfigItem <Integer> breedXpPig = new ConfigItem("breedXpPig", 5, "猪繁殖获得经验", 1 ,10);
        public static ConfigItem <Integer> breedXpCow = new ConfigItem("breedXpCow", 5, "牛繁殖获得经验", 1 ,10);
        public static ConfigItem <Integer> emeraldFrequencyBigHills = new ConfigItem("emeraldFrequencyBigHills", 6, "山地绿宝石矿每区块最少生成个数", 0 ,12);
        public static ConfigItem <Boolean> overworldAdamantiteOre = new ConfigItem("overworldAdamantiteOre", false, "主世界生成艾德曼");
        public static ConfigItem <Integer> copperFrequencyOverworld = new ConfigItem("copperFrequencyOverworld_v0.0.5", 40, "主世界铜矿生成频率", 0 ,60);
        public static ConfigItem <Integer> silverFrequencyOverworld = new ConfigItem("silverFrequencyOverworld_v0.0.5", 10, "主世界银矿生成频率", 0 ,60);
        public static ConfigItem <Integer> ironFrequencyOverworld = new ConfigItem("ironFrequencyOverworld_v0.0.5", 60, "主世界铁矿生成频率", 0 ,90);
        public static ConfigItem <Integer> goldFrequencyOverworld = new ConfigItem("goldFrequencyOverworld_v0.0.5.3", 20, "主世界金矿生成频率", 0 ,30);
        public static ConfigItem <Integer> mithrilFrequencyOverworld = new ConfigItem("mithrilFrequencyOverworld_v0.0.5", 0, "主世界秘银矿生成频率", 0 ,7);
        public static ConfigItem <Integer> lapisFrequencyOverworld = new ConfigItem("lapisFrequencyOverworld", 5, "主世界青金石矿生成频率", 0 ,7);
        public static ConfigItem <Integer> diamondFrequencyOverworld = new ConfigItem("diamondFrequencyOverworld_v0.0.5", 0, "主世界钻石矿生成频率", 0 ,7);
        public static ConfigItem <Integer> adamantiumFrequencyOverworld = new ConfigItem("adamantiumFrequencyOverworld", 0, "主世界艾德曼矿生成频率", 0 ,5);
        public static ConfigItem <Integer> redstoneFrequencyOverworld = new ConfigItem("redstoneFrequencyOverworld", 10, "主世界红石矿生成频率", 0 ,15);
        public static ConfigItem <Integer> coalFrequencyOverworld = new ConfigItem("coalFrequencyOverworld", 50, "主世界煤炭生成频率", 25 ,75);
        public static ConfigItem <Integer> copperFrequencyUnderworld = new ConfigItem("copperFrequencyUnderworld_v0.0.5", 0, "地下世界铜矿生成频率", 0 ,120);
        public static ConfigItem <Integer> silverFrequencyUnderworld = new ConfigItem("silverFrequencyUnderworld_v0.0.5", 0, "地下世界银矿生成频率", 0 ,120);
        public static ConfigItem <Integer> ironFrequencyUnderworld = new ConfigItem("ironFrequencyUnderworld_v0.0.5", 0, "地下世界铁矿生成频率", 0 ,120);
        public static ConfigItem <Integer> goldFrequencyUnderworld = new ConfigItem("goldFrequencyUnderworld_v0.0.5", 0, "地下世界金矿生成频率", 0 ,60);
        public static ConfigItem <Integer> mithrilFrequencyUnderworld = new ConfigItem("mithrilFrequencyUnderworld_v0.0.5", 10, "地下世界秘银矿生成频率", 0 ,20);
        public static ConfigItem <Integer> lapisFrequencyUnderworld = new ConfigItem("lapisFrequencyUnderworld_v0.0.5", 0, "地下世界青金石矿生成频率", 0 ,10);
        public static ConfigItem <Integer> diamondFrequencyUnderworld = new ConfigItem("diamondFrequencyUnderworld_v0.0.5", 5, "地下世界钻石矿生成频率", 0 ,10);
        public static ConfigItem <Integer> fancyRedFrequencyUnderworld = new ConfigItem("fancyRedFrequencyUnderworld", 5, "地下世界红钻矿生成频率", 0 ,10);
        public static ConfigItem <Integer> adamantiumFrequencyUnderworld = new ConfigItem("adamantiumFrequencyUnderworld_v0.0.9", 5, "地下世界艾德曼矿生成频率", 0 ,20);
        public static ConfigItem <Integer> redstoneFrequencyUnderworld = new ConfigItem("redstoneFrequencyUnderworld", 5, "地下世界红石矿生成频率", 0 ,20);
        public static ConfigItem <Integer> underworldMantleBlockOffset = new ConfigItem("underworldMantleBlockOffset_v0.0.2", 0, "地下世界地幔位置向上偏移", 0 ,70);
        public static ConfigItem <Integer> netherAdamantiumMaxCountPerChunk = new ConfigItem("netherAdamantiumMaxCountPerChunk", 2, "地狱艾德曼每个区块最大生成数量", 0 ,8);
        public static ConfigItem <Integer> netherAdamantiumMaxCountPerVein = new ConfigItem("netherAdamantiumMaxCountPerVein", 1, "地狱艾德曼每个矿脉最大矿物生成数量", 0 ,10);
        public static ConfigItem <Boolean> logPlayersInteractWithPortal = new ConfigItem("logPlayersInteractWithPortal", true, "玩家通过传送门时发送警告(开关)");
        public static ConfigItem <Integer> underworldGateOpenDay = new ConfigItem("underworldGateOpenDay", 32, "地下世界传送门多少天可以打开(主世界天数)", 16 ,64);
        public static ConfigItem <Integer> netherGateOpenDay = new ConfigItem("netherGateOpenDay", 48, "地狱传送门多少天可以打开(主世界天数)", 24 ,96);
        public static ConfigItem <Boolean> isAfterDeathKeep = new ConfigItem("isAfterDeathKeep", false, "是否死亡不掉落");
        public static ConfigItem <Boolean> isCloseShop = new ConfigItem("isCloseShop", false, "关闭商店系统");
        public static ConfigItem <Float> plantGrowthRate = new ConfigItem("plantGrowthRate_v0.0.9", 0.5F, "作物生长速度倍率",0f,1f);
        public static ConfigItem <Integer> whichDayGenVillage = new ConfigItem("whichDayGenVillage", 24, "多少天生成村庄", 1 ,96);
        public static ConfigItem <Integer> maxLevelLimit = new ConfigItem("maxLevelLimit", 300, "玩家最大限制等级", 0 ,1000);
        public static ConfigItem <Integer> enhancePerLvlCostExp = new ConfigItem("enhancePerLvlCostExp", 100, "附魔书附魔每增加一级消耗多少经验", 100 ,400);
        public static ConfigItem <Float> zombieBossSpawnPercent = new ConfigItem("zombieBossSpawnPercent", 1.0f, "挖掘刷怪笼多大几率刷出僵尸BOSS", 0f,1f);
        public static ConfigItem <Integer> creeperFuseTime = new ConfigItem("creeperFuseTime", 30, "苦力怕蓄力时间(tick)", 20,100);
        public static ConfigItem <Integer> FlyingSkeletonSpawningWeight = new ConfigItem("FlyingSkeletonSpawningWeight",5,"飞行骷髅生成权重",0,100);
        public static ConfigItem <Boolean> enhanceMobsArmor = new ConfigItem("EnhanceMobsArmor",true,"怪物穿着护甲强化");

//        public static ConfigItem <String> md5String = new ConfigItem("md5String", new lh("wensc").a("busy"), "MD5");

    }

    public static void loadConfigs(){

        wenscMap.put("EnhanceLimit", wenscConfig.EnhanceLimit);
        wenscMap.put("BlnGravel", wenscConfig.BlnGravel);
        wenscMap.put("isOpenStrongBoxBreakRecord", wenscConfig.isOpenStrongBoxBreakRecord);
        wenscMap.put("isRecipeRingKiller", wenscConfig.isRecipeRingKiller);
        wenscMap.put("isRecipeGATorch", wenscConfig.isRecipeGATorch);
//        wenscMap.put("inWallDamageForPlayer", wenscConfig.inWallDamageForPlayer);
        wenscMap.put("steppedMobDamageFactor", wenscConfig.steppedMobDamageFactor);
        wenscMap.put("steppedMobDamageProgressMax", wenscConfig.steppedMobDamageProgressMax);
        wenscMap.put("steppedMobDamageProgressIncreaseDay_v0.1.0", wenscConfig.steppedMobDamageProgressIncreaseDay);
//        wenscMap.put("steppedPlayerDamageProgress", wenscConfig.steppedPlayerDamageProgress);
//        wenscMap.put("steppedPlayerDamageIncreasePerLvl", wenscConfig.steppedPlayerDamageIncreasePerLvl);
        wenscMap.put("fishingXp", wenscConfig.fishingXp);
        wenscMap.put("inRainDebuffTime", wenscConfig.inRainDebuffTime);
//        wenscMap.put("firstDayLongerDayTime", wenscConfig.firstDayLongerDayTime);
        wenscMap.put("timeSpeedInDay",wenscConfig.timeSpeedInDay);
        wenscMap.put("timeSpeedInNight",wenscConfig.timeSpeedInNight);
        wenscMap.put("playerDefenseCooldown", wenscConfig.playerDefenseCooldown);
        wenscMap.put("playerDefenceMaxTime", wenscConfig.playerDefenceMaxTime);
        wenscMap.put("playerDisarmPlayer", wenscConfig.playerDisarmPlayer);
        wenscMap.put("annihilationSkeletonSpawnInLight", wenscConfig.annihilationSkeletonSpawnInLight);
        wenscMap.put("ancientBoneLordSpawnLimitDay", wenscConfig.ancientBoneLordSpawnLimitDay);
        wenscMap.put("wanderingWitchSpawnChanceOverworld", wenscConfig.wanderingWitchSpawnChanceOverworld);
        wenscMap.put("wanderingWitchSpawnLimitDayOverworld", wenscConfig.wanderingWitchSpawnLimitDayOverworld);
        wenscMap.put("wanderingWitchSpawnLimitDayOther", wenscConfig.wanderingWitchSpawnLimitDayOther);
        wenscMap.put("wanderingWitchSpawnChanceUnderworld", wenscConfig.wanderingWitchSpawnChanceUnderworld);
        wenscMap.put("underworldRandomTeleport", wenscConfig.underworldRandomTeleport);
        wenscMap.put("underworldRandomTeleportTimeNew", wenscConfig.underworldRandomTeleportTimeNew);
        wenscMap.put("underworldDebuff", wenscConfig.underworldDebuff);
        wenscMap.put("underworldDebuffPeriod1", wenscConfig.underworldDebuffPeriod1);
        wenscMap.put("underworldDebuffPeriod2", wenscConfig.underworldDebuffPeriod2);
        wenscMap.put("netherrackDamage", wenscConfig.netherrackDamage);
        wenscMap.put("netherrackDamageLimitDay", wenscConfig.netherrackDamageLimitDay);
        wenscMap.put("netherDebuff", wenscConfig.netherDebuff);
        wenscMap.put("netherDebuffTime", wenscConfig.netherDebuffTime);
        wenscMap.put("quartzMaxExpLevel", wenscConfig.quartzMaxExpLevel);
        wenscMap.put("fancyRedExp", wenscConfig.fancyRedExp);
        wenscMap.put("diamondExp", wenscConfig.diamondExp);
        wenscMap.put("emeraldExp", wenscConfig.emeraldExp);
        wenscMap.put("quartzExp", wenscConfig.quartzExp);
        wenscMap.put("goldenAppleEatTime", wenscConfig.goldenAppleEatTime);
        wenscMap.put("enchantedGoldenAppleEatTime", wenscConfig.enchantedGoldenAppleEatTime);
        wenscMap.put("chikitanEatTime",wenscConfig.chikitanEatTime);
        wenscMap.put("allProtectionVDefenceFraction", wenscConfig.allProtectionVDefenceFraction);
        wenscMap.put("mobSpawnerSpeedUpWithPlayers", wenscConfig.mobSpawnerSpeedUpWithPlayers);
        wenscMap.put("isSpawnExchanger", wenscConfig.isSpawnDragger);
        wenscMap.put("boneLordTweak", wenscConfig.boneLordTweak);
        wenscMap.put("skeletonTripleShot", wenscConfig.skeletonTripleShot);
        wenscMap.put("compressedSkeletonSpawningWeight", wenscConfig.compressedSkeletonSpawningWeight);
        wenscMap.put("compressedSkeletonCrackStandTime", wenscConfig.compressedSkeletonCrackStandTime);
        wenscMap.put("compressedSkeletonExpandCount", wenscConfig.compressedSkeletonExpandCount);
        wenscMap.put("skeletonForceMeleeAttack", wenscConfig.skeletonForceMeleeAttack);
        wenscMap.put("boneLordAndLongdeadChangeWeaponChance", wenscConfig.boneLordAndLongdeadChangeWeaponChance);
        wenscMap.put("batPoisonAttack", wenscConfig.batPoisonAttack);
        wenscMap.put("infernalCreeperBoost", wenscConfig.infernalCreeperBoost);
        wenscMap.put("wolvesAttackPlayersWithMeat", wenscConfig.wolvesAttackPlayersWithMeat);
        wenscMap.put("villagerWoolToEmeraldShardCount", wenscConfig.villagerWoolToEmeraldShardCount);
        wenscMap.put("villagerWoolToEmeraldShardShardCount", wenscConfig.villagerWoolToEmeraldShardShardCount);
        wenscMap.put("canBoostIronGolem", wenscConfig.canBoostIronGolem);
        wenscMap.put("mobDefense", wenscConfig.mobDefense);
        wenscMap.put("mobDisarmWhenDefence", wenscConfig.mobDisarmWhenDefence);
        wenscMap.put("mobDisarmWhenAttack", wenscConfig.mobDisarmWhenAttack);
        wenscMap.put("mobAttackCauseFire", wenscConfig.mobAttackCauseFire);
        wenscMap.put("annihilationSkeletonDespawnTime", wenscConfig.annihilationSkeletonDespawnTime);
        wenscMap.put("enderDragonAttackWitherChance", wenscConfig.enderDragonAttackWitherChance);
        wenscMap.put("enderDragonImmuneToArrowHealthPercent", wenscConfig.enderDragonImmuneToArrowHealthPercent);
        wenscMap.put("enderDragonAttackSlowness", wenscConfig.enderDragonAttackSlowness);
        wenscMap.put("netherModAttackFireChance", wenscConfig.netherModAttackFireChance);
        wenscMap.put("slimeAttackDamageMultiplier", wenscConfig.slimeAttackDamageMultiplier);
        wenscMap.put("slimeAttackDamageBonus", wenscConfig.slimeAttackDamageBonus);
        wenscMap.put("giantZombieSpawnZombieCooldown", wenscConfig.giantZombieSpawnZombieCooldown);
        wenscMap.put("animalIllToDeathTime", wenscConfig.animalIllToDeathTime);
        wenscMap.put("breedXpChicken", wenscConfig.breedXpChicken);
        wenscMap.put("breedXpSheep", wenscConfig.breedXpSheep);
        wenscMap.put("breedXpPig", wenscConfig.breedXpPig);
        wenscMap.put("breedXpCow", wenscConfig.breedXpCow);
        wenscMap.put("emeraldFrequencyBigHills", wenscConfig.emeraldFrequencyBigHills);

        wenscMap.put("copperFrequencyOverworld_v0.0.5", wenscConfig.copperFrequencyOverworld);
        wenscMap.put("silverFrequencyOverworld_v0.0.5", wenscConfig.silverFrequencyOverworld);
        wenscMap.put("ironFrequencyOverworld_v0.0.5", wenscConfig.ironFrequencyOverworld);
        wenscMap.put("goldFrequencyOverworld_v0.0.5.3", wenscConfig.goldFrequencyOverworld);
        wenscMap.put("lapisFrequencyOverworld", wenscConfig.lapisFrequencyOverworld);
        wenscMap.put("redstoneFrequencyOverworld", wenscConfig.redstoneFrequencyOverworld);
        wenscMap.put("coalFrequencyOverworld", wenscConfig.coalFrequencyOverworld);

        wenscMap.put("adamantiumFrequencyOverworld", wenscConfig.adamantiumFrequencyOverworld);
        wenscMap.put("overworldAdamantiteOre", wenscConfig.overworldAdamantiteOre);
        wenscMap.put("mithrilFrequencyOverworld_v0.0.5", wenscConfig.mithrilFrequencyOverworld);
        wenscMap.put("diamondFrequencyOverworld_v0.0.5", wenscConfig.diamondFrequencyOverworld);
        wenscMap.put("copperFrequencyUnderworld_v0.0.5", wenscConfig.copperFrequencyUnderworld);
        wenscMap.put("silverFrequencyUnderworld_v0.0.5", wenscConfig.silverFrequencyUnderworld);
        wenscMap.put("ironFrequencyUnderworld_v0.0.5", wenscConfig.ironFrequencyUnderworld);
        wenscMap.put("goldFrequencyUnderworld_v0.0.5", wenscConfig.goldFrequencyUnderworld);
        wenscMap.put("lapisFrequencyUnderworld_v0.0.5", wenscConfig.lapisFrequencyUnderworld);
        wenscMap.put("redstoneFrequencyUnderworld", wenscConfig.redstoneFrequencyUnderworld);

        wenscMap.put("mithrilFrequencyUnderworld_v0.0.5", wenscConfig.mithrilFrequencyUnderworld);
        wenscMap.put("diamondFrequencyUnderworld_v0.0.5", wenscConfig.diamondFrequencyUnderworld);
        wenscMap.put("fancyRedFrequencyUnderworld", wenscConfig.fancyRedFrequencyUnderworld);
        wenscMap.put("adamantiumFrequencyUnderworld_v0.0.9", wenscConfig.adamantiumFrequencyUnderworld);
        wenscMap.put("underworldMantleBlockOffset_v0.0.2", wenscConfig.underworldMantleBlockOffset);
        wenscMap.put("netherAdamantiumMaxCountPerChunk", wenscConfig.netherAdamantiumMaxCountPerChunk);
        wenscMap.put("netherAdamantiumMaxCountPerVein", wenscConfig.netherAdamantiumMaxCountPerVein);
        wenscMap.put("logPlayersInteractWithPortal", wenscConfig.logPlayersInteractWithPortal);
        wenscMap.put("underworldGateOpenDay", wenscConfig.underworldGateOpenDay);
        wenscMap.put("netherGateOpenDay", wenscConfig.netherGateOpenDay);
        wenscMap.put("isAfterDeathKeep", wenscConfig.isAfterDeathKeep);
        wenscMap.put("isCloseShop", wenscConfig.isCloseShop);
        wenscMap.put("plantGrowthRate_v0.0.9", wenscConfig.plantGrowthRate);
        wenscMap.put("whichDayGenVillage", wenscConfig.whichDayGenVillage);
        wenscMap.put("maxLevelLimit", wenscConfig.maxLevelLimit);
        wenscMap.put("enhancePerLvlCostExp", wenscConfig.enhancePerLvlCostExp);
        wenscMap.put("zombieBossSpawnPercent", wenscConfig.zombieBossSpawnPercent);
        wenscMap.put("creeperFuseTime", wenscConfig.creeperFuseTime);
        wenscMap.put("FlyingSkeletonSpawningWeight",wenscConfig.FlyingSkeletonSpawningWeight);
        wenscMap.put("EnhanceMobsArmor",wenscConfig.enhanceMobsArmor);
        wenscMap.put("boostEnhance",wenscConfig.boostEnhance);

//        wenscMap.put("md5", wenscConfig.md5String);

        String filePth = "mite-extreme.cfg";
        File file_mite = new File(filePth);
        if (file_mite.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(file_mite.getName());
                properties.load(fr);
                fr.close();
                readConfigFromFile(file_mite, properties);
                packConfigFile(file_mite, properties);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        } else {
            try {
                if (file_mite.createNewFile()){
                    file_mite.setExecutable(true);//设置可执行权限
                    file_mite.setReadable(true);//设置可读权限
                    file_mite.setWritable(true);//设置可写权限
                    generateConfigFile(file_mite);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JFrame jFrame = new JFrame();
                jFrame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jFrame, "mite-extreme.cfg配置文件失败，请前往www.wensc.cn自行下载", "错误", 0);
                System.exit(0);
            }
        }
    }
    public static void beginToLoadShopConfig() {
        String shopConfigFilePath = "mite-extreme-shop.cfg";
        File shopConfigFile = new File(shopConfigFilePath);
        if (shopConfigFile.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(shopConfigFile.getName());
                properties.load(fr);
                fr.close();
                readShopConfigFromFile(shopConfigFile, properties);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        } else {
            try {
                if (shopConfigFile.createNewFile()){
                    shopConfigFile.setExecutable(true); // 设置可执行权限
                    shopConfigFile.setReadable(true); // 设置可读权限
                    shopConfigFile.setWritable(true); // 设置可写权限
                    generateShopConfigFile(shopConfigFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JFrame jFrame = new JFrame();
                jFrame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jFrame, "mite-extreme-shop.cfg配置文件失败，请前往www.wensc.cn自行下载", "错误", 0);
                System.exit(0);
            }
        }
    }

    public static void writePriceIntoMemory(Properties properties,FileWriter fileWriter, Item item, ItemStack itemStack) throws IOException {
        int sub = itemStack.getItemSubtype();
        String name = "";
        if(item.getHasSubtypes()) {
            name = itemStack.getUnlocalizedName() + "$" + itemStack.itemID + "$" + sub;
        } else {
            name = itemStack.getUnlocalizedName() + "$" + itemStack.itemID;
        }
        String itemPrice =  (String) properties.get(name);
        if(itemPrice != null) {
            String [] soldPriceAndBuyPrice = itemPrice.split(",");
            if(soldPriceAndBuyPrice.length == 2) {
                double soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
                double buyPrice = Double.parseDouble(soldPriceAndBuyPrice[1]);
                itemStack.setPrice(soldPrice, buyPrice);
                if(soldPrice > 0d || buyPrice > 0d) {
                    Items.priceStackList.add(itemStack);
                }
                item.soldPriceArray.put(sub, soldPrice);
                item.buyPriceArray.put(sub, buyPrice);
            } else {
                double soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
                itemStack.setPrice(soldPrice, 0d);
                if(soldPrice > 0d) {
                    Items.priceStackList.add(itemStack);
                }
                item.soldPriceArray.put(sub, soldPrice);
            }
        } else {
            writeShopConfigFormatter(fileWriter, item, itemStack);
        }
    }

    public static void readOrWritePriceLine(Properties properties, FileWriter fileWriter, Item item) throws IOException {
        List subs = item.getSubItems();
        for (int i = 0; i < subs.size(); i++) {
            ItemStack itemStack = (ItemStack) subs.get(i);
            writePriceIntoMemory(properties,fileWriter, item, itemStack);
        }
    }

    public static void  readShopConfigFromFile(File file_mite, Properties properties) {
        Items.priceStackList = new ArrayList<>();
        try{
            FileWriter fileWriter = new FileWriter(file_mite.getName(), true);
            for (Item item : Item.itemsList) {
                if(item != null) {
                    if(item instanceof ItemBlock) {
                        if(!((ItemBlock) item).getBlock().canBeCarried()) {
                            continue;
                        }
                    }
                    if(item instanceof ItemWorldMap) {
                        continue;
                    }
                    try {
                        readOrWritePriceLine(properties, fileWriter, item);
                    } catch (Exception e) {
                        Minecraft.setErrorMessage("配置项：" + item.getItemDisplayName() + " ID: " + item.itemID + " 错误！！！");
                        throw(e);
                    }
                }
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Collections.sort(Items.priceStackList, (o1, o2) -> {
                double offset;
                if(o2.getPrice().buyPrice > 0d && o1.getPrice().buyPrice > 0d) {
                    offset = o1.getPrice().buyPrice - o2.getPrice().buyPrice;
                } else {
                    offset = o2.getPrice().buyPrice - o1.getPrice().buyPrice;
                }
                return  offset > 0 ? 1 : offset == 0 ? 0 : -1;
            });
        }
    }

    public static void  readConfigFromFile(File file_mite, Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            ConfigItem configItem = wenscMap.get(key);
            if(configItem != null) {
                if(configItem.ConfigValue instanceof Boolean) {
                    configItem.setConfigValue(Boolean.parseBoolean(properties.getProperty(key)));
                } else if(configItem.ConfigValue instanceof Float) {
                    float value = Float.parseFloat(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (float)configItem.max ? (float) configItem.max : Math.max(value, (float) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else if(configItem.ConfigValue instanceof Double) {
                    double value = Double.parseDouble(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (double)configItem.max ? (double) configItem.max : Math.max(value, (double) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else if(configItem.ConfigValue instanceof Integer) {
                    int value = Integer.parseInt(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (int)configItem.max ? (int) configItem.max : Math.max(value, (int) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else {
                    configItem.setConfigValue(properties.getProperty(key));
                }
            }
        }
    }

    public static void packConfigFile(File file,Properties properties) {
        try{
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            for (Map.Entry<String, ConfigItem> entry: wenscMap.entrySet()) {
                ConfigItem value = entry.getValue();
                String localValue = properties.getProperty(value.ConfigKey);
                if(localValue == null) {
                    fileWriter.write("// " + value.ConfigComment + "\n");
                    fileWriter.write(value.ConfigKey + "=" + value.ConfigValue + "\n\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateConfigFile(File file) {
        try{
            FileWriter fileWriter = new FileWriter(file.getName());
            fileWriter.write("// MITE-Extreme配置文件，说明：【布尔类型：true为开启，false关闭】，在【名称=值】之间/之后不要存在空格或者其他无关字符，【tick：20tick=1秒】\n");
            for (Map.Entry<String, ConfigItem> entry: wenscMap.entrySet()) {
                ConfigItem value = entry.getValue();
                fileWriter.write("// " + value.ConfigComment + "\n");
                fileWriter.write(value.ConfigKey + "=" + value.ConfigValue + "\n\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeShopConfigFormatter(FileWriter fileWriter, Item item, ItemStack itemStack) throws IOException {
        int sub = itemStack.getItemSubtype();
        double soldPrice = (double)item.soldPriceArray.get(sub);
        double buyPrice = (double)item.buyPriceArray.get(sub);
        itemStack.setPrice(soldPrice, buyPrice);
        if(soldPrice > 0d || buyPrice > 0d) {
            Items.priceStackList.add(itemStack);
        }
        if(item.getHasSubtypes()) {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + itemStack.itemID + " meta:"+ sub + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID +"$" + sub + "=" + item.soldPriceArray.get(sub) +","+ item.buyPriceArray.get(sub)+ "\n\n");
        } else {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + item.itemID + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID + "=" + item.soldPriceArray.get(0) +","+ item.buyPriceArray.get(0) + "\n\n");
        }
    }

    public static void generateShopConfigFile(File file) {
        try{
            FileWriter fileWriter = new FileWriter(file.getName());
            fileWriter.write("// MITE-Extreme商店配置文件，说明：参数之间使用英文逗号分隔，请严格遵循格式（商品英文名=售出价格,购买价格），价格小于等于0代表不可出售或者不可购买，价格可以为小数，乱改造成无法启动概不负责\n");
            for (Item item : Item.itemsList) {
                if(item != null) {
                    if(item instanceof ItemBlock) {
                        if(!((ItemBlock) item).getBlock().canBeCarried()) {
                            continue;
                        }
                    }
                    if(item instanceof ItemWorldMap) {
                        continue;
                    }
                    if(item.getHasSubtypes()) {
                        List subs = item.getSubItems();
                        for (int i = 0; i < subs.size(); i++) {
                            ItemStack itemStack = (ItemStack)subs.get(i);
                            writeShopConfigFormatter(fileWriter, item, itemStack);
                        }
                    } else {
                        ItemStack itemStack = new ItemStack(item, 1, 0);
                        writeShopConfigFormatter(fileWriter, item, itemStack);
                    }
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Collections.sort(Items.priceStackList, (o1, o2) -> {
                double offset;
                if(o2.getPrice().buyPrice > 0d && o1.getPrice().buyPrice > 0d) {
                    offset = o1.getPrice().buyPrice - o2.getPrice().buyPrice;
                } else {
                    offset = o2.getPrice().buyPrice - o1.getPrice().buyPrice;
                }
                return  offset > 0 ? 1 : offset == 0 ? 0 : -1;
            });
        }
    }
}
