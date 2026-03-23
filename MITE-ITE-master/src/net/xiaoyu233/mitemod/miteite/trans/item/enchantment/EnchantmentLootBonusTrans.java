package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhancedPickaxe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentLootBonus.class)
public class EnchantmentLootBonusTrans extends Enchantment {
   protected EnchantmentLootBonusTrans(int id, yq rarity, int difficulty) {
      super(id, rarity, difficulty);
   }

   @Inject(method = "canEnchantItem(Lnet/minecraft/Item;)Z", at = @At("RETURN"), cancellable = true)
   private void extendsTools(Item item, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      if (item.getClass() == ItemShears.class && this == fortune) {
         callbackInfoReturnable.setReturnValue(true);
         callbackInfoReturnable.cancel();
      }
   }

   @Shadow
   public boolean canEnchantItem(Item item) {
      return false;
   }

   @Shadow
   public String getNameSuffix() {
      return null;
   }

   public int getNumLevelsForVibranium() {
      return 5;
   }

   @Shadow
   public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
      return false;
   }
}
