package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockBamboo;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BiomeJungle.class)
public class BiomeJungleTrans extends BiomeBase {
   protected BiomeJungleTrans(int par1) {
      super(par1);
   }
   @Inject(method = "decorate", at = @At("RETURN"))
   private void generateNaturalBamboo(World par1World, Random par2Random, int par3, int par4, CallbackInfo callbackInfo){
      for (int var6 = 0; var6 < 50; ++var6) {
         int var7 = par3 + par2Random.nextInt(16) + 8;
         byte var8 = (byte) (50 + var6);
         int var9 = par4 + par2Random.nextInt(16) + 8;
         int var10 = par1World.getBlockId(var7, var8, var9);
         if (var10 == Block.grass.blockID || var10 == Block.sand.blockID) {
            for(int var11 = 1; var11 < 4 + par2Random.nextInt(5); var11++){
               if(par1World.getBlockId(var7, var8 + var11, var9) == 0){
                  par1World.setBlock(var7, var8 + var11, var9, Blocks.blockBamboo.blockID);
               }else {
                  break;
               }
            }
         }
      }
   }
}
