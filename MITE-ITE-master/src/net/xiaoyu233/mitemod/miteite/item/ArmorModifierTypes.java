package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public enum ArmorModifierTypes implements ItemModifierTypes{
    //Armor Modifiers
    DURABILITY_MODIFIER(0.15F,"持久",EnumChatFormat.WHITE,50,5,(stack -> true)),
    PROJECTILE_PROTECTION_MODIFIER(1.0F,"弹射物保护", EnumChatFormat.AQUA,25,5,(stack -> hasNotOtherProtectionModifier(stack,3))),
    EXPLOSION_PROTECTION_MODIFIER(2.0F,"爆炸保护", EnumChatFormat.AQUA,25,5,(stack -> hasNotOtherProtectionModifier(stack,2))),
    FIRE_PROTECTION_MODIFIER(0.75F,"火焰保护", EnumChatFormat.AQUA,25,5,(stack -> hasNotOtherProtectionModifier(stack,0))),
    PROTECTION_MODIFIER(0.2F,"保护",EnumChatFormat.AQUA,25,5,(stack -> hasNotOtherProtectionModifier(stack,1))),
    STEADY_MODIFIER(0.15F,"稳定",EnumChatFormat.AQUA,25,5,(stack -> true)),
    BLESSED_MODIFIER(0.5F,"神圣",EnumChatFormat.AQUA,25,5,(stack -> true)),
    INDOMITABLE(0.1f,"坚毅不倒",EnumChatFormat.LIGHT_PURPLE,10,4,itemStack -> itemStack.getItem() instanceof ItemCuirass),
    ENERGETIC(0.1f,"海纳百川",EnumChatFormat.LIGHT_PURPLE,10,5,itemStack -> itemStack.getItem() instanceof ItemLeggings),
    SUN_AFFINITY(0.05f,"领航",EnumChatFormat.LIGHT_PURPLE,10,5,itemStack -> itemStack.getItem() instanceof ItemBoots),
    NIGHT_AFFINITY(0.05f,"夜行",EnumChatFormat.LIGHT_PURPLE,10,5,itemStack -> itemStack.getItem() instanceof ItemHelmet),
    IMMUNITY(0.2f,"免疫",EnumChatFormat.RED,5,4,itemStack -> itemStack.getItem() instanceof ItemHelmet),
    SWIFTNESS(0.05f,"灵敏",EnumChatFormat.RED,5,5,itemStack -> itemStack.getItem() instanceof ItemBoots);
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack>  canApplyTo;
    private final int maxLevel;

    ArmorModifierTypes(float levelAddition, String displayName, EnumChatFormat color, int weight,int maxLevel, Predicate<ItemStack> canApplyTo){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = color;
        this.weight = weight;
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
        return this.levelAddition * getModifierLevel(itemTag);
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
