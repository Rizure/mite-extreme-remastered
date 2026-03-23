package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Item;
import net.minecraft.ItemIngot;
import net.minecraft.ItemSword;
import net.minecraft.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemSword.class)
public class ItemSwordTrans extends Item {
   @Inject(method = "<init>", at = @At("RETURN"))
   public void injectSetCraftingDifficulty(CallbackInfo callbackInfo) {
      if (this.getHardestMetalMaterial() != null) {
         Material metal = this.getHardestMetalMaterial();
         this.setCraftingDifficultyAsComponent(ItemIngot.getCraftingDifficultyAsComponent(metal) * 2.0F);
      } else {
         this.setCraftingDifficultyAsComponent(400.0F);
      }
   }
}
