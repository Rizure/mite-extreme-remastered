package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityCaveSpider.class)
public class EntityCaveSpiderTrans extends EntityArachnid {
   public EntityCaveSpiderTrans(World par1World, float scaling) {
      super(par1World, scaling);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.maxHealth, 12 * Constant.getNormalMobModifier("Health", day));
      this.setEntityAttribute(GenericAttributes.attackDamage, 5 * Constant.getNormalMobModifier("Damage", day));
   }

   @Overwrite
   public EntityDamageResult attackEntityAsMob(Entity target) {
      EntityDamageResult result = super.attackEntityAsMob(target);
      if (result != null && !result.entityWasDestroyed()) {
         if (target instanceof EntityLiving) {
            target.getAsEntityLivingBase().addPotionEffect(new MobEffect(MobEffectList.poison.id, 480, 1));
         }

         return result;
      } else {
         return result;
      }
   }
}
