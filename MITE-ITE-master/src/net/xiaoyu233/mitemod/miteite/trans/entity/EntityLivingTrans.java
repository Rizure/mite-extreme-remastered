package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityLiving.class)
public abstract class EntityLivingTrans extends Entity {
   @Shadow
   public abstract ItemStack getLeggings();

   @Shadow
   public abstract void e(float par1);

   @Shadow
   protected boolean isJumping;

   public EntityLivingTrans(World par1World) {
      super(par1World);
   }

   protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {
   }

   public boolean getJumping() {
      return this.isJumping;
   }

   @Shadow
   public double getFootPosY() {
      return this.posY;
   }

   @Shadow
   public float getSilverArmorCoverage() {
      return 0;
   }

   public float getPurifyModifier(){
      ItemStack leggings = this.getLeggings();
      int purify_level = 0;
      if(leggings != null){
         purify_level = EnchantmentManager.getEnchantmentLevel(Enchantments.enchantmentPurify, leggings);
      }
      return 0.2F * purify_level;
   }

   @Overwrite
   public float getResistanceToPoison() {
      return Math.min(1.0F, this.getSilverArmorCoverage() * 0.5F + getPurifyModifier());
   }

   @Overwrite
   public float getResistanceToDrain() {
      return Math.min(1.0F, this.getSilverArmorCoverage() * 0.5F + getPurifyModifier());
   }

   @Overwrite
   public float getResistanceToShadow() {
      return Math.min(1.0F, this.getSilverArmorCoverage() * 0.5F + getPurifyModifier());
   }


   @Overwrite
   public boolean isInRain() {
      if (this.riddenByEntity != null || this.ridingEntity != null) {
         return false;
      } else {
         return this.worldObj.isInRain(this.getBlockPosX(), MathHelper.floor_double(this.getFootPosY() + (double) this.height), this.getBlockPosZ());
      }
   }

   @Shadow
   protected void entityInit() {
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "attackEntityFrom", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/EntityLiving;attackEntityFromHelper(Lnet/minecraft/Damage;Lnet/minecraft/EntityDamageResult;)Lnet/minecraft/EntityDamageResult;"))
   private void injectAfterDamageCallback(Damage damage, CallbackInfoReturnable<EntityDamageResult> c, EntityDamageResult result) {
      this.checkForAfterDamage(damage, result);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "onDeath", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/EnchantmentManager;getLootingModifier(Lnet/minecraft/EntityLiving;)I", shift = At.Shift.AFTER))
   private void injectDropHead(DamageSource par1DamageSource, CallbackInfo ci, Entity var2, EntityLiving var3, int var4) {
      if (var2 instanceof EntityPlayer) {
         ItemStack heldItemStack = ((EntityPlayer) var2).getHeldItemStack();
         if (heldItemStack != null) {
            float modifierValue = EnchantmentManager.getEnchantmentLevel(Enchantments.enchantmentBeheading, heldItemStack) * 0.2F;
            if (modifierValue > 0.0F) {
               boolean dropHead = (float) this.rand.nextInt(100) < modifierValue * 100.0F;
               if (dropHead) {
                  EntityLiving thisLiving = ReflectHelper.dyCast(this);
                  ItemStack headItemStack = null;
                  if (thisLiving instanceof EntityCreeper) {
                     headItemStack = new ItemStack(Item.skull, 1, 4);
                  }

                  if (thisLiving instanceof EntityZombie) {
                     headItemStack = new ItemStack(Item.skull, 1, 2);
                  }

                  if (thisLiving instanceof EntitySkeleton) {
                     if (((EntitySkeleton) thisLiving).getSkeletonType() == 1) {
                        headItemStack = new ItemStack(Item.skull, 1, 1);
                     } else {
                        headItemStack = new ItemStack(Item.skull, 1, 0);
                     }
                  }

                  if (thisLiving instanceof EntityPlayer) {
                     headItemStack = new ItemStack(Item.skull, 1, 3);
                  }

                  if (headItemStack != null) {
                     this.dropItemStack(headItemStack);
                  }
               }
            }
         }
      }
   }

   public boolean canBeTargetTo(EntityLiving from) {
      return true;
   }

   @Redirect(method = "onEntityUpdate", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult injectModifyPlayerInWallDamage(EntityLiving caller, Damage damage) {
      // 取消蝙蝠骑士窒息伤害
      if (ReflectHelper.dyCast(this) instanceof EntitySkeleton) {
         if (((EntitySkeleton) ReflectHelper.dyCast(this)).ridingEntity != null) {
            return null;
         }
      }
      // 取消玩家窒息伤害
      if (ReflectHelper.dyCast(this) instanceof EntityPlayer) {
//        return this.attackEntityFrom(new Damage(DamageSource.inWall, Configs.wenscConfig.inWallDamageForPlayer.ConfigValue));
         return null;
      } else {
         return this.attackEntityFrom(new Damage(DamageSource.inWall, 1.0f));
      }
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
   }

   public void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result) {
      this.tryDamageArmor(damage_source, amount, result);
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
