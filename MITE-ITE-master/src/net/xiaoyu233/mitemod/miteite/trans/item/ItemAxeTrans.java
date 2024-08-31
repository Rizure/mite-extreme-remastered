package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.BlockBreakInfo;
import net.minecraft.BlockLog;
import net.minecraft.ItemAxe;
import net.xiaoyu233.mitemod.miteite.block.BlockLog1;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemAxe.class)
public class ItemAxeTrans extends ItemToolTrans{
    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        if (blockBreakInfo.block instanceof BlockLog || blockBreakInfo.block instanceof BlockLog1){
            return 12;
        }
        return Math.max(super.getExpForBlockBreak(blockBreakInfo),1);
    }
}
