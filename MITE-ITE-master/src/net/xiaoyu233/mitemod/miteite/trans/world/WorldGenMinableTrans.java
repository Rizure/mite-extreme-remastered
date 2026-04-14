package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenMinable.class)
public class WorldGenMinableTrans {
   @Shadow
   private int minableBlockId;

   @Overwrite
   public int getRandomVeinHeight(World world, Random rand) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (world.underworld_y_offset != 0) {
            if (block == Block.oreAdamantium) {
               return this.getMinVeinHeight(world) + rand.nextInt(this.getMaxVeinHeight(world) - this.getMinVeinHeight(world));
            }

            if (block instanceof BlockOre && rand.nextFloat() < 0.75F) {
               return this.getMinVeinHeight(world) + rand.nextInt(this.getMaxVeinHeight(world) - this.getMinVeinHeight(world));
            }
         }

         return rand.nextInt(256);
      } else {
         float relative_height;
         if (block == Block.dirt) {
            do {
               relative_height = rand.nextFloat() * rand.nextFloat();
            } while (relative_height <= rand.nextFloat());
         } else if (block == Block.gravel) {
            do {
               relative_height = rand.nextFloat();
            } while (relative_height <= rand.nextFloat());
         } else if (block == Block.silverfish) {
            do {
               relative_height = rand.nextFloat();
            } while (relative_height >= rand.nextFloat());
         } else if (block == Block.oreRedstone) {
            do {
               relative_height = rand.nextFloat();
            } while (relative_height >= rand.nextFloat());
         } else if (block instanceof BlockOre) {
            do {
               relative_height = rand.nextFloat();
            } while (relative_height <= rand.nextFloat());
            if (block == Block.oreCopper && rand.nextBoolean()) {
               relative_height = rand.nextFloat() * 0.6F + 0.4F;
            }
         } else {
            Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + this.minableBlockId);
            return -1;
         }

         int min_height = this.getMinVeinHeight(world);
         int height_range = this.getMaxVeinHeight(world) - min_height + 1;
         return min_height + (int) (relative_height * (float) height_range);
      }
   }
//   @Shadow
//   public int getMinVeinHeight(World world) {
//      return 0;
//   };

   @Overwrite
   public int getMinVeinHeight(World world) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         return Configs.wenscConfig.underworldMantleBlockOffset.ConfigValue + 30;
      } else if (block == Block.dirt) {
         return 32;
      } else if (block == Block.gravel) {
         return 24;
      } else if (block == Block.oreCoal) {
         return 16;
      } else if (block == Block.oreCopper) {
         return 24;
      } else if (block == Block.oreSilver) {
         return 24;
      } else if (block == Block.oreGold) {
         return 0;
      } else if (block == Block.oreIron) {
         return 16;
      } else if (block == Block.oreMithril) {
         return 0;
      } else if (block == Block.oreAdamantium || block == Block.silverfish) {
         return 0;
      } else if (block == Block.oreRedstone) {
         return 0;
      } else if (block == Block.oreDiamond) {
         return 0;
      } else if (block == Blocks.fancyRed) {
         return 0;
      } else if (block == Block.oreLapis) {
         return 8;
      } else if (world.isTheNether()) {
         return 35;
      } else {
         Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.blockID);
         return -1;
      }
   }

   @Overwrite
   public int getMaxVeinHeight(World world) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (block == Block.oreCopper) {
            return 128;
         } else if (block == Block.oreSilver) {
            return 128;
         } else if (block == Block.oreGold) {
            return 128;
         } else if (block == Block.oreIron) {
            return 128;
         }
         return 224;
      } else if (world.isTheNether()) {
         return 115;
      } else if (block == Block.dirt) {
         return 128;
      } else if (block == Block.gravel) {
         return 128;
      } else if (block == Block.oreCoal) {
         return 96;
      } else if (block == Block.oreCopper) {
         return 128;
      } else if (block == Block.oreSilver) {
         return 96;
      } else if (block == Block.oreGold) {
         return 48;
      } else if (block == Block.oreIron) {
         return 64;
      } else if (block == Block.oreMithril) {
         return 32;
      } else if (block == Block.oreAdamantium) {
         return 16;
      } else if (block == Block.silverfish) {
         return 24;
      } else if (block == Block.oreRedstone) {
         return 24;
      } else if (block == Block.oreDiamond) {
         return 32;
      } else if (block == Block.oreLapis) {
         return 40;
      } else {
         Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.blockID);
         return -1;
      }
   }
}
