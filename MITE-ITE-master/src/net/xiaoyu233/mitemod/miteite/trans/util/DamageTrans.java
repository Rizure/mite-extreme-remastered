package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityMirrorSkeleton;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.GemUtil;
import net.xiaoyu233.mitemod.miteite.util.ModifierUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Damage.class)
public abstract class DamageTrans {
   @Shadow
   abstract Entity getResponsibleEntity();

   @Shadow
   private float amount;
   @Shadow
   private DamageSource source;

   @Overwrite
   protected float applyTargetDefenseModifiers(EntityLiving target, EntityDamageResult result) {
      if (target.onClient()) {
         Minecraft.setErrorMessage("applyTargetDefenseModifiers: called on client?");
      }
      if (this.amount <= 0.0F) {
         return 0.0F;
      } else if (this.isAbsolute()) {
         return this.amount;
      } else {
         ItemStack boots = target.getBoots();
         this.amount = ModifierUtil.calcDamageAfterSwiftnessModifierPerform(boots, target, this.amount);

         if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            if (!this.bypassesMundaneArmor() && player.isBlocking()) {
               ItemStack item_stack = player.getHeldItemStack();
               float invincible_modifier = ModifierUtil.calcInvincibleModifier(item_stack);
               if (item_stack != null && item_stack.getItem() instanceof ItemTool) {
                  ItemTool item_tool = (ItemTool) item_stack.getItem();
                  result.applyHeldItemDamageResult(item_stack.tryDamageItem(DamageSource.generic, (int) (this.amount * (float) item_tool.getToolDecayFromAttackingEntity(item_stack, null)), target));
               }
               this.amount /= 2.0F + invincible_modifier;
               if (this.amount < 1.0F) {
                  this.amount = 1.0F;
               }
            }
         }

         float total_protection = target.getTotalProtection(this.getSource());
         ItemStack[] wornItems = target.getWornItems();
         int delta;


         float protection_fraction = 0.0F;


         if (wornItems.length >= 4) {
            boolean allProtectionV = true;
            ItemStack[] var6 = wornItems;
            int var7 = wornItems.length;
            for (delta = 0; delta < var7; ++delta) {
               ItemStack armor = var6[delta];
               if (armor == null) {
                  allProtectionV = false;
               } else {
                  allProtectionV = armor.getEnchantmentLevel(Enchantment.protection) >= 5 && allProtectionV;
               }
            }
            if (allProtectionV) {
               protection_fraction += Configs.wenscConfig.allProtectionVDefenceFraction.ConfigValue;
            }
         }
         protection_fraction = MathHelper.clamp_float(protection_fraction, 0.0F, 1.0F);
         this.amount *= 1.0F - protection_fraction;

         protection_fraction = GemUtil.calcProtectionFraction(protection_fraction, target);
         this.amount *= 1.0F - protection_fraction;

         protection_fraction = ModifierUtil.calcIndomitableFraction(target);
         this.amount *= 1.0F - protection_fraction;

         protection_fraction = ModifierUtil.calcDenseImmunityFraction(total_protection, target, wornItems);
         this.amount *= 1.0F - protection_fraction;

         float execute_boost = GemUtil.calcExecuteBoost(this.getItemAttackedWith(), target);
         this.amount *= 1.0F + execute_boost;

         this.amount = GemUtil.performPredationEffect(this.getItemAttackedWith(), this.amount, this.getResponsibleEntity(), target);

         if (this.getResponsibleEntity() instanceof EntityMirrorSkeleton){
            this.amount *= 1.0F + ((EntityMirrorSkeleton) this.getResponsibleEntity()).predictDamageBoostToSelf();
         }

         DebugAttack.setTargetProtection(total_protection);
         float amount_dealt_to_armor = Math.min(target.getProtectionFromArmor(this.getSource(), false), this.amount);
         target.tryDamageArmorP(this.getSource(), amount_dealt_to_armor, result);
         DebugAttack.setDamageDealtToArmor(amount_dealt_to_armor);
         float piercing = Enchantment.piercing.getLevelFraction(this.getItemAttackedWith()) * 5.0F;
         float effective_protection = Math.max(total_protection - piercing, 0.0F);
         DebugAttack.setPiercing(piercing);
         if (target instanceof EntityPlayer && effective_protection >= this.amount) {
            delta = (int) (effective_protection - this.amount);
            for (int i = -1; i < delta; ++i) {
               if (target.getRNG().nextFloat() < 0.1F) {
                  return 0.0F;
               }
            }
         }

         return Math.max(this.amount - effective_protection, 1.0F);
      }
   }

   @Shadow
   private boolean bypassesMundaneArmor() {
      return false;
   }

   @Shadow
   private ItemStack getItemAttackedWith() {
      return null;
   }

   public Entity getResponsibleEntityP() {
      return this.source.getResponsibleEntity();
   }

   @Shadow
   private DamageSource getSource() {
      return null;
   }

   @Shadow
   private boolean isAbsolute() {
      return false;
   }
}
