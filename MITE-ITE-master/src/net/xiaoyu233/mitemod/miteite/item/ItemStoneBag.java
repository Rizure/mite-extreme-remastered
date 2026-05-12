package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class ItemStoneBag extends Item {

   public ItemStoneBag(int id) {
      super(id, Materials.invincible, "miscItem");
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeModeTab.tabTools);
      this.setCraftingDifficultyAsComponent(25.0F);
   }

   @Override
   public boolean isHarmedByAcid() {
      return false;
   }

   @Override
   public boolean canCatchFire() {
      return false;
   }

   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if(player.onServer()){
         ItemStack holding = player.getHeldItemStack();
         Random rand = new Random(player.worldObj.rand.nextLong());
         NBTTagCompound tagCompound = holding.stackTagCompound;
         boolean soundSold = false;
         boolean soundStore = false;
         if(tagCompound == null){
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("cobble", 0);
            compound.setInteger("nether_rack", 0);
            holding.stackTagCompound = compound;
            player.makeSound("random.anvil_land", 0.4F, 0.4F);
            player.addChatMessage("储物箱已启封");
         }else {
            int inventory_size = player.inventory.getSizeInventory();
            if(tagCompound.hasKey("cobble")){
               boolean containsCobble = false;
               for(int i = 0; i < inventory_size; i++){
                  ItemStack stack = player.inventory.getStackInSlot(i);
                  if(stack != null && stack.getItem().itemID == Block.cobblestone.blockID){
                     containsCobble = true;
                  }
               }
               if(tagCompound.getInteger("cobble") != 0 && !containsCobble){
                  int count = tagCompound.getInteger("cobble");
                  if(ctrl_is_down){
                     ItemStack var5 = new ItemStack(Block.cobblestone,count);
                     double soldPrice = (double) var5.getItem().soldPriceArray.get(var5.getItemSubtype());
                     if (soldPrice > 0d) {
                        double totalMoney = var5.stackSize * soldPrice;
                        player.money += totalMoney;
                        BigDecimal two = new BigDecimal(player.money);
                        player.money = two.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        count = 0;
                        player.addChatMessage("现有余额：" + player.money);
                        soundSold = true;
                     } else {
                        if(player.onServer()){
                           player.addChatMessage("商店不支持售出此商品");
                        }
                     }
                  }else {
                     int consume = Math.min(64, count);
                     soundStore = true;
                     player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Block.cobblestone,consume));
                     count -= consume;
                  }
                  tagCompound.setInteger("cobble",count);
               }else {
                  soundStore = true;
                  int count = tagCompound.getInteger("cobble");
                  for(int i = 0; i < inventory_size; i++){
                     ItemStack stack = player.inventory.getStackInSlot(i);
                     if(stack != null && stack.getItem().itemID == Block.cobblestone.blockID){
                        count += stack.stackSize;
                        player.inventory.setInventorySlotContents(i, null);
                     }
                  }
                  tagCompound.setInteger("cobble",count);
               }
            }
            if(tagCompound.hasKey("nether_rack")){
               boolean containsCobble = false;
               for(int i = 0; i < inventory_size; i++){
                  ItemStack stack = player.inventory.getStackInSlot(i);
                  if(stack != null && stack.getItem().itemID == Block.netherrack.blockID){
                     containsCobble = true;
                  }
               }
               if(tagCompound.getInteger("nether_rack") != 0 && !containsCobble){
                  int count = tagCompound.getInteger("nether_rack");
                  if(ctrl_is_down){
                     ItemStack var5 = new ItemStack(Block.netherrack,count);
                     double soldPrice = (double) var5.getItem().soldPriceArray.get(var5.getItemSubtype());
                     if (soldPrice > 0d) {
                        double totalMoney = var5.stackSize * soldPrice;
                        player.money += totalMoney;
                        BigDecimal two = new BigDecimal(player.money);
                        player.money = two.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        count = 0;
                        player.addChatMessage("现有余额：" + player.money);
                        soundSold = true;
                     } else {
                        if(player.onServer()){
                           player.addChatMessage("商店不支持售出此商品");
                        }
                     }
                  }else {
                     int consume = Math.min(64, count);
                     soundStore = true;
                     player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Block.netherrack,consume));
                     count -= consume;
                  }
                  tagCompound.setInteger("nether_rack",count);
               }else {
                  soundStore = true;
                  int count = tagCompound.getInteger("nether_rack");
                  for(int i = 0; i < inventory_size; i++){
                     ItemStack stack = player.inventory.getStackInSlot(i);
                     if(stack != null && stack.getItem().itemID == Block.netherrack.blockID){
                        count += stack.stackSize;
                        player.inventory.setInventorySlotContents(i, null);
                     }
                  }
                  tagCompound.setInteger("nether_rack",count);
               }
            }
         }
         if(soundSold){
            player.makeSound("random.levelup", 0.25F, 1.25F);
         }
         if(soundStore){
            player.makeSound("random.orb", 0.1F, 0.5F * ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.8F));
         }
      } else {
         player.bobItem();
         player.swingArm();
      }
      return true;
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      NBTTagCompound tagCompound = item_stack.getTagCompound();
      if (extended_info) {
         info.add(" ");
         if(tagCompound != null && tagCompound.hasKey("cobble")){
            int count = tagCompound.getInteger("cobble");
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("圆石数: " + count, new Object[0]));
         }
         if(tagCompound != null && tagCompound.hasKey("nether_rack")){
            int count = tagCompound.getInteger("nether_rack");
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("地狱岩数: " + count, new Object[0]));
         }
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("拒绝网络暴力 从你我做起", new Object[0]));
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("《贴脸开大》", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以存储圆石与地狱岩", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("ctrl右键可以将内容物全部售出", new Object[0]));
      }
   }
}
