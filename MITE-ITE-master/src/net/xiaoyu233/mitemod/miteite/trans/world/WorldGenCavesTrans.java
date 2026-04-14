package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WorldGenCaves.class)
public class WorldGenCavesTrans extends WorldGenBase {

   @Redirect(method = "recursiveGenerate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 8))
   public int forceGenerateLargeCave(Random rand, int par1, World par1World, int par2, int par3, int par4, int par5, byte[] par6ArrayOfByte) {
      return 0;
   }
}
