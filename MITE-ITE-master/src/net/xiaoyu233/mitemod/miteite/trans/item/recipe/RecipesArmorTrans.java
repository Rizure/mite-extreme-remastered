package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecipesArmor.class)
public abstract class RecipesArmorTrans {
    @Shadow
    private CraftingManager crafting_manager;

    @Shadow
    protected abstract void addBootsRecipe(Item boots, Item component);

    @Shadow
    protected abstract void addCuirassRecipe(Item cuirass, Item component);

    @Shadow
    protected abstract void addHelmetRecipe(Item helmet, Item component);

    @Shadow
    protected abstract void addLeggingsRecipe(Item leggings, Item component);

    @Overwrite
    public void addRecipes(CraftingManager par1CraftingManager) {
        this.crafting_manager = par1CraftingManager;
        Item[] components = new Item[]{Item.leather, Item.chainCopper, Item.chainSilver, Item.chainGold, Item.chainRustedIron, Item.chainIron, Item.chainMithril, Item.chainAdamantium, Item.chainAncientMetal, Item.ingotCopper, Item.ingotSilver, Item.ingotGold, Item.ingotIron, Item.ingotAncientMetal};

        for (Item component : components) {
            if (component.getMaterials().size() != 1) {
                Minecraft.setErrorMessage("addRecipes: armor components can have only 1 material (" + component + ")");
            }
            this.addHelmetRecipe(ItemArmor.getMatchingArmor(ItemHelmet.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addCuirassRecipe(ItemArmor.getMatchingArmor(ItemCuirass.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addLeggingsRecipe(ItemArmor.getMatchingArmor(ItemLeggings.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addBootsRecipe(ItemArmor.getMatchingArmor(ItemBoots.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
        }
        int plank_subtype, i;
        for(i = 0; i < Block.workbench.getNumSubBlocks(); ++i) {
            Material tool_material = BlockWorkbench.getToolMaterial(i);
            if (tool_material == Material.flint) {
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"K", "#", 'K', Item.knifeFlint, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"FS", "s#", 'F', Item.flint, 'S', Item.silk, 's', Item.stick, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"FS", "s#", 'F', Item.flint, 'S', Item.sinew, 's', Item.stick, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.knifeFlint, 1), false, new Object[]{"#", '#', new ItemStack(Block.workbench, 1, i)}).setDifficulty(25.0F);
            } else if (tool_material == Material.obsidian) {
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"K", "#", 'K', Item.knifeObsidian, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"OS", "s#", 'O', Block.obsidian, 'S', Item.silk, 's', Item.stick, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"OS", "s#", 'O', Block.obsidian, 'S', Item.sinew, 's', Item.stick, '#', Constant.getBlockComponentWithNewWood(i)});
                ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.knifeObsidian, 1), false, new Object[]{"#", '#', new ItemStack(Block.workbench, 1, i)}).setDifficulty(25.0F);
            } else {
                for(plank_subtype = 4; plank_subtype < 8; ++plank_subtype) {
                    ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Block.workbench, 1, i), true, new Object[]{"IL", "s#", 'I', ItemIngot.getMatchingItem(ItemIngot.class, tool_material), 'L', Item.leather, 's', Item.stick, '#', new ItemStack(Block.planks, 1, plank_subtype)});
                }
            }
        }
    }
}
