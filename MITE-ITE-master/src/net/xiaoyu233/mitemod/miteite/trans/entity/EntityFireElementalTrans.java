package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityFireElemental.class)
public class EntityFireElementalTrans extends EntityMonster {
    public EntityFireElementalTrans(World par1World) {
        super(par1World);
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.worldObj.getDayOfOverworld();
        this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.25D * Constant.getNormalMobModifier("Speed",day));
        this.setEntityAttribute(GenericAttributes.attackDamage, 6 * Constant.getNormalMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.maxHealth, 20 * Constant.getNormalMobModifier("Health",day));
        if (this.worldObj.getProvider().isHellWorld){
            this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + 20D);
        }
    }
    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player) {
            this.dropItem(Items.powder_blaze);
        }
    }
}
