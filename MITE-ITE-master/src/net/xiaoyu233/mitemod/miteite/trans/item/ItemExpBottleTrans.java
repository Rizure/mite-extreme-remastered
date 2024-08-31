package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.ItemExpBottle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemExpBottle.class)
public class ItemExpBottleTrans {
    @ModifyConstant(method = {
            "addInformation",
    }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 2))
    private static int injected(int value) {
        return 5;
    }
}
