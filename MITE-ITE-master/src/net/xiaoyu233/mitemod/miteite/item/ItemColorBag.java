package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;
import java.util.Random;

public class ItemColorBag extends Item{
    public ItemColorBag(int id, Material material, String texture) {
        super(id, material, texture);
        this.setMaxStackSize(16);
        this.setCraftingDifficultyAsComponent(100.0F);
        this.setCreativeTab(CreativeModeTab.tabTools);
    }
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        ItemStack totem = player.getHeldItemStack();
        Random rand = new Random((long) player.getHealth());
        if(totem.getItem() instanceof ItemColorBag){
            for(int i = 0; i < ItemDye.dyeColors.length; i ++){
                player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.dyePowder,1, rand.nextInt(ItemDye.dyeColors.length)));
                player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
                player.makeSound("random.anvil_land", 0.4F, 0.4F);
            }
            player.convertOneOfHeldItem((ItemStack) null);
            return true;
        }
        return false;
    }
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("豪德寺三花的卡片", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("虹龙洞的隐秘道具", new Object[0]));
        }

    }
}
