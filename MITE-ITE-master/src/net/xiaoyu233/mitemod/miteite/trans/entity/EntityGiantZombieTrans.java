package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieDoor;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EntityGiantZombie.class)
public class EntityGiantZombieTrans extends EntityMonster {
   private int spawnCounter;

   public EntityGiantZombieTrans(World var1) {
      super(var1);
   }

   @Override
   public boolean canBeKnockedBack() {
      return false;
   }

   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.worldObj.getDayOfOverworld();
      this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(14 * Constant.getBossMobModifier("Health", day));
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(80 * Constant.getBossMobModifier("Damage", day));
      this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.23D * Constant.getBossMobModifier("Speed", day));
   }

   @Override
   public boolean considerFleeing() {
      return false;
   }

//   @Override
//   protected void dropEquipment(boolean recently_hit_by_player, int par2) {
//      for(int var3 = 0; var3 < this.getInventory().length; ++var3) {
//         ItemStack var4 = this.getEquipmentInSlot(var3);
//         if (var4 != null && (!var4.isItemStackDamageable() || this.picked_up_a_held_item_array[var3] && var4.getRemainingDurability() > var4.getMaxDamage() / 4)) {
//            this.dropItemStack(var4, 0.0F);
//            this.setWornItem(var3, null);
//         }
//      }
//
//   }

   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player) {
         int looting = damage_source.getLootingModifier();
         for (int i = 0; i < 9 + this.rand.nextInt(2 + looting); i++) {
            this.dropItem(Item.ancientMetalNugget);
         }
         if (this.getWorld().getDayOfOverworld() > 63) {
            this.dropItem(Item.ingotMithril);
         }
         for (int i = 0; i < 12 + this.rand.nextInt(5 + looting * 2); i++) {
            this.dropItem(Item.rottenFlesh);
         }
         for (int i = 0; i < 2 + this.rand.nextInt(3 + looting); i++) {
            this.dropItem(Items.zombieBrain);
         }
         for (int i = 0; i < 8 + this.rand.nextInt(5 + looting * 2); i++) {
            this.dropItem(Item.bone);
         }
         for (int i = 0; i < 3 + this.rand.nextInt(1 + looting); i++) {
            if (this.rand.nextBoolean()){
               this.dropItem(Items.enhanceStoneIron);
            }
         }
      }
      super.dropFewItems(recently_hit_by_player, damage_source);
   }

   @Override
   protected EntityPlayer findPlayerToAttack(float max_distance) {
      EntityPlayer target = super.findPlayerToAttack(max_distance * 2.0F);
      if (target != null) {
         this.setAttackTarget(target);
      }

      return target;
   }

   public boolean getCanSpawnHere(boolean perform_light_check) {
      return this.isOutdoors() && this.getWorld().isBloodMoon(true);
   }

   @Override
   public EnumMonsterType getCreatureAttribute() {
      return EnumMonsterType.UNDEAD;
   }

   protected String getDeathSound() {
      return "mob.zombie.death";
   }

   protected String getHurtSound() {
      return "mob.zombie.hurt";
   }

   @Override
   protected String getLivingSound() {
      return "mob.zombie.say";
   }

   protected float getSoundPitch(String sound) {
      return super.getSoundPitch(sound) * 0.6F;
   }

   protected float getSoundVolume(String sound) {
      return super.getSoundVolume(sound) * 16.0F;
   }

   @Override
   public float getReach() {
      return super.getReach() * 2.5F;
   }

   @Inject(method = "<init>", at = @At("RETURN"))
   private void injectAI(CallbackInfo c) {
      this.tasks.addTask(0, new PathfinderGoalFloat(this));
      this.tasks.addTask(2, new PathfinderGoalMeleeAttack(this, EntityPlayer.class, 1.1D, true));
      this.tasks.addTask(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
      this.tasks.addTask(4, new PathfinderGoalMeleeAttack(this, EntityAnimal.class, 1.0D, true));
      this.tasks.addTask(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
      this.tasks.addTask(6, new PathfinderGoalRandomStroll(this, 1.0D));
      this.tasks.addTask(7, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new PathfinderGoalRandomLookaround(this));
      this.targetTasks.addTask(1, new PathfinderGoalHurtByTarget(this, true));
      this.targetTasks.addTask(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.targetTasks.addTask(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
      this.targetTasks.addTask(3, new PathfinderGoalNearestAttackableTarget(this, EntityAnimal.class, 10, true));
      this.setSize(0.6f * 6.1F, 1.8f * 6.0F);
   }

   @Override
   protected boolean isAIEnabled() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.onServer()) {
         if (this.spawnCounter < Configs.wenscConfig.giantZombieSpawnZombieCooldown.ConfigValue) {
            ++this.spawnCounter;
         } else {
            EntityZombie zombie = new EntityZombie(this.worldObj);
            zombie.setPosition(this.posX, this.posY, this.posZ);
            zombie.refreshDespawnCounter(-9600);
            this.worldObj.spawnEntityInWorld(zombie);
            zombie.onSpawnWithEgg(null);
            zombie.setAttackTarget(this.getTarget());
            zombie.entityFX(EnumEntityFX.summoned);
            this.spawnCounter = 0;
         }
      }

   }

   @Override
   public void onDeathUpdate() {
      super.onDeathUpdate();
      if (this.deathTime == 20) {
         if (this.onServer()) {
            float explosion_size_vs_blocks = 0.0F;
            float explosion_size_vs_living_entities = 2.5F;
            this.worldObj.createExplosion(this, this.posX, this.posY + (double) (this.height / 4.0F), this.posZ, explosion_size_vs_blocks, explosion_size_vs_living_entities, false);
            for (int i = 0; i < 8; i++) {
               EntityZombie zombie = new EntityZombie(this.worldObj);
               zombie.setPosition(this.posX + this.rand.nextFloat() - 0.5F, this.posY, this.posZ + this.rand.nextFloat() - 0.5F);
               zombie.refreshDespawnCounter(-9600);
               this.worldObj.spawnEntityInWorld(zombie);
               zombie.onSpawnWithEgg(null);
               zombie.addRandomWeapon();
               zombie.setAttackTarget(this.getTarget());
               zombie.entityFX(EnumEntityFX.summoned);
            }
            for (int i = 0; i < 4; i++) {
               EntitySkeleton skeleton = new EntitySkeleton(this.worldObj);
               skeleton.setPosition(this.posX + this.rand.nextFloat() - 0.5F, this.posY, this.posZ + this.rand.nextFloat() - 0.5F);
               skeleton.refreshDespawnCounter(-9600);
               this.worldObj.spawnEntityInWorld(skeleton);
               skeleton.onSpawnWithEgg(null);
               skeleton.addRandomWeapon();
               skeleton.setAttackTarget(this.getTarget());
               skeleton.entityFX(EnumEntityFX.summoned);
            }
            for (int var3 = 0; var3 < 24; ++var3) {
               this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D));
            }
            for (int var3 = 0; var3 < 24; ++var3) {
               this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D));
            }
            this.entityFX(EnumEntityFX.frags);
         }
      }
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
      this.makeSound("mob.zombie.step", 0.15F, 1.0F);
   }
}
