package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.*;
import net.xiaoyu233.mitemod.miteite.trans.entity.EntityGiantZombieTrans;
import net.xiaoyu233.mitemod.miteite.world.WorldGenBigTreeWithIDAndMeta;
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
   @Final
   public int biomeID;
   @Shadow
   @Final
   private boolean enableRain;
   @Shadow
   public int field_76754_C;
   @Shadow
   public byte fillerBlock;
   @Shadow
   public float maxHeight;
   @Shadow
   public float minHeight;
   @Shadow
   public float rainfall;
   @Shadow
   public float temperature;
   @Shadow
   public BiomeDecorator theBiomeDecorator;
   @Shadow
   public byte topBlock;
   @Shadow
   public int waterColorMultiplier;
   @Shadow
   protected List spawnableCaveCreatureList;
   @Shadow
   protected List spawnableCreatureList;
   @Shadow
   protected List spawnableMonsterList;
   @Shadow
   protected List spawnableWaterCreatureList;
   @Shadow
   protected WorldGenBigTree worldGeneratorBigTree;
   @Shadow
   protected WorldGenForest worldGeneratorForest;

   protected WorldGenTreesWithTreeId worldGeneratorTreesWithTreeId;

   @Shadow
   protected WorldGenSwampTree worldGeneratorSwamp;
   @Shadow
   protected WorldGenTrees worldGeneratorTrees;

   protected BiomeBaseTrans(int par1) {

   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.spawnableMonsterList.add(new BiomeMeta(EntityGiantZombie.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityGhast.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieLord.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityOreElemental.class, 1, 1, 1));
//      this.spawnableMonsterList.add(new BiomeMeta(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWanderingWitch.class, 1, 4, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieDoor.class, 2, 4, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityExchanger.class, 2,2, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityMirrorSkeleton.class, 5,2, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityDragger.class, 2,2, 4));
   }

   @Overwrite
   public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
      int percent = par1Random.nextInt(10);
      if(par1Random.nextInt(10) == 0) {
         switch (percent) {
            case 0:
               return new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood1.blockID, 0, Blocks.leaves1.blockID, 0);
            case 1:
               return new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood1.blockID, 1, Blocks.leaves1.blockID, 1);
            default:
               return new WorldGenBigTreeWithIDAndMeta(false, Blocks.wood.blockID, 0, Blocks.leaves.blockID, 0);
         }
      }
      switch (percent) {
         case 0:
            return new WorldGenTreesWithTreeId(false, 4, Blocks.wood1, 0, Blocks.leaves1, 0, false);
         case 1:
            return new WorldGenTreesWithTreeId(false, 4, Blocks.wood1, 1, Blocks.leaves1, 1, false);
         default:
            return new WorldGenTreesWithTreeId(false, 4, Blocks.wood, 0, Blocks.leaves, 0, false);
      }
   }

   @Shadow
   private BiomeDecorator createBiomeDecorator() {
      return null;
   }
}
