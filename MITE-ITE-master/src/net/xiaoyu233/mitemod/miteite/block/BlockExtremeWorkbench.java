package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public class BlockExtremeWorkbench extends Block{
   private IIcon top_icon;
   private IIcon side_icon;
   private IIcon bottom_icon;

   protected BlockExtremeWorkbench(int par1) {
      super(par1, Materials.invincible, new BlockConstants());
      this.setHardness(BlockHardness.workbench);
      this.setCreativeTab(CreativeModeTab.tabDecorations);
   }

   public IIcon a(int side, int metadata) {
      if (side == 0) {
         return bottom_icon;
      } else if (side == 1) {
         return top_icon;
      } else {
         return side_icon;
      }
   }

   public void a(mt par1IconRegister) {
      this.side_icon = par1IconRegister.a("crafting_table/final/side");
      this.top_icon = par1IconRegister.a("crafting_table/final/top");
      this.bottom_icon = par1IconRegister.a("crafting_table/final/bottom");
   }


   public boolean isValidMetadata(int metadata) {
      return metadata == 0;
   }

   public int getBlockSubtypeUnchecked(int metadata) {
      return metadata;
   }


   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
      if (player.onServer() && world.isAirOrPassableBlock(x, y + 1, z, false)) {
         Block block_above = world.getBlock(x, y + 1, z);
         if (block_above == null || !block_above.hidesAdjacentSide(world, x, y + 1, z, this, 1)) {
            player.displayGUIExtremeWorkbench(x, y, z);
         }
      }
      return true;
   }

   public boolean playerSwingsOnBlockActivated(boolean empty_handed) {
      return false;
   }

   public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
      return true;
   }

   public String getItemDisplayName(ItemStack itemStack) {
      return Translator.get("tile.extreme_workbench.name");
   }
}
