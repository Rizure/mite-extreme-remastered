package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityZombieDoor extends EntityZombie {
   private int spawnCounter;
   private int spawnSums;
   private boolean haveTryToSpawnExchanger = false;

   private boolean modifiedAttribute = false;
   private final Item[] doorList = new Item[]{Items.doorWood, Items.doorGold, Items.doorCopper, Items.doorSilver, Items.doorIron, Items.doorAncientMetal, Items.doorMithril, Items.doorAdamantium};
   private int danger_level;
   private int absorption_point;

   public EntityZombieDoor(World par1World) {
      super(par1World);
      this.danger_level = Constant.GARandom.nextInt(doorList.length);
      this.absorption_point = 80 + (int) (Math.pow(this.danger_level, 1.5) * 7);
   }

   public EntityZombieDoor(World par1World, int danger_level) {
      super(par1World);
      this.danger_level = danger_level;
      this.absorption_point = 80 + (int) (Math.pow(this.danger_level, 1.5) * 7);
   }

   @Override
   protected void addRandomEquipment() {
      super.addRandomEquipment();
      this.setCurrentItemOrArmor(0, (new ItemStack(doorList[this.danger_level], 1)).randomizeForMob(this, false));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld().getDayOfOverworld();
      this.setEntityAttribute(GenericAttributes.attackDamage, (8) * Constant.getEliteMobModifier("Damage", day, this.worldObj.isOverworld()));
      this.setEntityAttribute(GenericAttributes.maxHealth, (30) * Constant.getEliteMobModifier("Health", day, this.worldObj.isOverworld()));
      this.setEntityAttribute(GenericAttributes.movementSpeed, (0.23D) * Constant.getEliteMobModifier("Speed", day, this.worldObj.isOverworld()));
   }

   @Override
   public boolean canBeDisarmed() {
      return true;
   }

   @Override
   public EntityDamageResult attackEntityFrom(Damage damage) {
      boolean sounded = false;
      if (damage.isArrowDamage() && this.getHeldItem() instanceof ItemDoor) {
         damage.setAmount(0f);
      } else if (this.getHeldItem() instanceof ItemDoor && !damage.bypassesMundaneArmor()) {
         Entity damage_source = damage.getResponsibleEntityP();
         int damageBlocked = 0;
         if(this.absorption_point > 0){
            if (damage_source != null && Math.abs(this.posY - damage.getResponsibleEntityP().posY) >= 2.0D) {
               if(this.getHeldItem() == Item.doorWood){
                  this.getWorld().playSoundAtEntity(this,"mob.zombie.wood",1.0F,1.0F);
               }else {
                  this.getWorld().playSoundAtEntity(this, "mob.zombie.metal", 0.5F, 1.0F);
               }
               sounded = true;
               damage.scaleAmount(0.5F);
            }
            if (damage_source != null && isInFieldOfViewByAngle(this.posX, this.posZ, this.rotationYaw, damage_source.posX, damage_source.posZ, 60.0F)) {
               if (!sounded) {
                  if (this.getHeldItem() == Item.doorWood) {
                     this.getWorld().playSoundAtEntity(this, "mob.zombie.wood", 1.0F, 1.0F);
                  } else {
                     this.getWorld().playSoundAtEntity(this, "mob.zombie.metal", 0.5F, 1.0F);
                  }
               }
               damageBlocked += (int) damage.getAmount();
               this.absorption_point -= damageBlocked;
               if (this.absorption_point <= 0) {
                  if (this.getHeldItem() != Item.doorWood) {
                     this.getWorld().playSoundAtEntity(this, "mob.zombie.metal", 1.0F, 1.0F);
                  }
                  this.getWorld().playSoundAtEntity(this, "mob.zombie.woodbreak", 1.0F, 1.0F);
                  this.setHeldItemStack(null);
               }
               damage.setKnockbackOnly();
            }
         }
      }
      return super.attackEntityFrom(damage);
   }

   @Override
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player) {
         if (this.getHeldItem() == Item.doorAdamantium) {
            this.dropItem(Items.ban);
         }
         boolean shouldDropItem = this.danger_level > 3 || this.rand.nextBoolean();
         if (shouldDropItem) {
            this.dropItem(Items.voucherDestruction);
         }
         int day = this.getWorld().getDayOfOverworld();
         int diamond_count = Math.min((day + this.danger_level * 4) / 8, 12);
         for (int i1 = 0; i1 < diamond_count; i1++) {
            this.dropItemStack(new ItemStack(Items.dyePowder, 1, 4));
         }
         this.dropItem(Items.zombieBrain);
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setByte("danger_level", (byte) this.danger_level);
      par1NBTTagCompound.setShort("absorption_point", (short) this.absorption_point);
      par1NBTTagCompound.setShort("spawnCounter", (short) this.spawnCounter);
      par1NBTTagCompound.setByte("spawnSums", (byte) this.spawnSums);
      par1NBTTagCompound.setBoolean("modifiedAttribute", this.modifiedAttribute);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.spawnCounter = par1NBTTagCompound.getShort("spawnCounter");
      this.spawnSums = par1NBTTagCompound.getByte("spawnSums");
      this.danger_level = par1NBTTagCompound.getByte("danger_level");
      this.modifiedAttribute = par1NBTTagCompound.getBoolean("modifiedAttribute");
      this.absorption_point = par1NBTTagCompound.getShort("absorption_point");
   }

   @Override
   public boolean canCatchFire() {
      return false;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.onServer()) {
         if (!this.modifiedAttribute) {
            this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + danger_level * 4);
            this.heal(danger_level * 4);
            this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(this.getEntityAttributeValue(GenericAttributes.attackDamage) + danger_level);
            this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(this.getEntityAttributeValue(GenericAttributes.movementSpeed) + danger_level * 0.01D);
            this.modifiedAttribute = true;
         }
         EntityLiving target = this.getAttackTarget();
         if (target instanceof EntityPlayer) {
            if (spawnSums < 10) {
               if (this.spawnCounter < 20) {
                  ++this.spawnCounter;
               } else {
                  EntityZombie zombie = new EntityZombie(this.worldObj);
                  if (zombie.entityId == 207) {
                     return;
                  }
                  zombie.setPosition(this.posX, this.posY, this.posZ);
                  zombie.refreshDespawnCounter(-9600);
                  this.worldObj.spawnEntityInWorld(zombie);
                  zombie.onSpawnWithEgg(null);
                  zombie.addRandomWeapon();
                  zombie.setAttackTarget(this.getTarget());
                  zombie.entityFX(EnumEntityFX.summoned);
                  this.spawnCounter = 0;
                  spawnSums += 2;
               }
            }
            if (Configs.wenscConfig.isSpawnDragger.ConfigValue) {
               if (!this.haveTryToSpawnExchanger) {
                  if (rand.nextInt(10) == 0) {
                     EntityDragger entityExchanger = new EntityDragger(this.worldObj);
                     entityExchanger.setPosition(this.posX, this.posY, this.posZ);
                     entityExchanger.refreshDespawnCounter(-9600);
                     this.worldObj.spawnEntityInWorld(entityExchanger);
                     entityExchanger.onSpawnWithEgg(null);
                     entityExchanger.setAttackTarget(this.getTarget());
                     entityExchanger.entityFX(EnumEntityFX.summoned);
                  }
                  this.haveTryToSpawnExchanger = true;
               }
            }
         }
      }
   }

   public static boolean isInFieldOfViewByAngle(double entityX, double entityZ, float yaw,
                                                double targetX, double targetZ,
                                                float halfAngleDeg) {
      double dx = targetX - entityX;
      double dz = targetZ - entityZ;

      if (dx * dx + dz * dz < 1.0E-8) {
         return true;
      }

      // 目标方向对应的 yaw
      float targetYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));

      // 角度差，归一化到 [-180, 180]
      float delta = targetYaw - yaw;
      delta = ((delta % 360.0F) + 360.0F) % 360.0F;  // 先到 [0, 360)
      if (delta > 180.0F) {
         delta -= 360.0F;                          // 再到 (-180, 180]
      }

      return Math.abs(delta) <= halfAngleDeg;
   }
}
