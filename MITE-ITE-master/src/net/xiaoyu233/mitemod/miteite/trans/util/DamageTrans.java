package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.util.Configs;
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
      ItemStack boots = target.getBoots();
      if (boots != null) {
         float value = ArmorModifierTypes.SWIFTNESS.getModifierValue(boots.getTagCompound());
         if (target.getRNG().nextFloat() < value) {
            return 0.0F;
         }
      }
      if (this.amount <= 0.0F) {
         return 0.0F;
      } else if (this.isAbsolute()) {
         return this.amount;
      } else {
         if (this.getItemAttackedWith() != null && this.getItemAttackedWith().getGemMaxLevel(GemModifierTypes.predation) != 0) {
            if (this.getResponsibleEntity() instanceof EntityLiving) {
               float predation_boost = this.getItemAttackedWith().getGemMaxNumeric(GemModifierTypes.predation);
               predation_boost *= this.getResponsibleEntityP().getAsEntityLivingBase().getMaxHealth() - target.getMaxHealth();
               if (predation_boost > 0) {
                  this.amount += predation_boost;
               }
            }
         }

         if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            if (!this.bypassesMundaneArmor() && player.isBlocking()) {
               ItemStack item_stack = player.getHeldItemStack();
               float invincible_modifier = 0.0F;
               if (item_stack != null && item_stack.getItem() instanceof ItemTool) {
                  ItemTool item_tool = (ItemTool) item_stack.getItem();
                  result.applyHeldItemDamageResult(item_stack.tryDamageItem(DamageSource.generic, (int) (this.amount * (float) item_tool.getToolDecayFromAttackingEntity(item_stack, null)), target));
                  invincible_modifier = ToolModifierTypes.INVINCIBLE.getModifierValue(item_stack.getTagCompound());
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

         if (target instanceof EntityPlayer) {
            protection_fraction = ((EntityPlayer) target).getGemSumNumeric(GemModifierTypes.protection);
         }
         protection_fraction = MathHelper.clamp_float(protection_fraction, 0.0F, 1.0F);
         this.amount *= 1.0F - protection_fraction;

         if (this.getItemAttackedWith() != null && this.getItemAttackedWith().getGemMaxLevel(GemModifierTypes.execute) != 0) {
            float execute_boost = this.getItemAttackedWith().getGemMaxNumeric(GemModifierTypes.execute);
            execute_boost *= (1 - target.getHealthFraction());
            this.amount *= 1.0F + execute_boost;
         }

         if (target instanceof EntityPlayer) {
            float healthPercent = target.getHealthFraction();
            ItemStack chestplate = target.getCuirass();
            if (chestplate != null) {
               float value = ArmorModifierTypes.INDOMITABLE.getModifierValue(chestplate.getTagCompound());
               if (value != 0) {
                  protection_fraction = value * (1.0F - healthPercent);
               }
            }
         }
         protection_fraction = MathHelper.clamp_float(protection_fraction, 0.0F, 1.0F);
         this.amount *= 1.0F - protection_fraction;

         if (total_protection > 0) {
            float immunity_base = target instanceof EntityPlayer ? 70.0F : 35.0F;
            float dense_modifier = 0.0F;
            if (target instanceof EntityPlayer) {
               ItemStack[] var6 = wornItems;
               int var7 = wornItems.length;

               for (delta = 0; delta < var7; ++delta) {
                  ItemStack armor = var6[delta];
                  if (armor != null) {
                     dense_modifier += ArmorModifierTypes.DENSE.getModifierValue(armor.stackTagCompound);
                  }
               }
            }
            dense_modifier = MathHelper.clamp_float(dense_modifier, 0.0F, 1.0F);
            float immunity_fraction = total_protection / (total_protection + (immunity_base - 70.0F * dense_modifier));
            this.amount *= 1.0F - immunity_fraction;
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
