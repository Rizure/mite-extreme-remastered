package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.entity.EntityExchanger;
import net.xiaoyu233.mitemod.miteite.entity.EntityHostileSkeletonHorse;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;

@Mixin(EntitySkeleton.class)
public class EntitySkeletonTrans extends EntityMonster implements IRangedEntity {
   private static final int DATA_OBJ_ID_SKELETON_TYPE = 13;
   private static final int WITHER_SKELETON_ID = 1;
   private static final int ARROW_SKELETON_ID = 0;
   private static final int MELEE_ATTACK_SKELETON_ID = 2;
   @Shadow
   private final PathfinderGoalArrowAttack aiArrowAttack = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
   @Shadow
   private final PathfinderGoalMeleeAttack aiAttackOnCollide = new PathfinderGoalMeleeAttack(this, EntityPlayer.class, 1.2D, false);
   @Shadow
   public int forced_skeleton_type = -1;
   private int DATA_OBJ_ID_CAN_USE_FIRE_ARROW;
   private int DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW;
   private int DATA_OBJ_ID_COMPRESSED;
   @Shadow
   private int data_object_id_is_frenzied_by_bone_lord;
   public boolean haveTrySpawnBat = false;

   @Shadow
   private void addRandomEquipment(){
   }

   public EntitySkeletonTrans(World par1World) {
      super(par1World);
   }


   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
      if (this.worldObj.isUnderworld() && this.worldObj.getDayOfOverworld() < 64) {
         MonsterUtil.addDefaultArmor(64, this, true);
      }
   }

   @Overwrite
   public void addRandomWeapon() {
      int day_of_world = MinecraftServer.F().getOverworld().getDayOfOverworld();
      if (this.getSkeletonType() == 2 && day_of_world >= 64) {
         super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, day_of_world >= 96));
      } else if (this.getSkeletonType() == 2 && this.getRNG().nextInt(20) == 0 && day_of_world >= 10) {
         if (this.getRNG().nextBoolean()) {
            if (day_of_world >= 20) {
               super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, false));
            } else {
               super.setCurrentItemOrArmor(0, (new ItemStack(Item.swordRustedIron)).randomizeForMob(this, false));
            }
         } else {
            super.setCurrentItemOrArmor(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, false));
         }
      } else {
         this.setCombatTask();
         super.setCurrentItemOrArmor(0, (new ItemStack(this.getSkeletonType() == 2 ? (this.getRNG().nextInt(6) == 0 ? Items.clubIron : Items.clubWood) : Item.bow)).randomizeForMob(this, true));
      }
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      if (this.getSkeletonType() == WITHER_SKELETON_ID) {
         this.setEntityAttribute(GenericAttributes.attackDamage, (12) * Constant.getEliteMobModifier("Damage",day));
         this.setEntityAttribute(GenericAttributes.maxHealth, (30) * Constant.getEliteMobModifier("Health",day));
         this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getEliteMobModifier("Speed",day));
      } else {
         this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage",day));
         this.setEntityAttribute(GenericAttributes.maxHealth, 8 * Constant.getNormalMobModifier("Health",day));
         this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getNormalMobModifier("Speed",day));
      }
   }

   @Override
   protected float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f,0.25f);
   }




   @Overwrite
   public int getRandomSkeletonType(World world) {
      if (world.isTheNether()) {
         return WITHER_SKELETON_ID;
      } else {
         return (double)this.getRNG().nextFloat() < (this.isLongdead() ? 0.5D : 0.2d) ? MELEE_ATTACK_SKELETON_ID : ARROW_SKELETON_ID;
      }
   }


   private Item getWeapon(int day){
      return Constant.SWORDS[Math.max(Math.min((day - 16 - this.rand.nextInt(32)) / 16,Constant.SWORDS.length - 1),0)];
   }

   @Override
   public float getWeaponDamageBoost() {
      return 1.15f;
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(World world,CallbackInfo callbackInfo){
      this.tasks.addTask(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0));
   }

   @Inject(method = "onLivingUpdate",at = @At("RETURN"))
   public void injectLivingUpdate(CallbackInfo callbackInfo) {
      if(this.isCompressed() && this.getTicksExistedWithOffset() % 60 == 0){
         this.entityFX(EnumEntityFX.summoned);
      }
      if(this.isRiding() && this.ridingEntity instanceof EntityHostileSkeletonHorse){
          if(this.getTarget() != null && this.getTicksExistedWithOffset() % 20 == 0){
              ((EntityHostileSkeletonHorse)this.ridingEntity).setTarget(this.getTarget());
          }
      }
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci){
      if (par1NBTTagCompound.hasKey("TripleShot") && par1NBTTagCompound.getBoolean("TripleShot")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW, (byte)1);
      }

      if (par1NBTTagCompound.hasKey("FireBow") && par1NBTTagCompound.getBoolean("FireBow")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW, (byte)1);
      }

      if (par1NBTTagCompound.hasKey("Compressed") && par1NBTTagCompound.getBoolean("Compressed")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_COMPRESSED, (byte)1);
      }
      this.setCombatTask();
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci){
      par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
      par1NBTTagCompound.setBoolean("TripleShot", this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) != 0);
      par1NBTTagCompound.setBoolean("FireBow", this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW) != 0);
      par1NBTTagCompound.setBoolean("Compressed", this.isCompressed());
   }

   @Overwrite
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(DATA_OBJ_ID_SKELETON_TYPE, (byte)0);
      this.data_object_id_is_frenzied_by_bone_lord = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
      this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
      this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
      this.DATA_OBJ_ID_COMPRESSED = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
   }

   public boolean isHoldingRangedWeapon() {
      return this.getHeldItem() instanceof ItemBow;
   }

   @Shadow
   public int getSkeletonType() {
      return this.dataWatcher.getWatchableObjectByte(DATA_OBJ_ID_SKELETON_TYPE);
   }

   @Shadow
   public void setSkeletonType(int par1) {
   }
   @Override
   public void onDeathUpdate(){
      super.onDeathUpdate();
      if(this.isCompressed()){
         this.entityFX(EnumEntityFX.summoned);
      }
      if (!this.worldObj.isRemote && this.isCompressed() && this.deathTime == 20) {
         for(int integer = 0; integer < (Configs.wenscConfig.compressedSkeletonExpandCount.ConfigValue); ++integer) {
            int EntityID = EntityTypes.getEntityID(this);
            EntitySkeleton skeleton = (EntitySkeleton) EntityTypes.createEntityByID(EntityID, this.worldObj);
            skeleton.setPosition(this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ());
            skeleton.forced_skeleton_type = skeleton.getRandomSkeletonType(this.worldObj);
            skeleton.refreshDespawnCounter(-9600);
            this.worldObj.spawnEntityInWorld(skeleton);
            skeleton.onSpawnWithEgg(null);
            skeleton.setAttackTarget(this.getTarget());
            skeleton.entityFX(EnumEntityFX.summoned);
            int standTime = Configs.wenscConfig.compressedSkeletonCrackStandTime.ConfigValue;
            skeleton.addPotionEffect(new MobEffect(MobEffectList.weakness.id,standTime,127,true));
            skeleton.addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id,standTime,127,true));
            skeleton.addPotionEffect(new MobEffect(MobEffectList.resistance.id,standTime,127,true));
         }
      }
   }
   @Inject(method = "onSpawnWithEgg",at = @At("RETURN"))
   private void injectNewTypeSkeleton(CallbackInfoReturnable<GroupDataEntity> callbackInfo){
      if(rand.nextInt(100) < Configs.wenscConfig.RidingSkeletonSpawningWeight.ConfigValue && this.isLongdead() && this.getSkeletonType() == MELEE_ATTACK_SKELETON_ID){
         EntityHostileSkeletonHorse horse;
         horse = new EntityHostileSkeletonHorse(this.worldObj);
         horse.setPosition(this.posX, this.posY, this.posZ);
         this.worldObj.spawnEntityInWorld(horse);
         horse.onSpawnWithEgg(new GroupDataHorse(4, 0));
         horse.entityFX(EnumEntityFX.summoned);
         this.mountEntity(horse);
      }
      if(rand.nextInt(100) < Configs.wenscConfig.FlyingSkeletonSpawningWeight.ConfigValue && this.getSkeletonType() == 0 && !this.isLongdead()) {
         EntityBat entityBat;
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW, (byte)(1));
         entityBat = new EntityBat(this.worldObj);
         entityBat.setPosition(this.posX, this.posY, this.posZ);
         this.worldObj.spawnEntityInWorld(entityBat);
         entityBat.onSpawnWithEgg(null);
         entityBat.setAttackTarget(this.getTarget());
         entityBat.entityFX(EnumEntityFX.summoned);
         this.mountEntity(entityBat);
      }
   }
   @Override
    public boolean requiresLineOfSightToTargets() {
       return !this.isOutdoors();
    }

   private boolean isCompressed() {
      return this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_COMPRESSED) != 0;
   }

   @Shadow
   public boolean isLongdead() {
      return false;
   }

   public void setCombatTask() {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiArrowAttack);
      ItemStack var1 = this.getHeldItemStack();
      if (var1 != null && var1.getItem() instanceof ItemBow) {
         this.tasks.addTask(4, this.aiArrowAttack);
         this.tasks.addTask(4, new EntityAISeekFiringPosition(this, 1.0F, true));
      } else {
         this.tasks.addTask(4, this.aiAttackOnCollide);
      }

   }

   @Overwrite
   public void attackEntityWithRangedAttack(EntityLiving target, float par2) {
      int rawDay = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      EntityArrow var3 = new EntityArrow(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.difficultySetting * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
      int var4 = Math.max(EnchantmentManager.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItemStack()), 1);
      int var5 = Math.max(EnchantmentManager.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItemStack()), 1);
      double var6 = (double)(par2 * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting * 0.11F);
      var3.setDamage(var6);
      if (var4 > 0)
      {
         var3.setDamage(var3.getDamage() + (double)var4 * 0.5D + 0.5D);
      }
      if (var5 > 0)
      {
         var3.setKnockbackStrength(var5);
      }
      if (EnchantmentManager.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemStack()) > 0 || this.getSkeletonType() == 1 || this.isInFire() && this.getRNG().nextInt(3) == 0 || this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW) > 0 && rawDay > 196) {
         var3.setFire(100);
      }
      if ((Configs.wenscConfig.skeletonTripleShot.ConfigValue) && this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) > 0) {
         EntityArrow[] covering = new EntityArrow[2];
         for(int i = 0; i < covering.length;i ++){
            covering[i] = new EntityArrow(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.difficultySetting * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
            covering[i].motionX *= 1.0D + 0.25D * this.rand.nextDouble();
            covering[i].motionY *= 1.0D + 0.25D * this.rand.nextDouble();
            covering[i].motionZ *= 1.0D + 0.25D * this.rand.nextDouble();
            covering[i].setDamage(var6 / 2);
            if (var4 > 0) {
               covering[i].setDamage(covering[i].getDamage() + (double)var4 * 0.5 + 0.5);
            }
            if (var5 > 0) {
               covering[i].setKnockbackStrength(var5);
            }

            if (EnchantmentManager.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemStack()) > 0 || this.getSkeletonType() == 1 || this.isBurning() && this.rand.nextInt(3) == 0) {
               covering[i].setFire(100);
            }
            this.worldObj.spawnEntityInWorld(covering[i]);
         }
      }
      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.worldObj.spawnEntityInWorld(var3);
   }


   @Shadow
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("SkeletonType")) {
         byte var2 = par1NBTTagCompound.getByte("SkeletonType");
         this.setSkeletonType(var2);
      }
   }

   @Overwrite
   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
      super.setCurrentItemOrArmor(par1, par2ItemStack);
      if (this.getHeldItemStack() != null) {
         this.setCombatTask();
      }

   }

   @Shadow
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);

   }
}
