package net.xiaoyu233.mitemod.miteite.trans.render;

import com.google.common.collect.Maps;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.render.texture.RenderEnderCompass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(bik.class)
public class IconRendererTrans {
    @Shadow
    @Final
    private Map e = Maps.newHashMap();

    @Inject(method = "a(Ljava/lang/String;)Lnet/minecraft/IIcon;", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BEFORE), cancellable = true)
    private void injectRenderEnderCompass(String par1Str, CallbackInfoReturnable<IIcon> callbackInfoReturnable) {
        if(par1Str.equals("end_compass")){
            Object var2 = new RenderEnderCompass(par1Str);
            this.e.put(par1Str, var2);
            callbackInfoReturnable.setReturnValue((IIcon)var2);
            callbackInfoReturnable.cancel();
        }
    }
}
