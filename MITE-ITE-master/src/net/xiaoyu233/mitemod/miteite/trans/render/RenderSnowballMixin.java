package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.Entity;
import net.minecraft.EntityGelatinousSphere;
import net.minecraft.Item;
import net.minecraft.bgx;
import net.xiaoyu233.mitemod.miteite.entity.EntityGrenade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(bgx.class)
public class RenderSnowballMixin {
    @Shadow
    private Item a;
    @Inject(method = "a(Lnet/minecraft/Entity;DDDFF)V",at = @At(value = "INVOKE", target = "getIconFromSubtype", shift = At.Shift.BEFORE))
    public void injectRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9, CallbackInfo callbackInfo){
        if (par1Entity instanceof EntityGrenade) {
            this.a = ((EntityGrenade)par1Entity).getModelItem();
        }
    }
}
