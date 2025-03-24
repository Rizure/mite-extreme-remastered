package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityLongdead.class)
public class EntityLongdeadTrans extends EntitySkeletonTrans {
   public EntityLongdeadTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      if (day < 64) {
         this.setBoots((new ItemStack(Item.bootsChainAncientMetal)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsChainAncientMetal)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateChainAncientMetal)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetChainAncientMetal)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }
      this.initStockedWeapon();

   }

//   @Override
//   protected boolean willChangeWeapon() {
//      return Configs.wenscConfig.boneLordAndLongdeadChangeWeaponChance.ConfigValue > this.rand.nextFloat();
//   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 40.0D);
      if(this.isGuardian()){
         this.setEntityAttribute(GenericAttributes.maxHealth, 26 * Constant.getEliteMobModifier("Health",day,this.worldObj.isOverworld()));
         this.setEntityAttribute(GenericAttributes.movementSpeed, 0.29D * Constant.getEliteMobModifier("Speed",day,this.worldObj.isOverworld()));
         this.setEntityAttribute(GenericAttributes.attackDamage, 13 * Constant.getEliteMobModifier("Damage",day,this.worldObj.isOverworld()));
      }else {
         this.setEntityAttribute(GenericAttributes.attackDamage, 10 * Constant.getNormalMobModifier("Damage",day,this.worldObj.isOverworld()));
         this.setEntityAttribute(GenericAttributes.maxHealth, 18 * Constant.getNormalMobModifier("Health",day,this.worldObj.isOverworld()));
         this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getNormalMobModifier("Speed",day,this.worldObj.isOverworld()));
      }
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Shadow
   protected boolean isGuardian() {
      return false;
   }
}
