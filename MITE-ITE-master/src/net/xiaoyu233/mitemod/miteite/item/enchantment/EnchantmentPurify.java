package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentPurify extends Enchantment {
   protected EnchantmentPurify(int id, yq rarity, int difficulty) {
      super(id, rarity, difficulty);
   }

   @Override
   public int getNumLevels() {
      return 4;
   }

   @Override
   public String getNameSuffix() {
      return "purify";
   }

   @Override
   public boolean canEnchantItem(Item item) {
      return item instanceof ItemLeggings;
   }

   @Override
   public boolean isOnCreativeTab(CreativeModeTab var1) {
      return var1 == CreativeModeTab.tabCombat;
   }
}
