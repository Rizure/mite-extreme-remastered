package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;

import java.util.Random;

public class GemUtil {

   private static final Random itemRand = new Random();

   public static void performGoldizeEffect(BlockBreakInfo info, ItemStack item_stack){
      Block block = info.block;
      if (info.getResponsiblePlayer() instanceof EntityPlayer) {
         float goldChance = info.getHarvesterItemStack().getGemMaxNumeric(GemModifierTypes.goldize) * block.getBlockHardness(0);
         if (!block.isPortable(null, null, 0, 0, 0)) {
            int goldCount = (int) (Math.min(goldChance, 2.0F));
            for (int var1 = 0; var1 < goldCount; var1++) {
               info.world.spawnEntityInWorld(new EntityItem(info.world, info.x, info.y, info.z, new ItemStack(Item.goldNugget, 1)));
            }
            if (itemRand.nextFloat() < goldChance - goldCount) {
               info.world.spawnEntityInWorld(new EntityItem(info.world, info.x, info.y, info.z, new ItemStack(Item.goldNugget, 1)));
            }
         }
      }
   }

   public static float performPredationEffect(ItemStack stackAttackedWith, float origin, Entity responsibleEntity, EntityLiving target){
      if (stackAttackedWith != null && stackAttackedWith.getGemMaxLevel(GemModifierTypes.predation) != 0) {
         if (responsibleEntity instanceof EntityLiving) {
            float predation_boost = stackAttackedWith.getGemMaxNumeric(GemModifierTypes.predation);
            predation_boost *= responsibleEntity.getAsEntityLivingBase().getMaxHealth() - target.getMaxHealth();
            if (predation_boost > 0) {
               origin += predation_boost;
            }
         }
      }
      return origin;
   }

   public static float calcProtectionFraction(float protection_fraction, EntityLiving target){
      if (target instanceof EntityPlayer) {
         protection_fraction = ((EntityPlayer) target).getGemSumNumeric(GemModifierTypes.protection);
      }
      protection_fraction = MathHelper.clamp_float(protection_fraction, 0.0F, 1.0F);
      return protection_fraction;
   }

   public static float calcExecuteBoost(ItemStack stackAttackedWith, EntityLiving target){
      float execute_boost = 0.0F;
      if (stackAttackedWith != null && stackAttackedWith.getGemMaxLevel(GemModifierTypes.execute) != 0) {
         execute_boost = stackAttackedWith.getGemMaxNumeric(GemModifierTypes.execute);
         execute_boost *= (1 - target.getHealthFraction());
      }
      return execute_boost;
   }
}
