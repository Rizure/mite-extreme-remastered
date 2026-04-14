package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityArachnid.class)
public class EntityArachnidTrans extends EntityMonster {
   public EntityArachnidTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.setEntityAttribute(GenericAttributes.maxHealth, 10.0D);
      this.setEntityAttribute(GenericAttributes.followRange, 28.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 1.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 6.0D);
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      super.onDeath(damageSource);
      Entity player = damageSource.getResponsibleEntity();
      if (player instanceof EntityPlayer) {
         if (this.isPotionActive(MobEffectList.moveSpeed)) {
            this.dropItem(Items.powder_wind);
         }
      }
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "onUpdate", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "getEntityHit"), cancellable = true)
   public void fixNPEinThrowingWeb(CallbackInfo callbackInfo, Entity target, double distance, EntityLiving elb_target, Raycast raycast, RaycastCollision rc) {
      if (rc == null) {
         callbackInfo.cancel();
      }
   }
}
