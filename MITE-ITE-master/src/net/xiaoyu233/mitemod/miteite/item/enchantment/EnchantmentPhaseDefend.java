package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemClubMetal;

public class EnchantmentPhaseDefend extends Enchantment {
    protected EnchantmentPhaseDefend(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 1;
    }

    @Override
    public float enchantIndividualChance(int enchantmentLevel) {
        return enchantmentLevel < 50 ? 0 : 0.1f + 0.2f * (enchantmentLevel / 158f);
    }

    @Override
    public boolean enchantIndividually() {
        return true;
    }

    @Override
    public String getNameSuffix() {
        return "phasedefend";
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return item instanceof ItemArmor || item instanceof ItemSword;
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab var1) {
        return var1 == CreativeModeTab.tabCombat;
    }
}
