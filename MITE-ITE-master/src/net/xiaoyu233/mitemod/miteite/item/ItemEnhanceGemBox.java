package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ItemEnhanceGemBox extends Item {
   String tag = "";

   public ItemEnhanceGemBox(int id, String tag) {
      super(id, Material.diamond, "enhance_gem_box");
      this.tag = tag;
      this.setMaxStackSize(64);
      this.setCraftingDifficultyAsComponent(25.0F);
      this.setCreativeTab(CreativeModeTab.tabMisc);
   }

   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if (player.onServer()) {
         int amount = player.getHeldItemStack().stackSize;
         if (!ctrl_is_down) {
            amount = 1;
         }
         player.causeBreakingItemEffect(player.getHeldItem(), player.inventory.currentItem);
         for (int i = 0; i < amount; i++) {
            player.convertOneOfHeldItem((ItemStack) null);
            player.inventory.addItemStackToInventoryOrDropIt(this.fetchingGemWithTag(player.getRNG()));
         }
      } else {
         player.bobItem();
         player.swingArm();
      }
      return true;
   }

   private ItemStack fetchingGemWithTag(Random random) {
      if (Objects.equals(this.tag, "enhance_gem_box_phase1")) {
         return new ItemStack(Items.itemEnhanceGem, 1, GemModifierTypes.fetchingSubWithTier(random, 1));
      } else if (Objects.equals(this.tag, "enhance_gem_box_phase2")) {
         return new ItemStack(Items.itemEnhanceGem, 1, GemModifierTypes.fetchingSubWithTier(random, 2));
      } else if (Objects.equals(this.tag, "enhance_gem_box_phase3")) {
         return new ItemStack(Items.itemEnhanceGem, 1, GemModifierTypes.fetchingSubWithTier(random, 3));
      } else {
         return null;
      }
   }

   @Override
   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         info.add("");
         if (Objects.equals(this.tag, "enhance_gem_box_phase1")) {
            info.add("获得基础三角宝石其中之一");
         }
         if (Objects.equals(this.tag, "enhance_gem_box_phase2")) {
            info.add("获得特效三角宝石其中之一");
         }
         if (Objects.equals(this.tag, "enhance_gem_box_phase3")) {
            info.add("获得技能三角宝石其中之一");
         }
      }
      super.addInformation(item_stack, player, info, extended_info, slot);
   }
}
