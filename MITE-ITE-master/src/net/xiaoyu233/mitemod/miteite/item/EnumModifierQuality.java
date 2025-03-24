package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.EnumChatFormat;

public enum EnumModifierQuality {
    Common(EnumChatFormat.WHITE, "Common", 50),
    Uncommon(EnumChatFormat.AQUA, "Uncommon", 25),
    Rare(EnumChatFormat.BLUE, "Rare", 10),
    Epic(EnumChatFormat.DARK_PURPLE, "Epic", 5),
    Legend(EnumChatFormat.LIGHT_PURPLE, "Legend", 2),
    ;
    public final EnumChatFormat color;
    public final String name;
    public final int standard_weight;

    private EnumModifierQuality(EnumChatFormat color, String name, int standard_weight) {
        this.color = color;
        this.name = name;
        this.standard_weight = standard_weight;
    }
}
