package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(World.class)
public abstract class WorldTrans {

   @Shadow
   public static int getDayOfWorld(long unadjusted_tick) {
      return 0;
   }

   public int getDayOfOverworld() {
      return getDayOfWorld(this.worldInfo.getWorldTotalTime(0));
   }

   @Shadow
   public WorldData worldInfo;

   @ModifyConstant(method = "generateWeatherEvents(I)Ljava/util/List;", constant = @Constant(longValue = 6000L, ordinal = 3))
   private long earlierStormInBloodMoon(long time){
      return 5000L;
   }

   @ModifyConstant(method = "generateWeatherEvents(I)Ljava/util/List;", constant = @Constant(intValue = 13000))
   private int longerStormInBloodMoon(int duration){
      return 14000;
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getBlockId", at = @At(value = "FIELD", target = "Lnet/minecraft/Chunk;storageArrays:[Lnet/minecraft/ChunkSection;", shift = At.Shift.AFTER), cancellable = true)
   private void injectGetBlockId(int par1, int par2, int par3, CallbackInfoReturnable<Integer> cir, Chunk var4) {
      ChunkSection extended_block_storage = var4.storageArrays[par2 >> 4];
      if (extended_block_storage == null) {
         cir.setReturnValue(0);
         cir.cancel();
      } else {
         int par1_and_15 = par1 & 15;
         int par2_and_15 = par2 & 15;
         int par3_and_15 = par3 & 15;
         cir.setReturnValue(extended_block_storage.getExtBlockID(par1_and_15, par2_and_15, par3_and_15));
         cir.cancel();
      }
   }

}
