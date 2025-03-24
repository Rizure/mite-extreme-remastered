package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public enum ArmorModifierTypes implements ItemModifierTypes{
    //Armor Modifiers
    DURABILITY_MODIFIER(0.15F,"持久",EnumModifierQuality.Common,5,(stack -> true)),
    PROTECTION_MODIFIER(0.125F,"保护",EnumModifierQuality.Common,4,(stack -> hasNotOtherProtectionModifier(stack,1))),
    PROJECTILE_PROTECTION_MODIFIER(1.25F,"弹射物保护", EnumModifierQuality.Uncommon,4,(stack -> hasNotOtherProtectionModifier(stack,3))),
    EXPLOSION_PROTECTION_MODIFIER(2.5F,"爆炸保护", EnumModifierQuality.Uncommon,4,(stack -> hasNotOtherProtectionModifier(stack,2))),
    FIRE_PROTECTION_MODIFIER(1.0F,"火焰保护", EnumModifierQuality.Uncommon,4,(stack -> hasNotOtherProtectionModifier(stack,0))),
    BLESSED_MODIFIER(1.0F,"神圣",EnumModifierQuality.Uncommon,4,(stack -> true)),
    STEADY_MODIFIER(0.25F,"稳定",EnumModifierQuality.Rare,4,(stack -> true)),
    SUN_AFFINITY(0.05f,"领航",EnumModifierQuality.Rare,4,itemStack -> itemStack.getItem() instanceof ItemBoots),
    INDOMITABLE(0.125f,"坚毅不倒",EnumModifierQuality.Rare,4,itemStack -> itemStack.getItem() instanceof ItemCuirass),
    LEVITY(1.0f,"轻盈",EnumModifierQuality.Rare,4,itemStack -> itemStack.getItem() instanceof ItemLeggings),
    NIGHT_AFFINITY(0.05f,"夜行",EnumModifierQuality.Rare,4,itemStack -> itemStack.getItem() instanceof ItemHelmet),
    IMMUNITY(0.2f,"免疫",EnumModifierQuality.Epic,4,itemStack -> itemStack.getItem() instanceof ItemHelmet),
    BULLDOZE(1.0f,"威吓",EnumModifierQuality.Epic,4,itemStack -> itemStack.getItem() instanceof ItemCuirass),
    ENERGETIC(0.06f,"海纳百川",EnumModifierQuality.Epic,4,itemStack -> itemStack.getItem() instanceof ItemLeggings),
    SWIFTNESS(0.1f,"灵敏",EnumModifierQuality.Epic,4,itemStack -> itemStack.getItem() instanceof ItemBoots);
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack>  canApplyTo;
    private final int maxLevel;

    ArmorModifierTypes(float levelAddition, String displayName, EnumModifierQuality quality,int maxLevel, Predicate<ItemStack> canApplyTo){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = quality.color;
        this.weight = quality.standard_weight;
        this.canApplyTo = canApplyTo;
        this.maxLevel = maxLevel;
    }

    private static boolean hasNotOtherProtectionModifier(ItemStack stack,int protectionType){
        switch (protectionType){
            //FIRE_PROTECTION_MODIFIER
            case 0:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER);
            //PROTECTION_MODIFIER
            case 1:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //EXPLOSION_PROTECTION_MODIFIER
            case 2:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //PROJECTILE_PROTECTION_MODIFIER
            case 3:
                return !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            default:
                return true;
        }
    }
    private static boolean hasNoOtherModifier(ItemStack stack, ArmorModifierTypes... modifierTypes){
        boolean couldApply = true;
        for (ArmorModifierTypes modifierType : modifierTypes) {
            if (ItemModifierTypes.hasModifier(stack, modifierType)) {
                couldApply = false;
            }
        }
        return couldApply;
    }

    @Override
    public boolean canApplyTo(ItemStack stack) {
        return this.canApplyTo.test(stack);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag) * (float) (Math.pow(getModifierLevel(itemTag), Configs.wenscConfig.modifierCurveDisturbance.ConfigValue) / Math.pow(this.maxLevel,Configs.wenscConfig.modifierCurveDisturbance.ConfigValue));
    }

    public int getModifierLevel(@Nullable NBTTagCompound itemTag) {
        int lvl = 0;
        if (itemTag != null && itemTag.hasKey("modifiers")) {
            NBTTagCompound modifiers = itemTag.getCompoundTag("modifiers");
            if (modifiers.hasKey(this.nbtName)) {
                lvl = modifiers.getInteger(this.nbtName);
            }
        }

        return lvl;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }
}
