package net.xiaoyu233.mitemod.miteite.item.recipe;

import com.google.common.base.Objects;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceStone;
import net.xiaoyu233.mitemod.miteite.item.ItemForgingTemplates;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;

import java.util.function.Consumer;

public class ForgingTableRecipes {
   private static final BiMap<RecipeKey, ForgingRecipe> RECIPES = HashBiMap.create();
   private static final Consumer<ForgingRecipe> regiseterer = forgingRecipe -> {
      RecipeKey key = new RecipeKey(forgingRecipe.getMaterial(), forgingRecipe.getLevelToUpgrade(), forgingRecipe.getMode());
      RECIPES.put(key, forgingRecipe);
   };

   public static ForgingRecipe getRecipe(Material toolItem, int forgingLevel, int mode) {
      return RECIPES.get(new RecipeKey(toolItem, forgingLevel, mode));
   }

   public static ItemEnhanceStone getFlitterEnhanceStone(Material material) {
      if (material == Material.iron) {
         return Items.enhanceStoneIron;
      } else if (material == Material.mithril) {
         return Items.enhanceStoneMithril;
      } else if (material == Material.adamantium) {
         return Items.enhanceStoneAdamantium;
      } else if (material == Materials.vibranium) {
         return Items.enhanceStoneVibranium;
      } else {
         return Items.enhanceStoneUniversal;
      }
   }

   public static ItemForgingTemplates getFlitterTemplate(Material material) {
      return (ItemForgingTemplates) Item.getMatchingItem(ItemForgingTemplates.class, material);
   }

   //args:Input Tool Material, Enhance Stone Material, Required Forge Level, Table Level, Forging Itemstacks
   private static void registerForgingRecipe(Material forgee_material, Material forger_material, int upgrade_level, ForgingTableLevel table_level, ItemStack... items) {
      ForgingRecipe.Builder.of(forgee_material, upgrade_level, table_level).
              setChanceOfFailure(getFlitterEnhanceStone(forger_material).getFailChance()).
              setAxeDurabilityCost((int) (forgee_material.getDurability() * 400.0F)).
              setHammerDurabilityCost((int) (forgee_material.getDurability() * 500.0F)).
              setTimeReq(upgrade_level * 200 + 600).
              setQualityReward(EnumQuality.values()[Math.min(7, (upgrade_level + 1) / 2 + 2)]).
              addFaultFeedback(DowngradeFeedback.of(upgrade_level / 2)).
              addFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(Math.max(upgrade_level * 10 - table_level.getLevel() * 5, 0)))).
              addMaterials(items).
              setShouldUpgradeForgingLevel(true).
              setForging_Mode(TileEntityForgingTable.mode_forging_index).
              build(regiseterer);
   }

   private static void registerUpgradingRecipe(Material forgee_material, Material forger_material, int upgrade_level, ForgingTableLevel table_level, ItemStack... items) {
      ForgingRecipe.Builder.of(forgee_material, upgrade_level, table_level).
              setChanceOfFailure(getFlitterEnhanceStone(forger_material).getFailChance() - 36).
              setAxeDurabilityCost((int) (forgee_material.getDurability() * 200.0F)).
              setHammerDurabilityCost((int) (forgee_material.getDurability() * 250.0F)).
              setTimeReq(600 + upgrade_level * 600).
              setQualityReward(EnumQuality.values()[Math.min(7, (upgrade_level + 1) / 2 + 2)]).
              addFaultFeedback(DowngradeFeedback.of(upgrade_level / 2)).
              addFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(Math.max(upgrade_level * 10 - table_level.getLevel() * 5, 0)))).
              addMaterials(items).
              setShouldUpgradeForgingLevel(false).
              setForging_Mode(TileEntityForgingTable.mode_upgrading_index).
              build(regiseterer);
   }

   private static void registerDefusingRecipe(Material forgee_material, Material forger_material, int upgrade_level, ForgingTableLevel table_level, ItemStack... items) {
      ForgingRecipe.Builder.of(forgee_material, upgrade_level, table_level).
              setChanceOfFailure(getFlitterTemplate(forgee_material).getFailChance()).
              setAxeDurabilityCost((int) (forgee_material.getDurability() * 800.0F)).
              setHammerDurabilityCost((int) (forgee_material.getDurability() * 1000.0F)).
              setTimeReq(600 + forgee_material.getMinHarvestLevel() * 200).
              setQualityReward(EnumQuality.values()[Math.min(7, (upgrade_level + 1) / 2 + 2)]).
              addFaultFeedback(DowngradeFeedback.of(66)).
              addFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(666))).
              addMaterials(items).
              setShouldUpgradeForgingLevel(false).
              setForging_Mode(TileEntityForgingTable.mode_defusing_index).
              build(regiseterer);
   }

   private static void registerApplyingRecipe(Material forgee_material, Material forger_material, int upgrade_level, ForgingTableLevel table_level, ItemStack... items) {
      ForgingRecipe.Builder.of(forgee_material, upgrade_level, table_level).
              setChanceOfFailure(282 - getFlitterTemplate(forgee_material).getFailChance()).
              setAxeDurabilityCost((int) (forgee_material.getDurability() * 800.0F)).
              setHammerDurabilityCost((int) (forgee_material.getDurability() * 1000.0F)).
              setTimeReq(1200 + forgee_material.getMinHarvestLevel() * 300).
              setQualityReward(EnumQuality.values()[Math.min(7, (upgrade_level + 1) / 2 + 2)]).
              addFaultFeedback(DowngradeFeedback.of(0)).
              addFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(20))).
              addMaterials(items).
              setShouldUpgradeForgingLevel(false).
              setForging_Mode(TileEntityForgingTable.mode_applying_index).
              build(regiseterer);
   }

   public static void registerAll() {
      registerForgingRecipe(Material.copper, Material.iron, 0, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 1),
              new ItemStack(Item.ingotGold, 1));
      registerForgingRecipe(Material.copper, Material.iron, 1, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 2),
              new ItemStack(Item.ingotGold, 1));

      for (int i = 0; i < 15; i++) {
         registerUpgradingRecipe(Material.copper, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotCopper, 1),
                 new ItemStack(Items.dyePowder, 1, 4));
         registerUpgradingRecipe(Material.silver, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotSilver, 1),
                 new ItemStack(Items.dyePowder, 1, 4));
         registerUpgradingRecipe(Material.gold, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotGold, 1),
                 new ItemStack(Items.dyePowder, 1, 4));
         registerUpgradingRecipe(Material.iron, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Items.ironNugget, 4),
                 new ItemStack(Items.shardEmerald, 3));
         registerUpgradingRecipe(Material.ancient_metal, Material.ancient_metal, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Items.ancientMetalNugget, 4),
                 new ItemStack(Items.shardEmerald, 3));
         registerUpgradingRecipe(Material.mithril, Material.mithril, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Items.mithrilNugget, 3),
                 new ItemStack(Items.diamond, 1));
         registerUpgradingRecipe(Material.adamantium, Material.adamantium, i, ForgingTableLevel.ADAMANTIUM,
                 new ItemStack(Items.adamantiumNugget, 3),
                 new ItemStack(Items.fancyRed, 1));
         registerUpgradingRecipe(Materials.vibranium, Materials.vibranium, i, ForgingTableLevel.VIBRANIUM,
                 new ItemStack(Items.vibraniumNugget, 2),
                 new ItemStack(Items.fancyRed, 2));
      }

      for (int i = 0; i < 15; i++) {
         registerDefusingRecipe(Material.copper, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Items.forgingTemplatesCopper,1));
         registerDefusingRecipe(Material.silver, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Items.forgingTemplatesSilver,1));
         registerDefusingRecipe(Material.gold, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Items.forgingTemplatesGold,1));
         registerDefusingRecipe(Material.iron, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Items.forgingTemplatesIron,1));
         registerDefusingRecipe(Material.ancient_metal, Material.ancient_metal, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Items.forgingTemplatesAncientMetal,1));
         registerDefusingRecipe(Material.mithril, Material.mithril, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Items.forgingTemplatesMithril,1));
         registerDefusingRecipe(Material.adamantium, Material.adamantium, i, ForgingTableLevel.ADAMANTIUM,
                 new ItemStack(Items.forgingTemplatesAdamantium,1));
         registerDefusingRecipe(Materials.vibranium, Materials.vibranium, i, ForgingTableLevel.VIBRANIUM,
                 new ItemStack(Items.forgingTemplatesVibranium,1));
      }

      for (int i = 0; i < 15; i++) {
         registerApplyingRecipe(Material.copper, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotCopper, 4),
                 new ItemStack(Items.forgingTemplatesCopper,1));
         registerApplyingRecipe(Material.silver, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotSilver, 4),
                 new ItemStack(Items.forgingTemplatesSilver,1));
         registerApplyingRecipe(Material.gold, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotGold, 4),
                 new ItemStack(Items.forgingTemplatesGold,1));
         registerApplyingRecipe(Material.iron, Material.iron, i, ForgingTableLevel.IRON,
                 new ItemStack(Item.ingotIron, 4),
                 new ItemStack(Items.forgingTemplatesIron,1));
         registerApplyingRecipe(Material.ancient_metal, Material.ancient_metal, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Item.ingotAncientMetal, 4),
                 new ItemStack(Items.forgingTemplatesAncientMetal,1));
         registerApplyingRecipe(Material.mithril, Material.mithril, i, ForgingTableLevel.MITHRIL,
                 new ItemStack(Item.ingotMithril, 4),
                 new ItemStack(Items.forgingTemplatesMithril,1));
         registerApplyingRecipe(Material.adamantium, Material.adamantium, i, ForgingTableLevel.ADAMANTIUM,
                 new ItemStack(Item.ingotAdamantium, 4),
                 new ItemStack(Items.forgingTemplatesAdamantium,1));
         registerApplyingRecipe(Materials.vibranium, Materials.vibranium, i, ForgingTableLevel.VIBRANIUM,
                 new ItemStack(Items.ingotVibranium, 4),
                 new ItemStack(Items.forgingTemplatesVibranium,1));
      }

      registerForgingRecipe(Material.silver, Material.iron, 0, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotSilver, 1),
              new ItemStack(Item.ingotGold, 1));
      registerForgingRecipe(Material.silver, Material.iron, 1, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotSilver, 2),
              new ItemStack(Item.ingotGold, 1));

      registerForgingRecipe(Material.gold, Material.iron, 0, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 1),
              new ItemStack(Item.ingotSilver, 1),
              new ItemStack(Item.ingotGold, 1));
      registerForgingRecipe(Material.gold, Material.iron, 1, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 1),
              new ItemStack(Item.ingotSilver, 1),
              new ItemStack(Item.ingotGold, 2));

      registerForgingRecipe(Material.iron, Material.iron, 0, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 1),
              new ItemStack(Item.ingotGold, 1),
              new ItemStack(Items.enhanceStoneIron, 1));
      registerForgingRecipe(Material.iron, Material.iron, 1, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotSilver, 1),
              new ItemStack(Item.ingotGold, 1),
              new ItemStack(Items.enhanceStoneIron, 2));
      registerForgingRecipe(Material.iron, Material.iron, 2, ForgingTableLevel.IRON,
              new ItemStack(Item.ingotCopper, 1),
              new ItemStack(Item.ingotSilver, 1),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Items.enhanceStoneIron, 2));

      registerForgingRecipe(Material.ancient_metal, Material.iron, 0, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 1),
              new ItemStack(Item.ingotGold, 1),
              new ItemStack(Items.enhanceStoneIron, 1));
      registerForgingRecipe(Material.ancient_metal, Material.iron, 1, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 2),
              new ItemStack(Item.ingotGold, 1),
              new ItemStack(Items.enhanceStoneIron, 1));
      registerForgingRecipe(Material.ancient_metal, Material.iron, 2, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 2),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Items.enhanceStoneIron, 1));
      registerForgingRecipe(Material.ancient_metal, Material.iron, 3, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 2),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Items.enhanceStoneIron, 2));

      registerForgingRecipe(Material.mithril, Material.mithril, 0, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 1),
              new ItemStack(Item.ingotGold, 1),
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Items.enhanceStoneIron, 2));
      registerForgingRecipe(Material.mithril, Material.mithril, 1, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 2),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Items.enhanceStoneMithril, 1));
      registerForgingRecipe(Material.mithril, Material.mithril, 2, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotAncientMetal, 1),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Items.enhanceStoneMithril, 1));
      registerForgingRecipe(Material.mithril, Material.mithril, 3, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotAncientMetal, 2),
              new ItemStack(Item.ingotGold, 2),
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Items.enhanceStoneMithril, 1));
      registerForgingRecipe(Material.mithril, Material.mithril, 4, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 3),
              new ItemStack(Item.ingotGold, 3),
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Items.enhanceStoneMithril, 2));
      registerForgingRecipe(Material.mithril, Material.mithril, 5, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotAncientMetal, 3),
              new ItemStack(Item.ingotGold, 3),
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Items.enhanceStoneMithril, 2));
      registerForgingRecipe(Material.mithril, Material.mithril, 6, ForgingTableLevel.MITHRIL,
              new ItemStack(Item.ingotIron, 3),
              new ItemStack(Item.ingotAncientMetal, 3),
              new ItemStack(Item.ingotGold, 3),
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Items.enhanceStoneMithril, 2));

      registerForgingRecipe(Material.adamantium, Material.adamantium, 0, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 1, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 1),
              new ItemStack(Item.ingotAdamantium, 1),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 2, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Item.ingotAdamantium, 1),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 3, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Item.ingotAdamantium, 2),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 4, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 3),
              new ItemStack(Item.ingotAdamantium, 3),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 5, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 3),
              new ItemStack(Items.enhanceStoneAdamantium, 2));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 6, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.enhanceStoneAdamantium, 2));
      registerForgingRecipe(Material.adamantium, Material.adamantium, 7, ForgingTableLevel.ADAMANTIUM,
              new ItemStack(Item.ingotMithril, 5),
              new ItemStack(Item.ingotAdamantium, 5),
              new ItemStack(Items.enhanceStoneAdamantium, 2));

      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 0, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 2),
              new ItemStack(Item.ingotAdamantium, 2),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 1, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 3),
              new ItemStack(Item.ingotAdamantium, 2),
              new ItemStack(Items.enhanceStoneAdamantium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 2, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 3),
              new ItemStack(Item.ingotAdamantium, 3),
              new ItemStack(Items.enhanceStoneVibranium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 3, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 3),
              new ItemStack(Items.enhanceStoneVibranium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 4, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.enhanceStoneVibranium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 5, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.ingotVibranium, 1),
              new ItemStack(Items.enhanceStoneVibranium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 6, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.ingotVibranium, 2),
              new ItemStack(Items.enhanceStoneVibranium, 1));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 7, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 4),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.ingotVibranium, 2),
              new ItemStack(Items.enhanceStoneVibranium, 2));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 8, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 6),
              new ItemStack(Item.ingotAdamantium, 4),
              new ItemStack(Items.ingotVibranium, 2),
              new ItemStack(Items.enhanceStoneVibranium, 2));
      registerForgingRecipe(Materials.vibranium, Materials.vibranium, 9, ForgingTableLevel.VIBRANIUM,
              new ItemStack(Item.ingotMithril, 6),
              new ItemStack(Item.ingotAdamantium, 6),
              new ItemStack(Items.ingotVibranium, 2),
              new ItemStack(Items.enhanceStoneVibranium, 2));

   }

   public static class RecipeKey {
      private final Material toolItem;
      private final int levelToUpgrade;
      private final int forgingMode;

      public RecipeKey(Material toolItem, int levelToUpgrade, int mode) {
         this.toolItem = toolItem;
         this.levelToUpgrade = levelToUpgrade;
         this.forgingMode = mode;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         RecipeKey key = (RecipeKey) o;
         return levelToUpgrade == key.levelToUpgrade &&
                 Objects.equal(toolItem, key.toolItem) &&
                 forgingMode == key.forgingMode;
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(toolItem, levelToUpgrade, forgingMode);
      }
   }
}
