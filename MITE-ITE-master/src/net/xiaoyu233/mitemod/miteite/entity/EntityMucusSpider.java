package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityMucusSpider extends EntitySpider {

    int num_webs;

    public EntityMucusSpider(World par1World) {
        super(par1World);
        this.num_webs = 0;
    }

    protected void applyEntityAttributes() {
        int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.followRange, 48.0);
        this.setEntityAttribute(GenericAttributes.attackDamage, 6 * Constant.getNormalMobModifier("Damage", day));
        this.setEntityAttribute(GenericAttributes.maxHealth, 20 * Constant.getNormalMobModifier("Health", day));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.75);
    }

    public EntityDamageResult attackEntityAsMob(Entity target) {
        EntityDamageResult result = super.attackEntityAsMob(target);
        if (result != null && !result.entityWasDestroyed()) {
            if (!target.isRiding()) {
                target.mountEntity(this);
            }
            if (result.entityLostHealth() && target instanceof EntityLiving) {
                target.getAsEntityLivingBase().addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, 25, 5));
            }
            return result;
        } else {
            return result;
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        Entity player = damageSource.getResponsibleEntity();
        if (player instanceof EntityPlayer) {
            this.dropItem(Item.slimeBall);
        }
    }
}
