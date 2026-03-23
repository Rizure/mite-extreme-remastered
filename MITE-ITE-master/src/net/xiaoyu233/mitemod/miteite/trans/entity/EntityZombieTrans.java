package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.entity.EntityDragger;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(EntityZombie.class)
class EntityZombieTrans extends EntityAnimalWatcher {
   @Shadow
   private boolean is_smart;
   private int spawn_bat_counter;

   @Inject(method = "<init>", at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.is_smart = true;
   }

   @Inject(method = "addRandomWeapon", at = @At("RETURN"))
   private void extendsWeapon(CallbackInfo callbackInfo) {
      int day_of_world = MinecraftServer.F().getOverworld().getDayOfOverworld();
      if (day_of_world >= 64 && this.getHeldItem() == null) {
         if (this.rand.nextInt(8) == 0) {
            MonsterUtil.addDefaultTool(day_of_world, this);
         } else {
            MonsterUtil.addDefaultWeapon(day_of_world, this);
         }
      }
   }

   @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo callbackInfo) {
      par1NBTTagCompound.setInteger("spawnBatCounter", this.spawn_bat_counter);
   }

   @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo callbackInfo) {
      this.spawn_bat_counter = par1NBTTagCompound.getInteger("spawnBatCounter");
   }

   @Inject(method = "onUpdate", at = @At("RETURN"))
   private void extraBat(CallbackInfo callbackInfo) {
      if (this.spawn_bat_counter >= 0) {
         this.spawn_bat_counter++;
      }
      if (this.spawn_bat_counter > 0 && this.spawn_bat_counter < 6000) {
         if (this.posY <= 40 && !this.isOutdoors() && this.getTarget() != null) {
            if (this.rand.nextInt(64) > 64 - this.worldObj.getDayOfOverworld()) {
               EntityVampireBat bat = new EntityVampireBat(this.worldObj);
               bat.setPosition(this.posX, this.posY, this.posZ);
               bat.refreshDespawnCounter(-9600);
               this.worldObj.spawnEntityInWorld(bat);
               bat.onSpawnWithEgg(null);
               bat.setAttackTarget(this.getTarget());
               if (this.onServer()) {
                  bat.entityFX(EnumEntityFX.summoned);
               }
            }
            this.spawn_bat_counter = -1;
         }
      }
   }

   @Shadow
   @Final
   protected static IAttribute field_110186_bp;

   public EntityZombieTrans(World world) {
      super(world);
   }

   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
      if (this.worldObj.isUnderworld() && this.worldObj.getDayOfOverworld() < 64) {
         MonsterUtil.addDefaultArmor(64, this, true);
      }
   }


   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage", day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 30 * Constant.getNormalMobModifier("Health", day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23D * Constant.getNormalMobModifier("Speed", day));
   }

   //
//      EntityDamageResult result = super.attackEntityFrom(damage);
//      if (result != null && !result.entityWasDestroyed() && result.entityWasNegativelyAffected() && damage.wasCausedByPlayer()) {
//         this.is_smart = true;
//      }
//
//      return result;

   @Override
   protected float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f, 0.25f);
   }
}
