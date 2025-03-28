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
   private boolean haveTriedToSpawnBat = false;
   @Inject(method = "<init>",at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.is_smart = true;
   }
   @Inject(method = "addRandomWeapon", at = @At("RETURN"))
   private void extendsWeapon(CallbackInfo callbackInfo) {
      int day_of_world = MinecraftServer.F().getOverworld().getDayOfOverworld();
      if (day_of_world >= 64 && this.getHeldItem() == null) {
         super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, day_of_world >= 96));
      }
   }
   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo callbackInfo){
      par1NBTTagCompound.setBoolean("haveTriedToSpawnBat", this.haveTriedToSpawnBat);
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo callbackInfo){
      this.haveTriedToSpawnBat = par1NBTTagCompound.getBoolean("haveTriedToSpawnBat");
   }
   @Inject(method = "onUpdate", at = @At("RETURN"))
   private void extraBat(CallbackInfo callbackInfo) {
      if(!this.haveTriedToSpawnBat) {
         if(this.posY <= 40 && !this.isOutdoors() && this.getTarget() != null){
            if(this.rand.nextInt(64) > 64 - this.worldObj.getDayOfOverworld()){
               EntityVampireBat bat = new EntityVampireBat(this.worldObj);
               bat.setPosition(this.posX, this.posY, this.posZ);
               bat.refreshDespawnCounter(-9600);
               this.worldObj.spawnEntityInWorld(bat);
               bat.onSpawnWithEgg(null);
               bat.setAttackTarget(this.getTarget());
               bat.entityFX(EnumEntityFX.summoned);
            }
            this.haveTriedToSpawnBat = true;
         }
      }
   }
   private Item getWeapon(int day){
      return this.rand.nextInt(4) == 0 ? Constant.TOOLS[Math.max(Math.min((day - 16 - this.rand.nextInt(32)) / 16,Constant.SWORDS.length - 1),0)] : Constant.SWORDS[Math.max(Math.min((day - 16 - this.rand.nextInt(32)) / 16,Constant.SWORDS.length - 1),0)];
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
   protected int getConversionTimeBoost() {
      int var1 = 1;
      if (this.rand.nextFloat() < 0.01F) {
         int var2 = 0;
         for(int var3 = (int)this.posX - 4; var3 < (int)this.posX + 4 && var2 < 14; ++var3) {
            for(int var4 = (int)this.posY - 4; var4 < (int)this.posY + 4 && var2 < 14; ++var4) {
               for(int var5 = (int)this.posZ - 4; var5 < (int)this.posZ + 4 && var2 < 14; ++var5) {
                  int var6 = this.worldObj.getBlockId(var3, var4, var5);
                  if (var6 == Block.fenceIron.blockID || Arrays.stream(Constant.bedBlockTypes).anyMatch(e -> e.blockID == var6)) {
                     if (this.rand.nextFloat() < 0.3F) {
                        ++var1;
                     }

                     ++var2;
                  }
               }
            }
         }
      }

      return var1;
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 30 * Constant.getNormalMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23D * Constant.getNormalMobModifier("Speed",day));
   }

   @Override
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.15D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

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
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f,0.25f);
   }
}
