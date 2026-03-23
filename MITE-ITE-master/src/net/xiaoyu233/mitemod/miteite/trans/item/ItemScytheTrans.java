package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemScythe.class)
public abstract class ItemScytheTrans extends ItemTool {

   protected ItemScytheTrans(int par1, Material material) {
      super(par1, material);
   }

   @SoftOverride
   protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
      Block block = blockBreakInfo.block;
      if (block instanceof BlockCrops) {
         if (((BlockCrops) block).isMature(blockBreakInfo.getMetadata())) {
            return 10;
         }
      }
      if (this.materials_effective_against.contains(block)) {
         return 2;
      }
      return 1;
   }
}
