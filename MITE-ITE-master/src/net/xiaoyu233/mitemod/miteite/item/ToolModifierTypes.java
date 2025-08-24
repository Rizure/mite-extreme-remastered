package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.util.function.Predicate;


//TODO Weapon Modifiers{
//  自然之力 - 攻击一个怪物：20%概率燃烧目标.20%概率给予玩家速度效果.20%概率给予玩家力量效果。
//  可燃 - 火焰附加
//  剧毒 - 给予目标中毒效果
// }
// Tool Modifiers{
//  震击 - 挖掘方块时充能,到达一定层数后提供一段时间的急迫和速度
//  圣盾 - 当这个工具在你手中时,你将被赋予抗火效果.此外,损害减少,但耐久也会随之减低。
// }

public enum ToolModifierTypes implements ItemModifierTypes{
    //Tool Modifiers
    EFFICIENCY_MODIFIER(0.2F,"急速",EnumModifierQuality.Common, (ToolModifierTypes::isNotWeapon),12),
    DURABILITY_MODIFIER(0.15F,"耐久",EnumModifierQuality.Common,(stack -> true),5),
    DAMAGE_MODIFIER(1.0F,"锋利", EnumModifierQuality.Common, stack -> hasNoOtherDamageModifier(stack,2) && isWeapon(stack),12),
    SMITE(2.0F,"神圣",EnumModifierQuality.Uncommon,stack -> hasNoOtherDamageModifier(stack,0) && isWeapon(stack),12),
    BANE_OF_ARTHROPOD(2.0F,"节肢杀手",EnumModifierQuality.Uncommon,stack -> hasNoOtherDamageModifier(stack,1) && isWeapon(stack),12),
    INVINCIBLE(1.25f,"不动如山",EnumModifierQuality.Uncommon,(stack -> true),4),
    SLOWDOWN_MODIFIER(1.25F,"织网",EnumModifierQuality.Rare, ToolModifierTypes::isWeapon,4),
    UNNATURAL_MODIFIER(0.125f,"超自然",EnumModifierQuality.Rare, ToolModifierTypes::isNotWeapon,4),
    APOCALYPSE(1.25f,"启示录",EnumModifierQuality.Rare,(ToolModifierTypes::isWeapon),4),
    AQUADYNAMIC_MODIFIER(0.375F,"喜水",EnumModifierQuality.Rare, (ToolModifierTypes::isNotWeapon),4),
    STEADY(1.0F,"老成",EnumModifierQuality.Rare, (ToolModifierTypes::isNotWeapon),4),
    DEMON_POWER(0.15f,"恶魔之力",EnumModifierQuality.Epic, ToolModifierTypes::isWeapon, 4),
    PURGATORY(0.15f,"炼狱",EnumModifierQuality.Epic, ToolModifierTypes::isWeapon, 4),
    OVERHEAT(0.15f,"过热",EnumModifierQuality.Epic, ToolModifierTypes::isWeapon, 4),
    DISCORD(6.0f,"混沌",EnumModifierQuality.Epic,ToolModifierTypes::isWeapon,4),
    URBAN_LEGEND(0.25f,"都市传说",EnumModifierQuality.Legend,ToolModifierTypes::isWeapon,4),
    GEOLOGY(0.25f,"地质学",EnumModifierQuality.Legend,itemStack -> itemStack.getItem() instanceof ItemPickaxe && WithoutMiningModifier(itemStack,0),4),
    BLESS_OF_NATURE(0.05f,"自然祝福",EnumModifierQuality.Legend,(stack -> true),4),
    MELTING(0.25f,"自动冶炼",EnumModifierQuality.Legend,itemStack -> itemStack.getItem() instanceof ItemPickaxe && WithoutMiningModifier(itemStack,1),4),
//    PLUNDER(0.02f,"劫掠",EnumModifierQuality.Legend, ToolModifierTypes::isWeapon, 4)
    ;
    

//    BEHEADING_MODIFIER(0.02f, "斩首" , EnumChatFormats.DEAR_GREEN,1, ToolModifierTypes::isWeapon, 5);
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack> canApplyTo;
    private final int maxLevel;
    ToolModifierTypes(float levelAddition, String displayName, EnumModifierQuality quality, Predicate<ItemStack> canApplyTo,int maxLevel){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = quality.color;
        this.weight = quality.standard_weight;
        this.canApplyTo = canApplyTo;
        this.maxLevel = maxLevel;
    }

    public static boolean isWeapon(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer || item instanceof ItemCudgel;
    }
    public static boolean isNotWeapon(ItemStack stack) {
        Item item = stack.getItem();
        return !(item instanceof ItemSword || item instanceof ItemCudgel);
    }
    @Override
    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag) * (float) (Math.pow(getModifierLevel(itemTag), Configs.wenscConfig.modifierCurveDisturbance.ConfigValue) / Math.pow(this.maxLevel,Configs.wenscConfig.modifierCurveDisturbance.ConfigValue));
    }

    @Override
    public int getModifierLevel(NBTTagCompound itemTag) {
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
        return weight;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }

    @Override
    public boolean canApplyTo(ItemStack itemStack) {
        return this.canApplyTo.test(itemStack);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
    private static boolean WithoutMiningModifier(ItemStack stack,int MiningType){
        switch (MiningType){
            case 0:
                return !ItemModifierTypes.hasModifier(stack,MELTING);
            case 1:
                return !ItemModifierTypes.hasModifier(stack,GEOLOGY);
            default:
                return true;
        }

    }
    private static boolean hasNoOtherDamageModifier(ItemStack stack,int damageType){
        switch (damageType){
            //SMITE
            case 0:
                return !ItemModifierTypes.hasModifier(stack,DAMAGE_MODIFIER) && !ItemModifierTypes.hasModifier(stack,BANE_OF_ARTHROPOD);
            //BANE_OF_ARTHROPOD
            case 1:
                return !ItemModifierTypes.hasModifier(stack,DAMAGE_MODIFIER) && !ItemModifierTypes.hasModifier(stack,SMITE);
            //SHARPNESS
            case 2:
                return !ItemModifierTypes.hasModifier(stack,SMITE) && !ItemModifierTypes.hasModifier(stack,BANE_OF_ARTHROPOD);
            default:
                return true;
        }
    }
}
