package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.EnchantmentInstance;
import net.minecraft.EntityInsentient;
import net.minecraft.ItemStack;

import java.util.List;
import java.util.Random;

import static net.minecraft.EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability;

public class MonsterUtil {
    public static void addDefaultArmor(int day_count, EntityInsentient monster, boolean haveAll) {
       Random rand = monster.getRNG();
       if(day_count > 8){
           if (rand.nextInt(Math.max(3 - day_count / 32, 1)) == 0 || haveAll) {
               for(int index = 3; index >= 0; --index) {
                   if (rand.nextBoolean() || haveAll) {
                       monster.setCurrentItemOrArmor(index + 1, (new ItemStack(Constant.ARMORS[index][getRandomItemTier(rand,day_count,index)])).randomizeForMob(monster, day_count > 32));
                   }
               }
           }
       }
    }

    public static ItemStack addRandomEnchantment(Random par0Random, ItemStack itemStack, int enchantment_levels,int maxTotalLevel,int maxEnchantmentNum) {
        enchantment_levels = getEnchantmentLevelsAlteredByItemEnchantability(enchantment_levels, itemStack.getItem());
        if (enchantment_levels >= 1) {
            List<EnchantmentInstance> enchantmentList = EnchantmentUtil.buildEnchantmentList(par0Random, itemStack, enchantment_levels,maxTotalLevel, maxEnchantmentNum);
            if (enchantmentList != null) {
                for (EnchantmentInstance enchantment : enchantmentList) {
                    itemStack.addEnchantment(enchantment.enchantmentobj, enchantment.enchantmentLevel);
                }
            }

        }
        return itemStack;
    }

    public static int getRandomItemTier(Random rand,int day_count,int equip_index){
        return getRandomItemTier(rand,day_count / 8, day_count / 16,equip_index);
    }

    public static int getRandomItemTier(Random random, int maxTier, int minTier, int equip_index) {
        if(maxTier > Constant.ARMORS[equip_index].length - 1){
            maxTier = Constant.ARMORS[equip_index].length - 1;
        }
        if(minTier < 0){
            maxTier = 0;
        }
        int result = maxTier;
        while (result > minTier){
            if(random.nextInt(4) == 0){
                return result;
            }
            result --;
        }
        return result;
    }
}
