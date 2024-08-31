package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemElementPowder extends Item{
    String elementName = "";
    public ItemElementPowder(int id, String elementName) {
        super(id, Materials.invincible, "powder");
        this.elementName = elementName;
        this.setMaxStackSize(64);
        this.setCreativeTab(CreativeModeTab.tabMisc);
    }
    @Override
    public boolean isHarmedByAcid() {
        return false;
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            switch (this.elementName) {
                case "dark":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Deep Dark Fantasy +O>", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化恢复宝石", new Object[0]));
                    break;
                case "freeze":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("1+1=⑨", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("幻想乡没有巴士", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化急迫、保护宝石", new Object[0]));
                    break;
                case "wind":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("沃像风~一样自由", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("就像你的温楼~无法挽留", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化伤害、生命、急迫宝石", new Object[0]));
                    break;
                case "blaze":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("我会善尽我的职责，", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("在场的每一个人，都不会死！", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化伤害、保护宝石", new Object[0]));
                    break;
                case "metal":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("萃集的梦、虚幻、随后百鬼夜行", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化伤害、保护、急迫宝石", new Object[0]));
                    break;
                case "thunder":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("何方道友在此渡劫", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化伤害宝石", new Object[0]));
                    break;
                case "wood":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("木之妖精", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化生命、急迫、保护、恢复宝石", new Object[0]));
                    break;
                case "liquid":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("くし", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化生命、恢复宝石", new Object[0]));
                    break;
                case "earth":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("轻尘清寒", new Object[0]));
                    info.add(EnumChatFormat.BLUE + Translator.getFormatted("可转化生命、恢复宝石", new Object[0]));
                    break;
                default:
                    break;
            }
        }
    }
}
