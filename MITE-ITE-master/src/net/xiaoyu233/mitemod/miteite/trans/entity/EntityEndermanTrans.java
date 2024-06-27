package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMonster {

   public EntityEndermanTrans(World par1World) {
      super(par1World);
   }

   @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 256))
   private static int injected(int value) {
      return 1024;
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.worldObj.getDayOfOverworld();
      this.setEntityAttribute(GenericAttributes.attackDamage, 18 * net.xiaoyu233.mitemod.miteite.util.Constant.getEliteMobModifier("Damage",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 60 * net.xiaoyu233.mitemod.miteite.util.Constant.getEliteMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * net.xiaoyu233.mitemod.miteite.util.Constant.getEliteMobModifier("Speed",day));
   }

   @Overwrite
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player){
         this.dropItem(Items.voucherPhase);
      }
      int item_id = this.getDropItemId();
      if (item_id > 0) {
         int num_drops = this.rand.nextInt(2 + damage_source.getLootingModifier());

         for(int i = 0; i < num_drops; ++i) {
            this.dropItem(item_id, 1);
         }
      }
   }

   public EntityDamageResult attackEntityAsMob(Entity target) {
      this.worldObj.setEntityState(this, EnumEntityState.golem_throw);
      EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), (float)this.getEntityAttributeValue(GenericAttributes.attackDamage)));
      if (result != null && result.entityWasKnockedBack()) {
         target.motionY += 0.2D;
      }

      return result;
   }

}
