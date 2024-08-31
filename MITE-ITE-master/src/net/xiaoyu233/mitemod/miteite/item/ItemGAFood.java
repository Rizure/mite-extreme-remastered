package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.util.List;
import java.util.Objects;

public class ItemGAFood extends ItemFood {
    String tag = "";
    public ItemGAFood(int id, Material material, int satiation, int nutrition, int sugar_content, boolean has_protein, boolean has_essential_fats, boolean has_phytonutrients, String tag) {
        super(id,material,satiation,nutrition,sugar_content,has_protein,has_essential_fats,has_phytonutrients,"foodItem");
        this.tag = tag;
    }
    protected void onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        super.onEaten(par1ItemStack,par2World,par3EntityPlayer);
        if(Objects.equals(this.tag, "risky_agent")){
            int outcome = itemRand.nextInt(100);
            if (!par2World.isRemote) {
                if(outcome > 90){
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.resistance.id, 30 * 20, 0));
                }else if(outcome < 10){
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.fireResistance.id, 60 * 20, 0));
                }else {
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.nightVision.id, 180 * 20, 0));
                }
                if(outcome % 2 == 0) {
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, 22 * 20, 0));
                }
                if(outcome % 17 == 0){
                    par3EntityPlayer.attackEntityFrom(new Damage(DamageSource.poison,Math.max(0F, par3EntityPlayer.getHealth() - 2.0F)));
                }
                if(outcome % 13 == 0){
                    par3EntityPlayer.getFoodStats().addNutrition(20);
                    par3EntityPlayer.getFoodStats().addSatiation(20);
                }
            }
        }
        if(Objects.equals(this.tag, "suspicious_stew")){
            int outcome = 8 + itemRand.nextInt(24);
            par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, outcome * 20, 4));
        }if(Objects.equals(this.tag, "spider_leg")){
            par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.poison.id, 12 * 20, 4));
        }
        if(Objects.equals(this.tag, "wuzhi")){
            //saturation
            par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.field_76443_y.id, 1200 * 20, 4));
        }
        if(Objects.equals(this.tag, "chikitan")){
            int outcome = itemRand.nextInt(par3EntityPlayer.getEnhanceLevel() + 1);
            par3EntityPlayer.addExperience(Configs.wenscConfig.emeraldExp.ConfigValue * 2);
            if(outcome == 0){
                par3EntityPlayer.setEnhanceLevel(par3EntityPlayer.getEnhanceLevel() + 1);
            }
        }
    }
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        super.addInformation(item_stack,player,info,extended_info,slot);
        if (extended_info) {
            info.add(" ");
            switch (this.tag) {
                case "bugu":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("虚度周而复始的时光", new Object[0]));
                    break;
                case "wuzhi":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("不食五谷、吸风饮露", new Object[0]));
                    break;
                case "demonPill":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("这颗妖丹散逸着灵力", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("需要在熔炉里炼制", new Object[0]));
                    break;
                case "demonPillCooked":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("sore wa dou kana", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("附魔金战士是不会倒下的", new Object[0]));
                    break;
                case "cubeSugar":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("滂甜", new Object[0]));
                    break;
                case "chikitan":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("伐毛洗髓!", new Object[0]));
                    break;
                case "spicy_strip":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("吃了还想吃", new Object[0]));
                    break;
                case "zombie_brain":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("歪比歪比", new Object[0]));
                    break;
                case "spider_leg":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("幽魂之地特产", new Object[0]));
                    break;
                case "cold_spider_leg":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("山女的腿(什", new Object[0]));
                    break;
                case "zombie_brain_cooked":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("歪比巴卜", new Object[0]));
                    break;
                case "risky_agent":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("用了就离不开它", new Object[0]));
                    break;
                case "suspicious_stew":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("信春哥!", new Object[0]));
                    break;
                default:
                    break;
            }
        }
    }
}
