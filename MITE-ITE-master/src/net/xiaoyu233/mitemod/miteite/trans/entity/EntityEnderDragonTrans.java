package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityLargeFireballNonExplodeBlock;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityEnderDragon.class)
public class EntityEnderDragonTrans extends EntityInsentient implements IComplex {
   @Shadow
   private EntityComplexPart dragonPartHead;
   @Shadow
   private boolean forceNewTarget;
   @Shadow
   private EntityEnderCrystal healingEnderCrystal;
   @Shadow
   private Entity target;
   @Shadow
   private double targetX;
   @Shadow
   private double targetY;
   @Shadow
   private double targetZ;
   private int weaknessCountdown;
   private boolean forceExplodeAttack;
   private boolean processingExplodeAttack;

   public boolean canSpawnInShallowWater() {
      return false;
   }

   public String an() {
      return this.getEntityName();
   }

   ;

   public float aT() {
      return this.getMaxHealth();
   }

   ;

   public float aN() {
      return this.getHealth();
   }

   ;

   public EntityEnderDragonTrans(World par1World) {
      super(par1World);
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.worldObj.getDayOfOverworld();
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(Math.min(2000, 1250 * Constant.getBossMobModifier("Health", day)));
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("WeaknessCountDown")) {
         this.weaknessCountdown = par1NBTTagCompound.getInteger("WeaknessCountDown");
         this.processingExplodeAttack = par1NBTTagCompound.getBoolean("processingExplodeAttack");
         this.forceExplodeAttack = par1NBTTagCompound.getBoolean("forceExplodeAttack");
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("WeaknessCountDown", this.weaknessCountdown);
      par1NBTTagCompound.setBoolean("processingExplodeAttack", this.processingExplodeAttack);
      par1NBTTagCompound.setBoolean("forceExplodeAttack", this.forceExplodeAttack);
   }

   @Inject(method = "onLivingUpdate", at = @At("HEAD"))
   private void injectUpdate(CallbackInfo callback) {
      if (this.onServer() && this.getHealth() < this.getMaxHealth() / 2) {
         if (this.weaknessCountdown > 0) {
            this.weaknessCountdown--;
         } else {
            for (Object playerEntity : this.worldObj.playerEntities) {
               ((EntityPlayer) playerEntity).addPotionEffect(new MobEffect(MobEffectList.weakness.id, 600, 0));
            }
            this.weaknessCountdown = 1200;
         }
      }
   }

   @Overwrite
   private void attackEntitiesInList(List par1List) {
      for (Object value : par1List) {
         Entity var3 = (Entity) value;
         if (var3 instanceof EntityLiving) {
            if (var3 instanceof EntityPlayer && Configs.wenscConfig.enderDragonAttackSlowness.ConfigValue) {
               ((EntityPlayer) var3).addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, 15 * 20, 0));
            }
            var3.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), this.getHealth() > 150.0F ? 65.0F : 50.0F));
         }
      }

   }

   @Overwrite
   public EntityDamageResult attackEntityFromPart(EntityComplexPart par1EntityDragonPart, Damage damage) {
      if (par1EntityDragonPart != this.dragonPartHead && damage.getAmount() > 1.0F) {
         damage.scaleAmount(0.2F, 0.5F);
      }

      Entity responsibleEntityP = damage.getResponsibleEntityP();
      if (responsibleEntityP instanceof EntityPlayer && rand.nextFloat() < Configs.wenscConfig.enderDragonAttackWitherChance.ConfigValue) {
         ((EntityPlayer) responsibleEntityP).addPotionEffect(new MobEffect(MobEffectList.wither.id, 20 * 10, 0));
      }

      if (damage.isArrowDamage() && this.getHealthFraction() < Configs.wenscConfig.enderDragonImmuneToArrowHealthPercent.ConfigValue) {
         damage.setAmount(0);
      }
      if (this.getHealthFraction() < 0.3F) {
         damage.scaleAmount(0.5f);
      }

      float var4 = super.rotationYaw * 3.1415927F / 180.0F;
      float var5 = MathHelper.sin(var4);
      float var6 = MathHelper.cos(var4);
      this.targetX = super.posX + (double) (var5 * 5.0F) + (double) ((this.getRNG().nextFloat() - 0.5F) * 2.0F);
      this.targetY = super.posY + (double) (this.getRNG().nextFloat() * 3.0F) + 1.0D;
      this.targetZ = super.posZ - (double) (var6 * 5.0F) + (double) ((this.getRNG().nextFloat() - 0.5F) * 2.0F);
      this.target = null;
      return !(damage.getSource().getResponsibleEntity() instanceof EntityPlayer) && !damage.isExplosion() ? null : this.func_82195_e(damage);
   }

   @Redirect(method = "onLivingUpdate",
           slice = @Slice(
                   from = @At(value = "FIELD", target = "Lnet/minecraft/EntityLiving;hurtTime:I", opcode = Opcodes.GETFIELD),
                   to = @At(value = "INVOKE", target = "Lnet/minecraft/EntityEnderDragon;attackEntitiesInList(Ljava/util/List;)V")),
           at = @At(value = "INVOKE", target = "Lnet/minecraft/AxisAlignedBB;expand(DDD)Lnet/minecraft/AxisAlignedBB;"))
   private AxisAlignedBB redirectExpandAttackRange(AxisAlignedBB caller, double par1, double par3, double par5) {
      return caller.expand(par1, par3, par5).expand(1.2d, 1.2d, 1.2d);
   }

   @Shadow
   public World func_82194_d() {
      return null;
   }

   @Shadow
   private EntityDamageResult func_82195_e(Damage damage) {
      return null;
   }

   @Overwrite
   private void setNewTarget() {
      this.forceNewTarget = false;
      if (this.getRNG().nextInt(2) == 0 && !super.worldObj.playerEntities.isEmpty() || this.getHealthFraction() < 0.3F) {
         this.target = (Entity) super.worldObj.playerEntities.get(this.getRNG().nextInt(super.worldObj.playerEntities.size()));
      } else {
         boolean var1;
         do {
            this.targetX = 0.0D;
            this.targetY = 70.0F + this.getRNG().nextFloat() * 50.0F;
            this.targetZ = 0.0D;
            this.targetX += this.getRNG().nextFloat() * 120.0F - 60.0F;
            this.targetZ += this.getRNG().nextFloat() * 120.0F - 60.0F;
            double var2 = super.posX - this.targetX;
            double var4 = super.posY - this.targetY;
            double var6 = super.posZ - this.targetZ;
            var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
         } while (!var1);

         this.target = null;
      }
   }

   //   @Inject(method = "updateDragonEnderCrystal", at = @At(value = "INVOKE", target = "attackEntityFromPart",shift = At.Shift.AFTER))
//   private void targetCrystalCracker(CallbackInfo callbackInfo){
//      List<Entity> entitiesNearCrystal = this.healingEnderCrystal.getNearbyEntities(5.0F,5.0F);
//      for(int i = 0; i < entitiesNearCrystal.size();i++){
//         Entity entity = entitiesNearCrystal.get(i);
//         if(entity instanceof EntityPlayer){
//            this.target = (EntityLiving) entity;
//            this.forceExplodeAttack = true;
//            break;
//         }
//      }
//   }
   @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityEnderDragon;forceNewTarget:Z", shift = At.Shift.BEFORE))
   private void targetCrystalCracker(CallbackInfo callbackInfo) {
      if (!super.worldObj.playerEntities.isEmpty()) {
         List Players = super.worldObj.playerEntities;
         for (int i = 0; i < Players.size(); i++) {
            EntityPlayer player = (EntityPlayer) Players.get(i);
            if (player != null && this.healingEnderCrystal != null && player.getDistanceToEntity(this.healingEnderCrystal) < 12.0F) {
               this.forceExplodeAttack = true;
               this.target = player;
            }
         }
      }
   }

   @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityEnderDragon;targetY:D", shift = At.Shift.AFTER, ordinal = 1))
   private void forceExplodeAttack_setFiringPlace(CallbackInfo callbackInfo) {
      if (this.forceExplodeAttack || (this.rand.nextInt(200) == 0 && !this.processingExplodeAttack)) {
         this.processingExplodeAttack = true;
         double dx = this.posX - this.target.posX;
         double dy = this.posY - this.target.posY;
         double dz = this.posZ - this.target.posZ;
         this.targetX = this.target.posX + dx / 2;
         this.targetZ = this.target.posZ + dz / 2;
         double var14 = Math.sqrt(dx * dx + dz * dz);
         double var16 = var14 / (double) 40.0F;
         this.targetY = this.target.boundingBox.minY + var16 + 30.0F;
      }
   }

   @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityEnderDragon;forceNewTarget:Z", shift = At.Shift.BEFORE))
   private void forceExplodeAttack_performStrike(CallbackInfo callbackInfo) {
      if (this.dragonPartHead.getDistance(this.targetX, this.targetY, this.targetZ) < 18.0F && this.processingExplodeAttack) {
         this.forceExplodeAttack = false;
         this.processingExplodeAttack = false;
         Entity target = this.target;
         if (target != null) {
            double dx = (this.target.posX - this.posX);
            double dy = (this.target.posY - this.posY);
            double dz = (this.target.posZ - this.posZ);
            int range = (int) getDistanceToEntity(target);
            double div = 1.0D / (double) range;
            for (int delta = 0; delta < range; delta++) {
               this.worldObj.createExplosion(this, this.dragonPartHead.posX + dx * div * delta, this.dragonPartHead.posY + 0.25F + dy * div * delta, this.dragonPartHead.posZ + dz * div * delta, 0.0F, 1.25F, false);
            }
            this.makeSound("mob.enderdragon.growl", 1000.0F, 1.0F);
         }
         this.setNewTarget();
      }
   }

   @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityEnderDragon;forceNewTarget:Z", shift = At.Shift.BEFORE))
   private void forceFireballAttack_performStrike(CallbackInfo callbackInfo) {
      if (this.ticksExisted % 9 == 0 && this.targetY - 14.0F < this.posY && this.dragonPartHead.getDistance(this.targetX, this.targetY, this.targetZ) > 24.0F && !this.processingExplodeAttack) {
         Vec3D target_center = this.worldObj.getWorldVec3Pool().getVecFromPool(this.targetX, this.targetY - 20.0F, this.targetZ);
         Vec3D this_center = this.dragonPartHead.getCenterPoint();
         this_center.yCoord -= 1.0F;
         Vec3D launch_center;
         super.worldObj.playAuxSFXAtEntity(null, 1008, (int) super.posX, (int) super.posY, (int) super.posZ, 0);
         for (int i = 1; i > 0; --i) {
            launch_center = this_center;
            launch_center.xCoord += -2.0F + this.rand.nextFloat() * 4.0F;
            launch_center.zCoord += -2.0F + this.rand.nextFloat() * 4.0F;
            EntityLargeFirebal var17 = new EntityLargeFireballNonExplodeBlock(super.worldObj, this, launch_center, target_center, 4.0F);
            var17.field_92057_e = 2;
            var17.accelerationX *= 1.0D + 3.0D * Math.sin(Math.toRadians(this.dragonPartHead.rotationYaw + 90.0F));
            var17.accelerationZ *= 1.0D + 3.0D * Math.cos(Math.toRadians(this.dragonPartHead.rotationYaw + 90.0F));
            var17.accelerationY *= 8.0d;
            super.worldObj.spawnEntityInWorld(var17);
         }
      }
   }

   @Override
   public boolean isImmuneTo(DamageSource damage_source) {
      if (this.getHealthFraction() < 0.95F) {
         return super.isImmuneTo(damage_source) && !damage_source.isExplosion();
      } else {
         return super.isImmuneTo(damage_source);
      }
   }
}
