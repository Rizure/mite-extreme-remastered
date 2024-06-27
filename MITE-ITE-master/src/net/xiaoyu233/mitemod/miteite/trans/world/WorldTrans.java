package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static net.xiaoyu233.mitemod.miteite.util.WorldUtil.isBloodMoonDay;

@Mixin(World.class)
public abstract class WorldTrans {
   @Shadow public abstract ChunkCoordinates getSpawnPoint();

   @Shadow public static int getDayOfWorld(long unadjusted_tick){
      return 0;
   }

   public int getDayOfOverworld(){
      return getDayOfWorld(this.worldInfo.getWorldTotalTime(0));
   }

   @Shadow
   public WorldData worldInfo;
   @Shadow
   protected Set activeChunkSet;
   @Shadow
   public final int getDimensionId() {
      return 0;
   }
   @Shadow
   private long getWorldCreationTime() {
      return 0;
   }

   @Shadow
   private String getDimensionName() {
      return null;
   }
   @Overwrite
   public final List generateWeatherEvents(int day) {
      if (!this.isOverworld()) {
         Debug.setErrorMessage("generateWeatherEvents: called for " + this.getDimensionName());
      }

      List events = new ArrayList();
      if (day < 2) {
         return events;
      } else {
         long first_tick_of_day = (long)((day - 1) * 24000 - 6000);
         Random random = new Random(this.getWorldCreationTime() + (long)(this.getDimensionId() * 938473) + (long)day);
         random.nextInt();

         for(int i = 0; i < 3 && random.nextInt(4) <= 0; ++i) {
            WeatherEvent event = new WeatherEvent(first_tick_of_day + (long)random.nextInt(24000), random.nextInt(12000) + 6000);
            if (!isHarvestMoon(event.start, true) && !isHarvestMoon(event.end, true) && !isHarvestMoon(event.start + 6000L, true) && !isHarvestMoon(event.end - 6000L, true) && !isBloodMoon(event.start, false) && !isBloodMoon(event.end, false) && !isBlueMoon(event.start, false) && !isBlueMoon(event.end, false)) {
               events.add(event);
            }
         }

         if (isBloodMoon(first_tick_of_day + 6000L, false)) {
            WeatherEvent event = new WeatherEvent(first_tick_of_day + 5000L, (int) (13000 * Configs.wenscConfig.timeSpeedInDay.ConfigValue) + 1000);
            event.setStorm(event.start, event.end);
            events.add(event);
         }

         return events;
      }
   }
   @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getBlockId", at = @At(value = "FIELD", target = "Lnet/minecraft/Chunk;storageArrays:[Lnet/minecraft/ChunkSection;", shift = At.Shift.AFTER), cancellable = true)
   private void injectGetBlockId(int par1, int par2, int par3, CallbackInfoReturnable<Integer> cir, Chunk var4) {
      ChunkSection extended_block_storage = var4.storageArrays[par2 >> 4];
      if (extended_block_storage == null) {
         cir.setReturnValue(0);
         cir.cancel();
      } else {
         int par1_and_15 = par1 & 15;
         int par2_and_15 = par2 & 15;
         int par3_and_15 = par3 & 15;
         cir.setReturnValue(extended_block_storage.getExtBlockID(par1_and_15, par2_and_15, par3_and_15));
         cir.cancel();
      }
   }

   @Overwrite
   public static boolean isBloodMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      if (exclusivelyAtNight && World.isDaytime(unadjustedTick)) {
         return false;
      } else {
         return isBloodMoonDay(unadjustedTick) && !isBlueMoon(unadjustedTick, exclusivelyAtNight);
      }
   }

   @Overwrite
   public static boolean isBlueMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      if (exclusivelyAtNight && World.isDaytime(unadjustedTick)) {
         return false;
      } else {
         return unadjustedTick / 24000L + 1L == 128L;
      }
   }

   @Overwrite
   public static boolean isHarvestMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      return (!exclusivelyAtNight || !World.isDaytime(unadjustedTick)) && isBloodMoon(unadjustedTick + 72000L, exclusivelyAtNight);
   }

   public final boolean anySolidBlockIn(AxisAlignedBB bounding_box) {
      int min_x = bounding_box.getBlockCoordForMinX();
      int max_x = bounding_box.getBlockCoordForMaxX();
      int min_y = bounding_box.getBlockCoordForMinY();
      int max_y = bounding_box.getBlockCoordForMaxY();
      int min_z = bounding_box.getBlockCoordForMinZ();
      int max_z = bounding_box.getBlockCoordForMaxZ();

      for(int x = min_x; x <= max_x; ++x) {
         for(int y = min_y; y <= max_y; ++y) {
            for(int z = min_z; z <= max_z; ++z) {
               Block block = this.getBlock(x, y, z);
               if (block != null && block.isSolid(this.getBlockMetadata(x, y, z))) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Shadow
   public Block getBlock(int x, int y, int z) {
      return null;
   }

   @Shadow
   public final int getBlockMetadata(int x, int y, int z) {
      return 0;
   }

//   @Overwrite
//   public long getTotalWorldTime() {
//      //Redirect to overworld
//      return this.worldInfo.getWorldTotalTime(0);
//   }

   @Overwrite
   public final boolean isBloodMoon(boolean exclusivelyAtNight) {
      if (!this.isOverworld()) {
         return false;
      } else if (exclusivelyAtNight && this.isDaytime()) {
         return false;
      } else {
         return isBloodMoonDay(this.getTotalWorldTime()) && !this.isBlueMoon(exclusivelyAtNight);
      }
   }

   @Shadow
   private boolean isBlueMoon(boolean exclusively_at_night) {
      return false;
   }

   @Shadow
   private boolean isDaytime() {
      return false;
   }

   @Shadow public abstract long getTotalWorldTime();

   @Overwrite
   public final boolean isHarvestMoon(boolean exclusivelyAtNight) {
      return isHarvestMoon(this.getTotalWorldTime(), exclusivelyAtNight);
   }

   @Shadow
   private boolean isOverworld() {
      return false;
   }
}
