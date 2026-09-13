package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockManmadeIce extends Block{
    protected BlockManmadeIce(int par1) {
        super(par1, Material.ice, new BlockConstants());
        this.slipperiness = 0.98F;
        this.modifyMinHarvestLevel(1);
        this.setBlockHardness(1.0F);
        this.setCreativeTab(CreativeModeTab.tabBlock);
    }

    public int getRenderType() {
        return 0;
    }

    public int n() {
        return 0;
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        ItemStack itemStack = info.getHarvesterItemStack();
        if (itemStack != null) {
            if (itemStack.getEnchantmentLevel(Enchantment.silkTouch) > 0) {
                return this.dropBlockAsItself(info);
            }
        }
        return 0;
    }
}
