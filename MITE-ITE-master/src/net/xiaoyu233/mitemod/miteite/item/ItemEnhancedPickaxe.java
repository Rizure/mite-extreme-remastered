package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

public class ItemEnhancedPickaxe extends ItemTool {
    public int tier;
    protected ItemEnhancedPickaxe(int par1, Material material, int tier) {
        super(par1, material);
        this.addMaterialsEffectiveAgainst(new Material[]{Material.adamantium, Material.ancient_metal, Material.circuits, Material.clay, Material.coal, Material.copper, Material.coral, Material.diamond, Material.emerald, Material.glass, Material.gold, Material.hardened_clay, Material.ice, Material.iron, Material.mithril, Material.netherrack, Material.obsidian, Material.quartz, Material.redstone, Material.stone, Material.rusted_iron, Material.silver});
        this.tier = tier;
    }
    public float getBaseHarvestEfficiency(Block block) {
        return super.getBaseHarvestEfficiency(block) * 0.5F;
    }

    @Override
    public float getBaseDamageVsEntity() {
        return 6.0F;
    }

    public int getNumComponentsForDurability() {
        return 6;
    }

    public float getBaseDecayRateForBreakingBlock(Block block) {
        return 2.0F;
    }

    public float getBaseDecayRateForAttackingEntity(ItemStack item_stack) {
        return 2.0F;
    }

    @Override
    public String getToolType() {
        return "super_pickaxe";
    }
}
