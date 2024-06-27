package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerAbilities.class)
public class PlayerAbilitiesTrans {
    @Shadow
    private float walkSpeed = 0.1F;
    @Shadow
    public EntityPlayer player;
    @Overwrite
    public float getWalkSpeed() {
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
        float speed_boost_or_slow_down_factor = this.player.getSpeedBoostOrSlowDownFactor() * modifier_factor;
        return (this.player.hasFoodEnergy() ? this.walkSpeed : this.walkSpeed * 0.75F) * EnchantmentManager.getSpeedModifier(this.player) * speed_boost_or_slow_down_factor;
    }
}
