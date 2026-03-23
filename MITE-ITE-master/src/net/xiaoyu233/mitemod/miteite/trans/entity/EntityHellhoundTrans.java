package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityHellhound.class)
public abstract class EntityHellhoundTrans extends EntityWolf implements IMonster {
   public EntityHellhoundTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      super.applyEntityAttributes();
      this.setEntityAttribute(GenericAttributes.movementSpeed, (double) 0.4F);
      this.setEntityAttribute(GenericAttributes.maxHealth, (double) 30.0F * Constant.getNormalMobModifier("Health", day));
      this.setEntityAttribute(GenericAttributes.attackDamage, (double) 6.0F * Constant.getNormalMobModifier("Damage", day));
   }
}
