//package net.xiaoyu233.mitemod.miteite.trans.render;
//
//import net.minecraft.Entity;
//import net.minecraft.RenderInvisibleStalker;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//
//@Mixin(RenderInvisibleStalker.class)
//public class RenderInvisibleStalkerTrans {
//   @Overwrite
//   public float getModelOpacity(Entity entity) {
//      int x = entity.getBlockPosX();
//      int y = entity.getBlockPosY();
//      int z = entity.getBlockPosZ();
//      int blockLight = entity.worldObj.getBlockLightValue(x,y,z);
//      return blockLight / 15.0F;
//   }
//}
