package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldGenVillage.class)
public abstract class WorldGenVillageTrans{
   @ModifyConstant(method = "canSpawnStructureAtCoords", constant = @Constant(intValue = 60))
   private int resetDaysThatVillageSpawn(int day){
      return Configs.wenscConfig.whichDayGenVillage.ConfigValue;
   }
}
