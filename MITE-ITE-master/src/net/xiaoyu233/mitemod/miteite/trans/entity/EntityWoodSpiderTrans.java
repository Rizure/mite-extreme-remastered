package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityWoodSpider.class)
public class EntityWoodSpiderTrans extends EntityArachnid {
   public EntityWoodSpiderTrans(World par1World, float scaling) {
      super(par1World, scaling);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.maxHealth, 6 * Constant.getNormalMobModifier("Health", day));
      this.setEntityAttribute(GenericAttributes.attackDamage, 3 * Constant.getNormalMobModifier("Damage", day));
   }
}
