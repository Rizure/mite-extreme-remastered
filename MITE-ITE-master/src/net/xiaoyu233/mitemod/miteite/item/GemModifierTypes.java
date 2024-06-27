package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.IIcon;
import net.minecraft.Material;

import javax.swing.*;

public enum GemModifierTypes{
    damage("damage", "Red"),
    health("health", "Green"),
    protection("protection", "Purple"),
    recover("recover", "Aquamarine"),
    haste("haste","Teal");
    public String gemName;
    public String iconName;

    GemModifierTypes(String gemName, String iconName) {
        this.gemName = gemName;
        this.iconName = iconName;
    }

    public float getRate() {
        if(this == protection) {
            return 0.01f;
        } else if(this == recover) {
            return 0.125f;
        } else if(this == haste) {
            return 0.125f;
        } else {
            return 1f;
        }
    }
}
