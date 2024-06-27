package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntitySpider.class)
public class EntitySpiderTrans extends EntityArachnid {

   public EntitySpiderTrans(World par1World, float scaling) {
      super(par1World, scaling);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.worldObj.getDayOfOverworld();
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 5 * Constant.getNormalMobModifier("Damage",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 15 * Constant.getNormalMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 1.0D * Constant.getNormalMobModifier("Speed",day));
   }
}
