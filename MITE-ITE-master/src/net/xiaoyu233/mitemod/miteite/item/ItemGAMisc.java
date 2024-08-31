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
                default:
                    break;
            }
        }
    }
}
