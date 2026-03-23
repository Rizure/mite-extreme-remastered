package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockLeaves.class)
public class BlockLeavesTrans extends BlockTransparant {
   protected BlockLeavesTrans(int par1, Material par2Material, boolean par3) {
      super(par1, par2Material, par3);
   }

   public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
      return false;
   }

   @Overwrite
   public int dropBlockAsEntityItem(BlockBreakInfo info) {
      if (BitHelper.isBitSet(info.getMetadata(), 4)) {
         return 0;
      } else if (info.getBiome().isSwampBiome() && info.world.rand.nextInt(2) == 0) {
         return 0;
      } else {
         int leaf_kind = this.getBlockSubtype(info.getMetadata());
         int num_drops;
         if ((num_drops = this.dropBlockAsEntityItem(info, Block.sapling.blockID, leaf_kind, 1, (leaf_kind == 3 ? 0.025F : 0.05F) + 0.01F * info.getHarvesterFortune())) > 0) {
            return num_drops;
         } else if ((num_drops = this.dropBlockAsEntityItem(info, Item.stick.itemID, 0, 1, 0.02F + 0.004F * info.getHarvesterFortune())) > 0) {
            return num_drops;
         } else if (leaf_kind == 0) {
            return this.dropBlockAsEntityItem(info, info.getBiome().isJungleBiome() ? Item.orange.itemID : Item.appleRed.itemID, 0, 1, 0.005F + 0.001F * info.getHarvesterFortune());
         } else {
            return leaf_kind == 3 ? this.dropBlockAsEntityItem(info, Item.banana.itemID, 0, 1, 0.005F + 0.001F * info.getHarvesterFortune()) : 0;
         }
      }
   }
}
