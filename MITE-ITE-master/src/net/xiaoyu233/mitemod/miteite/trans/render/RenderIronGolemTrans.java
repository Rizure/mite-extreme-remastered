package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(bht.class)
public class RenderIronGolemTrans extends bhe {

   public RenderIronGolemTrans(bbo par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }
   @Inject(method = "a(Lnet/minecraft/EntityIronGolem;FFF)V",at = @At("HEAD"))
   private void modifySizeWhenEnhanced(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4, CallbackInfo callbackInfo){
      if (par1EntityIronGolem.getDataWatcher().getWatchableObjectByte(par1EntityIronGolem.DATA_OBJ_IS_BOOSTED) != 0) {
         GL11.glScalef(1.5F, 1.5F, 1.5F);
      }
   }

   @Shadow
   protected bjo a(Entity entity) {
      return null;
   }

   @Shadow
   protected void setTextures() {
   }
}
