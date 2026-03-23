package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockTNT.class)
public class BlockTNTTrans extends BlockFalling {
   public BlockTNTTrans(int par1, Material material, BlockConstants constants) {
      super(par1, material, constants);
   }

   @Overwrite
   public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
      return false;
   }
}
