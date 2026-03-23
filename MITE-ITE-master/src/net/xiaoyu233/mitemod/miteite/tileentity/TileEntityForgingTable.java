package net.xiaoyu233.mitemod.miteite.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.*;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingRecipe;
import net.xiaoyu233.mitemod.miteite.item.recipe.IFaultFeedback;
import net.xiaoyu233.mitemod.miteite.network.SPacketFinishForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketUpdateForgingTableModeState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityForgingTable extends TileEntity implements IInventory {
   private String customName;
   private final ItemStack[] items = new ItemStack[ForgingTableSlots.slotSize];
   private final ForgingTableSlots slots = new ForgingTableSlots(this);
   private int forgingTime;
   private int currentFailCheckTime;
   //nullable
   private ForgingRecipe usedRecipe;
   private int maxTime;
   private boolean isForging;
   private boolean isUsing;
   private int forging_mode;
   public static final int mode_upgrading_index = 0;
   public static final int mode_defusing_index = 1;
   public static final int mode_applying_index = 2;
   public static final int mode_forging_index = 3;

   public void setItem(int index, ItemStack itemStack) {
      this.items[index] = itemStack;
   }

   @Override
   //onContainerClosed
   public void closeChest() {
      this.finishForging();
      this.isUsing = false;
   }

   public int getForging_mode() {
      return this.forging_mode;
   }

   public void setForging_mode(int mode) {
      this.forging_mode = mode < 4 ? mode : 0;
      if(!this.worldObj.isRemote){
         this.worldObj.getAsWorldServer().sendPacketToAllPlayersInAllDimensions(new SPacketUpdateForgingTableModeState(this.xCoord, this.yCoord, this.zCoord, mode));
         this.onInventoryChanged();
      }
   }

   private void addExpForToolWhenCompleted(ItemStack toolItem){
      if (toolItem.stackTagCompound != null) {
         NBTTagCompound compound;
         compound = toolItem.stackTagCompound;
         if (compound.hasKey("using_exp")) {
            int currentExp = compound.getInteger("using_exp");
            compound.setInteger("using_exp", currentExp + (int) Math.pow(3, toolItem.getMaterialForRepairs().getMinHarvestLevel()) * 10);
         }
      }
   }

   private ItemStack getAppliedTemplate(ItemStack toolItem, ItemStack templateStack){
      NBTTagCompound tagCompound = toolItem.stackTagCompound;
      if (tagCompound != null) {
         if(toolItem.getItem() instanceof ItemTool){
            //全可附加属性：检查可附加，不检查等级
            List<ToolModifierTypes> all_modifiers = ModifierUtils.getAllToolModifiers(toolItem);
            //已拥有属性：未初始化
            List<ToolModifierTypes> obtained_modifiers = new ArrayList<>();
            NBTTagCompound toolModifier = tagCompound.getCompoundTag("modifiers");
            //录入拥有的副属性
            for (int n = 0; n < all_modifiers.size(); n++) {
               if (toolModifier.hasKey(all_modifiers.get(n).nbtName)) {
                  obtained_modifiers.add(all_modifiers.get(n));
               }
            }
            ToolModifierTypes Toolmodifier = null;
            if(!obtained_modifiers.isEmpty()){
               Toolmodifier = obtained_modifiers.get(this.getWorldObj().rand.nextInt(obtained_modifiers.size()));
            }

            NBTTagCompound templateTagCompound = templateStack.stackTagCompound;
            NBTTagCompound templateModifiers = templateTagCompound.getCompoundTag("modifiers");
            if (Toolmodifier != null) {
               templateModifiers.setInteger(Toolmodifier.nbtName, 1);
            }
         }else if(toolItem.getItem() instanceof ItemArmor){
            //全可附加属性：检查可附加，不检查等级
            List<ArmorModifierTypes> all_modifiers = ModifierUtils.getAllArmorModifiers(toolItem);
            //已拥有属性：未初始化
            List<ArmorModifierTypes> obtained_modifiers = new ArrayList<>();
            NBTTagCompound armorModifiers = tagCompound.getCompoundTag("modifiers");
            //录入拥有的副属性
            for (int n = 0; n < all_modifiers.size(); n++) {
               if (armorModifiers.hasKey(all_modifiers.get(n).nbtName)) {
                  obtained_modifiers.add(all_modifiers.get(n));
               }
            }
            ArmorModifierTypes armorModifier = null;
            if(!obtained_modifiers.isEmpty()){
               armorModifier = obtained_modifiers.get(this.getWorldObj().rand.nextInt(obtained_modifiers.size()));
            }

            NBTTagCompound templateTagCompound = templateStack.stackTagCompound;
            NBTTagCompound templateModifiers = templateTagCompound.getCompoundTag("modifiers");
            if (armorModifier != null) {
               templateModifiers.setInteger(armorModifier.nbtName, 1);
            }
         }
      }
      return templateStack;
   }

   private List<ArmorModifierTypes> initArmorModifierInTemplate(List<ArmorModifierTypes> modifierList, ItemStack template, ItemStack equipStack){
      List<ArmorModifierTypes> all_armor_modifiers = ModifierUtils.getAllArmorModifiers(equipStack);
      NBTTagCompound tagCompound = template.stackTagCompound;
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      if (modifiers != null) {
         for (int n = 0; n < all_armor_modifiers.size(); n++) {
            if (modifiers.hasKey(all_armor_modifiers.get(n).nbtName)) {
               modifierList.add(all_armor_modifiers.get(n));
            }
         }
      }
      return modifierList;
   }

   private List<ToolModifierTypes> initToolModifierInTemplate(List<ToolModifierTypes> modifierList, ItemStack template, ItemStack equipStack){
      List<ToolModifierTypes> all_tool_modifiers = ModifierUtils.getAllToolModifiers(equipStack);
      NBTTagCompound tagCompound = template.stackTagCompound;
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      if (modifiers != null) {
         for (int n = 0; n < all_tool_modifiers.size(); n++) {
            if (modifiers.hasKey(all_tool_modifiers.get(n).nbtName)) {
               modifierList.add(all_tool_modifiers.get(n));
            }
         }
      }
      return modifierList;
   }
   private void completeForging() {
      ForgingRecipe currentRecipe = this.usedRecipe;
      ItemStack toolItem = this.slots.getToolItem();
      if (currentRecipe.getMode() == TileEntityForgingTable.mode_forging_index) {
         toolItem.setForgingGrade(currentRecipe.getLevelToUpgrade() + 1);
      } else if (currentRecipe.getMode() == TileEntityForgingTable.mode_upgrading_index) {
         this.addExpForToolWhenCompleted(toolItem);
      } else if (currentRecipe.getMode() == TileEntityForgingTable.mode_defusing_index) {
         Item newTemplate = Item.getMatchingItem(ItemForgingTemplates.class, this.usedRecipe.getMaterial());
         ItemStack templateStack = new ItemStack(newTemplate);
         toolItem = this.getAppliedTemplate(toolItem, templateStack);
      } else if (currentRecipe.getMode() == TileEntityForgingTable.mode_applying_index) {
         List<ArmorModifierTypes> template_armor_modifiers = new ArrayList<>();
         List<ToolModifierTypes> template_tool_modifiers = new ArrayList<>();
         ItemStack template = this.slots.getTemplates();
         if(template != null){
            this.initArmorModifierInTemplate(template_armor_modifiers, template, toolItem);
            this.initToolModifierInTemplate(template_tool_modifiers, template, toolItem);
         }
         NBTTagCompound equipTagCompound = toolItem.stackTagCompound;
         if(toolItem.getItem() instanceof ItemTool){
            //全可附加属性：检查可附加，不检查等级
            List<ToolModifierTypes> all_modifiers = ModifierUtils.getAllToolModifiers(toolItem);
            //目前可附加属性：检查可附加，检查等级
            List<ToolModifierTypes> available_modifiers = ModifierUtils.getAllCanBeAppliedToolModifiers(toolItem);
            //已拥有属性：未初始化
            List<ToolModifierTypes> obtained_modifiers = new ArrayList<>();
            NBTTagCompound modifiers = equipTagCompound.getCompoundTag("modifiers");
            //确认副属性条目数上限
            int modifierTypesCap = Math.min(all_modifiers.size(), 4 + (toolItem.getForgingGrade() / 5));
            //录入拥有的副属性
            for (int n = 0; n < all_modifiers.size(); n++) {
               if (modifiers.hasKey(all_modifiers.get(n).nbtName)) {
                  if(available_modifiers.contains(all_modifiers.get(n))){
                     obtained_modifiers.add(all_modifiers.get(n));
                  }
               }
            }
            if (obtained_modifiers.size() + template_tool_modifiers.size() <= modifierTypesCap) {
               for (ToolModifierTypes obtainedToolModifier : template_tool_modifiers) {
                  modifiers.setInteger(obtainedToolModifier.nbtName, 1);
               }
            }
         }else if(toolItem.getItem() instanceof ItemArmor){
            //全可附加属性：检查可附加，不检查等级
            List<ArmorModifierTypes> all_modifiers = ModifierUtils.getAllArmorModifiers(toolItem);
            //目前可附加属性：检查可附加，检查等级
            List<ArmorModifierTypes> available_modifiers = ModifierUtils.getAllCanBeAppliedArmorModifiers(toolItem);
            //已拥有属性：未初始化
            List<ArmorModifierTypes> obtained_modifiers = new ArrayList<>();
            NBTTagCompound modifiers = equipTagCompound.getCompoundTag("modifiers");
            //确认副属性条目数上限
            int modifierTypesCap = Math.min(all_modifiers.size(), 4 + (toolItem.getForgingGrade() / 5));
            //录入拥有的副属性
            for (int n = 0; n < all_modifiers.size(); n++) {
               if (modifiers.hasKey(all_modifiers.get(n).nbtName)) {
                  if(available_modifiers.contains(all_modifiers.get(n))){
                     obtained_modifiers.add(all_modifiers.get(n));
                  }
               }
            }
            if (obtained_modifiers.size() + template_armor_modifiers.size() <= modifierTypesCap) {
               for (ArmorModifierTypes obtainedArmorModifier : template_armor_modifiers) {
                  modifiers.setInteger(obtainedArmorModifier.nbtName, 1);
               }
            }
         }
      }
      EnumQuality qualityReward = currentRecipe.getQualityReward();
      if (qualityReward != null) {
         toolItem.setQuality(qualityReward);
      }
      this.getWorldObj().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.levelup", 1.0F, 0.5F);
      this.slots.setOutput(toolItem);
      this.slots.onFinishForging(SPacketFinishForging.Status.COMPLETED);
      this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost(), currentRecipe.getAxeDurabilityCost());
      this.slots.costItems(currentRecipe);
      this.slots.setToolItem(null);
      this.slots.updateSlots();
   }

   @Override
   public ItemStack decrStackSize(int index, int count) {
      if (this.items[index] != null) {
         ItemStack var3;
         if (this.items[index].stackSize <= count) {
            var3 = this.items[index];
            this.items[index] = null;
         } else {
            var3 = this.items[index].splitStack(count);
            if (this.items[index].stackSize == 0) {
               this.items[index] = null;
            }
         }
         return var3;
      } else {
         return null;
      }
   }

   public void dropAllItems() {
      this.slots.dropItems(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
   }

   private void failForging() {
      ForgingRecipe currentRecipe = this.usedRecipe;
      this.getWorldObj().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.anvil_break", 1.0F, 1.1F);
      this.slots.onFinishForging(SPacketFinishForging.Status.FAILED);
      this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost() / 2, currentRecipe.getAxeDurabilityCost() / 2);
      this.slots.costItems(currentRecipe);
      ItemStack result = this.slots.getToolItem();
      if (!this.slots.hasUniversal_withEffecting()) {
         for (IFaultFeedback iFaultFeedback : currentRecipe.getFaultFeedback()) {
            result = iFaultFeedback.accept(result);
         }
         this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost() / 2, currentRecipe.getAxeDurabilityCost() / 2);
      }
      this.slots.setToolItem(result);
      this.slots.updateSlots();
   }

   private void finishForging() {
      this.isForging = false;
      this.forgingTime = 0;
      this.usedRecipe = null;
      this.maxTime = 0;
      this.slots.updateTime(0);
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   public ItemStack getItem(int index) {
      return this.items[index];
   }

   public boolean b() {
      return this.customName != null && this.customName.length() > 0;
   }

   @Override
   public int getSizeInventory() {
      return ForgingTableSlots.slotSize;
   }

   public ForgingTableSlots getSlots() {
      return this.slots;
   }

   @Override
   //getItemStack
   public ItemStack getStackInSlot(int var1) {
//        System.out.println("items size: " + items.length);
      return this.items[var1];
   }

   @Override
   //removeItem
   public ItemStack getStackInSlotOnClosing(int par1) {
      if (this.items[par1] != null) {
         ItemStack var2 = this.items[par1];
         this.items[par1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return this.slots.isItemValidForSlot(var1, var2);
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      if (player.getWorld().getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockForgingTable && player.getWorld().getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) instanceof TileEntityForgingTable) {
         return player.getDistance((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
      } else {
         return false;
      }
   }

   @Override
   public void onInventoryChanged() {
      super.onInventoryChanged();
      ForgingRecipe usedRecipe = this.slots.getUsedRecipe(this.getBlockMetadata());
      ItemStack toolItem = this.slots.getToolItem();
      if (toolItem != null) {
         ForgingRecipe recipeFromTool = this.slots.getRecipeFromTool(toolItem);
         this.slots.updateInfo(recipeFromTool);
      } else {
         this.slots.updateInfo(null);
      }

      if (this.usedRecipe != null) {
         if (this.usedRecipe != usedRecipe || this.slots.getForgingTime(this.usedRecipe) != this.maxTime) {
            this.finishForging();
            this.slots.onFinishForging(SPacketFinishForging.Status.CANCELED);
         }
      }

   }

   @Override
   public void openChest() {
      this.isUsing = true;
      if(!this.worldObj.isRemote){
         this.worldObj.getAsWorldServer().sendPacketToAllPlayersInAllDimensions(new SPacketUpdateForgingTableModeState(this.xCoord, this.yCoord, this.zCoord, this.forging_mode));
      }
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.slots.readFromNBT(par1NBTTagCompound, this);
      this.forging_mode = par1NBTTagCompound.getInteger("Forging_mode");
      if (this.b()) {
         this.customName = par1NBTTagCompound.getString("CustomName");
      }
   }

   @Override
   //setItem
   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.items[var1] = var2;
   }

   public boolean startForging() {
      this.usedRecipe = this.slots.getUsedRecipe(this.getBlockMetadata());
      if (this.usedRecipe != null) {
         this.isForging = true;
         this.maxTime = Math.round((float) this.usedRecipe.getTimeReq() / this.slots.getEffectivityFactorFromTool());
         this.currentFailCheckTime = this.getWorldObj().rand.nextInt(this.maxTime);
         return true;
      } else {
         return false;
      }
   }

   public boolean isForging() {
      return this.isForging;
   }

   public void destroyInventory() {
      Arrays.fill(this.items, null);
   }

   public void updateEntity() {
      if (!this.getWorldObj().isRemote && this.isForging) {
         ++this.forgingTime;
         this.slots.updateTime(this.forgingTime);
         if (this.forgingTime % 20 == 0 && this.getWorldObj().rand.nextInt(10) != 0) {
            this.getWorldObj().getAsWorldServer().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.anvil_land", 1.0F, 0.875F + MathHelper.clamp_float((float) this.forgingTime / (float) this.currentFailCheckTime, 0.0F, 1.0F) * 0.125F);
         }

         if (this.forgingTime == this.currentFailCheckTime) {
            if (this.getWorldObj().rand.nextInt(100) < this.slots.getChanceOfFailure(this.usedRecipe)) {
               this.failForging();
               this.finishForging();
            }
         } else if (this.forgingTime >= this.maxTime) {
            this.completeForging();
            this.finishForging();
         }
      }
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      if (this.b()) {
         par1NBTTagCompound.setString("CustomName", this.customName);
      }
      par1NBTTagCompound.setInteger("Forging_mode", this.forging_mode);
      this.slots.writeToNBT(par1NBTTagCompound);
   }

   public boolean isUsing() {
      return this.isUsing;
   }
}
