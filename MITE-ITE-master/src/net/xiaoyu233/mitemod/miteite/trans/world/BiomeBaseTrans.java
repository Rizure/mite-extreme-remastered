package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.*;
import net.xiaoyu233.mitemod.miteite.trans.entity.EntityGiantZombieTrans;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.world.WorldGenBigTreeWithIDAndMeta;
import net.xiaoyu233.mitemod.miteite.world.WorldGenCherry;
import net.xiaoyu233.mitemod.miteite.world.WorldGenTreesWithTreeId;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(BiomeBase.class)
public class BiomeBaseTrans {
   @Shadow
   protected List spawnableMonsterList;

   protected BiomeBaseTrans(int par1) {
   }

   @Inject(method = "<init>", at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo) {
      this.spawnableMonsterList.add(new BiomeMeta(EntityGiantZombie.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityGhast.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieLord.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityOreElemental.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWanderingWitch.class, 1, 4, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieDoor.class, 2, 4, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityExchanger.class, 2, 2, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityMirrorSkeleton.class, 5, 2, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityDragger.class, 2, 2, 4));
   }

   @Overwrite
   public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
      WorldGenBigTreeWithIDAndMeta tree;
      int percent = par1Random.nextInt(10);
      if (par1Random.nextInt(10) == 0) {
         switch (percent) {
            case 0:
               tree = new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood1.blockID, 0, Blocks.leaves1.blockID, 0);
               tree.setHeightLimit(18);
               return tree;
            case 1:
               tree = new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood1.blockID, 1, Blocks.leaves1.blockID, 1);
               tree.setHeightLimit(10);
               return tree;
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

   @Inject(method = "decorate", at = @At("RETURN"))
   private void generateCommonEmeraldOres(World par1World, Random par2Random, int par3, int par4, CallbackInfo callbackInfo){
      int var5 = Configs.wenscConfig.emeraldFrequencyBigHills.ConfigValue + par2Random.nextInt(3);
      for (int var6 = 0; var6 < var5; ++var6) {
         int var7 = par3 + par2Random.nextInt(16);
         int var8 = par2Random.nextInt(28) + 4;
         int var9 = par4 + par2Random.nextInt(16);
         int var10 = par1World.getBlockId(var7, var8, var9);
         if (var10 == Block.stone.blockID) {
            par1World.setBlock(var7, var8, var9, Block.oreEmerald.blockID, 0, 2);
         }
      }
   }
}
