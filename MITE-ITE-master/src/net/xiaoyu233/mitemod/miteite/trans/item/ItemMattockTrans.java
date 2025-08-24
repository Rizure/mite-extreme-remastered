package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.BlockBreakInfo;
import net.minecraft.ItemMattock;
import net.minecraft.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemMattock.class)
public abstract class ItemMattockTrans extends ItemShovelTrans{
    protected ItemMattockTrans(int par1, Material material) {
        super(par1, material);
    }

    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        if (this.materials_effective_against.contains(blockBreakInfo.block)){
            return 5;
        }
        return 0;
    }
}
