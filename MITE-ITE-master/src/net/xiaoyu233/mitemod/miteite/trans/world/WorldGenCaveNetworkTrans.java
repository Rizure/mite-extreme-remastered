package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.MapGenCaveNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MapGenCaveNetwork.class)
public class WorldGenCaveNetworkTrans {
   @ModifyConstant(method = "isOriginOfCaveNetwork", constant = @Constant(intValue = 200))
   private int forceGenStub(int par1){
      return 125;
   }
   @ModifyConstant(method = "isOriginOfCaveNetwork", constant = @Constant(doubleValue = (double)1000.0F))
   private double unlockRestrictRange(double par1){
      return 0.0D;
   }
}
