package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockMushroom;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(BlockMushroom.class)
public class BlockMushroomTrans {
    @Inject(method = "tryGrowGiantMushroom(Lnet/minecraft/World;IIILnet/minecraft/EntityPlayer;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;triggerAchievement(Lnet/minecraft/Statistic;)V"))
    public void newdrops(World world,int par1,int par2,int par3, EntityPlayer player, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.powder_dark,1));
    }
}
