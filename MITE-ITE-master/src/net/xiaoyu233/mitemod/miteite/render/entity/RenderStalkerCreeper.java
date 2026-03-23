package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.Entity;
import net.minecraft.bgg;
import net.xiaoyu233.mitemod.miteite.entity.EntityStalkerCreeper;

public class RenderStalkerCreeper extends bgg {
   public RenderStalkerCreeper() {
      this.d *= this.scale = EntityStalkerCreeper.getScale();
   }

   public String getSubtypeName() {
      return "stalker_creeper";
   }
   @Override
   public float getModelOpacity(Entity entity) {
      int x = entity.getBlockPosX();
      int y = entity.getBlockPosY();
      int z = entity.getBlockPosZ();
      int blockLight = entity.worldObj.getBlockLightValue(x,y,z);
      if(entity.worldObj.isOverworld()){
         return blockLight / 15.0F;
      }else {
         return 1.0F;
      }
   }
}
