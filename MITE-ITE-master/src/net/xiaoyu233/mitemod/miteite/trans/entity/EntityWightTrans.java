package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityWight.class)
public class EntityWightTrans extends EntityMonster {
    public EntityWightTrans(World par1World) {
        super(par1World);
    }
    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
        this.setEntityAttribute(GenericAttributes.maxHealth, 30 * Constant.getNormalMobModifier("Health",day));
        this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.25D * Constant.getNormalMobModifier("Speed",day));
        this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(9.0 * Constant.getNormalMobModifier("Damage",day));
        this.getEntityAttribute(GenericAttributes.followRange).setAttribute(64.0);
    }
    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (this.rand.nextFloat() < (recently_hit_by_player ? 1.0F : 0.05F)) {
            this.dropItem(Items.demonPillRaw);
        }
    }
}
