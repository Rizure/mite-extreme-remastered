package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockExtendedToolbench;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerExtremeWorkbench;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;
import net.xiaoyu233.mitemod.miteite.util.EnumToolbenchType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(awy.class)
public class GuiContainerTrans {
   @Shadow
   public Container e;

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "drawItemStackTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockWorkbench;getToolMaterial(I)Lnet/minecraft/Material;", shift = At.Shift.AFTER))
   public void judgeGemListExistThenPrevent(ItemStack par1ItemStack, int par2, int par3, Slot slot, CallbackInfo ci, List var4, Item item, aah recipe, Material material_to_check_tool_bench_hardness_against, boolean upper_body_in_web, List tooltips, ContainerWorkbench container_workbench) {
      for (int i = 0; i < container_workbench.craft_matrix.getInventory().length; i++) {
         if (container_workbench.craft_matrix.getInventory() != null) {
            ItemStack itemStack = container_workbench.craft_matrix.getInventory()[i];
            if (itemStack != null) {
               if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("Gems")) {
                  NBTTagList nbtTagList = itemStack.stackTagCompound.getTagList("Gems");
                  for (int j = 0; j < nbtTagList.tagCount(); j++) {
                     NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtTagList.tagAt(j);
                     if (nbtTagCompound.getShort("id") >= 0 && nbtTagCompound.getByte("meta") >= 0) {
                        Item itemLocal = Item.getItem(nbtTagCompound.getShort("id"));
                        if (itemLocal instanceof ItemEnhanceGem) {
                           tooltips.add(EnumChatFormat.RED + Translator.get("container.crafting.needsRemoveGems"));
                           this.e.crafting_result_shown_but_prevented = true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Redirect(method = "drawItemStackTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
   private boolean redirectRemoveDamageLimitation(InventoryCrafting caller) {
      aah recipe = ((MITEContainerCrafting) this.e).current_crafting_result.recipe;
      if (recipe instanceof ShapedRecipes) {
         return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
      } else if (recipe instanceof ShapelessRecipes) {
         return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
      }
      return caller.hasDamagedItem();
   }

   @Redirect(
           method = {"drawItemStackTooltip"},
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/BlockWorkbench;getToolMaterial(I)Lnet/minecraft/Material;"
           )
   )
   public Material redirectBenchMaterial(int par1, ItemStack par1ItemStack, int par2, int par3, Slot slot) {
      ContainerWorkbench container_workbench = (ContainerWorkbench)this.e;
      int metadata = container_workbench.getBlockMetadata();
      int benchType = container_workbench.benchType;
      Material tool_material;
      if (benchType == EnumToolbenchType.ORIGINAL.ordinal()) {
         tool_material = BlockWorkbench.getToolMaterial(metadata);
      } else if (benchType == EnumToolbenchType.EXTENDED.ordinal()) {
         tool_material = BlockExtendedToolbench.getToolMaterial(metadata);
      } else {
         return null;
      }
      return tool_material;
   }

   @Inject(method = "drawItemStackTooltip", at = @At(value = "FIELD", target = "Lnet/minecraft/Container;crafting_result_shown_but_prevented:Z", ordinal = 4, shift = At.Shift.AFTER))
   private void appendExtremeToolbenchMaterial(CallbackInfo callbackInfo){
      if (this.e instanceof ContainerExtremeWorkbench) {
         this.e.crafting_result_shown_but_prevented = false;
      }
   }
}
