package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockExtendedToolbench;
import net.xiaoyu233.mitemod.miteite.util.EnumToolbenchType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(axk.class)
public class GuiCraftingTableTrans extends axp {
   private static final String BOOST_INFO = LocaleI18n.translateToLocal("container.workbench.boost");

   public GuiCraftingTableTrans(Container par1Container) {
      super(par1Container);
   }

   @Shadow
   protected void a(float v, int i, int i1) {
   }

   @Inject(method = "a", at = @At(value = "INVOKE", target = "Lnet/minecraft/Container;getSlot(I)Lnet/minecraft/Slot;", shift = At.Shift.BEFORE), cancellable = true)
   private void injectRemoveChooseQualityIcon(float par1, int par2, int par3, CallbackInfo callback) {
      //Do nothing,just cancel
      callback.cancel();
   }

   @Overwrite
   protected void b(int par1, int par2) {
      ContainerWorkbench container_workbench = (ContainerWorkbench) this.e;
      int metadata = container_workbench.getBlockMetadata();
      int benchType = container_workbench.benchType;
      Material tool_material = null;
      if (benchType == EnumToolbenchType.ORIGINAL.ordinal()) {
         tool_material = BlockWorkbench.getToolMaterial(metadata);
      } else if (benchType == EnumToolbenchType.EXTENDED.ordinal()) {
         tool_material = BlockExtendedToolbench.getToolMaterial(metadata);
      }
      String var3 = Translator.get("tile.toolbench." + tool_material + ".name");
      this.o.b(var3, 29, 6, 4210752);
      this.o.b(bkb.a("container.inventory"), 7, this.d - 96 + 3, 4210752);
      if (this.e.player.getCraftingBoostFactor() != 0.0F) {
         this.o.b(BOOST_INFO + (int) (this.e.player.getCraftingBoostFactor() * 100.0F) + "%", 90, 6, 11141290);
      }
      this.o.b(bkb.a(Translator.get("tile.toolbench.estimatedtime") + this.f.h.crafting_period / 20 + "s"), 90, 6,  4210752);

   }
}
