package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EnumQuality;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnumQuality.class)
public class EnumQualityTrans {
   @Shadow
   @Final
   private float durability_modifier;

   @Overwrite
   public float getDurabilityModifier() {
      if(this.durability_modifier > 1.0F){
         this.durability_modifier -= 1.0F;
         this.durability_modifier /= 2.0F;
         this.durability_modifier += 1.0F;
         return this.durability_modifier;
      }
      return this.durability_modifier;
   }
}
