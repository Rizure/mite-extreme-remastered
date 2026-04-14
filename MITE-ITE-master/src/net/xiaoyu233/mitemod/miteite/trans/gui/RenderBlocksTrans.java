package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(bfr.class)
public class RenderBlocksTrans {
   @Shadow
   public IBlockAccess a;

   @Shadow
   public void a(Block par1Block, int par2, double par3, double par5, double par7, float par9) {
   }

   @Shadow
   public boolean b(Block par1Block, int par2, int par3, int par4) {
      return false;
   }

   @Inject(method = "a(Lnet/minecraft/BlockFlowerPot;III)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/IBlockAccess;getBlockId(III)I", shift = At.Shift.AFTER), cancellable = true)
   private void addSaplingPot(BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4, CallbackInfoReturnable<Boolean> cir) {
      boolean isSaplingPot = false;
      bfq var5 = bfq.a;
      if (this.a.getBlockId(par2, par3, par4) == Blocks.flowerPotSapling.blockID) {
         float var14 = 0.0F;
         float var15 = 4.0F;
         float var16 = 0.0F;
         var5.c(var14 / 16.0F, var15 / 16.0F, var16 / 16.0F);
         this.b(Blocks.sapling1, par2, par3, par4);
         var5.c(-var14 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
         isSaplingPot = true;
      }
      if(isSaplingPot){
         cir.cancel();
      }
   }
}
