package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityThrownExpBottle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
@Mixin(EntityThrownExpBottle.class)
public class EntityThrownExpBottleTrans {
    @ModifyConstant(method = {
            "onImpact(Lnet/minecraft/RaycastCollision;)V",
    }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 2))
    private static int injected(int value) {
        return 5;
    }
}
