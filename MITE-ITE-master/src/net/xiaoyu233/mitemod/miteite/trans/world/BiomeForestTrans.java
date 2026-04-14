package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.world.WorldGenBigTreeWithIDAndMeta;
import net.xiaoyu233.mitemod.miteite.world.WorldGenCherry;
import net.xiaoyu233.mitemod.miteite.world.WorldGenTreesWithTreeId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(BiomeForest.class)
public class BiomeForestTrans extends BiomeBase {
   protected BiomeForestTrans(int par1) {
      super(par1);
   }

   @Overwrite
   public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
      WorldGenBigTreeWithIDAndMeta tree;
      int percent = par1Random.nextInt(10);
      if (par1Random.nextInt(10) == 0) {
         return this.worldGeneratorForest;
      }
      if (par1Random.nextInt(4) == 0) {
         switch (percent) {
            case 0:
               tree = new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood1.blockID, 0, Blocks.leaves1.blockID, 0);
               tree.setHeightLimit(18);
               return tree;
            case 1:
            case 2:
            case 3:
               return new WorldGenCherry(false,3,4,8);
            default:
               tree = new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood.blockID, 0, Blocks.leaves.blockID, 0);
               tree.setHeightLimit(20);
               return tree;
         }
      }
      switch (percent) {
         case 0:
            return new WorldGenTreesWithTreeId(false, 4, Blocks.wood1, 0, Blocks.leaves1, 0, false);
         case 1:
         case 2:
         case 3:
            return new WorldGenCherry(false,1 + par1Random.nextInt(3),2 + par1Random.nextInt(3),5 + par1Random.nextInt(3));
         default:
            return new WorldGenTreesWithTreeId(false, 4, Blocks.wood, 0, Blocks.leaves, 0, false);
      }
   }
}
