package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockReed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockReed.class)
public class BlockReedTrans {
    @ModifyConstant(method = {
            "updateTick"
    }, constant = @Constant(intValue = 16))
    private static int ModifyThunderFrequencyInNormal(int value) {
        return 5;
    }
}
