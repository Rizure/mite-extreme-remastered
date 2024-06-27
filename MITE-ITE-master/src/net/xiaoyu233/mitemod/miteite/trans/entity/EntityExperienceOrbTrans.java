package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityExperienceOrb.class)
public class EntityExperienceOrbTrans {
   @Overwrite
   public static int getXPSplit(int par0) {
      return par0 / 3 > 0 ? par0 / 3 : 1;
   }
}
