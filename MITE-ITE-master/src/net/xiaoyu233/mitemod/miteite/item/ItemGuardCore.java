package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemGuardCore extends Item implements IDamageableItem {
    private Material reinforcement_material;
    public int level;

    ItemGuardCore(int id, Material reinforcement_material, int level){
        super(id, reinforcement_material,"regeneration_core");
        this.level = level * 10;
        this.setCreativeTab(CreativeModeTab.tabTools);
        this.reinforcement_material = reinforcement_material;
        this.setMaxDamage(this.getMultipliedDurability());
    }

    public boolean isHarmedByAcid() {
        return false;
    }
    public boolean isHarmedByFire() {
        return false;
    }
    public boolean isHarmedByLava() {
        return false;
    }

    public final int getMultipliedDurability()
    {
        return 400 * (int)this.reinforcement_material.getDurability() * 20;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }

    @Override
    public int getRepairCost() {
        return 40;
    };

    public Material getMaterialForDurability()
    {
        return this.reinforcement_material;
    }

    public Material getMaterialForRepairs()
    {
        return this.reinforcement_material;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.get("提供生命值上限"+ this.level + "%的护盾"));
        }
    }
}
