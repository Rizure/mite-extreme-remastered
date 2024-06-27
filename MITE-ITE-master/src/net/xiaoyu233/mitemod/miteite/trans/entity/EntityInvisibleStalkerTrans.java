package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityInvisibleStalker.class)
public class EntityInvisibleStalkerTrans extends EntityMonster {

   public EntityInvisibleStalkerTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(GenericAttributes.followRange).setAttribute(40.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 30 * Constant.getNormalMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23D * Constant.getNormalMobModifier("Speed",day));
   }
   @Override
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (this.rand.nextFloat() < (recently_hit_by_player ? 1.0F : 0.05F)) {
         this.dropItem(Items.colorBag);
      }
   }
}
