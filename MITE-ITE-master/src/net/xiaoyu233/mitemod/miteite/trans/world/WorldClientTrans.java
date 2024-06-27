package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.minecraft.client.main.Main;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashSet;
import java.util.Set;

@Mixin(bdd.class)
public class WorldClientTrans extends World {
   @Shadow
   public boolean tick_has_passed;
   @Shadow
   private NetClientHandler addedTileEntityList;
   @Shadow
   private bdb entityRemoval;
   private boolean pushTimeNextTick;
   private int nextTimeCounter = 0;
   private int currentTimeCounter = 0;
   @Shadow
   private Set theCalendar;
   @Shadow
   @Final
   private Minecraft worldLogAgent;

   @Shadow
   private Set vecPool = new HashSet();

   public WorldClientTrans(IDataManager par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, MethodProfiler par5Profiler, IConsoleLogManager par6ILogAgent, long world_creation_time, long total_world_time) {
      super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
   }

   @Shadow
   protected IChunkProvider createChunkProvider() {
      return null;
   }

   @Shadow
   public Entity getEntityByID(int var1) {
      return null;
   }

//   @Overwrite
//   protected void onEntityAdded(Entity par1Entity) {
////      if(par1Entity instanceof EntityZombieBoss) {
////         ((EntityZombieBoss) par1Entity).healAndBroadcast();
////      }
//      super.onEntityAdded(par1Entity);
//      if (this.theCalendar.contains(par1Entity)) {
//         this.theCalendar.remove(par1Entity);
//      }
//   }

   @Overwrite
   public void tick() {
      if (Minecraft.java_version_is_outdated) {
         Minecraft.O.h = null;
         Minecraft.O = null;
      }

      int var1;
      if (Main.is_MITE_DS && !this.worldLogAgent.A) {
         this.addedTileEntityList.e();
         var1 = this.addedTileEntityList.g().clearReceivedPackets();
         if (var1 > 0) {
            System.out.println(var1 + " packets cleared from the dedicated server received queue");
         }
      } else {
         super.tick();
         if (this.getTimeOfDay() % 24000L < 12000L) {
            float TimeSpeed = Configs.wenscConfig.timeSpeedInDay.ConfigValue;
            if(TimeSpeed < 1.0F){
               this.nextTimeCounter = (int) (1.0F / TimeSpeed);
               if(this.currentTimeCounter ++ > this.nextTimeCounter){
                  this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
               }
            }else {
               int remains = (int) (TimeSpeed * 10 % 10);
               this.setTotalWorldTime(this.getTotalWorldTime() + (long) TimeSpeed);
               this.nextTimeCounter += remains;
               if(this.nextTimeCounter > 10){
                  this.nextTimeCounter -= 10;
                  this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
               }
            }
         } else {
            float TimeSpeed = Configs.wenscConfig.timeSpeedInNight.ConfigValue;
            if(TimeSpeed < 1.0F){
               this.nextTimeCounter = (int) (1.0F / TimeSpeed);
               if(this.currentTimeCounter ++ > this.nextTimeCounter){
                  this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
               }
            }else {
               int remains = (int) (TimeSpeed * 10 % 10);
               this.setTotalWorldTime(this.getTotalWorldTime() + (long) TimeSpeed);
               this.nextTimeCounter += remains;
               if(this.nextTimeCounter > 10){
                  this.nextTimeCounter -= 10;
                  this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
               }
            }
         }

         this.skylightSubtracted = this.calculateSkylightSubtracted(1.0F);
         this.theProfiler.startSection("reEntryProcessing");

         for(var1 = 0; var1 < 10 && !this.theCalendar.isEmpty(); ++var1) {
            Entity var2 = (Entity)this.theCalendar.iterator().next();
            this.theCalendar.remove(var2);
            if (!this.loadedEntityList.contains(var2)) {
               this.spawnEntityInWorld(var2);
            }
         }

         this.theProfiler.endStartSection("connection");
         this.addedTileEntityList.e();
         this.theProfiler.endStartSection("chunkCache");
         this.entityRemoval.unloadQueuedChunks();
         this.theProfiler.endStartSection("tiles");
         this.tickBlocksAndAmbiance();
         this.theProfiler.endSection();
         this.tick_has_passed = true;
      }

   }
}
