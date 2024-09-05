package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.EntityFinalZombieBoss;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ItemColorBag extends Item{
    public String tag;
    public ItemColorBag(int id, Material material, String tag) {
        super(id, material, "usable");
        this.tag = tag;
        this.setMaxStackSize(16);
        this.setCraftingDifficultyAsComponent(100.0F);
        this.setCreativeTab(CreativeModeTab.tabTools);
    }
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        ItemStack totem = player.getHeldItemStack();
        Random rand = new Random((long) player.getHealth());
        if(totem.getItem() == Items.colorBag){
            for(int i = 0; i < ItemDye.dyeColors.length; i ++){
                player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.dyePowder,1, rand.nextInt(ItemDye.dyeColors.length)));
                player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
                player.makeSound("random.anvil_land", 0.4F, 0.4F);
            }
            player.convertOneOfHeldItem((ItemStack) null);
            return true;
        }else if(totem.getItem() == Items.gemBlue){
            for(int i = 0; i < 64; i ++){
                player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Blocks.blockColorful,1, rand.nextInt(16)));
                player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
                player.makeSound("random.anvil_land", 0.4F, 0.4F);
            }
            player.convertOneOfHeldItem((ItemStack) null);
            return true;
        }else if(totem.getItem() == Items.gemYellow){
            for(int i = 0; i < 64; i ++){
                player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Blocks.blockGotcha,1, rand.nextInt(16)));
                player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
                player.makeSound("random.anvil_land", 0.4F, 0.4F);
            }
            player.convertOneOfHeldItem((ItemStack) null);
            return true;
        }else if(totem.getItem() == Items.final_key){
            RaycastCollision rc = player.getSelectedObject(partial_tick, false);
            if(rc!=null){
                if(rc.isBlock()){
                    player.makeSound("random.anvil_land", 0.4F, 0.4F);
                    if(rc.getBlockHitID()== Blocks.mobSpawner.blockID){
                        player.convertOneOfHeldItem((ItemStack) null);
                        int x = rc.block_hit_x;
                        int y = rc.block_hit_y;
                        int z = rc.block_hit_z;
                        player.worldObj.setBlockToAir(x,y,z);
                        player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.itemEnhanceGem2,1,player.worldObj.rand.nextInt(GemModifierTypes.values().length)));
                        Item[] drops = {Item.netherQuartz,Item.netherQuartz,Item.emerald,Item.emerald,Item.diamond,Item.blazePowder,Item.ghastTear};
                        for(int i = 0; i < player.worldObj.getDayOfOverworld(); i += 12){
                            player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(drops[player.worldObj.rand.nextInt(drops.length)]));
                        }
                        return true;
                    }
                }
                else if(rc.isEntity()){
                    if(rc.getEntityHit() instanceof EntityZombieBoss){
                        ((EntityZombieBoss) rc.getEntityHit()).enhance();
                    }
                    
                }
            }
            
        }
        return false;
    }
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            if(Objects.equals(this.tag, "color_bag")){
                info.add(" ");
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("豪德寺三花的卡片", new Object[0]));
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("虹龙洞的隐秘道具", new Object[0]));
            }
            if(Objects.equals(this.tag, "gem_y")){
                info.add(" ");
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("今日修仙不努力", new Object[0]));
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("万魂幡里做兄弟", new Object[0]));
            }
            if(Objects.equals(this.tag, "gem_b")){
                info.add(" ");
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("不是，道友", new Object[0]));
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("你这人皇幡怎么冒黑烟啊", new Object[0]));
            }
            if(Objects.equals(this.tag, "final_key")){
                info.add(" ");
                info.add(EnumChatFormat.BROWN + Translator.getFormatted("我在我家发现了开锁师傅的广告", new Object[0]));
                info.add(EnumChatFormat.RED + Translator.getFormatted("做好准备", new Object[0]));
            }
            
        }

    }
}
