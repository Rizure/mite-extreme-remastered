package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityGrenade;

import java.util.List;

public class ItemGrenade extends Item {
    public ItemGrenade(int id, Material material) {
        super(id, material, "grenade");
        this.setMaxStackSize(1);
        this.setCraftingDifficultyAsComponent(1000.0F);
        this.setCreativeTab(CreativeModeTab.tabCombat);
    }

    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if (player.onServer()) {
            if (!player.inCreativeMode()) {
                player.convertOneOfHeldItem((ItemStack)null);
                player.addHungerServerSide(0.25F * EnchantmentManager.getEnduranceModifier(player));
            }

            WorldServer world = player.getWorldServer();
            world.playSoundAtEntity(player, "random.bow", 0.3F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            world.spawnEntityInWorld(new EntityGrenade(world, player));
        } else {
            player.bobItem();
        }

        return true;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add("");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("限时万能钥匙", new Object[0]));
        }

    }
}
