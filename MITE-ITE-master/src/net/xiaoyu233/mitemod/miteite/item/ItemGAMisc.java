package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemGAMisc extends Item {
    String tag = "";
    public ItemGAMisc(int id, String tag) {
        super(id, Materials.invincible, "miscItem");
        this.tag = tag;
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
            switch (this.tag) {
                case "pants":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("《海角之恋》", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("胖次东南飞，五里一徘徊", new Object[0]));
                    break;
                case "bad_apple":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("BAD APPLE ! !", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Lotus Land Story", new Object[0]));
                    break;
                case "gowther":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Goat's Sin of Lust", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Gowther", new Object[0]));
                    break;
                case "merlin":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Boar's Sin of Gluttony", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Merlin", new Object[0]));
                    break;
                case "ban":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Fox's Sin of Greed", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Ban", new Object[0]));
                    break;
                case "king":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Grizzly's Sin of Sloth", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("King", new Object[0]));
                    break;
                case "meliodas":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Dragon's Sin of Wrath", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Meliodas", new Object[0]));
                    break;
                case "diane":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Serpent's Sin of Envy", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Diane", new Object[0]));
                    break;
                case "escanor":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Lion's Sin of Pride", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Escanor", new Object[0]));
                    break;
                case "cracked_key":
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("唔哦哦哦哦哦哦！", new Object[0]));
                    info.add(EnumChatFormat.BROWN + Translator.getFormatted("Niiiiiiiiice!!!", new Object[0]));
                    break;
                default:
                    break;
            }
        }
    }
}
