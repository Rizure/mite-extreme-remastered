package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockAnvil.class)
public class BlockAnvilTrans extends BlockFalling {
   public BlockAnvilTrans(int par1, Material material, BlockConstants constants) {
      super(par1, material, constants);
   }

   public float getCraftingDifficultyAsComponent(int metadata) {
      return this.blockMaterial.isMetal() ? ItemIngot.getCraftingDifficultyAsComponent(this.blockMaterial) * 31.0F : ItemRock.getCraftingDifficultyAsComponent(this.blockMaterial) * (float) (this.blockMaterial == Material.quartz ? 16 : 31);
   }
}
