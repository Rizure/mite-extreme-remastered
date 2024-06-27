package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityDemonSpider.class)
public class EntityDemonSpiderTrans extends EntityArachnid {
    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.worldObj.getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.maxHealth, 25 * Constant.getNormalMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage",day));
    }

    public EntityDemonSpiderTrans(World par1World) {
        super(par1World, 1.0F);
        this.tasks.addTask(4, new EntityAIGetOutOfLava(this, 1.0F));
    }
}
