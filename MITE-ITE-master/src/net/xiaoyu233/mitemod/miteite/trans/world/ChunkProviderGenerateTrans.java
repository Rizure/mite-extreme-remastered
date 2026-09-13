package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.ChunkProviderGenerate;
import net.minecraft.IChunkProvider;
import net.minecraft.World;
import net.minecraft.WorldGenDungeons;
import net.xiaoyu233.mitemod.miteite.world.WorldGenBlizzardCave;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ChunkProviderGenerate.class)
public abstract class ChunkProviderGenerateTrans implements IChunkProvider {
    @Shadow
    private Random rand;

    @Shadow
    private World worldObj;

    @Inject(method = "populate", at = @At(value = "INVOKE", target = "Lnet/minecraft/BiomeBase;decorate(Lnet/minecraft/World;Ljava/util/Random;II)V", shift = At.Shift.BEFORE))
    private void injectIceDungeon(IChunkProvider par1IChunkProvider, int par2, int par3, CallbackInfo callbackInfo){
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        for(int var12 = 0; var12 < 2 && this.rand.nextInt(24) == 0; ++var12) {
            int var13 = var4 + this.rand.nextInt(16) + 8;
            int var14 = 16 + this.rand.nextInt(24);
            int var15 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenBlizzardCave()).generate(this.worldObj, this.rand, var13, var14, var15);
        }
    }
}
