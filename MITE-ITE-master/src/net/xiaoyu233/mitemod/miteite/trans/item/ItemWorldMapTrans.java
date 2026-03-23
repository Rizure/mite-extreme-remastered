package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Entity;
import net.minecraft.ItemWorldMap;
import net.minecraft.World;
import net.minecraft.ali;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemWorldMap.class)
public class ItemWorldMapTrans {
   @ModifyVariable(method = "updateMapData", at = @At(value = "STORE", ordinal = 0))
   private int[] fixRendering(int[] a){
      return new int[2048];
   }
}
