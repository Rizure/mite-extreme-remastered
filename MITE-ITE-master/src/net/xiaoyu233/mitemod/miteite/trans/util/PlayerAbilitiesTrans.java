package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerAbilities.class)
public class PlayerAbilitiesTrans {
    @Shadow
    public EntityPlayer player;
    @Inject(method = "getWalkSpeed()F",at = @At(value = "RETURN"), cancellable = true)
    private void modifierBoost(CallbackInfoReturnable<Float> callbackInfoReturnable){
        int hour_of_day = this.player.worldObj.getHourOfDay();
        float modifier_factor = 1.0F;
        if(this.player.worldObj.isOverworld()){
            if(hour_of_day < 18 && hour_of_day > 6){
                ItemStack boots = this.player.getBoots();
                if (boots != null){
                    modifier_factor += ArmorModifierTypes.SUN_AFFINITY.getModifierValue(boots.getTagCompound());
                }
            }else {
                ItemStack helmet = this.player.getHelmet();
                if (helmet != null){
                    modifier_factor += ArmorModifierTypes.NIGHT_AFFINITY.getModifierValue(helmet.getTagCompound());
                }
            }
        }
        callbackInfoReturnable.setReturnValue(modifier_factor * callbackInfoReturnable.getReturnValue());
    }
}
