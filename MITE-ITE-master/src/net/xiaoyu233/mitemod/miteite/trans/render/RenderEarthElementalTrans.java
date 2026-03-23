package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEarthElemental.class)
public class RenderEarthElementalTrans extends bgu {
   public RenderEarthElementalTrans() {
      super(new ModelInvisibleStalker(), 0.5F);
   }

   protected bjo getTextures(EntityEarthElemental earth_elemental) {
      int type = earth_elemental.getType();
      if(type == EntityEarthElemental.STONE_NORMAL){
         return super.textures[0];
      }else if(type == EntityEarthElemental.STONE_MAGMA){
         return super.textures[1];
      }else if(type == EntityEarthElemental.OBSIDIAN_NORMAL){
         return super.textures[2];
      }else if(type == EntityEarthElemental.OBSIDIAN_MAGMA){
         return super.textures[3];
      }else if(type == EntityEarthElemental.NETHERRACK_NORMAL){
         return super.textures[4];
      }else if(type == EntityEarthElemental.NETHERRACK_MAGMA){
         return super.textures[5];
      }else if(type == EntityEarthElemental.END_STONE_NORMAL){
         return super.textures[6];
      }else if(type == EntityEarthElemental.END_STONE_MAGMA){
         return super.textures[7];
      }else if(type == EntityEarthElemental.CLAY_NORMAL){
         return super.textures[8];
      }else if (type == EntityEarthElemental.CLAY_HARDENED) {
         return super.textures[9];
      }else if(type == 5 /* PLANKS */){
         return super.textures[10];
      }
      return super.textures[0];
   }

   @Inject(method = "setTextures()V", at = @At("RETURN"))
   private void injectTexture(CallbackInfo c) {
      this.setTexture(10, "plank", false);
   }

   @Intrinsic
   @Shadow
   private void setTexture(int index, String name, boolean magma) {
   }

   @Shadow
   protected void setTextures() {
   }
}
