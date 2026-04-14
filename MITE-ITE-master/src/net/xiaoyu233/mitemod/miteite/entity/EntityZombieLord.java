package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class EntityZombieLord extends EntityRevenant {
   private int fx_counter;
   private int spawnCounter;
   private int spawnSums;

   public EntityZombieLord(World par1World) {
      super(par1World);
   }

   private boolean haveTryToSpawnExchanger = false;


   @Override
   protected void addRandomEquipment() {
      int day = this.getWorld().getDayOfOverworld();
      this.addRandomWeapon();
      this.setCurrentItemOrArmor(1, (new ItemStack(Items.helmetVibranium, 1)).randomizeForMob(this, day > 64));
      this.setCurrentItemOrArmor(2, (new ItemStack(Items.cuirassVibranium, 1)).randomizeForMob(this, day > 64));
      this.setCurrentItemOrArmor(3, (new ItemStack(Items.leggingsVibranium, 1)).randomizeForMob(this, day > 64));
      this.setCurrentItemOrArmor(4, (new ItemStack(Items.bootsVibranium, 1)).randomizeForMob(this, day > 64));
   }

   public void addRandomWeapon() {
      int day = this.getWorld().getDayOfOverworld();
      List items = new ArrayList();
      items.add(new RandomItemListEntry(Items.swordVibranium, 2));
      if (day > 10) {
         items.add(new RandomItemListEntry(Items.battleAxeVibranium, 1));
      }
      if (day > 20) {
         items.add(new RandomItemListEntry(Items.warHammerVibranium, 1));
      }
      RandomItemListEntry entry = (RandomItemListEntry) WeightedRandom.getRandomItem(this.rand, items);
      this.setHeldItemStack((new ItemStack(entry.item)).randomizeForMob(this, day > 64));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld().getDayOfOverworld();
      this.setEntityAttribute(GenericAttributes.attackDamage, (15) * Constant.getEliteMobModifier("Damage", day, this.worldObj.isOverworld()));
      this.setEntityAttribute(GenericAttributes.maxHealth, (40) * Constant.getEliteMobModifier("Health", day, this.worldObj.isOverworld()));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getEliteMobModifier("Speed", day, this.worldObj.isOverworld()));
   }

   @Override
   public boolean canBeDisarmed() {
      return false;
   }

   public int getMaxSpawnedInChunk() {
      return 1;
   }

   @Override
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      super.dropFewItems(recently_hit_by_player, damage_source);
      if (recently_hit_by_player) {
         this.dropItem(Items.voucherOverlord);
         int day = this.getWorld().getDayOfOverworld();
         int diamond_count = Math.min((day + 16) / 32, 3);
         for (int i1 = 0; i1 < diamond_count; i1++) {
            this.dropItem(Item.diamond);
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setShort("spawnCounter", (short) this.spawnCounter);
      par1NBTTagCompound.setByte("spawnSums", (byte) this.spawnSums);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.spawnCounter = par1NBTTagCompound.getShort("spawnCounter");
      this.spawnSums = par1NBTTagCompound.getByte("spawnSums");
   }

   @Override
   public boolean canCatchFire() {
      return false;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.onServer()) {
         if (fx_counter > 0) {
            fx_counter--;
         } else {
            this.fx_counter = 60;
            this.entityFX(EnumEntityFX.summoned);
         }
         if (Configs.wenscConfig.isSpawnDragger.ConfigValue) {
            EntityLiving target = this.getAttackTarget();
            if (target instanceof EntityPlayer) {
               if (spawnSums < 4) {
                  if (this.spawnCounter < 20) {
                     ++this.spawnCounter;
                  } else {
                     EntityRevenant zombie = new EntityRevenant(this.worldObj);
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
                     ++spawnSums;
                  }
               }
               if (!haveTryToSpawnExchanger) {
                  if (rand.nextInt(20) == 0) {
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
}
