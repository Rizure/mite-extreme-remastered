package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.EnumToolbenchType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerWorkbench.class)
public abstract class ContainerWorkbenchTrans extends MITEContainerCrafting {
   @Shadow
   private int x;
   @Shadow
   private int y;
   @Shadow
   private int z;
   public ContainerWorkbenchTrans(EntityPlayer player) {
      super(player);
   }
   public int benchType;

   @Inject(method = {"<init>"}, at = {@At("RETURN")})
   public void injectInit(CallbackInfo callbackInfo) {
      int id = this.world.getBlockId(this.x, this.y, this.z);
      if (id == Block.workbench.blockID) {
         this.benchType = EnumToolbenchType.ORIGINAL.ordinal();
      } else if (id == Blocks.blockExtendedToolbench.blockID || id == Blocks.blockRemasteredToolbench.blockID) {
         this.benchType = EnumToolbenchType.EXTENDED.ordinal();
      }
   }
   @Overwrite
   public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
      return (this.world.getBlock(this.x, this.y, this.z) instanceof BlockWorkbench) && par1EntityPlayer.getDistanceSq((double) this.x + (double) 0.5F, (double) this.y + (double) 0.5F, (double) this.z + (double) 0.5F) <= (double) 64.0F;
   }
}


