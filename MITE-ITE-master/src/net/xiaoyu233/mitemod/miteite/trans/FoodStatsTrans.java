package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodMetaData.class)
public class FoodStatsTrans {
   @Shadow
   private int nutrition;
   @Shadow
   private float heal_progress;
   @Shadow
   private EntityPlayer player;

   @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/FoodMetaData;heal_progress:F", ordinal = 1))
   private float redirectHealFactor(FoodMetaData foodMetaData, ServerPlayer par1EntityPlayer){
      float heal_base = 4.0E-4F + (float) this.nutrition * 2.0E-5F;
      heal_base *= (1f + this.player.getGemSumNumeric(GemModifierTypes.recover) + this.player.getGemSumNumeric(GemModifierTypes.polish) * 25.0F * (1 - this.player.getHealthFraction()));
      return this.heal_progress + heal_base;
   }

}
