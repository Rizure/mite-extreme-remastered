package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.EnchantmentDigging;
import net.minecraft.Item;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhancedPickaxe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentDigging.class)
public class EnchantmentDiggingTrans {
    @Inject(method = "canEnchantItem(Lnet/minecraft/Item;)Z", at = @At("RETURN"), cancellable = true)
    private void extendsTools(Item item, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(item.getClass() == ItemEnhancedPickaxe.class){
            callbackInfoReturnable.setReturnValue(true);
            callbackInfoReturnable.cancel();
        }
    }
    
}
