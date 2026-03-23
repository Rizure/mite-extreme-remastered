package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.Random;

public class BlockBamboo extends Block {
   private final Random Random;
   protected BlockBamboo(int par1) {
      super(par1, Material.wood, (new BlockConstants()).setNeverHidesAdjacentFaces().setNotAlwaysLegal());
      float var2 = 0.375F;
      this.setBlockBoundsForAllThreads((double)(0.5F - var2), (double)0.0F, (double)(0.5F - var2), (double)(0.5F + var2), (double)1.0F, (double)(0.5F + var2));
      this.setTickRandomly(true);
      this.setBlockHardness(0.8F);
      this.setLightValue(0.75F);
      this.Random = new Random();
   }
   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      boolean isCommonMonster = par5Entity instanceof EntityZombie || par5Entity instanceof EntitySpider || par5Entity instanceof EntityCreeper || par5Entity instanceof EntitySkeleton;
      if (par1World.isWorldServer() && isCommonMonster && this.Random.nextInt(300) == 1) {
         WorldServer var20 = (WorldServer) par1World;
         var20.addWeatherEffect(new EntityLightning(var20, par2, par3, par4));
         par1World.spawnEntityInWorld(new EntityLightning(par1World, par2, par3, par4));
         if (par1World.isBlueMoon24HourPeriod()) {
            par5Entity.dropItem(Items.powder_thunder, 1);
         }
      }
   }

   public String getMetadataNotes() {
      return "All bits used to track growth";
   }

   public boolean isValidMetadata(int metadata) {
      return metadata >= 0 && metadata < 16;
   }

   public boolean updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if (super.updateTick(par1World, par2, par3, par4, par5Random)) {
         return true;
      } else if (par5Random.nextFloat() > par1World.getBiomeGenForCoords(par2, par4).temperature - 0.2F) {
         return false;
      } else if (par1World.getBlockLightValue(par2, par3, par4) < 15) {
         return false;
      } else if (this.canOccurAt(par1World, par2, par3 + 1, par4, 0)) {
         int metadata = par1World.getBlockMetadata(par2, par3, par4);
         ++metadata;
         if (metadata == 5) {
            par1World.setBlock(par2, par3 + 1, par4, this.blockID);
            metadata = 0;
         }

         par1World.setBlockMetadata(par2, par3, par4, metadata, 4);
         return true;
      } else {
         return false;
      }
   }

   public boolean canOccurAt(World world, int x, int y, int z, int metadata) {
      return world.canBlockSeeTheSky(x, y + 1, z) && super.canOccurAt(world, x, y, z, metadata);
   }

   public boolean isLegalAt(World world, int x, int y, int z, int metadata) {
      if (!super.isLegalAt(world, x, y, z, metadata)) {
         return false;
      } else if (world.getBlock(x, y - 1, z) == this) {
         --y;
         int height = 1;

         while(true) {
            --y;
            if (world.getBlock(x, y, z) != this) {
               return height < 15;
            }

            ++height;
         }
      } else {
         return true;
      }
   }

   public boolean isLegalOn(int metadata, Block block_below, int block_below_metadata) {
      if (block_below == this) {
         return true;
      } else {
         return block_below == grass || block_below == dirt || block_below == sand;
      }
   }

   public boolean canBePlacedOnBlock(int metadata, Block block_below, int block_below_metadata, double block_below_bounds_max_y) {
      return block_below == this || super.canBePlacedOnBlock(metadata, block_below, block_below_metadata, block_below_bounds_max_y);
   }

   public boolean canBeCarried() {
      return false;
   }

   public int dropBlockAsEntityItem(BlockBreakInfo info) {
      if (info.wasNotLegal() || info.wasExploded()) {
         info.world.destroyBlock(info, false, true);
      }

      return !info.wasExploded() && !info.wasCrushed() ? this.dropBlockAsEntityItem(info, Items.bamboo) : 0;
   }

   public int getRenderType() {
      return 1;
   }

   public int d(World par1World, int par2, int par3, int par4) {
      return Items.bamboo.itemID;
   }

   public boolean isSolid(boolean[] is_solid, int metadata) {
      return false;
   }

   public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
      return false;
   }

   public boolean blocksFluids(boolean[] blocks_fluids, int metadata) {
      return true;
   }
}
