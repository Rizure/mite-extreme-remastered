package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Mixin(EntityInsentient.class)
public abstract class EntityInsentientTrans extends EntityLiving {
   @Shadow
   public float[] equipmentDropChances;
   @Shadow
   public boolean picked_up_a_held_item;
   public boolean[] picked_up_a_held_item_array;
   private final Map<EntityPlayer,Integer> playerSteppedCountMap = new HashMap<>();

   public int resetAttackMapTimer = 100;

   @Shadow
   protected float defaultPitch;
   @Shadow
   protected int numTicksToChaseTarget;

   public EntityInsentientTrans(World par1World) {
      super(par1World);
   }

   public boolean canBeDisarmed() {
      return true;
   }

   @Shadow
   public boolean canNeverPickUpItem(Item item_on_ground) {
      return false;
   }

   @Shadow
   public boolean canWearItem(ItemStack item_stack_on_ground) {
      return false;
   }

   @Shadow
   public boolean getAlwaysRenderNameTag() {
      return false;
   }

   @Shadow
   public void setAlwaysRenderNameTag(boolean par1) {
   }

   @Shadow
   public String getCustomNameTag() {
      return "";
   }

   @Shadow
   public void setCustomNameTag(String par1Str) {
   }

   public float[] getEquipmentDropChances() {
      return this.equipmentDropChances;
   }

   @Shadow
   public ItemStack getEquipmentInSlot(int var5) {
      return null;
   }

   @Shadow
   public ItemStack getHeldItemStack() {
      return null;
   }

   @Shadow
   public void setHeldItemStack(ItemStack itemStack) {
   }

   @Shadow
   public ItemStack[] getInventory() {
      return new ItemStack[0];
   }

   @Shadow
   public List getItemsWithinPickupDistance() {
      return null;
   }

   @Shadow
   public final EntityLiving getTarget() {
      return null;
   }

   @Shadow
   public ItemStack[] getWornItems() {
      return new ItemStack[0];
   }

   @Shadow
   public boolean hasEquipment() {
      return false;
   }

   public float getWeaponDamageBoost(){
      return 1.0f;
   }

   @Shadow
   public int livingSoundTime;

   @Shadow
   public int getTalkInterval() {
      return 80;
   }

   @Shadow
   public double distanceToNearestPlayer() {
      return (double)this.worldObj.distanceToNearestPlayer(this.posX, this.posY, this.posZ);
   }

   @Shadow
   public void makeLivingSound() {}

   @Shadow
   public void makeLongDistanceLivingSound() {}

   @Overwrite
   public void onEntityUpdate() {
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("mobBaseTick");
      if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
         this.livingSoundTime = -this.getTalkInterval();
         if (this.worldObj.isRemote) {
            return;
         }
         double distance_to_nearest_player = this.distanceToNearestPlayer();
         if (distance_to_nearest_player <= 16.0D) {
            this.makeLivingSound();
         } else if (distance_to_nearest_player <= 64.0D) {
            this.makeLongDistanceLivingSound();
         }
      }
      if (!this.worldObj.isRemote) {
         if(resetAttackMapTimer <= 0) {
            this.playerSteppedCountMap.clear();
         } else {
            resetAttackMapTimer --;
         }
      }
      this.worldObj.theProfiler.endSection();
   }

   @Inject(method = "applyEntityAttributes",at = @At("RETURN"))
   private void injectEntityAttributes(CallbackInfo c){
      this.picked_up_a_held_item_array = new boolean[5];
   }

   @Inject(method = "onMeleeAttacked",at = @At("HEAD"))
   private void injectMeleeAttacked(EntityLiving attacker, EntityDamageResult result,CallbackInfo c) {
      if (result != null && attacker != null && result.entityLostHealth()) {
         ItemStack stack = attacker.getHeldItemStack();
         if (stack != null) {
            Item item = stack.getItem();
            if (attacker instanceof EntityPlayer && (item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemCudgel || item instanceof ItemPickaxe)) {
               int amp = 1;
               if (dyCast(this) instanceof EntityEarthElemental){
                  amp = 2;
               }
               if (!item.isMaxToolLevel(stack)) {
                  item.addExpForTool(stack, (EntityPlayer) attacker, (int) Math.min(this.getMaxHealth(), result.getAmountOfHealthLost() * amp / 2.0F));
               }
               boolean performDiscord = ToolModifierTypes.DISCORD.getModifierValue(stack.getTagCompound()) != 0;
               float discordLvl = ToolModifierTypes.DISCORD.getModifierValue(stack.getTagCompound());
               float slowMdfLvl = ToolModifierTypes.SLOWDOWN_MODIFIER.getModifierValue(stack.getTagCompound());
               float witherLvl = ToolModifierTypes.APOCALYPSE.getModifierValue(stack.getTagCompound());
//               float plunderLvl = ToolModifierTypes.PLUNDER.getModifierValue(stack.getTagCompound());
               if (result.entity instanceof EntityLiving && slowMdfLvl >= 1.0F) {
                  ((EntityLiving)result.entity).addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, (int)(slowMdfLvl * 20.0F), (int)(slowMdfLvl / 2.0F)));
               }
               if (result.entity instanceof EntityLiving && witherLvl >= 1.0F) {
                  ((EntityLiving)result.entity).addPotionEffect(new MobEffect(MobEffectList.wither.id, (int)(witherLvl * 40.0F), (int)(witherLvl / 3.0F)));
               }
               if (result.entity instanceof EntityInsentient && performDiscord){
                  List <Entity>targets  = result.entity.getNearbyEntities(discordLvl, discordLvl);
                  for(int i = 0; i < targets.size() ; i++){
                     EntityMonster entityMonster = targets.get(i) instanceof EntityMonster ? (EntityMonster) targets.get(i) : null;
                     if(entityMonster != null) {
                        entityMonster.setRevengeTarget(this);
                     }
                  }
               }
               if(attacker.getRNG().nextFloat() < ToolModifierTypes.BLESS_OF_NATURE.getModifierValue(stack.getTagCompound())){
                  attacker.getAsPlayer().getFoodStats().addSatiation(2);
                  attacker.getAsPlayer().getFoodStats().addNutrition(1);
                  if(attacker.getRNG().nextFloat() < ToolModifierTypes.BLESS_OF_NATURE.getModifierValue(stack.getTagCompound())){
                     attacker.getAsPlayer().heal(1);
                  }
               }
            }
         }
      }

      super.onMeleeAttacked(attacker, result);
   }

   @Inject(method = "attackEntityFrom",at = @At(value = "INVOKE",target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;",shift = At.Shift.BEFORE))
   private void injectAttackEntityFrom(Damage damage, CallbackInfoReturnable<EntityDamageResult> callbackInfo){
         if(!(ReflectHelper.dyCast(EntityInsentient.class,this) instanceof EntityZombieBoss)) {
            double max = Configs.wenscConfig.steppedMobDamageFactor.ConfigValue;
            if (max != 0.0D) {
               Entity responsibleEntity = damage.getSource().getResponsibleEntity();
               ItemStack itemAttackedWith = damage.getItemAttackedWith();
               if (responsibleEntity instanceof EntityPlayer && itemAttackedWith != null && itemAttackedWith.getItem() instanceof ItemTool) {
                  EntityPlayer player = (EntityPlayer) responsibleEntity;
                  this.resetAttackMapTimer = 100;
                  if (this.playerSteppedCountMap.containsKey(responsibleEntity)) {
                     Integer time = this.playerSteppedCountMap.get(responsibleEntity);
                     damage.setAmount((float) (damage.getAmount() +
                             //Increase per lvl: enchantment + player base
                             (time * EnchantmentManager.getEnchantmentLevel(Enchantments.CONQUEROR,itemAttackedWith) * 2) +
                             (Math.min(max, time * Math.max(0,player.getExperienceLevel()) * 0.1))));
                     this.playerSteppedCountMap.put(player, time + 1);
                  } else {
                     this.playerSteppedCountMap.put(player, 1);
                  }
               }
            }
         }
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "tryPickUpItems",
           at = @At(value = "FIELD",
                   target = "Lnet/minecraft/EntityInsentient;persistenceRequired:Z",
                   shift = At.Shift.BEFORE))
   private void injectPickUpItems(CallbackInfo ci, List entity_items, Iterator iterator, EntityItem entity_item, ItemStack item_stack_on_ground, Item item_on_ground, int var5, boolean pickup, ItemStack current_item_stack, boolean set_dead){
      if (entity_item.canBePickUpByPlayer()){
         this.picked_up_a_held_item_array[var5] = true;
      }
      this.picked_up_a_held_item = true;
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callbackInfo){
      if (par1NBTTagCompound.hasKey("picked_up_held_items")) {
         if (this.picked_up_a_held_item_array == null) {
            this.picked_up_a_held_item_array = new boolean[5];
         }

         byte[] picked_up_held_items = par1NBTTagCompound.getByteArray("picked_up_held_items");
         for (int i = 0, jLength = picked_up_held_items.length; i < jLength; i++) {
            if (picked_up_held_items[i] == 1) {
               this.picked_up_a_held_item_array[i] = true;
            }
         }
      }

      if (par1NBTTagCompound.hasKey("PlayerSteppedCountMap")) {
         NBTTagList attackCountMap = par1NBTTagCompound.getTagList("PlayerSteppedCountMap");
         int count = attackCountMap.tagCount();

         for(int i = 0; i < count; ++i) {
            NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
            Entity attacker = this.getWorldServer().getEntityByID(a.getInteger("Attacker"));
            if (attacker instanceof EntityPlayer) {
               this.playerSteppedCountMap.put((EntityPlayer) attacker, a.getInteger("Count"));
            }
         }
      }
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo c){
      byte[] picked_up_held_items = new byte[this.picked_up_a_held_item_array.length];
      boolean[] pickedUpAHeldItemArray = this.picked_up_a_held_item_array;

      for(int i = 0; i < pickedUpAHeldItemArray.length; ++i) {
         if (pickedUpAHeldItemArray[i]) {
            picked_up_held_items[i] = 1;
         }
      }
      NBTTagList nbtTagList = new NBTTagList();
      for (Map.Entry<EntityPlayer, Integer> integerEntry : this.playerSteppedCountMap.entrySet()) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("Attacker", ( integerEntry).getKey().entityId);
         compound.setInteger("Count", (integerEntry).getValue());
         nbtTagList.appendTag(compound);
      }

      par1NBTTagCompound.setTag("PlayerSteppedCountMap", nbtTagList);

      par1NBTTagCompound.setByteArray("picked_up_held_items", picked_up_held_items);
   }

   @Accessor("came_from_spawner")
   public abstract boolean isCame_from_spawner();

   public boolean isDead() {
      return this.isDead;
   }

   @Shadow
   public boolean isWearing(ItemStack itemStack) {
      return false;
   }

   @Shadow
   public void setCanPickUpLoot(boolean par1) {
   }

   @Shadow
   public void setCurrentItemOrArmor(int var5, ItemStack setStackSize) {
   }

   @Shadow
   public boolean setWornItem(int i, ItemStack itemStack) {
      return false;
   }
}
