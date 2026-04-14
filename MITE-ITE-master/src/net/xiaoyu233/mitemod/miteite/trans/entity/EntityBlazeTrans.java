package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityBlaze.class)
public class EntityBlazeTrans extends EntityMonster {
   public EntityBlazeTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.worldObj.getDayOfOverworld();
      this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getNormalMobModifier("Damage", day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 30 * Constant.getNormalMobModifier("Health", day));
      this.setEntityAttribute(GenericAttributes.followRange, 64);
   }

   @ModifyConstant(method = {
           "attackEntity",
   }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 20))
   private static int decreaseFireballCooldown(int value) {
      if(Configs.wenscConfig.boostBlaze.ConfigValue)
         return 10;
      return 20;
   }

   @ModifyConstant(method = {
           "attackEntity",
   }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 4))
   private static int increaseFireballInAttack(int value) {
      if(Configs.wenscConfig.boostBlaze.ConfigValue)
         return 11;
      return 4;
   }

   @ModifyConstant(method = {
           "attackEntity",
   }, constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 0.5F, ordinal = 0))
   private static float increaseFireballLeadAccuracy(float value) {
      if(Configs.wenscConfig.boostBlaze.ConfigValue)
         return 0.0F;
      return 0.5F;
   }

   @ModifyConstant(method = {
           "attackEntity",
   }, constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 0.5F, ordinal = 2))
   private static float increaseFireballLocationAccuracy(float value) {
      if(Configs.wenscConfig.boostBlaze.ConfigValue)
         return 0.0F;
      return 0.5F;
   }

   @ModifyConstant(method = {
           "attackEntity",
   }, constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 30.0F))
   private static float increaseAttackingRange(float value) {
      if(Configs.wenscConfig.boostBlaze.ConfigValue)
         return 60.0F;
      return 30.0F;
   }
}
