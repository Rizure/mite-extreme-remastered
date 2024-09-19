package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.GenericAttributes;
import net.minecraft.Material;
import net.minecraft.MobEffectAttackDamage;
import net.minecraft.MobEffectList;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEffectList.class)
public class MobEffectListTrans {
    @Shadow
    @Final
    public static MobEffectList weakness;
    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void injectClinit(CallbackInfo callback){
        weakness.func_111184_a(GenericAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", -0.2, 1);
    }
}
