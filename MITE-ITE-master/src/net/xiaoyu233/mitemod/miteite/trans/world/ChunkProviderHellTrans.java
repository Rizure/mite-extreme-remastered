package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(ChunkProviderHell.class)
public class ChunkProviderHellTrans {
   @Shadow
   private Random hellRNG;
   @Shadow
   private World worldObj;

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "populate", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldData;getEarliestMITEReleaseRunIn()I", shift = At.Shift.BEFORE, ordinal = 0))
   private void generateNetherAdamantium(IChunkProvider par1IChunkProvider, int par2, int par3, CallbackInfo callbackInfo, int var4, int var5, int var14){
      if (Configs.wenscConfig.netherAdamantiumMaxCountPerChunk.ConfigValue > 0 && Configs.wenscConfig.netherAdamantiumMaxCountPerVein.ConfigValue > 0) {
         WorldGenMinable var12 = (new WorldGenMinable(Blocks.netherAdamantiumOre.blockID, Configs.wenscConfig.netherAdamantiumMaxCountPerVein.ConfigValue, Block.netherrack.blockID)).setMinableBlockMetadata(0);
         int count = this.hellRNG.nextInt(Configs.wenscConfig.netherAdamantiumMaxCountPerChunk.ConfigValue) + 1;
         for (int var6 = 0; var6 < count; ++var6) {
            int var7 = var4 + this.hellRNG.nextInt(16);
            int var8 = this.hellRNG.nextInt(80) + 35;
            int var9 = var5 + this.hellRNG.nextInt(16);
            var12.generate(this.worldObj, this.hellRNG, var7, var8, var9);
         }
      }
   }
}
