package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Mixin(ItemTool.class)
public class ItemToolTrans extends Item implements IUpgradableItem {
   @Shadow
   protected List blocks_effective_against = new ArrayList();
   @Shadow
   protected List materials_effective_against = new ArrayList();
   @Shadow
   private float damageVsEntity;
   @Shadow
   private Material effective_material;
   @Final
   private BiFunction<Integer,Boolean,Integer> expForLevel;

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInitExpForLevel(int par1, Material material,CallbackInfo callbackInfo){
      if (material == Material.copper || material == Material.silver || material == Material.gold){
         this.expForLevel = this.createExpForLevel(300,75,25);
      }else if (material == Material.iron){
         this.expForLevel = this.createExpForLevel(300,75,25);
      }else if (material == Material.ancient_metal){
         this.expForLevel = this.createExpForLevel(450,150,75);
      } else if (material == Material.mithril) {
         this.expForLevel = this.createExpForLevel(750,250,125);
      }else if (material == Material.adamantium){
         this.expForLevel = this.createExpForLevel(900,300,150);
      }else if (material == Materials.vibranium){
         this.expForLevel = this.createExpForLevel(1200,400,200);
      }else {
         this.expForLevel = this.createExpForLevel(450,150,75);
      }
//      this.expForLevel = this.createExpForLevel(1,1,0);
   }

   private BiFunction<Integer, Boolean, Integer> createExpForLevel(int start, int base, int increase){
      return (level, isWeapon) -> level == 0 ? start : base + level * increase * (isWeapon ? 2 : 1 );
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      super.addInformation(item_stack, player, info, extended_info, slot);
      if (item_stack.hasTagCompound()) {
         if (item_stack.getTagCompound().hasKey("tool_level")) {
            int tool_level = item_stack.getTagCompound().getInteger("tool_level");
            int maxToolLevel = this.getMaxToolLevel(item_stack);
            if (this.isMaxToolLevel(item_stack)) {
               info.add("工具等级:§6已达到最高级" + maxToolLevel);
            } else {
               info.add("工具等级:" + tool_level + "/" + maxToolLevel);
               if (item_stack.getTagCompound().hasKey("tool_exp")) {
                  info.add("工具经验" + EnumChatFormat.WHITE + item_stack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(tool_level, this.isWeapon(item_stack.getItem())));
               }
            }
         }

         int forgingGrade;
         if (item_stack.getTagCompound().hasKey("forging_grade") && (forgingGrade = item_stack.getTagCompound().getInteger("forging_grade")) != 0) {
            info.add("§5强化等级:§6" + StringUtil.intToRoman(forgingGrade));
            if (extended_info) {
               info.add("  §7装备经验增加:§a" + this.getEquipmentExpBounce(item_stack) * 100 + "%");
               if (this.isWeapon(item_stack.getItem())){
                  info.add("  §9攻击力增加:§6" + ItemStack.field_111284_a.format(this.getEnhancedDamage(item_stack)));
               }else {
                  info.add("  §9挖掘速度加:§6" + ItemStack.field_111284_a.format(item_stack.getEnhanceFactor() * 100) + "%");
               }
            }
         }

         if (extended_info) {
            info.add("§5宝石:");
            info.add(" §3攻击增加:§6" + ItemStack.field_111284_a.format(item_stack.getGemMaxNumeric(GemModifierTypes.damage)));
            info.add(" §3效率增加:§6" + ItemStack.field_111284_a.format(item_stack.getGemMaxNumeric(GemModifierTypes.haste)));
            NBTTagCompound compound = item_stack.stackTagCompound.getCompoundTag("modifiers");
            if (!compound.hasNoTags()) {
               info.add("工具强化:");
               ToolModifierTypes[] var8 = ToolModifierTypes.values();
               for (ToolModifierTypes value : var8) {
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
            }
         }
      }
   }

   private int applyCalculateDurabilityModifier(int origin, ItemStack stack) {
      NBTTagCompound compound = stack.getTagCompound();
      return (int)((float)origin * Math.max(0.0F, 1.0F - ToolModifierTypes.DURABILITY_MODIFIER.getModifierValue(compound)));
   }

   @Overwrite
   public boolean canBlock() {
      return false;
   }

   protected float getAttackDamageBounce(ItemStack stack) {
      return ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound);
   }

   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      Multimap<String, AttributeModifier> var1 = super.getAttrModifiers(stack);
      var1.put(GenericAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Tool modifier", (double)this.damageVsEntity + (double)this.getAttackDamageBounce(stack) + this.getEnhancedDamage(stack), 0));
      return var1;
   }

   @Shadow
   private float getBaseHarvestEfficiency(Block block) {
      return 0.0F;
   }

   @Shadow
   private float getCombinedDamageVsEntity() {
      return 0.0F;
   }

   private float getEnhancedDamage(ItemStack itemStack) {
      return this.isWeapon(itemStack.getItem()) ? (float) (itemStack.getEnhanceFactor() * (double) this.getCombinedDamageVsEntity()) : 0.0f;
   }

   public int getExpReqForLevel(int level, boolean isSword) {
      return this.expForLevel.apply(level, isSword);
   }

   public EnumItemInUseAction getItemInUseAction(ItemStack par1ItemStack, EntityPlayer player) {
//      return EnchantmentManager.hasEnchantment(par1ItemStack, Enchantments.DEFENCED) ? EnumItemInUseAction.BLOCK : null;
      return player.canDefense() ? EnumItemInUseAction.BLOCK : null;
   }
   @Overwrite
   public float getMaterialHarvestEfficiency() {
      if (this.effective_material == Material.wood) {
         return 1.0F;
      } else if (this.effective_material == Material.flint) {
         return 1.25F;
      } else if (this.effective_material == Material.obsidian) {
         return 1.5F;
      } else if (this.effective_material == Material.rusted_iron) {
         return 1.25F;
      } else if (this.effective_material == Material.copper) {
         return 1.75F;
      } else if (this.effective_material == Material.silver) {
         return 1.75F;
      } else if (this.effective_material == Material.gold) {
         return 1.75F;
      } else if (this.effective_material == Material.iron) {
         return 2.0F;
      } else if (this.effective_material == Material.mithril) {
         return 2.5F;
      } else if (this.effective_material == Material.adamantium) {
         return 3.0F;
      } else if (this.effective_material == Material.diamond) {
         return 2.5F;
      } else if (this.effective_material == Material.ancient_metal) {
         return 2.0F;
      } else if (this.effective_material == Materials.vibranium) {
         return 3.5F;
      } else {
         Minecraft.setErrorMessage("getMaterialHarvestEfficiency: tool material not handled");
         return 0.0F;
      }
   }

   @Shadow
   public int getMaterialHarvestLevel() {
      return 0;
   }

   public int getMaxDamage(ItemStack item_stack) {
      return super.getMaxDamage(item_stack);
   }

   @Overwrite
   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return Configs.wenscConfig.playerDefenceMaxTime.ConfigValue;
   }

   public float getMeleeDamageBonus(ItemStack stack) {
      return this.getCombinedDamageVsEntity() + ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound) + this.getEnhancedDamage(stack);
   }

   public final float getMultipliedHarvestEfficiency(Block block, ItemStack itemStack, EntityPlayer player) {
      float commonModifierValue = ToolModifierTypes.EFFICIENCY_MODIFIER.getModifierValue(itemStack.getTagCompound());
      float unnaturalModifierValue = ToolModifierTypes.UNNATURAL_MODIFIER.getModifierValue(itemStack.getTagCompound());
      float gemModifierValue = itemStack.getGemMaxNumeric(GemModifierTypes.haste);

      if (unnaturalModifierValue > 0.0F) {
         int deltaLevel = itemStack.getItem().getMaterialForRepairs().getMinHarvestLevel() - block.blockMaterial.getMinHarvestLevel();
         if (deltaLevel > 0) {
            commonModifierValue += (float)deltaLevel * unnaturalModifierValue;
         }
      }
      commonModifierValue += gemModifierValue;

      float enhanceModifierValue = (float) (1 + itemStack.getEnhanceFactor());
      float result = this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue) * enhanceModifierValue;
      if(player.isInWater() || player.isInRain()){
         result *= 1 + ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierValue(itemStack.getTagCompound());
      }
      return result;
   }

   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer player) {
      return this.isEffectiveAgainstBlock(block, metadata) ? this.getMultipliedHarvestEfficiency(block, itemStack, player) : super.getStrVsBlock(block, metadata);
   }

   @Overwrite
   public final int getToolDecayFromAttackingEntity(ItemStack item_stack, EntityLiving entity_living_base) {
      return this.applyCalculateDurabilityModifier(Math.max((int)(100.0F * ReflectHelper.dyCast(ItemTool.class, this).getBaseDecayRateForAttackingEntity(item_stack)), 1), item_stack);
   }

   @Shadow
   private int getToolDecayFromBreakingBlock(BlockBreakInfo info) {
      return 0;
   }

   public boolean hasExpAndLevel() {
      return true;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLivingBase, EntityLiving par3EntityLivingBase) {
      if (par2EntityLivingBase.onClient()) {
         Minecraft.setErrorMessage("ItemTool.hitEntity: called on client?");
      }

      if (par3EntityLivingBase instanceof EntityPlayer && ((EntityPlayer)par3EntityLivingBase).inCreativeMode()) {
         return false;
      } else {
         par1ItemStack.tryDamageItem(DamageSource.generic, this.getToolDecayFromAttackingEntity(par1ItemStack, par2EntityLivingBase), par3EntityLivingBase);
         return true;
      }
   }

   @Shadow
   public boolean isEffectiveAgainstBlock(Block block, int metadata) {
      return false;
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return this.getMaxToolLevel(itemStack) <= this.getToolLevel(itemStack);
   }

   public int getMaxToolLevel(ItemStack itemStack){
      return 6 + itemStack.getMaterialForRepairs().getMinHarvestLevel() * 2 + itemStack.getForgingGrade();
   }

   public boolean isWeapon(Item item) {
      return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer || item instanceof ItemCudgel;
   }

   @Overwrite
   public boolean onBlockDestroyed(BlockBreakInfo info) {
      if (info.world.isRemote) {
         Minecraft.setErrorMessage("ItemTool.onBlockDestroyed: called on client?");
      }

      Block block = info.block;
      ItemStack item_stack = info.getHarvesterItemStack();
      if (!(block instanceof BlockOre && info.getMetadata() == 1) && block != Block.oreDiamond && block != Block.oreCoal && block != Block.oreEmerald && block != Block.oreRedstone && block != Block.oreLapis && block != Block.oreNetherQuartz){
         float expReward = ToolModifierTypes.GEOLOGY.getModifierValue(info.getHarvesterItemStack().getTagCompound());
         if (expReward != 0){
            ItemStack dropItemStack = new ItemStack(info.block);
            ItemStack smeltingResult = RecipesFurnace.smelting().getSmeltingResult(dropItemStack, 5);
            if (smeltingResult != null){
               info.world.spawnEntityInWorld(new EntityExperienceOrb(info.world, info.drop_x, info.drop_y + 0.5D, info.drop_z, (int) (smeltingResult.getExperienceReward() * expReward)));
            }
         }
      }
      if(itemRand.nextFloat() < ToolModifierTypes.BLESS_OF_NATURE.getModifierValue(info.getHarvesterItemStack().getTagCompound())){
         info.getResponsiblePlayer().getFoodStats().addSatiation(1);
         info.getResponsiblePlayer().getFoodStats().addNutrition(2);
         if(itemRand.nextFloat() < ToolModifierTypes.BLESS_OF_NATURE.getModifierValue(info.getHarvesterItemStack().getTagCompound())){
            info.getResponsiblePlayer().heal(1);
         }
      }
      if (item_stack.isItemStackDamageable() && !block.isPortable(info.world, info.getHarvester(), info.x, info.y, info.z) && !info.isResponsiblePlayerInCreativeMode() && info.getBlockHardness() > 0.0F && this.getStrVsBlock(block, info.getMetadata()) > 1.0F) {
         if (!(item_stack.getItem() instanceof ItemSword) && this.isEffectiveAgainstBlock(info.block, info.getMetadata()) &&!item_stack.getItem().isMaxToolLevel(item_stack)) {
            this.addExpForTool(info.getHarvesterItemStack(), info.getResponsiblePlayer(), this.getExpForBlockBreak(info));
         }

         info.getHarvesterItemStack().tryDamageItem(DamageSource.generic, this.applyCalculateDurabilityModifier(this.getToolDecayFromBreakingBlock(info), info.getHarvesterItemStack()), info.getHarvester());
         return true;
      } else {
         return false;
      }
   }

   protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo){
      return 2 * (int)Math.pow(blockBreakInfo.block.getMinHarvestLevel(0),2);
   }

   @Override
   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      super.addExpForTool(stack, player, (int) (exp * (this.getEquipmentExpBounce(stack) + 1)));
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      ToolModifierTypes modifierType;
      //全可附加属性：检查可附加，不检查等级
      List<ToolModifierTypes> all_modifiers = ModifierUtils.getAllToolModifiers(stack);
      //目前可附加属性：检查可附加，检查等级
      List<ToolModifierTypes> available_modifiers = ModifierUtils.getAllCanBeAppliedToolModifiers(stack);
      //已拥有属性：未初始化
      List<ToolModifierTypes> obtained_modifiers = new ArrayList<>();
      //确认副属性条目数上限
      int modifierTypesCap = Math.min(all_modifiers.size(), 4 + (stack.getForgingGrade() / 5));
      //首次升级（目前没问题）
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
      //之后的升级（仍然有问题）
      else {
         int upgradeCount = itemRand.nextInt(5) == 0 ? 2 : 1;
         while (upgradeCount > 0){
            //初始化
            available_modifiers = ModifierUtils.getAllCanBeAppliedToolModifiers(stack);
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
   }

   //Client+Server
   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if (this.getItemInUseAction(player.getHeldItemStack(), player) == EnumItemInUseAction.BLOCK) {
         player.setHeldItemInUse();
         return true;
      }
      return false;
   }

   @Override
   public void onItemUseFinish(ItemStack item_stack, World world, EntityPlayer player) {
//      super.onItemUseFinish(item_stack, world, player);
      if (player.onServer()){
         player.setDefenseCooldown(Configs.wenscConfig.playerDefenseCooldown.ConfigValue);
      }
   }

   @Override
   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
      super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
      if (par3EntityPlayer.onServer()){
         par3EntityPlayer.setDefenseCooldown(Configs.wenscConfig.playerDefenseCooldown.ConfigValue);
      }
   }
}
