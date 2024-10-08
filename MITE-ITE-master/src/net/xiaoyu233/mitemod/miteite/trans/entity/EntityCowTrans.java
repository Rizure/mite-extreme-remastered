package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityCow.class)
public abstract class EntityCowTrans extends EntityLivestockTrans {

   public EntityCowTrans(World world) {
      super(world);
   }

   @SoftOverride
   public int getBreedExp() {
      return Configs.wenscConfig.breedXpCow.ConfigValue;
   }
   public float getAIMoveSpeed() {
      return this.riddenByEntity == null ? super.getAIMoveSpeed() : 0.075F;
   }


   public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
      if (super.onEntityRightClicked(player, item_stack)) {
         return true;
      } else if (this.riddenByEntity == null) {
         if (player.onServer()) {
            player.mountEntity(this);
         }

         return true;
      } else {
         return false;
      }
   }

   public void moveEntityWithHeading(float par1, float par2) {
      if (this.riddenByEntity != null) {
         this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
         this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
         this.setRotation(this.rotationYaw, this.rotationPitch);
         this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
         par1 = ((EntityLiving)this.riddenByEntity).moveStrafing * 0.5F;
         par2 = ((EntityLiving)this.riddenByEntity).moveForward;
         if (par2 <= 0.0F) {
            par2 *= 0.25F;
         }


         this.stepHeight = 1.0F;
         this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
         if (!this.worldObj.isRemote) {
            this.setAIMoveSpeed(this.getAIMoveSpeed());
            super.moveEntityWithHeading(par1, par2);
         }

         this.prevLimbSwingAmount = this.limbSwingAmount;
         double var8 = this.posX - this.prevPosX;
         double var5 = this.posZ - this.prevPosZ;
         float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;
         if (var7 > 1.0F) {
            var7 = 1.0F;
         }

         this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4F;
         this.limbSwing += this.limbSwingAmount;
      } else {
         this.stepHeight = 0.5F;
         this.jumpMovementFactor = 0.02F;
         super.moveEntityWithHeading(par1, par2);
      }

   }
}
