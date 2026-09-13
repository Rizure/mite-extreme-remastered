package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEnderEye.class)
public class ItemEnderEyeTrans {
    @Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;getSelectedObject(FZ)Lnet/minecraft/RaycastCollision;", shift = At.Shift.AFTER), cancellable = true)
    private void tryLaunchAltar(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> callbackInfo){
        RaycastCollision rc = player.getSelectedObject(partial_tick, false);
        if (rc != null && rc.isBlock() && rc.getBlockHit() == Blocks.endAltar) {
            if(player.onServer()){
                WorldServer server = player.getWorldServer();
                World world = player.getWorld();
                ChunkPosition strongholdPos = server.findClosestStructure("Stronghold", (int)player.posX, 64, (int)player.posZ);
                int x = strongholdPos.x;
                int y;
                int z = strongholdPos.z;
                if(strongholdPos != null){
                    for(y = 255; y >= 16; y--){
                        if(!world.isAirBlock(x,y,z)){
                            break;
                        }
                    }
                    player.playSound("mob.zombie.remedy", 4.0F, 0.5F);
                    player.is_runegate_teleporting = true;
                    player.getAsEntityPlayerMP().runegate_destination_coords = new int[]{x,y,z};
                    player.getAsEntityPlayerMP().playerNetServerHandler.sendPacket(new Packet85SimpleSignal(EnumSignal.runegate_start));
                }else {
                    callbackInfo.setReturnValue(false);
                    callbackInfo.cancel();
                }
            }
            if (player.onClient()) {
                player.swingArm();
            } else if (!player.inCreativeMode()) {
                player.convertOneOfHeldItem((ItemStack)null);
            }
            callbackInfo.setReturnValue(true);
            callbackInfo.cancel();
        }
    }
}
