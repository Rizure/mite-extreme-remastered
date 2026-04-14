package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public class BiomeDecoratorTrans {
   private static final int ADAMANTIUM_FREQUENCY_OVERWORLD = Configs.wenscConfig.adamantiumFrequencyOverworld.ConfigValue;
   private static final int ADAMANTIUM_FREQUENCY_UNDERWORLD = Configs.wenscConfig.adamantiumFrequencyUnderworld.ConfigValue;
   private static final int COPPER_FREQUENCY_OVERWORLD = Configs.wenscConfig.copperFrequencyOverworld.ConfigValue;
   private static final int COPPER_FREQUENCY_UNDERWORLD = Configs.wenscConfig.copperFrequencyUnderworld.ConfigValue;
   private static final int DIAMOND_FREQUENCY_OVERWORLD = Configs.wenscConfig.diamondFrequencyOverworld.ConfigValue;
   private static final int DIAMOND_FREQUENCY_UNDERWORLD = Configs.wenscConfig.diamondFrequencyUnderworld.ConfigValue;
   private static final int FANCY_RED_FREQUENCY_UNDERWORLD = Configs.wenscConfig.fancyRedFrequencyUnderworld.ConfigValue;
   private static final int GOLD_FREQUENCY_OVERWORLD = Configs.wenscConfig.goldFrequencyOverworld.ConfigValue;
   private static final int GOLD_FREQUENCY_UNDERWORLD = Configs.wenscConfig.goldFrequencyUnderworld.ConfigValue;
   private static final int IRON_FREQUENCY_OVERWORLD = Configs.wenscConfig.ironFrequencyOverworld.ConfigValue;
   private static final int IRON_FREQUENCY_UNDERWORLD = Configs.wenscConfig.ironFrequencyUnderworld.ConfigValue;
   private static final int LAPIS_FREQUENCY_OVERWORLD = Configs.wenscConfig.lapisFrequencyOverworld.ConfigValue;
   private static final int LAPIS_FREQUENCY_UNDERWORLD = Configs.wenscConfig.lapisFrequencyUnderworld.ConfigValue;
   private static final int MITHRIL_FREQUENCY_OVERWORLD = Configs.wenscConfig.mithrilFrequencyOverworld.ConfigValue;
   private static final int MITHRIL_FREQUENCY_UNDERWORLD = Configs.wenscConfig.mithrilFrequencyUnderworld.ConfigValue;
   private static final int SILVER_FREQUENCY_OVERWORLD = Configs.wenscConfig.silverFrequencyOverworld.ConfigValue;
   private static final int SILVER_FREQUENCY_UNDERWORLD = Configs.wenscConfig.silverFrequencyUnderworld.ConfigValue;
   private static final int REDSTONE_FREQUENCY_OVERWORLD = Configs.wenscConfig.redstoneFrequencyOverworld.ConfigValue;
   private static final int REDSTONE_FREQUENCY_UNDERWORLD = Configs.wenscConfig.redstoneFrequencyUnderworld.ConfigValue;
   private static final int COAL_FREQUENCY_OVERWORLD = Configs.wenscConfig.coalFrequencyOverworld.ConfigValue;
   private static final boolean veinSizeIncreasesWithDepth = Configs.wenscConfig.veinSizeIncreasesWithDepth.ConfigValue;
   @Shadow
   protected WorldGenMinable adamantiteGen;
   @Shadow
   protected int chunk_X;
   @Shadow
   protected int chunk_Z;
   @Shadow
   protected WorldGenMinable coalGen;
   @Shadow
   protected WorldGenMinable copperGen;
   @Shadow
   protected World currentWorld;
   @Shadow
   protected WorldGenMinable diamondGen;

   protected WorldGenMinable fancyRedGen = new WorldGenMinable(Blocks.fancyRed.blockID, 3);
   @Shadow
   protected WorldGenMinable dirtGen;
   @Shadow
   protected WorldGenMinable goldGen;
   @Shadow
   protected WorldGenMinable gravelGen;
   @Shadow
   protected WorldGenMinable ironGen;
   @Shadow
   protected WorldGenMinable lapisGen;
   @Shadow
   protected WorldGenMinable mithrilGen;
   @Shadow
   protected Random randomGenerator;
   @Shadow
   protected WorldGenMinable redstoneGen;
   @Shadow
   protected WorldGenMinable silverGen;
   @Shadow
   protected WorldGenMinable silverfishGen;

   @Overwrite
   public void decorate(World par1World, Random par2Random, int par3, int par4) {
//      lock.lock();
      try {
         if (this.currentWorld == null) {
            this.currentWorld = par1World;
            this.randomGenerator = par2Random;
            this.randomGenerator.setSeed((long) (par3 + par4 * 65536) + par1World.getSeed() * 4294967296L);
            this.chunk_X = par3;
            this.chunk_Z = par4;
            this.decorate();
            this.currentWorld = null;
            this.randomGenerator = null;
//            lock.unlock();
         }
      } finally {
//           System.out.println(par3+","+par4);
      }
//      if(!lock.isLocked()){
//         if (this.currentWorld != null) {
//            throw new RuntimeException("Already decorating!!");
//         } else {
//            this.currentWorld = par1World;
//            this.randomGenerator = par2Random;
//            this.randomGenerator.setSeed((long)(par3 + par4 * 65536) + par1World.getSeed() * 4294967296L);
//            this.chunk_X = par3;
//            this.chunk_Z = par4;
//            this.decorate();
//            this.currentWorld = null;
//            this.randomGenerator = null;
//         }
//      }
   }

   @Shadow
   protected void decorate() {
   }

   public BiomeDecoratorTrans(BiomeBase par1BiomeGenBase) {
   }

   @Shadow
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable) {
   }

   @Overwrite
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable, boolean vein_size_increases_with_depth) {
      int resource_multiplier = 1;
      frequency *= resource_multiplier;
//      if (this.currentWorld.underworld_y_offset != 0 && world_gen_minable != this.gravelGen) {
//         if (world_gen_minable == this.adamantiteGen) {
//            frequency *= 3;
//         } else {
//            frequency *= 4;
//         }
//      }
      while (frequency-- > 0) {
         if (this.randomGenerator.nextInt(10) == 0) {
            int x = this.chunk_X + this.randomGenerator.nextInt(16);
            int y = world_gen_minable.getRandomVeinHeight(this.currentWorld, this.randomGenerator);
            int z = this.chunk_Z + this.randomGenerator.nextInt(16);
            if (y >= 0) {
               world_gen_minable.generate(this.currentWorld, this.randomGenerator, x, y, z, vein_size_increases_with_depth);
            }
         }
      }
   }

   protected void generateOres() {
      if (this.currentWorld.isOverworld()) {
         this.genMinable(200, this.dirtGen);
         this.genMinable(200, this.gravelGen);
         this.genMinable(COAL_FREQUENCY_OVERWORLD, this.coalGen);
         this.genMinable(COPPER_FREQUENCY_OVERWORLD, this.copperGen, veinSizeIncreasesWithDepth);
         this.genMinable(SILVER_FREQUENCY_OVERWORLD, this.silverGen, veinSizeIncreasesWithDepth);
         this.genMinable(GOLD_FREQUENCY_OVERWORLD, this.goldGen, veinSizeIncreasesWithDepth);
         this.genMinable(IRON_FREQUENCY_OVERWORLD, this.ironGen, veinSizeIncreasesWithDepth);
         this.genMinable(MITHRIL_FREQUENCY_OVERWORLD, this.mithrilGen, veinSizeIncreasesWithDepth);
         this.genMinable(5, this.silverfishGen, veinSizeIncreasesWithDepth);
         this.genMinable(REDSTONE_FREQUENCY_OVERWORLD, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_OVERWORLD, this.diamondGen);
         this.genMinable(LAPIS_FREQUENCY_OVERWORLD, this.lapisGen);
         if (Configs.wenscConfig.overworldAdamantiteOre.ConfigValue) {
            this.genMinable(ADAMANTIUM_FREQUENCY_OVERWORLD, this.adamantiteGen, false);
         }
      } else if (this.currentWorld.isUnderworld()) {
         this.genMinable(300, this.gravelGen);
         this.genMinable(COPPER_FREQUENCY_UNDERWORLD, this.copperGen, veinSizeIncreasesWithDepth);
         this.genMinable(SILVER_FREQUENCY_UNDERWORLD, this.silverGen, veinSizeIncreasesWithDepth);
         this.genMinable(GOLD_FREQUENCY_UNDERWORLD, this.goldGen, veinSizeIncreasesWithDepth);
         this.genMinable(IRON_FREQUENCY_UNDERWORLD, this.ironGen, veinSizeIncreasesWithDepth);
         this.genMinable(MITHRIL_FREQUENCY_UNDERWORLD, this.mithrilGen, veinSizeIncreasesWithDepth);
         this.genMinable(ADAMANTIUM_FREQUENCY_UNDERWORLD, this.adamantiteGen, veinSizeIncreasesWithDepth);
         this.genMinable(REDSTONE_FREQUENCY_UNDERWORLD, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_UNDERWORLD, this.diamondGen);
         this.genMinable(FANCY_RED_FREQUENCY_UNDERWORLD, this.fancyRedGen);
         this.genMinable(LAPIS_FREQUENCY_UNDERWORLD, this.lapisGen);
         if (this.currentWorld.underworld_y_offset != 0) {
            this.genMinable(50, this.silverfishGen);
         }
      } else if (!this.currentWorld.isTheEnd()) {
         Minecraft.setErrorMessage("generateOres: don't know how to handle world " + this.currentWorld);
      }

   }

}
