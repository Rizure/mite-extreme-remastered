package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;

import java.util.Random;

public class ModifierUtil {

   private static final Random itemRand = new Random();

   public static void performGeologyEffect(BlockBreakInfo info, ItemStack item_stack){
      Block block = info.block;
      float expReward = 0;
      if (block instanceof BlockOre && info.getMetadata() != 1) {
         expReward = ToolModifierTypes.GEOLOGY.getModifierValue(item_stack.getTagCompound());
         if (expReward != 0) {
            ItemStack dropItemStack = new ItemStack(info.block);
            ItemStack smeltingResult = RecipesFurnace.smelting().getSmeltingResult(dropItemStack, 5);
            if (smeltingResult != null) {
               int expectedExperience = smeltingResult.getExperienceReward();
               if(expectedExperience != 0){
                  info.world.spawnEntityInWorld(new EntityExperienceOrb(info.world, info.drop_x, info.drop_y + 0.5D, info.drop_z, (int) (smeltingResult.getExperienceReward() * expReward)));
               }
            }
         }
      }
   }

   public static void performSteadyEffect(BlockBreakInfo info, ItemStack item_stack){
      Block block = info.block;
      float expReward = 0;
      expReward = ToolModifierTypes.STEADY.getModifierValue(item_stack.getTagCompound()) * block.getBlockHardness(0);
      if (!block.isPortable(null, null, 0, 0, 0)) {
         int expReward_int = (int) (Math.min(expReward, 4.0F));
         for (int var1 = 0; var1 < expReward_int; var1++) {
            info.world.spawnEntityInWorld(new EntityExperienceOrb(info.world, info.drop_x, info.drop_y + 0.5D, info.drop_z, 1));
         }
         if (itemRand.nextFloat() < expReward - expReward_int) {
            info.world.spawnEntityInWorld(new EntityExperienceOrb(info.world, info.drop_x, info.drop_y + 0.5D, info.drop_z, 1));
         }
      }
   }

   public static void performBlessedOfNatureEffect(BlockBreakInfo info, ItemStack item_stack){
      Block block = info.block;
      float modifierBoostByHardness = block.getBlockHardness(0);
      if(modifierBoostByHardness < 1.0F){
         modifierBoostByHardness = 1.0F;
      }
      float baseModifierValue = ToolModifierTypes.BLESS_OF_NATURE.getModifierValue(info.getHarvesterItemStack().getTagCompound());
      if (itemRand.nextFloat() < modifierBoostByHardness * baseModifierValue) {
         info.getResponsiblePlayer().getFoodStats().addSatiation(2);
         info.getResponsiblePlayer().getFoodStats().addNutrition(1);
         if(info.getResponsiblePlayer().onServer()){
            info.getResponsiblePlayer().getAsEntityPlayerMP().addProtein(1600);
            info.getResponsiblePlayer().getAsEntityPlayerMP().addPhytonutrients(1600);
         }
         if (itemRand.nextFloat() < modifierBoostByHardness * baseModifierValue) {
            info.getResponsiblePlayer().heal(1);
         }
         if(info.block instanceof BlockCrops){
            for(int dx = -2; dx <= 2; dx++){
               for(int dz = -2; dz <= 2; dz++){
                  Block anotherCrops = info.world.getBlock(info.x + dx, info.y, info.z + dz);
                  int metadata = info.world.getBlockMetadata(info.x + dx, info.y, info.z + dz);
                  if(((BlockCrops) info.block).isMature(info.getMetadata())){
                     if(anotherCrops instanceof BlockCrops && itemRand.nextFloat() < baseModifierValue && dx * dz == 0){
                        info.world.setBlockMetadata(info.x + dx, info.y, info.z + dz, ((BlockCrops)anotherCrops).incrementGrowth(metadata), 2);
                        info.world.playAuxSFX(2005,info.x + dx, info.y, info.z + dz, 0);
                     }
                  }
                  if(anotherCrops instanceof BlockCrops && ((BlockCrops) anotherCrops).isBlighted(metadata) && itemRand.nextFloat() < baseModifierValue * 4){
                     info.world.setBlockMetadata(info.x + dx, info.y, info.z + dz, ((BlockCrops)anotherCrops).setBlighted(metadata, false), 2);
                     info.world.playAuxSFX(2005,info.x + dx, info.y, info.z + dz, 0);
                  }
               }
            }
         }
      }
   }

   public static float calcDamageAfterSwiftnessModifierPerform(ItemStack boots, EntityLiving target, float origin){
      if (boots != null) {
         float value = ArmorModifierTypes.SWIFTNESS.getModifierValue(boots.getTagCompound());
         if (target.getRNG().nextFloat() < value) {
            return 0.0F;
         }
      }
      return origin;
   }

   public static float calcInvincibleModifier(ItemStack item_stack){
      float invincible_modifier = 0.0F;
      if (item_stack != null && item_stack.getItem() instanceof ItemTool) {
         invincible_modifier += ToolModifierTypes.INVINCIBLE.getModifierValue(item_stack.getTagCompound());
      }
      return invincible_modifier;
   }

   public static float calcIndomitableFraction(EntityLiving target){
      float protection_fraction = 0.0F;
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
      return protection_fraction;
   }

   public static float calcDenseImmunityFraction(float total_protection, EntityLiving target, ItemStack[] wornItems){
      float immunity_fraction = 0.0F;
      if (total_protection > 0) {
         float immunity_base = target instanceof EntityPlayer ? 70.0F : 35.0F;
         float dense_modifier = 0.0F;
         if (target instanceof EntityPlayer) {
            ItemStack[] var6 = wornItems;
            int var7 = wornItems.length;

            for (int delta = 0; delta < var7; ++delta) {
               ItemStack armor = var6[delta];
               if (armor != null) {
                  dense_modifier += ArmorModifierTypes.DENSE.getModifierValue(armor.stackTagCompound);
               }
            }
         }
         dense_modifier = MathHelper.clamp_float(dense_modifier, 0.0F, 1.0F);
         immunity_fraction = total_protection / (total_protection + (immunity_base - 70.0F * dense_modifier));
      }
      return immunity_fraction;
   }
}
