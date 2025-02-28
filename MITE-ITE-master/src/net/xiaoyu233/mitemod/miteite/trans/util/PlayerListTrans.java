//package net.xiaoyu233.mitemod.miteite.trans.util;
//
//import net.minecraft.*;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(PlayerList.class)
//public class PlayerListTrans {
//    @Inject(method = "playerLoggedOut", at = @At(value = "INVOKE", target = "Lnet/Minecraft/World;removePlayerEntityDangerously(Lnet/Minecraft/Entity;)V", shift = At.Shift.BEFORE))
//    public void aVoidSusDead(ServerPlayer par1EntityPlayerMP, CallbackInfo callbackInfo){
//        if(par1EntityPlayerMP.ridingEntity instanceof EntityPlayer){
//            par1EntityPlayerMP.dismountEntity(par1EntityPlayerMP.ridingEntity);
//        }
//    }
//}
