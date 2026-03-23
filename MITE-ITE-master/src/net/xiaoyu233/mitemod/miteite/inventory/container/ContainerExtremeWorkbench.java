package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockExtremeWorkbench;

public class ContainerExtremeWorkbench extends MITEContainerCrafting {
   private int x;
   private int y;
   private int z;
   private boolean unlock_next_tick;

   public ContainerExtremeWorkbench(EntityPlayer player, int x, int y, int z) {
      super(player);
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @Override
   public int getMatrixSize() {
      return 3;
   }

   @Override
   public void createSlots() {
      this.addSlotToContainer(new SlotResult(this.player, this.craft_matrix, this.craft_result, 0, 124, 35));

      for(int var6 = 0; var6 < 3; ++var6) {
         for(int var7 = 0; var7 < 3; ++var7) {
            this.addSlotToContainer(new Slot(this.craft_matrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
         }
      }

      for(int var3 = 0; var3 < 3; ++var3) {
         for(int var7 = 0; var7 < 9; ++var7) {
            this.addSlotToContainer(new Slot(this.player.inventory, var7 + var3 * 9 + 9, 8 + var7 * 18, 84 + var3 * 18));
         }
      }

      for(int var4 = 0; var4 < 9; ++var4) {
         this.addSlotToContainer(new Slot(this.player.inventory, var4, 8 + var4 * 18, 142));
      }
   }

   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.world.getBlock(this.x, this.y, this.z) instanceof BlockExtremeWorkbench && entityPlayer.getDistanceSq((double)this.x + (double)0.5F, (double)this.y + (double)0.5F, (double)this.z + (double)0.5F) <= (double)64.0F;
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(par2);
      if (var4 != null && var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if (par2 == 0) {
            if (!this.mergeItemStack(var5, 10, 46, true)) {
               return null;
            }

            var4.onSlotChange(var5, var3);
         } else if (par2 >= 10 && par2 < 37) {
            if (!this.mergeItemStack(var5, 37, 46, false)) {
               return null;
            }
         } else if (par2 >= 37 && par2 < 46) {
            if (!this.mergeItemStack(var5, 10, 37, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var5, 10, 46, false)) {
            return null;
         }

         if (var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }

         if (var5.stackSize == var3.stackSize) {
            return null;
         }

         var4.onPickupFromSlot(par1EntityPlayer, var5);
      }

      return var3;
   }

   public void onUpdate() {
      if (!(this.player instanceof bey)) {
         this.onCraftMatrixChanged((IInventory)null);
         SlotResult crafting_slot = this.getCraftingSlot();
         if (crafting_slot.checkCraftingResultIndex(this.player)) {
            this.player.clearCrafting();
         }

         if (this.player instanceof ClientPlayer) {
            this.player.getAsEntityClientPlayerMP().crafting_experience_cost_tentative = 0;
         }

         if (!crafting_slot.canPlayerCraftItem(this.player)) {
            if (this.player instanceof ClientPlayer && this.crafting_result_shown_but_prevented) {
               this.player.getAsEntityClientPlayerMP().crafting_experience_cost_tentative = this.player.getAsEntityClientPlayerMP().crafting_experience_cost;
            }

            this.player.clearCrafting();
         }

         if (this.player.worldObj.isRemote) {
            ClientPlayer player = (ClientPlayer)this.player;
            if (player.crafting_proceed) {
               ItemStack item_stack = crafting_slot.getStack();
               int crafting_experience_cost = player.crafting_experience_cost;
               this.recordSlotStackSizes();
               crafting_slot.onPickupFromSlot(player, item_stack);
//               this.lockSlotsThatChanged();
               Minecraft.O.h.netClientHandler.c((new Packet85SimpleSignal(EnumSignal.crafting_completed)).setInteger(crafting_experience_cost));
               if (crafting_experience_cost > 0 && ++player.crafting_ticks >= 20) {
                  player.crafting_proceed = false;
               }
               player.crafting_ticks = 0;
            }
            player.crafting_proceed = false;
         }
         super.onUpdate();
      }
   }
   public boolean isRecipeForbidden(aah recipe) {
      return false;
   }
   private SlotResult getCraftingSlot() {
      return (SlotResult)this.getSlot(0);
   }
}
