package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;

public class ItemBamboo extends Item {
   private int spawnID;
   public ItemBamboo(int par1, Block par2Block, String texture) {
      super(par1, par2Block.blockMaterial, texture);
      this.spawnID = par2Block.blockID;
      this.setCraftingDifficultyAsComponent(25.0F);
   }

   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      RaycastCollision rc = player.getSelectedObject(partial_tick, false);
      if (rc != null && rc.isBlock()) {
         return player.tryPlaceHeldItemAsBlock(rc, Block.getBlock(this.spawnID));
      } else {
         return false;
      }
   }

   public boolean tryPlaceAsBlock(RaycastCollision rc, Block block, EntityPlayer player, ItemStack item_stack) {
      if (block == Blocks.blockBamboo) {
         if (rc.getBlockHit() == block) {
            int x = rc.block_hit_x;
            int y = rc.block_hit_y;
            int z = rc.block_hit_z;
            ++y;
            if (Blocks.blockBamboo.tryPlaceBlock(rc.world, x, y, z, EnumFace.TOP, 0, player, true, true)) {
               return true;
            }
         }

         int x = rc.neighbor_block_x;
         int y = rc.neighbor_block_y;
         int z = rc.neighbor_block_z;
         if (rc.world.getBlock(x, y, z) != block) {
            return super.tryPlaceAsBlock(rc, block, player, item_stack);
         }

         if (block.isLegalAt(rc.world, rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, 0) && block.canReplaceBlock(0, rc.getBlockHit(), rc.block_hit_metadata)) {
            return super.tryPlaceAsBlock(rc, block, player, item_stack);
         }

         ++y;
         if (Blocks.blockBamboo.tryPlaceBlock(rc.world, x, y, z, EnumFace.TOP, 0, player, true, true)) {
            return true;
         }
      }

      return super.tryPlaceAsBlock(rc, block, player, item_stack);
   }
}
