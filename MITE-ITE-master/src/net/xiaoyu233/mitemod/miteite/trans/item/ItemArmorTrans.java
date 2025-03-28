package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.*;
import net.xiaoyu233.mitemod.miteite.util.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mixin(ItemArmor.class)
public abstract class ItemArmorTrans extends Item implements IDamageableItem, IUpgradableItem {
   @Shadow
   @Final
   public int armorType;
   @Shadow
   private Material effective_material;
   @Shadow
   @Final
   private boolean is_chain_mail;

   @Final
   private Function<Integer,Integer> expForLevel;

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInitExpForLevel(int par1, Material material,int par2,boolean is_chain_mail, CallbackInfo callbackInfo){
      if (material == Material.copper || material == Material.silver || material == Material.gold){
         this.expForLevel = this.createExpForLevel(20,8,2);
      }else if (material == Material.iron){
         this.expForLevel = this.createExpForLevel(30,12,3);
      }else if (material == Material.ancient_metal){
         this.expForLevel = this.createExpForLevel(40,16,4);
      } else if (material == Material.mithril) {
         this.expForLevel = this.createExpForLevel(60,24,6);
      }else if (material == Material.adamantium){
         this.expForLevel = this.createExpForLevel(80,32,8);
      }else if (material == Materials.vibranium){
         this.expForLevel = this.createExpForLevel(120,48,12);
      }else {
         this.expForLevel = this.createExpForLevel(40,16,4);
      }
//      this.expForLevel = this.createExpForLevel(1,1,0);
   }

   private Function<Integer, Integer> createExpForLevel(int start, int base,int increase){
      return (level) -> level == 0 ? start : base + level * increase;
   }

   @Overwrite
   public static float getTotalArmorProtection(ItemStack[] armors, DamageSource damage_source, boolean include_enchantments, EntityLiving owner) {
      float total_defense = 0.0F;
      if (damage_source != null && damage_source.isUnblockable()) {
         return total_defense;
      } else {
         if (damage_source == null || !damage_source.bypassesMundaneArmor()) {
            ItemStack[] var5 = armors;
            int var6 = armors.length;
            for(int var7 = 0; var7 < var6; ++var7) {
               ItemStack item_stack = var5[var7];
               if (item_stack != null) {
                  Item item = item_stack.getItem();
                  if (item instanceof ItemHorseArmor) {
                     ItemHorseArmor barding = (ItemHorseArmor)item;
                     total_defense += (float)barding.getProtection();
                  } else if (item.isArmor()) {
                     ItemArmor armor = (ItemArmor)item_stack.getItem();
                     if (damage_source != null) {
                        if (damage_source.getResponsibleEntity() instanceof EntityLiving && damage_source.getResponsibleEntity().isEntityUndead()) {
                           total_defense += ArmorModifierTypes.BLESSED_MODIFIER.getModifierValue(item_stack.stackTagCompound);
                        }

                        total_defense += ItemUtil.getProtectionForDamage(item_stack, damage_source, owner);
                     }

                     total_defense += armor.getProtectionAfterDamageFactor(item_stack, owner);
                  }
               }
            }
         }

         if (include_enchantments) {
            total_defense += EnchantmentProtection.getTotalProtectionOfEnchantments(armors, damage_source, owner);
         }

          if (damage_source != null && damage_source.isFallDamage()) {
              for (ItemStack item_stack : armors) {
                  if (item_stack != null) {
                      Item item = item_stack.getItem();
                      if (item.isArmor()) {
                          total_defense += ArmorModifierTypes.LEVITY.getModifierValue(item_stack.stackTagCompound) * 3F;
                      }
                  }
              }
          }

          total_defense = MathHelper.tryFitToNearestInteger(total_defense, 1.0E-4F);
         return total_defense;
      }
   }

   @Overwrite
   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      int forgingGrade;
      if (itemStack.hasTagCompound()) {
         int toolLevel = itemStack.getTagCompound().getInteger("tool_level");
         if (itemStack.getTagCompound().hasKey("tool_level")) {
            int maxArmorLevel = this.getMaxToolLevel(itemStack);
            if (this.isMaxToolLevel(itemStack)) {
               info.add("装备等级:§6已达到最高级" + maxArmorLevel);
            } else {
               info.add("装备等级:" + toolLevel + "/" + maxArmorLevel);
               if (itemStack.getTagCompound().hasKey("tool_exp")) {
                  info.add("装备经验" + EnumChatFormat.WHITE + itemStack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(toolLevel, this.armorType, ReflectHelper.dyCast(this)));
               }
            }
         }

         if (itemStack.getTagCompound().hasKey("forging_grade") && (forgingGrade = itemStack.getTagCompound().getInteger("forging_grade")) != 0) {
            info.add("§5强化等级:§6" + StringUtil.intToRoman(forgingGrade));
            if (extended_info) {
               info.add("  §7装备经验增加:§a" +  ItemStack.field_111284_a.format(this.getEquipmentExpBounce(itemStack) * 100) + "%");
               info.add("  §9护甲增加:§6" + ItemStack.field_111284_a.format(this.getEnhancedProtection(itemStack)));
            }
         }

         if (extended_info) {
            info.add("§5宝石:");
            info.add(" §3抗性增加:§6" + ItemStack.field_111284_a.format(itemStack.getGemMaxNumeric(GemModifierTypes.protection)));
            info.add(" §3生命增加:§6" + ItemStack.field_111284_a.format(itemStack.getGemMaxNumeric(GemModifierTypes.health)));
            info.add(" §3恢复增加:§6" + ItemStack.field_111284_a.format(itemStack.getGemMaxNumeric(GemModifierTypes.recover)));
            NBTTagCompound compound = itemStack.stackTagCompound.getCompoundTag("modifiers");
            if (!compound.hasNoTags()) {
               info.add("装备强化:");
               ArmorModifierTypes[] var9 = ArmorModifierTypes.values();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  ArmorModifierTypes value = var9[var11];
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
            }
         }
      }

      if (extended_info) {
         info.add("");
         float protection = this.getProtectionAfterDamageFactor(itemStack, player);
         forgingGrade = protection < 1.0F ? 2 : 1;
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("item.tooltip.protectionBonus", new Object[]{StringHelper.formatFloat(protection, forgingGrade, forgingGrade)}));
      }

   }

   @Shadow
   public abstract void func_82813_b(ItemStack par1ItemStack, int par2);

   @Override
   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      super.addExpForTool(stack, player, (int) (exp * (this.getEquipmentExpBounce(stack) + 1)));
   }

   public int getExpReqForLevel(int tool_level, int slotIndexl, ItemArmor armor) {
      switch(slotIndexl) {
         case 0:
            return 5 * this.expForLevel.apply(tool_level);
         case 1:
            return 8 * this.expForLevel.apply(tool_level);
         case 2:
            return 7 * this.expForLevel.apply(tool_level);
         case 3:
            return 4 * this.expForLevel.apply(tool_level);
         default:
            return 64 * tool_level;
      }
   }

   private float getEnhancedProtection(ItemStack itemStack) {
      return (float)(itemStack.getEnhanceFactor() * (double)this.getRawProtection() * 0.489700376987457275390625F);
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return this.getExpReqForLevel(i, this.armorType, ReflectHelper.dyCast(this));
   }
   @Overwrite
   public final float getDamageFactor(ItemStack item_stack, EntityLiving owner) {
      if (owner != null && !owner.isEntityPlayer()) {
         return Configs.wenscConfig.enhanceMobsArmor.ConfigValue ? Constant.getNormalMobModifier("Damage", owner.worldObj.getDayOfOverworld()) : 0.5F;
      } else if (owner instanceof EntityPlayer && item_stack.getMaxDamage() > 1 && item_stack.getItemDamage() >= item_stack.getMaxDamage() - 1) {
         return 0.0F;
      } else {
         float armor_damage_factor = 2.0F - (float)item_stack.getItemDamage() / (float)item_stack.getItem().getMaxDamage(item_stack) * 2.0F;
         if (armor_damage_factor > 1.0F) {
            armor_damage_factor = 1.0F;
         }

         return armor_damage_factor;
      }
   }

   @Overwrite
   public int getMaterialProtection() {
      int protection;
      if (this.effective_material == Material.leather) {
         protection = 2;
      } else if (this.effective_material == Material.rusted_iron) {
         protection = 6;
      } else if (this.effective_material == Material.copper) {
         protection = 7;
      } else if (this.effective_material == Material.silver) {
         protection = 7;
      } else if (this.effective_material == Material.gold) {
         protection = 6;
      } else if (this.effective_material != Material.iron && this.effective_material != Material.ancient_metal) {
         if (this.effective_material == Material.mithril) {
            protection = 9;
         } else if (this.effective_material == Material.adamantium) {
            protection = 10;
         } else {
            if (this.effective_material != Materials.vibranium) {
               return 0;
            }

            protection = 11;
         }
      } else {
         protection = 8;
      }

      if (this.is_chain_mail) {
         protection -= 2;
      }

      return protection;
   }

   @Override
   public int getMaxDamage(ItemStack item_stack) {
      return super.getMaxDamage(item_stack);
   }

   @Overwrite
   public float getMultipliedProtection(ItemStack item_stack) {
      float multiplied_protection = this.getRawProtection();
      if (item_stack != null && item_stack.hasEnchantment(Enchantment.protection, false)) {
         multiplied_protection += multiplied_protection * item_stack.getEnchantmentLevelFraction(Enchantment.protection) * 0.5F;
      }

      if (item_stack != null && item_stack.stackTagCompound != null) {
         float protection_modifier = ArmorModifierTypes.PROTECTION_MODIFIER.getModifierValue(item_stack.stackTagCompound);
         if (protection_modifier > 0.0F) {
            multiplied_protection *= (1 + protection_modifier);
         }
      }

      if (item_stack.getForgingGrade() != 0) {
         multiplied_protection += this.getEnhancedProtection(item_stack);
      }

      return multiplied_protection;
   }

   @Shadow
   private float getProtectionAfterDamageFactor(ItemStack itemStack, EntityLiving owner) {
      return 0.0F;
   }

   private float getRawProtection() {
      return (float)(this.getNumComponentsForDurability() * this.getMaterialProtection()) / 24.0F;
   }

   @Shadow
   public int getRepairCost() {
      return 0;
   }

   public boolean hasExpAndLevel() {
      return true;
   }

   public int getMaxToolLevel(ItemStack itemStack){
      return this.getMaterialForDurability().getMinHarvestLevel() * 2 + itemStack.getForgingGrade();
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return getMaxToolLevel(itemStack) <= this.getToolLevel(itemStack);
   }

   public boolean isWeapon(Item b) {
      return false;
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
//      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
//      ArmorModifierTypes modifierType = ModifierUtils.getModifierWithWeight(ModifierUtils.getAllCanBeAppliedArmorModifiers(stack),player.getRNG());
//      if (modifierType != null) {
//         if (modifiers.hasKey(modifierType.nbtName)) {
//            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers, modifierType) + "级"
//            ));
//         } else {
//            this.addModifierLevelFor(modifiers, modifierType);
//            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
//         }
//      }
//      player.suppressNextStatIncrement();
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      ArmorModifierTypes modifierType;
      //全可附加属性
      List<ArmorModifierTypes> all_modifiers = ModifierUtils.getAllArmorModifiers(stack);
      //目前可附加属性
      List<ArmorModifierTypes> available_modifiers = ModifierUtils.getAllCanBeAppliedArmorModifiers(stack);
      //已拥有属性
      List<ArmorModifierTypes> obtained_modifiers = new ArrayList<>();
      int modifierTypesCap = Math.min(all_modifiers.size(), 4 + (stack.getForgingGrade() / 5));
      int debugBreaker = 0;
      // 首次升级
      if(tagCompound.getInteger("tool_level") == 1){
         int i = itemRand.nextInt(3) == 0 ? 0 : 1;
         while (i < modifierTypesCap){
            modifierType = ModifierUtils.getModifierWithWeight(available_modifiers,player.getRNG());
            if (modifierType != null) {
               if (!modifiers.hasKey(modifierType.nbtName) && modifierType.canApplyTo(stack)) {
                  this.addModifierLevelFor(modifiers, modifierType);
                  player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
                  i++;
               }else {
                  available_modifiers.remove(modifierType);
               }
            } else {
               Minecraft.setErrorMessage("onItemLevelUp: No matching modifier to apply.");
               i++;
            }
         }
      }
      else {
         int upgradeCount = itemRand.nextInt(5) == 0 ? 2 : 1;
         while (upgradeCount > 0){
            available_modifiers = ModifierUtils.getAllCanBeAppliedArmorModifiers(stack);
            obtained_modifiers.clear();

            //录入拥有的副属性
            for (int n = 0; n < all_modifiers.size(); n++) {
               if(modifiers.hasKey(all_modifiers.get(n).nbtName)){
                  obtained_modifiers.add(all_modifiers.get(n));
               }
            }
            System.out.println("检查：已有属性obtained_modifiers:" + obtained_modifiers);

            //词条数目不够直接附加新属性
            if(obtained_modifiers.size() < modifierTypesCap){
               for (int n = 0; n < obtained_modifiers.size(); n++) {
                  //保证取一个新属性
                  if(available_modifiers.contains(obtained_modifiers.get(n))){
                     available_modifiers.remove(obtained_modifiers.get(n));
                     n = 0;
                  }
               }
               System.out.println("检查：保证全新的属性available_modifiers:" + available_modifiers);
               modifierType = ModifierUtils.getModifierWithWeight(available_modifiers,player.getRNG());
               this.addModifierLevelFor(modifiers, modifierType);
               player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
               return;
            }

            //其他情况
            for (int n = 0; n < obtained_modifiers.size(); n++) {
               //删除已有的不兼容/已满级的副属性
               if(!(available_modifiers.contains(obtained_modifiers.get(n)))){
                  obtained_modifiers.remove(obtained_modifiers.get(n));
                  n = 0;
               }
            }
            System.out.println("检查：可升级属性obtained_modifiers:" + obtained_modifiers);
            //升级已有的
            if(!obtained_modifiers.isEmpty()){
               int n = itemRand.nextInt(obtained_modifiers.size());
               if(Configs.wenscConfig.allowInfLeveling.ConfigValue || (obtained_modifiers.get(n).getMaxLevel() > modifiers.getInteger(obtained_modifiers.get(n).getNbtName()))){
                  player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + obtained_modifiers.get(n).color.toString() + obtained_modifiers.get(n).displayName + "§r属性已升级到" +
                          this.addModifierLevelFor(modifiers, obtained_modifiers.get(n))
                          + "级"));
               }
            }else {
               Minecraft.setErrorMessage("onItemLevelUp: No matching modifier to upgrade/apply.");
            }
            upgradeCount--;
         }
      }
      player.suppressNextStatIncrement();
   }
}
