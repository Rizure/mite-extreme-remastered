package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.CreativeModeTab;
import net.minecraft.Enchantment;
import net.minecraft.Item;
import net.minecraft.yq;
import org.spongepowered.asm.mixin.Shadow;

public class EnchantmentArrowDamageTrans extends Enchantment {
    protected EnchantmentArrowDamageTrans(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Shadow
    public String getNameSuffix() {
        return null;
    }

    @Shadow
    public boolean canEnchantItem(Item item) {
        return false;
    }
    public int getNumLevelsForVibranium() {
        return 7;
    }

    @Shadow
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }
}
