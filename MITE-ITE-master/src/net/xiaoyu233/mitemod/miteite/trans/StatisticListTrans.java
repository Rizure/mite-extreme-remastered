package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.Block;
import net.minecraft.Statistic;
import net.minecraft.StatisticList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatisticList.class)
public class StatisticListTrans {
    @ModifyConstant(method = {
            "initBreakableStats",
            "initStats",
            "initMinableStats",
            "initUsableStats"
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
    @Inject(method = "replaceAllSimilarBlocks([Lnet/minecraft/Statistic;)V",at = @At("RETURN"))
    private static void addSlabBlock(Statistic[] par0ArrayOfStatBase, CallbackInfo callbackInfo){
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.obsidianDoubleSlab.blockID, Block.obsidianSingleSlab.blockID);
    }
    @Shadow
    private static void replaceSimilarBlocks(Statistic[] par0ArrayOfStatBase, int par1, int par2) {}
}
