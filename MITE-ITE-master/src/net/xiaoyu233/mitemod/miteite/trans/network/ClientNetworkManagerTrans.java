package net.xiaoyu233.mitemod.miteite.trans.network;

import com.google.common.base.Charsets;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityGrenade;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Mixin(NetClientHandler.class)
public class ClientNetworkManagerTrans extends NetworkManagerTrans{
   @Shadow
   private Minecraft h;
   @Shadow
   private bdd i;

   @SoftOverride
   public void handleCraftingBoost(SPacketCraftingBoost packet) {
      this.h.h.setCraftingBoostFactor(packet.getFactor(), null);
   }

   @Overwrite
   public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange) {
      int var2 = par1Packet52MultiBlockChange.xPosition * 16;
      int var3 = par1Packet52MultiBlockChange.zPosition * 16;
      if (par1Packet52MultiBlockChange.metadataArray != null) {
         DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(par1Packet52MultiBlockChange.metadataArray));

         try {
            long before = System.nanoTime();

            int delay;
            for(delay = 0; delay < par1Packet52MultiBlockChange.size; ++delay) {
               short var8 = var4.readShort();
               short var9 = var4.readShort();
               int var10 = var9 >> 8;
               int var11 = var9 & 15;
               int var12 = var8 >> 12 & 15;
               int var13 = var8 >> 8 & 15;
               int var14 = var8 & 255;
               this.i.g(var12 + var2, var14, var13 + var3, var10, var11);
            }

            delay = (int)(System.nanoTime() - before) / 10000000;
            if (delay > 0) {
               Minecraft.MITE_log.logInfo("Long time processing handleMultiBlockChange (delay=" + delay + ") #Blocks=" + par1Packet52MultiBlockChange.size);
            }
         } catch (IOException var15) {
            System.out.println("Exception occured, packet52");
         }
      }

   }

   @Overwrite
   public void handleMultiBlockChange(Packet97MultiBlockChange packet) {
      byte[] bytes = packet.getBytes();
      int base_x = packet.chunk_x * 16;
      int base_z = packet.chunk_z * 16;
      long before = System.nanoTime();

      int delay;
      for(delay = 0; delay < packet.num_blocks; ++delay) {
         int offset = delay * 6;
         int x = base_x + bytes[offset];
         int y = bytes[offset + 1] & 255;
         int z = base_z + bytes[offset + 2];
         int block_id = bytes[offset + 3];
         int id_extra = bytes[offset + 4];
         if(id_extra < 0) {
            id_extra = 256 + id_extra;
         }
         byte metadata = bytes[offset + 5];
         this.i.g(x, y, z, block_id * 256 + id_extra, metadata);
         if (this.i.hasSkylight()) {
            this.i.getChunkFromBlockCoords(x, z).addPendingSkylightUpdate(x, y, z);
         }
      }

      delay = (int)(System.nanoTime() - before) / 10000000;
      if (delay > 0) {
         Minecraft.MITE_log.logInfo("Long time processing handleMultiBlockChange97 (delay=" + delay + ") #Blocks=" + packet.num_blocks);
      }

   }

   @Overwrite
   public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload) {
      if ("MC|ShopSize".equals(par1Packet250CustomPayload.channel)){
         DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));

         try {
            int shopSize = var2.readInt();
            Items.shopSize = shopSize;
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      } else if ("MC|TrList".equals(par1Packet250CustomPayload.channel)) {
         DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));

         try {
            int var3 = var2.readInt();
            awe var4 = this.h.n;
            if (var4 != null && var4 instanceof axw && var3 == this.h.h.openContainer.windowId) {
               IMerchant var5 = ((axw)var4).g();
               MerchantRecipeList var6 = MerchantRecipeList.a(var2);
               var5.a(var6);
            }
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      } else if ("MC|Brand".equals(par1Packet250CustomPayload.channel)) {
         this.h.h.c(new String(par1Packet250CustomPayload.data, Charsets.UTF_8));
      }

   }

   @Overwrite
   public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn) {
      double var2 = SpatialScaler.getPosX(par1Packet24MobSpawn.scaled_pos_x);
      double var4 = SpatialScaler.getPosY(par1Packet24MobSpawn.scaled_pos_y);
      double var6 = SpatialScaler.getPosZ(par1Packet24MobSpawn.scaled_pos_z);
      float var8 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_yaw);
      float var9 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_pitch);
      EntityInsentient var10 = (EntityInsentient)EntityTypes.createEntityByID(par1Packet24MobSpawn.type, this.h.f);
      if (var10 != null) {
         var10.rotationYawHead = (float)(par1Packet24MobSpawn.scaled_head_yaw * 360) / 256.0F;
         Entity[] var11 = var10.getParts();
         if (var11 != null) {
            int var12 = par1Packet24MobSpawn.entity_id - var10.entityId;

            for (Entity entity : var11) {
               entity.entityId += var12;
            }
         }

         var10.entityId = par1Packet24MobSpawn.entity_id;
         var10.getDataWatcher().a(par1Packet24MobSpawn.c());
         var10.onSendToClient(par1Packet24MobSpawn);
         var10.setPositionAndRotation(var2, var4, var6, var8, var9);
         var10.prevRenderYawOffset = var10.renderYawOffset = var8;
         var10.prevRotationYawHead = var10.rotationYawHead;
         var10.motionX = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_x);
         var10.motionY = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_y);
         var10.motionZ = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_z);
         if (par1Packet24MobSpawn.is_decoy) {
            var10.setAsDecoy();
         }

         this.i.a(par1Packet24MobSpawn.entity_id, var10);
      } else {
         Minecraft.MITE_log.logWarning("Bad mob spawning packet with entity_id:" + par1Packet24MobSpawn.type);
      }

   }
   @Overwrite
   public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn) {
      double var2;
      double var4;
      double var6;
      if (par1Packet23VehicleSpawn.position_set_using_unscaled_integers) {
         var2 = (double)par1Packet23VehicleSpawn.unscaled_pos_x;
         var4 = (double)par1Packet23VehicleSpawn.unscaled_pos_y;
         var6 = (double)par1Packet23VehicleSpawn.unscaled_pos_z;
      } else {
         var2 = SpatialScaler.getPosX(par1Packet23VehicleSpawn.scaled_pos_x);
         var4 = SpatialScaler.getPosY(par1Packet23VehicleSpawn.scaled_pos_y);
         var6 = SpatialScaler.getPosZ(par1Packet23VehicleSpawn.scaled_pos_z);
      }

      Entity var8 = null;
      if (par1Packet23VehicleSpawn.type == 10) {
         var8 = EntityMinecartAbstract.createMinecart(this.i, var2, var4, var6, par1Packet23VehicleSpawn.throwerEntityId);
      } else if (par1Packet23VehicleSpawn.type == 90) {
         Entity var9 = this.a(par1Packet23VehicleSpawn.throwerEntityId);
         if (var9 instanceof EntityPlayer) {
            var8 = new EntityFishingHook(this.i, var2, var4, var6, (EntityPlayer)var9);
         }

         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 60) {
         if (par1Packet23VehicleSpawn.arrow_item_id == -1) {
            var8 = null;
         } else {
            var2 = par1Packet23VehicleSpawn.exact_pos_x;
            var4 = par1Packet23VehicleSpawn.exact_pos_y;
            var6 = par1Packet23VehicleSpawn.exact_pos_z;
            var8 = new EntityArrow(this.i, var2, var4, var6, (ItemArrow)Item.itemsList[par1Packet23VehicleSpawn.arrow_item_id], par1Packet23VehicleSpawn.launcher_was_enchanted);
            if (par1Packet23VehicleSpawn.arrow_stuck_in_block) {
               ((EntityArrow)var8).setInGround();
            }

            EntityArrow arrow = (EntityArrow)var8;
            arrow.xTile = par1Packet23VehicleSpawn.arrow_tile_x;
            arrow.yTile = par1Packet23VehicleSpawn.arrow_tile_y;
            arrow.zTile = par1Packet23VehicleSpawn.arrow_tile_z;
            arrow.setInTile(par1Packet23VehicleSpawn.arrow_in_tile);
            arrow.setInData(par1Packet23VehicleSpawn.arrow_in_data);
         }
      } else if (par1Packet23VehicleSpawn.type == 61) {
         var8 = new EntitySnowball(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 71) {
         var8 = new EntityItemFrame(this.i, par1Packet23VehicleSpawn.unscaled_pos_x, par1Packet23VehicleSpawn.unscaled_pos_y, par1Packet23VehicleSpawn.unscaled_pos_z, par1Packet23VehicleSpawn.throwerEntityId);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 77) {
         var8 = new EntityLeash(this.i, par1Packet23VehicleSpawn.unscaled_pos_x, par1Packet23VehicleSpawn.unscaled_pos_y, par1Packet23VehicleSpawn.unscaled_pos_z);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 65) {
         var8 = new EntityEnderPearl(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 72) {
         var8 = new EntityEnderSignal(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 76) {
         var8 = new EntityFireworks(this.i, var2, var4, var6, (ItemStack)null);
      } else if (par1Packet23VehicleSpawn.type == 63) {
         var8 = new EntityLargeFirebal(this.i, var2, var4, var6, (double)par1Packet23VehicleSpawn.approx_motion_x, (double)par1Packet23VehicleSpawn.approx_motion_y, (double)par1Packet23VehicleSpawn.approx_motion_z);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 64) {
         var8 = new EntitySmallFireball(this.i, var2, var4, var6, (double)par1Packet23VehicleSpawn.approx_motion_x, (double)par1Packet23VehicleSpawn.approx_motion_y, (double)par1Packet23VehicleSpawn.approx_motion_z);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 66) {
         var8 = new EntityWitherSkull(this.i, var2, var4, var6, (double)par1Packet23VehicleSpawn.approx_motion_x, (double)par1Packet23VehicleSpawn.approx_motion_y, (double)par1Packet23VehicleSpawn.approx_motion_z);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 62) {
         var8 = new EntityEgg(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 500) {
         var8 = new EntityBrick(this.i, var2, var4, var6, Item.brick);
      } else if (par1Packet23VehicleSpawn.type == 501) {
         var8 = new EntityBrick(this.i, var2, var4, var6, Item.netherrackBrick);
      } else if (MathHelper.isInRange(par1Packet23VehicleSpawn.type, 600, 699)) {
         var8 = new EntityGelatinousSphere(this.i, var2, var4, var6, Item.slimeBall, par1Packet23VehicleSpawn.type - 600);
      } else if (par1Packet23VehicleSpawn.type == 700) {
         var8 = new EntityWeb(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 73) {
         var8 = new EntityPotion(this.i, var2, var4, var6, par1Packet23VehicleSpawn.throwerEntityId);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 75) {
         var8 = new EntityThrownExpBottle(this.i, var2, var4, var6);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 1) {
         var8 = new EntityBoat(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 50) {
         var8 = new EntityTNTPrimed(this.i, var2, var4, var6, (EntityLiving)null);
      } else if (par1Packet23VehicleSpawn.type == 51) {
         var8 = new EntityEnderCrystal(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 2) {
         var8 = new EntityItem(this.i, var2, var4, var6);
      } else if (par1Packet23VehicleSpawn.type == 70) {
         var8 = new EntityFallingBlock(this.i, (double)MathHelper.floor_double(var2) + 0.5, (double)MathHelper.floor_double(var4) + 0.5, (double)MathHelper.floor_double(var6) + 0.5, par1Packet23VehicleSpawn.throwerEntityId & '\uffff', par1Packet23VehicleSpawn.throwerEntityId >> 16);
         par1Packet23VehicleSpawn.throwerEntityId = 0;
      } else if (par1Packet23VehicleSpawn.type == 100) {
         var8 = new EntityGrenade(this.i, var2, var4, var6);
      }

      if (var8 != null) {
         ((Entity)var8).rotationYaw = SpatialScaler.getRotation(par1Packet23VehicleSpawn.scaled_yaw);
         ((Entity)var8).rotationPitch = SpatialScaler.getRotation(par1Packet23VehicleSpawn.scaled_pitch);
         if (var8 instanceof EntityBoat) {
            ((EntityBoat)var8).a(((Entity)var8).posX, ((Entity)var8).posY, ((Entity)var8).posZ, ((Entity)var8).rotationYaw, ((Entity)var8).rotationPitch, 3);
            ((Entity)var8).prevRotationYaw = ((Entity)var8).rotationYaw;
         }

         Entity[] var12 = ((Entity)var8).getParts();
         if (var12 != null) {
            int var10 = par1Packet23VehicleSpawn.entityId - ((Entity)var8).entityId;

            for(int var11 = 0; var11 < var12.length; ++var11) {
               var12[var11].entityId += var10;
            }
         }

         ((Entity)var8).entityId = par1Packet23VehicleSpawn.entityId;
         this.i.a(par1Packet23VehicleSpawn.entityId, (Entity)var8);
         if (par1Packet23VehicleSpawn.throwerEntityId > 0) {
            if (par1Packet23VehicleSpawn.type == 60) {
               Entity var13 = this.a(par1Packet23VehicleSpawn.throwerEntityId);
               if (var13 instanceof EntityLiving) {
                  EntityArrow var14 = (EntityArrow)var8;
                  var14.shootingEntity = var13;
               }

               ((Entity)var8).h(par1Packet23VehicleSpawn.exact_motion_x, par1Packet23VehicleSpawn.exact_motion_y, par1Packet23VehicleSpawn.exact_motion_z);
               return;
            }

            ((Entity)var8).h((double)par1Packet23VehicleSpawn.approx_motion_x, (double)par1Packet23VehicleSpawn.approx_motion_y, (double)par1Packet23VehicleSpawn.approx_motion_z);
         }
      }

   }

   @SoftOverride
   public void handleOverlayMessage(SPacketOverlayMessage packet) {
      this.h.r.setOverlayMsg(packet.getMsg(), packet.getTime(), packet.getColor());
   }

   public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth) {
      this.h.h.n(par1Packet8UpdateHealth.healthMP);
      this.h.h.getFoodStats().setSatiation(par1Packet8UpdateHealth.satiation, false);
      this.h.h.getFoodStats().setNutrition(par1Packet8UpdateHealth.nutrition, false);
      this.h.h.setPhytonutrients(par1Packet8UpdateHealth.phytonutrients);
      this.h.h.setProtein(par1Packet8UpdateHealth.protein);
      this.h.h.money = par1Packet8UpdateHealth.money;
      this.h.h.setEnhanceLevel(par1Packet8UpdateHealth.enhance_level);
      if (this.h.h.vision_dimming < par1Packet8UpdateHealth.vision_dimming) {
         this.h.h.vision_dimming = par1Packet8UpdateHealth.vision_dimming;
      }
   }

   public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems) {
      ClientPlayer var2 = this.h.h;
      if (par1Packet104WindowItems.windowId == 0) {
         var2.inventoryContainer.a(par1Packet104WindowItems.itemStack);
      } else if (par1Packet104WindowItems.windowId == var2.openContainer.windowId) {
         var2.openContainer.a(par1Packet104WindowItems.itemStack);
      }

      this.h.h.itemsSynced();
   }

   @SoftOverride
   public void processFinishForgingPacket(SPacketFinishForging packet) {
      awe openingGUI = this.h.n;
      if (openingGUI instanceof GuiForgingTable) {
         ((GuiForgingTable)openingGUI).enableButton();
      }

   }

   @Override
   public void handleUpdateDefense(BiPacketUpdateDefense packet) {
      this.h.h.setDefenseCooldown(packet.getTime());
   }

   @SoftOverride
   public void processForgingTableInfoPacket(SPacketForgingTableInfo packet) {
      awe openingGUI = this.h.n;
      if (openingGUI instanceof GuiForgingTable) {
         if (packet.getInfo() instanceof SPacketForgingTableInfo.EnhanceInfo) {
            SPacketForgingTableInfo.EnhanceInfo info = (SPacketForgingTableInfo.EnhanceInfo)packet.getInfo();
            ((GuiForgingTable)openingGUI).setEnhanceInfo(info.getChanceOfFailure(), info.getFaultFeedbacks(), info.getDuration(), info.getHammerDurabilityCost(), info.getAxeDurabilityCost());
         } else {
            ((GuiForgingTable)openingGUI).setInfo(packet.getInfo().asString(), packet.getInfo().getColor());
         }
      }

   }
   @Shadow
   private Entity a(int par1) {
      return null;
   }
}
