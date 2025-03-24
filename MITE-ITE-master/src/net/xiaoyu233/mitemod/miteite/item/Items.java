package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.ItemArmor.getMatchingArmor;
import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.createInstance;

public class Items extends Item{
    public static int shopSize = 0;
    public static List<ItemStack> priceStackList = new ArrayList();
    public static final Item BLAZE_COAL_POWDER = new ItemBlazeCoalPowder(Constant.getNextItemID());
    public static final Item DIAMOND_CHUNK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},Constant.getNextItemID(),Material.diamond,"diamond_chunk").setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) /(float)4);
    public static final Item OBSIDIAN_STICK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},Constant.getNextItemID(),Material.obsidian,"obsidian_stick");
    public static final ItemAxe VIBRANIUM_AXE = createInstance(ItemAxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemBattleAxe VIBRANIUM_BATTLE_AXE = createInstance(ItemBattleAxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_BOOTS = new ItemBoots(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemBow VIBRANIUM_BOW = new ItemBow(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_CHESTPLATE = new ItemCuirass(Constant.getNextItemID(), Materials.vibranium, false);
    public static final ItemDagger VIBRANIUM_DAGGER = createInstance(ItemDagger.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_HELMET = new ItemHelmet(Constant.getNextItemID(),Materials.vibranium,false);
    public static final Item VIBRANIUM_INGOT = createInstance(ItemIngot.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_LEGGINGS = new ItemLeggings(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemNugget VIBRANIUM_NUGGET = createInstance(ItemNugget.class, new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemPickaxe VIBRANIUM_PICKAXE = createInstance(ItemPickaxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemShovel VIBRANIUM_SHOVEL = createInstance(ItemShovel.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemSword VIBRANIUM_SWORD = createInstance(ItemSword.class,new Class[]{int.class,Material.class},Constant.getNextItemID(), Materials.vibranium);
    public static final ItemWarHammer VIBRANIUM_WAR_HAMMER = createInstance(ItemWarHammer.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemEnhanceStone IRON_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.iron).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.iron) * 2f);
    public static final ItemEnhanceStone MITHRIL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.mithril).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.mithril) * 2f);
    public static final ItemEnhanceStone ADAMANTIUM_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.adamantium).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.adamantium) * 2f);
    public static final ItemEnhanceStone UNIVERSAL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.universal).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.ender_pearl) * 2f);

    public static final Item lavaInPipes = new ItemLavaInPipes(Constant.getNextItemID(), Materials.lava);

    public static final Item clubIron = new ItemClubMetal(Constant.getNextItemID(), Material.iron);
    public static final Item clubMithril = new ItemClubMetal(Constant.getNextItemID(), Material.mithril);
    public static final Item clubAdamantium = new ItemClubMetal(Constant.getNextItemID(), Material.adamantium);
    public static final Item clubVibranium = new ItemClubMetal(Constant.getNextItemID(), Materials.vibranium);

    public static final Item ringKillerCopper = new ItemRingKiller(Constant.getNextItemID(), Materials.copper).setUnlocalizedName("ringKillerCopper");
    public static final Item ringKillerIron = new ItemRingKiller(Constant.getNextItemID(), Materials.iron).setUnlocalizedName("ringKillerIron");
    public static final Item ringKillerAncient = new ItemRingKiller(Constant.getNextItemID(), Materials.ancient_metal).setUnlocalizedName("ringKillerAncient");
    public static final Item ringKillerMithril = new ItemRingKiller(Constant.getNextItemID(), Materials.mithril).setUnlocalizedName("ringKillerMithril");
    public static final Item ringKillerAdamantium = new ItemRingKiller(Constant.getNextItemID(), Materials.adamantium).setUnlocalizedName("ringKillerAdamantium");
    public static final Item ringKillerVibranium = new ItemRingKiller(Constant.getNextItemID(), Materials.vibranium).setUnlocalizedName("ringKillerVibranium");
    public static final Item Stack_Torch = createInstance(Item.class, new Class[]{int.class, Material.class, String.class}, Constant.getNextItemID(), Material.circuits, "stack_torch");

    public static final Item voucherPhase = new ItemMobVoucher(Constant.getNextItemID(), "phase");
    public static final Item voucherDestruction = new ItemMobVoucher(Constant.getNextItemID(), "destruction");
    public static final Item voucherOverlord = new ItemMobVoucher(Constant.getNextItemID(), "overlord");
    public static final Item voucherStrike = new ItemMobVoucher(Constant.getNextItemID(), "strike");
    public static final Item voucherCluster = new ItemMobVoucher(Constant.getNextItemID(), "cluster");
    public static final Item voucherMagic = new ItemMobVoucher(Constant.getNextItemID(), "magic");
    public static final Item voucherCore = new ItemMobVoucher(Constant.getNextItemID(), "core");
    public static final Item voucherFishing = new ItemMobVoucher(Constant.getNextItemID(), "fishing");
    public static final Item voucherPlanting = new ItemMobVoucher(Constant.getNextItemID(), "planting");
    public static final Item voucherVillager = new ItemMobVoucher(Constant.getNextItemID(), "villager");
    public static final Item voucherClubCore = new ItemMobVoucher(Constant.getNextItemID(), "club_core");

    public static final Item itemDynamicCoreIron = new ItemDynamicCore(Constant.getNextItemID(), Materials.iron, 1).setUnlocalizedName("dynamic_core_iron");
    public static final Item itemDynamicCoreAncient_metal = new ItemDynamicCore(Constant.getNextItemID(), Materials.ancient_metal, 2).setUnlocalizedName("dynamic_core_ancient_metal");
    public static final Item itemDynamicCoreMithril = new ItemDynamicCore(Constant.getNextItemID(), Materials.mithril, 3).setUnlocalizedName("dynamic_core_mithril");

    public static final Item itemDynamicCoreAdamantium = new ItemDynamicCore(Constant.getNextItemID(), Materials.adamantium, 4).setUnlocalizedName("dynamic_core_adamantium");
    public static final Item itemDynamicCoreVibranium = new ItemDynamicCore(Constant.getNextItemID(), Materials.vibranium, 5).setUnlocalizedName("dynamic_core_vibranium");
    public static final Item fancyRed = (new ItemFancyRed(Constant.getNextItemID(), Material.diamond, "fancy_red"));

    public static ItemEnhanceGem itemEnhanceGem = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 1)).setUnlocalizedName("enhance_gem_phase1");
    public static ItemEnhanceGem itemEnhanceGem2 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 2)).setUnlocalizedName("enhance_gem_phase2");
    public static ItemEnhanceGem itemEnhanceGem3 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 3)).setUnlocalizedName("enhance_gem_phase3");
    public static ItemEnhanceGem itemEnhanceGem4 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 4)).setUnlocalizedName("enhance_gem_phase4");
    public static ItemEnhanceGem itemEnhanceGem5 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 5)).setUnlocalizedName("enhance_gem_phase5");
    public static ItemEnhanceGem itemEnhanceGem6 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 6)).setUnlocalizedName("enhance_gem_phase6");

    public static ItemEnhanceGemBox itemEnhanceGemBox = (ItemEnhanceGemBox)(new ItemEnhanceGemBox(Constant.getNextItemID())).setUnlocalizedName("enhance_gem_box_phase1");
    public static ItemGemShard itemGemShard = (ItemGemShard)(new ItemGemShard(Constant.getNextItemID())).setUnlocalizedName("gem_shard");
    public static final Item Bunch_Torch = createInstance(Item.class, new Class[]{int.class, Material.class, String.class}, Constant.getNextItemID(), Material.circuits, "bunch_torch");
    public static final Item voucherSpider = new ItemMobVoucher(Constant.getNextItemID(), "spider");
    public static final Item itemRegenerationCoreIron = new ItemRegenerationCore(Constant.getNextItemID(), Materials.iron, 1).setUnlocalizedName("regeneration_core_iron");
    public static final Item itemRegenerationCoreAncient_metal = new ItemRegenerationCore(Constant.getNextItemID(), Materials.ancient_metal, 2).setUnlocalizedName("regeneration_core_ancient_metal");
    public static final Item itemRegenerationCoreMithril = new ItemRegenerationCore(Constant.getNextItemID(), Materials.mithril, 3).setUnlocalizedName("regeneration_core_mithril");

    public static final Item itemRegenerationCoreAdamantium = new ItemRegenerationCore(Constant.getNextItemID(), Materials.adamantium, 4).setUnlocalizedName("regeneration_core_adamantium");
    public static final Item itemRegenerationCoreVibranium = new ItemRegenerationCore(Constant.getNextItemID(), Materials.vibranium, 5).setUnlocalizedName("regeneration_core_vibranium");
    public static final Item voucherGuard = new ItemMobVoucher(Constant.getNextItemID(), "guard");
    public static final Item itemGuardCoreIron = new ItemGuardCore(Constant.getNextItemID(), Materials.iron, 1).setUnlocalizedName("guard_core_iron");
    public static final Item itemGuardCoreAncient_metal = new ItemGuardCore(Constant.getNextItemID(), Materials.ancient_metal, 2).setUnlocalizedName("guard_core_ancient_metal");
    public static final Item itemGuardCoreMithril = new ItemGuardCore(Constant.getNextItemID(), Materials.mithril, 3).setUnlocalizedName("guard_core_mithril");

    public static final Item itemGuardCoreAdamantium = new ItemGuardCore(Constant.getNextItemID(), Materials.adamantium, 4).setUnlocalizedName("guard_core_adamantium");
    public static final Item itemGuardCoreVibranium = new ItemGuardCore(Constant.getNextItemID(), Materials.vibranium, 5).setUnlocalizedName("guard_core_vibranium");
    public static final ItemFood buguDan = (new ItemGAFood(Constant.getNextItemID(), Materials.vegetable, 4, 4, 250, false, false, true, "bugu")).setPlantProduct().setAlwaysEdible();
    public static final ItemFood demonPillRaw = (new ItemGAFood(Constant.getNextItemID(), Materials.cake, 2,2, -1000, false,false,false,"demonPill")).setAlwaysEdible();
    public static final ItemFood demonPillCooked = (ItemFood) (new ItemGAFood(Constant.getNextItemID(), Materials.cake, 4,4, -2000, false,false,false,"demonPillCooked")).setAlwaysEdible().setXPReward(2);
    public static final ItemFood cubeSugar = (new ItemGAFood(Constant.getNextItemID(),Materials.sugar,3,3, 1000,false,false,false,"cubeSugar")).setAlwaysEdible().setPotionEffect(MobEffectList.regeneration.id, 8, 0, 1.0F);
    public static final Item powder_dark = new ItemElementPowder(Constant.getNextItemID(),"dark");
    public static final Item powder_freeze = new ItemElementPowder(Constant.getNextItemID(),"freeze");
    public static final Item powder_wind = new ItemElementPowder(Constant.getNextItemID(),"wind");
    public static final Item powder_blaze = new ItemElementPowder(Constant.getNextItemID(),"blaze");
    public static final Item powder_metal = new ItemElementPowder(Constant.getNextItemID(),"metal");
    public static final Item powder_thunder = new ItemElementPowder(Constant.getNextItemID(),"thunder");
    public static final Item powder_wood = new ItemElementPowder(Constant.getNextItemID(),"wood");
    public static final Item powder_liquid = new ItemElementPowder(Constant.getNextItemID(),"liquid");
    public static final Item powder_earth = new ItemElementPowder(Constant.getNextItemID(),"earth");
    public static final ItemConsumables colorBag = (ItemConsumables) new ItemConsumables(Constant.getNextItemID(), Material.dye, "color_bag").setCreativeTab(CreativeModeTab.tabTools);
    public static final Item pants = new ItemGAMisc(Constant.getNextItemID(),"pants");
    public static final ItemFood chikitan = new ItemGAFood(Constant.getNextItemID(),Materials.vegetable,20,0,0,false,false,false,"chikitan").setAlwaysEdible().setPlantProduct().setPotionEffect(MobEffectList.resistance.id, 10, 3, 0.33F);
    public static final ItemFood spicyStrip = new ItemGAFood(Constant.getNextItemID(),Materials.dough,2,2,0,false,false,false,"spicy_strip").setPotionEffect(MobEffectList.hunger.id, 16, 1, 1.0F);
    public static final ItemFood zombieBrain = new ItemGAFood(Constant.getNextItemID(),Materials.meat,1,2,0,true,false,false,"zombie_brain").setPotionEffect(MobEffectList.poison.id, 22, 0, 0.9F).setAnimalProduct();
    public static final ItemFood riskyAgent = new ItemGAFood(Constant.getNextItemID(),Materials.meat,4,4,0,true,false,false,"risky_agent").setAlwaysEdible().setAnimalProduct();
    public static final ItemFood suspiciousStew = new ItemGAFood(Constant.getNextItemID(),Material.vegetable,4,4,0,false,false,true,"suspicious_stew").setAlwaysEdible().setPlantProduct();
    public static final ItemExplosion c4 = new ItemExplosion(Constant.getNextItemID(),Material.circuits,"c4");
    public static final ItemFood zombieBrainCooked = new ItemGAFood(Constant.getNextItemID(),Material.meat,2,4,0,true,true,false,"zombie_brain_cooked").setPotionEffect(MobEffectList.nightVision.id, 60, 0, 0.9F).setAnimalProduct().setAlwaysEdible();
    public static final ItemFood wuzhiDan = (new ItemGAFood(Constant.getNextItemID(), Materials.vegetable, 0, 20, 0, false, true, true, "wuzhi")).setPlantProduct().setAlwaysEdible();
    public static final ItemFood hugeSpiderLeg = (new ItemGAFood(Constant.getNextItemID(), Material.meat, 4,4,0,true,true,false,"spider_leg")).setAlwaysEdible().setAnimalProduct().setPotionEffect(MobEffectList.poison.id, 12, 0, 0.9F);
    public static final ItemFood coldSpiderLeg = (new ItemGAFood(Constant.getNextItemID(), Material.meat, 6,6,0,true,true,false,"cold_spider_leg")).setAlwaysEdible().setAnimalProduct().setPotionEffect(MobEffectList.moveSpeed.id, 240, 1, 1.0F);
    public static final ItemGAMisc badApple = new ItemGAMisc(Constant.getNextItemID(),"bad_apple");
    public static final ItemEnhanceStone VIBRANIUM_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.vibranium).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Materials.vibranium) * 2f);
    //淫欲
    public static final ItemGAMisc gowther = new ItemGAMisc(Constant.getNextItemID(),"gowther");
    //暴食
    public static final ItemGAMisc merlin = new ItemGAMisc(Constant.getNextItemID(),"merlin");
    //贪婪
    public static final ItemGAMisc ban = new ItemGAMisc(Constant.getNextItemID(),"ban");
    //懒惰
    public static final ItemGAMisc king = new ItemGAMisc(Constant.getNextItemID(),"king");
    //愤怒
    public static final ItemGAMisc meliodas = new ItemGAMisc(Constant.getNextItemID(),"meliodas");
    //嫉妒
    public static final ItemGAMisc diane = new ItemGAMisc(Constant.getNextItemID(),"diane");
    //傲慢
    public static final ItemGAMisc escanor = new ItemGAMisc(Constant.getNextItemID(),"escanor");
    public static final Item gemYellow = (ItemConsumables) new ItemConsumables(Constant.getNextItemID(), Material.gold, "gem_y").setCreativeTab(CreativeModeTab.tabTools);
    public static final Item gemBlue = (ItemConsumables) new ItemConsumables(Constant.getNextItemID(), Material.diamond, "gem_b").setCreativeTab(CreativeModeTab.tabTools);
    public static final ItemEnhancedPickaxe towards_Pickaxe = new ItemEnhancedPickaxe(Constant.getNextItemID(),Material.iron,1);
    public static final ItemEnhancedPickaxe stairs_Pickaxe = new ItemEnhancedPickaxe(Constant.getNextItemID(),Material.ancient_metal,2);
    public static final ItemEnhancedPickaxe star_Pickaxe = new ItemEnhancedPickaxe(Constant.getNextItemID(),Material.mithril,3);
    public static final ItemEnhancedPickaxe plate_Pickaxe = new ItemEnhancedPickaxe(Constant.getNextItemID(),Materials.adamantium,4);
    public static final ItemEnhancedPickaxe cube_Pickaxe = new ItemEnhancedPickaxe(Constant.getNextItemID(),Materials.vibranium,5);
    public static final ItemConsumables final_key = new ItemConsumables(Constant.getNextItemID(),Materials.vibranium,"final_key");
    public static final ItemGAMisc cracked_key = new ItemGAMisc(Constant.getNextItemID(),"cracked_key");
    public static final ItemConsumables endScroll = new ItemConsumables(Constant.getNextItemID(),Material.paper,"end_scroll");
    public static final ItemGrenade grenade = new ItemGrenade(Constant.getNextItemID(),Material.circuits);
    public static final Item clubAncientMetal = new ItemClubMetal(Constant.getNextItemID(), Material.ancient_metal);
    private static Item register(String resourceLocation, Item item, CreativeModeTab tab) {
        item.setResourceLocation(item.getResourceLocationPrefix() + resourceLocation);
        item.setUnlocalizedName(resourceLocation);
        item.setCreativeTab(tab);
        return item;
    }

    private static Item register(String resourceLocation, Item item) {
        item.setResourceLocation(item.getResourceLocationPrefix() + resourceLocation);
        item.setUnlocalizedName(resourceLocation);
        return item;
    }

    @Overwrite
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
//        par3List.add(new ItemStack(par1, 1, 0));
    }

    public static void registerItems() {
        register("obsidian_stick", OBSIDIAN_STICK, CreativeModeTab.tabMaterials);
        register("vibranium", VIBRANIUM_INGOT, CreativeModeTab.tabMaterials);
        register("vibranium_nugget", VIBRANIUM_NUGGET, CreativeModeTab.tabMaterials);
        register("vibranium_helmet", VIBRANIUM_HELMET);
        register("vibranium_chestplate", VIBRANIUM_CHESTPLATE);
        register("vibranium_leggings", VIBRANIUM_LEGGINGS);
        register("vibranium_boots", VIBRANIUM_BOOTS);
        register("vibranium_sword", VIBRANIUM_SWORD);
        register("vibranium_shovel", VIBRANIUM_SHOVEL);
        register("vibranium_pickaxe", VIBRANIUM_PICKAXE);
        register("vibranium_axe", VIBRANIUM_AXE);
        register("vibranium_dagger", VIBRANIUM_DAGGER);
        register("vibranium_war_hammer", VIBRANIUM_WAR_HAMMER);
        register("vibranium_battle_axe", VIBRANIUM_BATTLE_AXE);
        register("diamond_chunk", DIAMOND_CHUNK, CreativeModeTab.tabMaterials);
        register("blaze_coal_powder", BLAZE_COAL_POWDER, CreativeModeTab.tabMaterials);
        register("bows/vibranium/", VIBRANIUM_BOW).setUnlocalizedName("vibranium_bow");
        register("enhance_stone/iron",IRON_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/mithril",MITHRIL_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/adamantium",ADAMANTIUM_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/universal",UNIVERSAL_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/vibranium",VIBRANIUM_ENHANCE_STONE , CreativeModeTab.tabMaterials);

        register("clubs/iron", clubIron).setUnlocalizedName("iron_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("clubs/ancient_metal", clubAncientMetal).setUnlocalizedName("ancient_metal_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("clubs/mithril", clubMithril).setUnlocalizedName("mithril_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("clubs/adamantium", clubAdamantium).setUnlocalizedName("adamantium_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("clubs/vibranium", clubVibranium).setUnlocalizedName("vibranium_club").setLowestCraftingDifficultyToProduce(1.0F);

        register("stack_torch", Stack_Torch).setUnlocalizedName("stack_torch").setMaxStackSize(16).setCraftingDifficultyAsComponent(1.0E-9F);
        register("bunch_torch", Bunch_Torch).setUnlocalizedName("bunch_torch").setMaxStackSize(64).setCraftingDifficultyAsComponent(1.0E-9F);

        register("lava_in_pipes", lavaInPipes).setUnlocalizedName("lava_in_pipes").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_copper", ringKillerCopper).setUnlocalizedName("ring_killer_copper").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_iron", ringKillerIron).setUnlocalizedName("ring_killer_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_ancient", ringKillerAncient).setUnlocalizedName("ring_killer_ancient").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_mithril", ringKillerMithril).setUnlocalizedName("ring_killer_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_adamantium", ringKillerAdamantium).setUnlocalizedName("ring_killer_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_vibranium", ringKillerVibranium).setUnlocalizedName("ring_killer_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("voucher/voucher_exchanger", voucherPhase).setUnlocalizedName("voucher_exchanger").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_door", voucherDestruction).setUnlocalizedName("voucher_door").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_zombie_lord", voucherOverlord).setUnlocalizedName("voucher_zombie_lord").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_annihilation_skeleton", voucherStrike).setUnlocalizedName("voucher_annihilation_skeleton").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_pigman", voucherCluster).setUnlocalizedName("voucher_pigman").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_witch", voucherMagic).setUnlocalizedName("voucher_witch").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_core", voucherCore).setUnlocalizedName("voucher_core").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_fishing", voucherFishing).setUnlocalizedName("voucher_fishing").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_planting", voucherPlanting).setUnlocalizedName("voucher_planting").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_villager", voucherVillager).setUnlocalizedName("voucher_villager").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_club_core", voucherClubCore).setUnlocalizedName("voucher_club_core").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_spider", voucherSpider).setUnlocalizedName("voucher_spider").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_guard", voucherGuard).setUnlocalizedName("voucher_guard").setLowestCraftingDifficultyToProduce(1.0F);

        register("dynamic_core/1", itemDynamicCoreIron).setUnlocalizedName("dynamic_core_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/2", itemDynamicCoreAncient_metal).setUnlocalizedName("dynamic_core_ancient_metal").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/3", itemDynamicCoreMithril).setUnlocalizedName("dynamic_core_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/4", itemDynamicCoreAdamantium).setUnlocalizedName("dynamic_core_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/5", itemDynamicCoreVibranium).setUnlocalizedName("dynamic_core_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("regeneration_core/1", itemRegenerationCoreIron).setUnlocalizedName("regeneration_core_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("regeneration_core/2", itemRegenerationCoreAncient_metal).setUnlocalizedName("regeneration_core_ancient_metal").setLowestCraftingDifficultyToProduce(1.0F);
        register("regeneration_core/3", itemRegenerationCoreMithril).setUnlocalizedName("regeneration_core_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("regeneration_core/4", itemRegenerationCoreAdamantium).setUnlocalizedName("regeneration_core_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("regeneration_core/5", itemRegenerationCoreVibranium).setUnlocalizedName("regeneration_core_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("guard_core/1", itemGuardCoreIron).setUnlocalizedName("guard_core_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("guard_core/2", itemGuardCoreAncient_metal).setUnlocalizedName("guard_core_ancient_metal").setLowestCraftingDifficultyToProduce(1.0F);
        register("guard_core/3", itemGuardCoreMithril).setUnlocalizedName("guard_core_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("guard_core/4", itemGuardCoreAdamantium).setUnlocalizedName("guard_core_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("guard_core/5", itemGuardCoreVibranium).setUnlocalizedName("guard_core_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("fancy_red", fancyRed).setUnlocalizedName("fancy_red").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase1", itemEnhanceGem).setUnlocalizedName("enhance_gem_phase1").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase2", itemEnhanceGem2).setUnlocalizedName("enhance_gem_phase2").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase3", itemEnhanceGem3).setUnlocalizedName("enhance_gem_phase3").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase4", itemEnhanceGem4).setUnlocalizedName("enhance_gem_phase4").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase5", itemEnhanceGem5).setUnlocalizedName("enhance_gem_phase5").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase5", itemEnhanceGem6).setUnlocalizedName("enhance_gem_phase6").setLowestCraftingDifficultyToProduce(1.0F);

        register("drug_zj", chikitan);
        register("drug_bg", buguDan);
        register("drug_bg_b",wuzhiDan);
        register("drug_yd", demonPillRaw);
        register("drug_yd_b", demonPillCooked);
        register("cubesugar", cubeSugar);
        register("latiao",spicyStrip);
        register("zombie_brain",zombieBrain);
        register("zombie_drug",riskyAgent);
        register("suspicious_stew",suspiciousStew);
        register("zombie_brain_cooked",zombieBrainCooked);
        register("spider_leg",hugeSpiderLeg);
        register("spider_leg_b",coldSpiderLeg);

        register("powder_an", powder_dark);
        register("powder_bing", powder_freeze);
        register("powder_feng", powder_wind);
        register("powder_huo", powder_blaze);
        register("powder_jin", powder_metal);
        register("powder_lei", powder_thunder);
        register("powder_mu", powder_wood);
        register("powder_shui", powder_liquid);
        register("powder_tu", powder_earth);
        
        register("gowther",gowther);
        register("merlin",merlin);
        register("meliodas",meliodas);
        register("king",king);
        register("ban",ban);
        register("diane",diane);
        register("escanor",escanor);
    
        register("bad_apple",badApple);
        register("color_bag",colorBag);
        register("pants", pants);
        register("c4",c4);
        register("gem_blue",gemBlue);
        register("gem_yellow",gemYellow);
        register("final_key",final_key);
        register("cracked_key",cracked_key);
        register("end_scroll",endScroll);
        register("t13",grenade);
        
        register("towards_pickaxe",towards_Pickaxe);
        register("stairs_pickaxe",stairs_Pickaxe);
        register("star_pickaxe",star_Pickaxe);
        register("plate_pickaxe",plate_Pickaxe);
        register("cube_pickaxe",cube_Pickaxe);

        Constant.initItemArray();
    }

    public static void registerRecipes(RecipeRegister register) {
        Material[] materials = new Material[]{Material.copper,Material.silver,Material.gold,Material.iron,Material.ancient_metal,Material.mithril,Materials.vibranium,Material.adamantium};
        for(int i = 0; i <  materials.length; i++){
            for(int id = Items.powder_dark.itemID; id <= Items.powder_earth.itemID; id++){
                register.registerShapedRecipe(new ItemStack(Items.powder_metal, 1), false, new Object[]{"###", "#*#","###", '#', Item.getMatchingItem(ItemNugget.class,materials[i]) , '*', getItem(id)});
            }
        }
        
        for(int flower_type = 0; flower_type <= 8; flower_type++){
            if(flower_type != 3){
                if(flower_type != 4){
                    if(flower_type != 6){
                        register.registerShapelessRecipe(new ItemStack(Items.chikitan,2),false,new Object[]{Item.emerald,Item.emerald,new ItemStack(Block.plantRed,1,flower_type),Item.egg});
                        register.registerShapelessRecipe(new ItemStack(Items.riskyAgent,1),false,new Object[]{Items.zombieBrainCooked,Items.zombieBrainCooked,Items.zombieBrainCooked,new ItemStack(Block.plantRed,1,flower_type)});
                        register.registerShapelessRecipe(new ItemStack(Items.suspiciousStew,1),false,new Object[]{Items.buguDan,Item.rottenFlesh,Item.spiderEye,new ItemStack(Item.dyePowder,1,15)});
                    }
                }
            }
        }
        register.registerShapelessRecipe(new ItemStack(Items.wuzhiDan,8),false,new Object[]{Items.buguDan,Item.redstone,Block.plantYellow,new ItemStack(Block.plantRed,1,0),new ItemStack(Block.plantRed,1,1),new ItemStack(Block.plantRed,1,2),new ItemStack(Block.plantRed,1,5),new ItemStack(Block.plantRed,1,7),new ItemStack(Block.plantRed,1,8)});
        register.registerShapelessRecipe(new ItemStack(Items.wuzhiDan,1),false,new Object[]{Items.buguDan,Item.redstone,Item.potato,Item.carrot,Item.onion,Block.mushroomBrown});
        register.registerShapelessRecipe(new ItemStack(Items.chikitan,2),false,new Object[]{Item.emerald,Item.emerald,Block.plantYellow,Item.egg});
        register.registerShapelessRecipe(new ItemStack(Items.cubeSugar,1),false,new Object[]{Item.sugar,Item.sugar,Item.sugar,Item.sugar});
        register.registerShapelessRecipe(new ItemStack(Items.spicyStrip,8),false,new Object[]{Item.rottenFlesh,Item.rottenFlesh,Item.rottenFlesh,Item.rottenFlesh});
        register.registerShapelessRecipe(new ItemStack(Items.riskyAgent,1),false,new Object[]{Items.zombieBrain,Items.zombieBrain,Items.zombieBrain,Block.plantYellow});
        register.registerShapelessRecipe(new ItemStack(Items.c4,4),false,new Object[]{Block.tnt,Block.pressurePlatePlanks});
        register.registerShapelessRecipe(new ItemStack(Items.grenade,5),false,new Object[]{Block.tnt,Block.woodenButton});
        register.registerShapelessRecipe(new ItemStack(Items.badApple,1),false,new Object[]{new ItemStack(Block.leaves,1,0),new ItemStack(Block.leaves,1,1),new ItemStack(Block.leaves,1,2),new ItemStack(Block.leaves,1,3)});
        
        for(int id = Items.itemEnhanceGem.itemID; id < Items.itemEnhanceGem6.itemID; id++){
            for(int sub = 0; sub < GemModifierTypes.values().length; sub++){
                register.registerShapelessRecipe(new ItemStack(id,1,0),false,
                        new Object[]{new ItemStack(id,1,sub),Items.powder_metal,Items.powder_blaze,Items.powder_thunder,Items.powder_wind});
                register.registerShapelessRecipe(new ItemStack(id,1,1),false,
                        new Object[]{new ItemStack(id,1,sub),Items.powder_wood,Items.powder_liquid,Items.powder_earth,Items.powder_wind});
                register.registerShapelessRecipe(new ItemStack(id,1,2),false,
                        new Object[]{new ItemStack(id,1,sub),Items.powder_metal,Items.powder_wood,Items.powder_freeze,Items.powder_blaze});
                register.registerShapelessRecipe(new ItemStack(id,1,3),false,
                        new Object[]{new ItemStack(id,1,sub),Items.powder_wood,Items.powder_liquid,Items.powder_earth,Items.powder_dark});
                register.registerShapelessRecipe(new ItemStack(id,1,4),false,
                        new Object[]{new ItemStack(id,1,sub),Items.powder_metal,Items.powder_wood,Items.powder_freeze,Items.powder_wind});
            }
        }

        for(int j = 0; j < 16; j++){
            register.registerShapelessRecipe(new ItemStack(Item.bed, 1, j), true, new Object[] {new ItemStack(Item.dyePowder, 1, j), new ItemStack(Item.bed, 1, 0)});
        }

        register.registerShapelessRecipe(new ItemStack(Blocks.blockLantern, 1), true,Blocks.torchWood, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget);
        register.registerShapelessRecipe(new ItemStack(Items.voucherCore, 1), true, Items.voucherStrike, Items.voucherDestruction, Items.voucherPhase, Items.voucherCluster, Items.voucherOverlord, Items.voucherMagic);
        register.registerShapedRecipe(new ItemStack(clubIron, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.ironNugget , '*', Items.ingotIron});
        register.registerShapedRecipe(new ItemStack(clubAncientMetal, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.ancientMetalNugget , '*', Items.ingotAncientMetal});
        register.registerShapedRecipe(new ItemStack(clubMithril, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.mithrilNugget , '*', Items.ingotMithril});
        register.registerShapedRecipe(new ItemStack(clubAdamantium, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.adamantiumNugget , '*', Items.ingotAdamantium});
        register.registerShapedRecipe(new ItemStack(clubVibranium, 1), true, new Object[]{"###", "#*#"," A ", '#', Items.VIBRANIUM_NUGGET , '*', Items.VIBRANIUM_INGOT, 'A', Items.voucherClubCore});

        register.registerShapedRecipe(new ItemStack(itemDynamicCoreIron, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotIron, 'D', Blocks.blockIron , 'B', Blocks.glass, 'C', Blocks.blockRedstone});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreAncient_metal, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotAncientMetal, 'D', Blocks.blockAncientMetal , 'B', Blocks.glass, 'C', Items.itemDynamicCoreIron});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreMithril, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotMithril, 'D', Blocks.blockMithril , 'B', Blocks.glass, 'C', Items.itemDynamicCoreAncient_metal});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreAdamantium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotAdamantium, 'D', Blocks.blockAdamantium , 'B', Blocks.glass, 'C', Items.itemDynamicCoreMithril});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreVibranium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.VIBRANIUM_INGOT, 'D', Blocks.blockVibranium , 'B', Blocks.glass, 'C', Items.itemDynamicCoreAdamantium});

        register.registerShapedRecipe(new ItemStack(itemRegenerationCoreIron, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotIron, 'D', Blocks.blockIron , 'B', Blocks.glass, 'C', Items.voucherSpider});
        register.registerShapedRecipe(new ItemStack(itemRegenerationCoreAncient_metal, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotAncientMetal, 'D', Blocks.blockAncientMetal , 'B', Blocks.glass, 'C', Items.itemRegenerationCoreIron});
        register.registerShapedRecipe(new ItemStack(itemRegenerationCoreMithril, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotMithril, 'D', Blocks.blockMithril , 'B', Blocks.glass, 'C', Items.itemRegenerationCoreAncient_metal});
        register.registerShapedRecipe(new ItemStack(itemRegenerationCoreAdamantium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotAdamantium, 'D', Blocks.blockAdamantium , 'B', Blocks.glass, 'C', Items.itemRegenerationCoreMithril});
        register.registerShapedRecipe(new ItemStack(itemRegenerationCoreVibranium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.VIBRANIUM_INGOT, 'D', Blocks.blockVibranium , 'B', Blocks.glass, 'C', Items.itemRegenerationCoreAdamantium});

        register.registerShapedRecipe(new ItemStack(itemGuardCoreIron, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotIron, 'D', Blocks.blockIron , 'B', Blocks.glass, 'C', Items.voucherGuard});
        register.registerShapedRecipe(new ItemStack(itemGuardCoreAncient_metal, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotAncientMetal, 'D', Blocks.blockAncientMetal , 'B', Blocks.glass, 'C', Items.itemGuardCoreIron});
        register.registerShapedRecipe(new ItemStack(itemGuardCoreMithril, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotMithril, 'D', Blocks.blockMithril , 'B', Blocks.glass, 'C', Items.itemGuardCoreAncient_metal});
        register.registerShapedRecipe(new ItemStack(itemGuardCoreAdamantium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotAdamantium, 'D', Blocks.blockAdamantium , 'B', Blocks.glass, 'C', Items.itemGuardCoreMithril});
        register.registerShapedRecipe(new ItemStack(itemGuardCoreVibranium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.VIBRANIUM_INGOT, 'D', Blocks.blockVibranium , 'B', Blocks.glass, 'C', Items.itemGuardCoreAdamantium});


        register.registerShapelessRecipe(new ItemStack(Items.voucherClubCore, 1), true, Items.voucherFishing, Items.voucherVillager, Items.voucherPlanting);

        register.registerShapelessRecipe(new ItemStack(Items.final_key,1),false,Items.cracked_key,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET);
        
        if(Configs.wenscConfig.isRecipeGATorch.ConfigValue) {
            Block[] woods = {Block.wood, Blocks.wood1};
            Item[] yarns = {Item.sinew, Item.silk};
            for(int wood_sub = 0; wood_sub < 4; wood_sub++){
                for (Block wood : woods) {
                    for (Item yarn : yarns) {
                        register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(wood, 1, wood_sub), new ItemStack(yarn, 1), new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
                        register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(wood, 1, wood_sub), new ItemStack(yarn, 1), new ItemStack(Item.coal, 1, 0), new ItemStack(Item.coal, 1, 1)});
                        register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(wood, 1, wood_sub), new ItemStack(yarn, 1), new ItemStack(Item.coal, 1, 0), new ItemStack(Item.coal, 1, 0)});
                    }
                }
            }
            for(int stack_size = 1; stack_size <= 9; stack_size++){
                register.registerShapelessRecipe(new ItemStack(Items.Bunch_Torch, 4 * stack_size), true, new Object[]{new ItemStack(Stack_Torch, stack_size)});
            }
            for(int stack_size = 1; stack_size <= 9; stack_size++){
                register.registerShapelessRecipe(new ItemStack(Block.torchWood, 4 * stack_size), true, (new ItemStack(Items.Bunch_Torch, stack_size)));
            }
            for(int stack_size = 1; stack_size <= 2; stack_size++){
                register.registerShapelessRecipe((new ItemStack(Items.Bunch_Torch, stack_size)), true, new ItemStack(Block.torchWood, 4 * stack_size));
            }
        }

        register.registerShapedRecipe(new ItemStack(endScroll),
                true,
                "#S#",
                "SAS",
                "#S#",
                '#', Item.paper,
                'A', Item.eyeOfEnder,
                'S', Item.shardObsidian);

        if(Configs.wenscConfig.isRecipeRingKiller.ConfigValue) {
            register.registerShapedRecipe(new ItemStack(ringKillerCopper, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordCopper , '*', Items.emerald});
            register.registerShapedRecipe(new ItemStack(ringKillerIron, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordIron , '*', Items.ringKillerCopper});
            register.registerShapedRecipe(new ItemStack(ringKillerAncient, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordAncientMetal , '*', Items.ringKillerIron});
            register.registerShapedRecipe(new ItemStack(ringKillerMithril, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordMithril , '*', Items.ringKillerAncient});
            register.registerShapedRecipe(new ItemStack(ringKillerAdamantium, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordAdamantium , '*', Items.ringKillerMithril});
            register.registerShapedRecipe(new ItemStack(ringKillerVibranium, 1), true, new Object[]{"###", "#*#","###", '#', VIBRANIUM_SWORD , '*', Items.ringKillerAdamantium});
        }

        for(int i =0; i < GemModifierTypes.values().length; i++) {
            register.registerShapelessRecipe(new ItemStack(itemEnhanceGem2,1,i), true,
                    new ItemStack(Items.itemGemShard, 1, 0), new ItemStack(itemEnhanceGem, 1, i), new ItemStack(itemEnhanceGem, 1, i), new ItemStack(itemEnhanceGem, 1, i));
            register.registerShapelessRecipe(new ItemStack(itemEnhanceGem3,1,i), true,
                    new ItemStack(Items.itemGemShard, 1, 1), new ItemStack(itemEnhanceGem2, 1, i), new ItemStack(itemEnhanceGem, 1, i), new ItemStack(itemEnhanceGem, 1, i));
            register.registerShapelessRecipe(new ItemStack(itemEnhanceGem4,1,i), true,
                    new ItemStack(Items.itemGemShard, 1, 2), new ItemStack(itemEnhanceGem3, 1, i), new ItemStack(itemEnhanceGem2, 1, i), new ItemStack(itemEnhanceGem, 1, i));
            register.registerShapelessRecipe(new ItemStack(itemEnhanceGem5,1,i), true,
                    new ItemStack(Items.itemGemShard, 1, 3), new ItemStack(itemEnhanceGem4, 1, i), new ItemStack(itemEnhanceGem3, 1, i), new ItemStack(itemEnhanceGem2, 1, i));
            register.registerShapelessRecipe(new ItemStack(itemEnhanceGem6,1,i), true,
                    new ItemStack(Items.itemGemShard, 1, 4), new ItemStack(itemEnhanceGem5, 1, i), new ItemStack(itemEnhanceGem4, 1, i), new ItemStack(itemEnhanceGem3, 1, i));
        }

        register.registerShapedRecipe(new ItemStack(OBSIDIAN_STICK), true, "#", "#", '#', Block.obsidian);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_INGOT),
                false,
                "NIN",
                "IDI",
                "NIN",
                'N',
                Item.mithrilNugget,
                'I',
                Item.ingotAdamantium,
                'D',
                Items.voucherCore);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_NUGGET, 9), true, VIBRANIUM_INGOT);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT),
                true,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET);
            
            Material[] lowerTierLoop = new Material[]{Material.copper,Material.silver,Material.gold};
            Material[] stdTierLoop = new Material[]{Material.iron,Material.ancient_metal,Material.mithril,Material.adamantium,Materials.vibranium};
            
            for (int i = 0; i < lowerTierLoop.length; i++){
                Item upper_ingot = getMatchingItem(ItemIngot.class,Material.iron);
                Item lower_equipment;
                Item upper_equipment;
                
                lower_equipment = getMatchingArmor(ItemHelmet.class,lowerTierLoop[i],false);
                upper_equipment = getMatchingArmor(ItemHelmet.class,Material.iron,false);
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
                lower_equipment = getMatchingArmor(ItemCuirass.class,lowerTierLoop[i],false);
                upper_equipment = getMatchingArmor(ItemCuirass.class,Material.iron,false);
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        "###",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
                lower_equipment = getMatchingArmor(ItemLeggings.class,lowerTierLoop[i],false);
                upper_equipment = getMatchingArmor(ItemLeggings.class,Material.iron,false);
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        "# #",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
                lower_equipment = getMatchingArmor(ItemBoots.class,lowerTierLoop[i],false);
                upper_equipment = getMatchingArmor(ItemBoots.class,Material.iron,false);
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "# #",
                        "#A#",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
                
            }

        for (int i = 0; i + 1 < stdTierLoop.length; i++){
            Item upper_ingot = getMatchingItem(ItemIngot.class,stdTierLoop[i + 1]);
            Item upper_nugget = getMatchingItem(ItemNugget.class,stdTierLoop[i + 1]);
            Item lower_equipment;
            Item upper_equipment;

            lower_equipment = getMatchingArmor(ItemHelmet.class,stdTierLoop[i],false);
            upper_equipment = getMatchingArmor(ItemHelmet.class,stdTierLoop[i + 1],false);
            if(lower_equipment != null && upper_equipment != null){
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            
            lower_equipment = getMatchingArmor(ItemCuirass.class,stdTierLoop[i],false);
            upper_equipment = getMatchingArmor(ItemCuirass.class,stdTierLoop[i + 1],false);
            if(lower_equipment != null && upper_equipment != null){
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        "###",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingArmor(ItemLeggings.class,stdTierLoop[i],false);
            upper_equipment = getMatchingArmor(ItemLeggings.class,stdTierLoop[i + 1],false);
            if(lower_equipment != null && upper_equipment != null){
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        "#A#",
                        "# #",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingArmor(ItemBoots.class,stdTierLoop[i],false);
            upper_equipment = getMatchingArmor(ItemBoots.class,stdTierLoop[i + 1],false);
            if(lower_equipment != null && upper_equipment != null){
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "# #",
                        "#A#",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemPickaxe.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemPickaxe.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "###",
                        " A ",
                        " S ",
                        '#', upper_ingot,
                        'A', lower_equipment,
                        'S', fetchingStickByMaterial(stdTierLoop[i + 1])).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemSword.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemSword.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "#",
                        "#",
                        "A",
                        '#', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemClubMetal.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemClubMetal.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null && stdTierLoop[i + 1] != Materials.vibranium) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "NNN",
                        "NIN",
                        " A ",
                        'N', upper_nugget,
                        'I', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemShovel.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemShovel.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment), 
                        true,
                        "#",
                        "A",
                        "S",
                        '#', upper_ingot,
                        'S', fetchingStickByMaterial(stdTierLoop[i + 1]),
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemAxe.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemAxe.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "##",
                        "A#",
                        "S ",
                        '#', upper_ingot,
                        'S', fetchingStickByMaterial(stdTierLoop[i + 1]),
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemDagger.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemDagger.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "I",
                        "A",
                        'I', upper_ingot,
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemWarHammer.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemWarHammer.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "III",
                        "IAI",
                        " S ",
                        'I', upper_ingot,
                        'S', fetchingStickByMaterial(stdTierLoop[i + 1]),
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemBattleAxe.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemBattleAxe.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "I I",
                        "IAI",
                        " S ",
                        'I', upper_ingot,
                        'S', fetchingStickByMaterial(stdTierLoop[i + 1]),
                        'A', lower_equipment).extendsNBT();
            }
            lower_equipment = getMatchingItem(ItemEnhancedPickaxe.class,stdTierLoop[i]);
            upper_equipment = getMatchingItem(ItemEnhancedPickaxe.class,stdTierLoop[i + 1]);
            if(lower_equipment != null && upper_equipment != null) {
                register.registerShapedRecipe(new ItemStack(upper_equipment),
                        true,
                        "WXY",
                        "VAZ",
                        "UG#",
                        'G', gemYellow,
                        'U', gowther,
                        'V', merlin,
                        'W', ban,
                        'X', king,
                        'Y', meliodas,
                        'Z', diane,
                        '#', escanor,
                        'A', lower_equipment).extendsNBT();
            }
        }
        register.registerShapedRecipe(new ItemStack(Items.clubVibranium),
                true,
                "NNN",
                "NIN",
                " AV",
                'N',Items.VIBRANIUM_NUGGET,
                'I',Items.VIBRANIUM_INGOT,
                'A',Items.clubAdamantium,
                'V',Items.voucherClubCore).extendsNBT();
        
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_PICKAXE),
                true,
                "###",
                " A ",
                " S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', pickaxeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SHOVEL),
                true,
                "#",
                "A",
                "S",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', shovelAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SWORD),
                true,
                " # ",
                " # ",
                " A ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', swordAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_AXE),
                true,
                "##",
                "A#",
                "S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', axeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_DAGGER),
                true,
                "I",
                "A",
                'I', VIBRANIUM_INGOT,
                'A', daggerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_WAR_HAMMER),
                true,
                "III",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', warHammerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BATTLE_AXE),
                true,
                "I I",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', battleAxeAncientMetal);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT, 9), true, Blocks.blockVibranium);
        register.registerShapelessRecipe(new ItemStack(Item.diamond),
                false,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK);
        register.registerShapedRecipe(new ItemStack(BLAZE_COAL_POWDER),
                true,
                " B ",
                "BCB",
                " B ",
                'B', Item.blazePowder,
                'C', Item.coal);
        RecipesFurnace.smelting().addSmelting(Block.coalBlock.blockID, new ItemStack(DIAMOND_CHUNK));
        RecipesFurnace.smelting().addSmelting(Items.demonPillRaw.itemID, new ItemStack(Items.demonPillCooked));
        RecipesFurnace.smelting().addSmelting(Items.zombieBrain.itemID, new ItemStack(Items.zombieBrainCooked));
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BOW),
                true,
                "NSL",
                "AVL",
                "NSL",
                'S', OBSIDIAN_STICK,
                'V', VIBRANIUM_INGOT,
                'L', Item.silk,
                'N', VIBRANIUM_NUGGET,
                'A', bowAncientMetal);
        register.registerShapedRecipe(new ItemStack(Item.emerald),
                true,
                "SS",
                "SS",
                'S', Item.shardEmerald);
        register.registerShapelessRecipe(new ItemStack(Item.shardEmerald, 4), true, Item.emerald);
        register.registerShapedRecipe(new ItemStack(IRON_ENHANCE_STONE),
                true,
                " C ",
                "SIS",
                " C ",
                'I', Item.ingotIron,
                'C', Item.ingotCopper,
                'S', Item.ingotSilver);
        register.registerShapedRecipe(new ItemStack(MITHRIL_ENHANCE_STONE),
                true,
                " I ",
                "GMG",
                " I ",
                'I', Item.ingotIron,
                'G', Item.ingotGold,
                'M', Item.ingotMithril);
        register.registerShapedRecipe(new ItemStack(ADAMANTIUM_ENHANCE_STONE),
                true,
                " D ",
                "MAM",
                " R ",
                'R', Block.blockRedstone,
                'D', Item.diamond,
                'M', Item.ingotMithril,
                'A', Item.ingotAdamantium);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_ENHANCE_STONE),
                true,
                "DAD",
                "MVM",
                "DAD",
                'A', Item.ingotAdamantium,
                'D', Item.diamond,
                'M', Item.ingotMithril,
                'V', Items.VIBRANIUM_INGOT);
        register.registerShapedRecipe(new ItemStack(UNIVERSAL_ENHANCE_STONE),
                true,
                "gDg",
                "bTb",
                "BEB",
                'g', Item.glowstone,
                'D', Item.bottleOfDisenchanting,
                'b', Item.blazePowder,
                'T', Item.ghastTear,
                'B', Item.book,
                'E', Item.enderPearl);
        register.registerShapedRecipe(new ItemStack(coldSpiderLeg,6),
                false,
                "A A",
                "ABA",
                "APA",
                'A',Items.hugeSpiderLeg,
                'B',Item.snowball,
                'P',Item.porkCooked);
        register.registerShapedRecipe(new ItemStack(Item.appleGold, 1, 0),true,
                "###",
                "IXI",
                "###",
                '#', Item.goldNugget,
                'I', Item.ingotGold,
                'X', Items.badApple);
        register.registerShapedRecipe(new ItemStack(Items.gemYellow, 1, 0),true,
                "###",
                "#X#",
                "###",
                '#', Item.ingotGold,
                'X', Items.gemBlue);
        register.registerShapedRecipe(new ItemStack(Items.towards_Pickaxe, 1, 0),true,
                "###",
                "#X#",
                "###",
                '#', Blocks.blockGotcha,
                'X', Item.pickaxeIron);
        register.registerShapedRecipe(new ItemStack(Block.planks, 4, 4), true, new Object[] {"#", '#', new ItemStack(Blocks.wood1, 1, 0)});
        register.registerShapedRecipe(new ItemStack(Block.planks, 4, 5), true, new Object[] {"#", '#', new ItemStack(Blocks.wood1, 1, 1)});

        register.registerShapedRecipe(new ItemStack(Block.woodSingleSlab, 6, 4), true, new Object[] {"###", '#', new ItemStack(Block.planks, 1, 4)});
        register.registerShapedRecipe(new ItemStack(Blocks.stairsMaple, 4), true,"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 4));

        register.registerShapedRecipe(new ItemStack(Block.woodSingleSlab, 6, 5), true, new Object[] {"###", '#', new ItemStack(Block.planks, 1, 5)});
        register.registerShapedRecipe(new ItemStack(Blocks.stairsCherry, 4), true,"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 5));
    }
    public static Item fetchingStickByMaterial(Material material){
        return material == Materials.vibranium ? OBSIDIAN_STICK : Item.stick;
    }
}
