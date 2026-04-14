package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockFlowerPotSapling extends BlockFlowerPot {
   public BlockFlowerPotSapling(int par1) {
      super(par1);
      this.setTextureName("flower_pot");
   }

   public String getMetadataNotes() {
      String[] types = BlockSapling1.WOOD_TYPES;
      String[] array = new String[4];

      for(int i = 0; i < 4; ++i) {
         if (this.isValidMetadata(i)) {
            StringHelper.addToStringArray(i + "=" + StringHelper.capitalize(types[i]), array);
         }
      }

      return StringHelper.implode(array, ", ", true, false) + " (empty and rose-filled pots are always BlockFlowerPot)";
   }

   @Override
   public boolean isValidMetadata(int metadata) {
      return metadata < 4;
   }

   @Override
   public int dropBlockAsEntityItem(BlockBreakInfo info) {
      if (!info.wasExploded() && !info.wasCrushed()) {
         int num_drops;
         return (num_drops = super.dropBlockAsEntityItem(info, Item.flowerPot)) > 0 ? num_drops + this.dropBlockAsEntityItem(info, BlockFlowerPotSapling.getPlantForMeta(info.getMetadata())) : 0;
      } else {
         return 0;
      }
   }

   public static ItemStack getPlantForMeta(int par0) {
      switch (par0) {
         case 0:
            return new ItemStack(Blocks.sapling1, 1, 0);
         case 1:
            return new ItemStack(Blocks.sapling1, 1, 1);
         case 2:
            return new ItemStack(Blocks.sapling1, 1, 2);
         case 3:
            return new ItemStack(Blocks.sapling1, 1, 3);
         default:
            return null;
      }
   }

   public static int getMetaForPlant(ItemStack par0ItemStack) {
      int var1 = par0ItemStack.getItem().itemID;
      if (var1 == Blocks.sapling1.blockID) {
         switch (par0ItemStack.getItemSubtype()) {
            case 0:
               return 0;
            case 1:
               return 1;
            case 2:
               return 2;
            case 3:
               return 3;
         }
      }
      return -1;
   }

   public boolean canBeCarried() {
      return false;
   }

}
