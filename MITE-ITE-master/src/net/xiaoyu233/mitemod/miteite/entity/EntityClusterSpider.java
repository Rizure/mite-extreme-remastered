package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityClusterSpider extends EntityArachnid{
    int num_webs;
    public EntityClusterSpider(World par1World) {
        super(par1World, 0.4F);
        this.num_webs = 4;
    }
    protected float getSoundVolume(String sound) {
        return super.getSoundVolume(sound) * 0.5F;
    }

    protected float getSoundPitch(String sound) {
        return super.getSoundPitch(sound) * 1.4F;
    }
    protected void applyEntityAttributes() {
        int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.followRange, 48.0);
        this.setEntityAttribute(GenericAttributes.attackDamage, 5 * Constant.getNormalMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.maxHealth, 10 * Constant.getNormalMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.75);
    }
    public EntityDamageResult attackEntityAsMob(Entity target) {
        EntityDamageResult result = super.attackEntityAsMob(target);
        if (result != null && !result.entityWasDestroyed()) {
            if (result.entityLostHealth() && target instanceof EntityLiving) {
                target.getAsEntityLivingBase().addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, 450, 1));
            }

            return result;
        } else {
            return result;
        }
    }
}
