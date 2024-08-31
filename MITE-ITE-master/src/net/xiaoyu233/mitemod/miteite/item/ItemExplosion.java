package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;

import java.util.List;
import java.util.Random;

public class ItemExplosion extends Item{
    public ItemExplosion(int id, Material material, String texture) {
        super(id, material, texture);
        this.setMaxStackSize(16);
        this.setCraftingDifficultyAsComponent(100.0F);
        this.setCreativeTab(CreativeModeTab.tabTools);
    }
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        ItemStack totem = player.getHeldItemStack();
        if(totem.getItem() instanceof ItemExplosion && player.getHealth() > 1.0F){
            player.addVelocity((double)(-MathHelper.sin(player.rotationYaw * 3.1415927F / 180.0F) * (float)8.0F * 0.5F), 0.75F, (double)(MathHelper.cos(player.rotationYaw * 3.1415927F / 180.0F) * (float)8.0F * 0.5F));
            player.attackEntityFrom(new Damage(DamageSource.absolute,1));
            player.heal(1.0F);
            player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
            player.convertOneOfHeldItem((ItemStack) null);
            player.triggerAchievement(Achievements.razTime);
            return true;
        }
        return false;
    }
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("火花骑士……？", new Object[0]));
        }

    }
}
