package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentLuckOfTheSea extends Enchantment {
    protected EnchantmentLuckOfTheSea(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    public String getNameSuffix() {
        return "luckOfTheSea";
    }

    @Override
    public boolean canEnchantItem(Item var1) {
        return var1 instanceof ItemFishingRod;
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab var1) {
        return var1 == CreativeModeTab.tabTools;
    }
}
