package net.xiaoyu233.mitemod.miteite.item.recipe;

import com.google.common.base.Objects;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceStone;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import java.util.function.Consumer;

public class ForgingTableRecipes {
    private static final BiMap<RecipeKey,ForgingRecipe> RECIPES = HashBiMap.create();
    private static final Consumer<ForgingRecipe> regiseterer = forgingRecipe -> {
        RecipeKey key = new RecipeKey(forgingRecipe.getMaterial(),forgingRecipe.getLevelToUpgrade());
        RECIPES.put(key,forgingRecipe);
    };

    public static ForgingRecipe getRecipe(Material toolItem, int forgingLevel){
        return RECIPES.get(new RecipeKey(toolItem,forgingLevel));
    }

    public static ItemEnhanceStone getFlitterEnhanceStone(Material material){
        if(material == Material.iron){
            return Items.IRON_ENHANCE_STONE;
        }else if(material == Material.mithril){
            return Items.MITHRIL_ENHANCE_STONE;
        }else if(material == Material.adamantium){
            return Items.ADAMANTIUM_ENHANCE_STONE;
        }else {
            return Items.UNIVERSAL_ENHANCE_STONE;
        }
    }
    //args:Input Tool Material, Enhance Stone Material, Required Forge Level, Table Level, Forging Itemstacks
    private static void registerForgingRecipe(Material forgee_material, Material forger_material,int upgrade_level,ForgingTableLevel table_level,ItemStack... items){
        ForgingRecipe.Builder.of(forgee_material,upgrade_level,table_level).
                setChanceOfFailure(getFlitterEnhanceStone(forger_material).getFailChance()).
                setAxeDurabilityCost((int) (forgee_material.getDurability() * 400.0F)).
                setHammerDurabilityCost((int) (forgee_material.getDurability() * 500.0F)).
                setTimeReq(upgrade_level * 200 + 600).
                setQualityReward(EnumQuality.values()[Math.min(7,upgrade_level / 3 + 2)]).
                addFaultFeedback(DowngradeFeedback.of(upgrade_level / 2)).
                addFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(Math.max(upgrade_level * 10 - table_level.getLevel() * 5,0)))).
                addMaterials(items).
                build(regiseterer);


    }

    public static void registerAll(){
        registerForgingRecipe(Material.copper,Material.iron,0,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,1),
                new ItemStack(Item.ingotSilver,1));
        registerForgingRecipe(Material.copper,Material.iron,1,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,2),
                new ItemStack(Item.ingotSilver,2));

        registerForgingRecipe(Material.silver,Material.iron,0,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotSilver,1),
                new ItemStack(Item.ingotGold,1));
        registerForgingRecipe(Material.silver,Material.iron,1,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotSilver,2),
                new ItemStack(Item.ingotGold,1));

        registerForgingRecipe(Material.gold,Material.iron,0,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,1),
                new ItemStack(Item.ingotSilver,1),
                new ItemStack(Item.ingotGold,1));
        registerForgingRecipe(Material.gold,Material.iron,1,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,2),
                new ItemStack(Item.ingotSilver,2),
                new ItemStack(Item.ingotGold,1));

        registerForgingRecipe(Material.iron,Material.iron,0,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,2),
                new ItemStack(Item.ingotSilver,2),
                new ItemStack(Item.ingotGold,1),
                new ItemStack(Items.IRON_ENHANCE_STONE,2));
        registerForgingRecipe(Material.iron,Material.iron,1,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,3),
                new ItemStack(Item.ingotSilver,3),
                new ItemStack(Item.ingotGold,2),
                new ItemStack(Items.IRON_ENHANCE_STONE,2));
        registerForgingRecipe(Material.iron,Material.iron,2,ForgingTableLevel.IRON,
                new ItemStack(Item.ingotCopper,4),
                new ItemStack(Item.ingotSilver,4),
                new ItemStack(Item.ingotIron,2),
                new ItemStack(Item.ingotGold,2),
                new ItemStack(Items.IRON_ENHANCE_STONE,3));

        registerForgingRecipe(Material.ancient_metal,Material.iron,0,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,2),
                new ItemStack(Item.ingotGold,2),
                new ItemStack(Items.IRON_ENHANCE_STONE,2));
        registerForgingRecipe(Material.ancient_metal,Material.iron,1,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,3),
                new ItemStack(Item.ingotGold,2),
                new ItemStack(Items.IRON_ENHANCE_STONE,2));
        registerForgingRecipe(Material.ancient_metal,Material.iron,2,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,3),
                new ItemStack(Item.ingotGold,3),
                new ItemStack(Items.IRON_ENHANCE_STONE,3));
        registerForgingRecipe(Material.ancient_metal,Material.iron,3,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,4),
                new ItemStack(Item.ingotGold,4),
                new ItemStack(Items.IRON_ENHANCE_STONE,6));

        registerForgingRecipe(Material.mithril,Material.mithril,0,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,2),
                new ItemStack(Item.ingotGold,2),
                new ItemStack(Item.ingotMithril,1),
                new ItemStack(Items.IRON_ENHANCE_STONE,2));
        registerForgingRecipe(Material.mithril,Material.mithril,1,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,3),
                new ItemStack(Item.ingotGold,3),
                new ItemStack(Item.ingotMithril,1),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,1));
        registerForgingRecipe(Material.mithril,Material.mithril,2,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,4),
                new ItemStack(Item.ingotGold,4),
                new ItemStack(Item.ingotMithril,1),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,2));
        registerForgingRecipe(Material.mithril,Material.mithril,3,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,4),
                new ItemStack(Item.ingotGold,4),
                new ItemStack(Item.ingotMithril,2),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,2));
        registerForgingRecipe(Material.mithril,Material.mithril,4,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,8),
                new ItemStack(Item.ingotGold,8),
                new ItemStack(Item.ingotMithril,2),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,3));
        registerForgingRecipe(Material.mithril,Material.mithril,5,ForgingTableLevel.MITHRIL,
                new ItemStack(Item.ingotIron,8),
                new ItemStack(Item.ingotGold,8),
                new ItemStack(Item.ingotMithril,4),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,4));
        registerForgingRecipe(Material.mithril,Material.mithril,6,ForgingTableLevel.MITHRIL,
                new ItemStack(Block.blockGold, 2),
                new ItemStack(Block.blockIron,2),
                new ItemStack(Item.ingotMithril,6),
                new ItemStack(Items.MITHRIL_ENHANCE_STONE,6));

        registerForgingRecipe(Material.adamantium,Material.adamantium,0,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,1),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,1));
        registerForgingRecipe(Material.adamantium,Material.adamantium,1,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,1),
                new ItemStack(Item.ingotAdamantium,1),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,1));
        registerForgingRecipe(Material.adamantium,Material.adamantium,2,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,2),
                new ItemStack(Item.ingotAdamantium,1),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,1));
        registerForgingRecipe(Material.adamantium,Material.adamantium,3,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,3),
                new ItemStack(Item.ingotAdamantium,2),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,2));
        registerForgingRecipe(Material.adamantium,Material.adamantium,4,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,4),
                new ItemStack(Item.ingotAdamantium,2),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,2));
        registerForgingRecipe(Material.adamantium,Material.adamantium,5,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,6),
                new ItemStack(Item.ingotAdamantium,4),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,4));
        registerForgingRecipe(Material.adamantium,Material.adamantium,6,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,8),
                new ItemStack(Item.ingotAdamantium,6),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,4));
        registerForgingRecipe(Material.adamantium,Material.adamantium,7,ForgingTableLevel.ADAMANTIUM,
                new ItemStack(Item.ingotMithril,8),
                new ItemStack(Item.ingotAdamantium,6),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,2),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,6));

        registerForgingRecipe(Materials.vibranium,Materials.vibranium,0,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Item.ingotMithril,2),
                new ItemStack(Item.ingotAdamantium,2),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,1));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,1,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Item.ingotMithril,4),
                new ItemStack(Item.ingotAdamantium,4),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,2));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,2,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Item.ingotMithril,6),
                new ItemStack(Item.ingotAdamantium,6),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,1),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,3));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,3,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Item.ingotMithril,8),
                new ItemStack(Item.ingotAdamantium,8),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,2),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,4));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,4,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,1),
                new ItemStack(Block.blockAdamantium,1),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,3),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,5));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,5,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,2),
                new ItemStack(Block.blockAdamantium,1),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,4),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,6));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,6,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,2),
                new ItemStack(Block.blockAdamantium,2),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,5),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,8));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,7,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,3),
                new ItemStack(Block.blockAdamantium,3),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,6),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,12));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,8,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,4),
                new ItemStack(Block.blockAdamantium,3),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,8),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,16));
        registerForgingRecipe(Materials.vibranium,Materials.vibranium,9,ForgingTableLevel.VIBRANIUM,
                new ItemStack(Block.blockMithril,4),
                new ItemStack(Block.blockAdamantium,4),
                new ItemStack(Blocks.blockVibranium,1),
                new ItemStack(Items.UNIVERSAL_ENHANCE_STONE,8),
                new ItemStack(Items.ADAMANTIUM_ENHANCE_STONE,16));

    }

    public static class RecipeKey{
        private final Material toolItem;
        private final int levelToUpgrade;

        public RecipeKey(Material toolItem, int levelToUpgrade) {
            this.toolItem = toolItem;
            this.levelToUpgrade = levelToUpgrade;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecipeKey key = (RecipeKey) o;
            return levelToUpgrade == key.levelToUpgrade && Objects.equal(toolItem, key.toolItem);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(toolItem, levelToUpgrade);
        }
    }
}
