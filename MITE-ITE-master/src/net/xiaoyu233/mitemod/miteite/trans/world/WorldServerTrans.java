package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.entity.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(WorldServer.class)
public abstract class WorldServerTrans extends World {

   public WorldServerTrans(IDataManager par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, MethodProfiler par5Profiler, IConsoleLogManager par6ILogAgent, long world_creation_time, long total_world_time) {
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

   @Overwrite
   public Class getSuitableCreature(EnumCreatureType creature_type, int x, int y, int z) {
      boolean check_depth = this.isOverworld();
      boolean is_blood_moon_day = this.isBloodMoonDay();
      boolean is_blood_moon_up = this.isBloodMoon(true);
      boolean is_freezing_biome = this.getBiomeGenForCoords(x, z).isFreezing();
      boolean is_desert_biome = this.getBiomeGenForCoords(x, z).isDesertBiome();
      boolean can_spawn_ghouls_on_surface = is_blood_moon_up;
      boolean can_spawn_wights_on_surface = is_blood_moon_up && is_freezing_biome;
      boolean can_spawn_shadows_on_surface = is_blood_moon_up && is_desert_biome;
      boolean can_spawn_revenants_on_surface = is_blood_moon_day;
      boolean can_spawn_bone_lords_on_surface = is_blood_moon_day;
      boolean can_spawn_giant_on_surface = is_blood_moon_up;

      for (int attempt = 0; attempt < 16; ++attempt) {
         List possible_creatures = this.getChunkProvider().getPossibleCreatures(creature_type, x, y, z);
         if (possible_creatures == null || possible_creatures.isEmpty()) {
            return null;
         }

         BiomeMeta entry = (BiomeMeta) WeightedRandom.getRandomItem(this.rand, possible_creatures);
         Class entity_class = entry.entityClass;
         if (entity_class == EntityCreeper.class) {
            if (!this.hasSkylight() || this.isDaytime() || this.rand.nextInt(4) == 0 || !this.isOutdoors(x, y, z)) {
               if (this.isUnderworld()) {
                  return EntityStalkerCreeper.class;
               } else if (check_depth && ((this.rand.nextInt(40) >= y && this.rand.nextFloat() < 0.5F) || (is_blood_moon_day && this.rand.nextInt(5) == 0))) {
                  return EntityStalkerCreeper.class;
               }
               return entity_class;
            }
         } else if (entity_class == EntityStalkerCreeper.class) {
            if (this.isUnderworld() && this.rand.nextInt(20) == 0) {
               return EntityInfernalCreeper.class;
            }
            return entity_class;
         } else if (entity_class == EntitySlime.class) {
            if (!this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityGiantZombie.class) {
            if (can_spawn_giant_on_surface) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityGhoul.class) {
            if (!check_depth || y <= 56 || can_spawn_ghouls_on_surface) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityJelly.class) {
            if (this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityWight.class) {
            if (!check_depth || y <= 48 || can_spawn_wights_on_surface) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityGiantZombie.class) {
            if (can_spawn_giant_on_surface) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityVampireBat.class) {
            if (!check_depth || y <= 48 || is_blood_moon_up) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityRevenant.class) {
            if (can_spawn_revenants_on_surface || !check_depth || y <= 44) {
               if (this.rand.nextInt(4) == 0) {
                  return EntityZombieLord.class;
               }
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityExchanger.class) {
            if (getDayOfOverworld() >= 16 || y <= 40) {
               return entity_class;
            }
            return EntitySkeleton.class;
         } else if (entity_class == EntityMirrorSkeleton.class) {
            if (getDayOfOverworld() >= 16 || y <= 40) {
               return entity_class;
            }
            return EntitySkeleton.class;
         } else if (entity_class == EntityZombieLord.class) {
            if (getDayOfOverworld() >= 16 || y <= 40) {
               return entity_class;
            }
            return EntityZombie.class;
         } else if (entity_class == EntityZombieDoor.class) {
            if (getDayOfOverworld() >= 16 || y <= 40) {
               if (check_depth && (this.rand.nextInt(40) >= y) && this.rand.nextInt(3) != 0) {
                  return EntityZombieExploder.class;
               }
               return entity_class;
            }
            return EntityZombie.class;
         } else if (entity_class == EntityInvisibleStalker.class) {
            if (!check_depth || y <= 40) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityEarthElemental.class) {
            if (!check_depth || y <= 40) {
               if (this.rand.nextInt(10) == 0) {
                  return EntityOreElemental.class;
               }
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityOreElemental.class) {
            if (!check_depth || y <= 40) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityBlob.class) {
            if ((!check_depth || y <= 40) && this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityOoze.class) {
            if ((!check_depth || y <= 32) && this.getBlock(x, y - 1, z) == Block.stone && this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityNightwing.class) {
            if (!check_depth || y <= 32 || is_blood_moon_up) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityBoneLord.class) {
            if (can_spawn_bone_lords_on_surface || !check_depth || y <= 32) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityPudding.class) {
            if ((!check_depth || y <= 24) && this.getBlock(x, y - 1, z) == Block.stone && this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityHellhound.class) {
            if (!check_depth || y <= 32) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityShadow.class) {
            if (!check_depth || y <= 32 || can_spawn_shadows_on_surface) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntitySpider.class) {
            if (!this.hasSkylight() || this.rand.nextInt(4) != 0 || !this.isOutdoors(x, y, z)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityWoodSpider.class) {
            if ((this.canBlockSeeTheSky(x, y, z) || this.blockTypeIsAbove(Block.leaves, x, y, z) || this.blockTypeIsAbove(Block.wood, x, y, z)) && this.blockTypeIsNearTo(Block.wood.blockID, x, y, z, 5, 2) && this.blockTypeIsNearTo(Block.leaves.blockID, x, y + 5, z, 5, 5)) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityBlackWidowSpider.class) {
            if (this.rand.nextBoolean()) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityGhast.class) {
            if (this.isTheNether()) {
               for (Object value : this.loadedEntityList) {
                  Entity entity = (Entity) value;
                  if (entity instanceof EntityGhast) {
                     if (entity.getDistanceSqToBlock(x, y, z) < 2304.0D && this.rand.nextFloat() < 0.8F) {
                        entity_class = null;
                     }
                  }
               }
            } else if (this.isOverworld()) {
               entity_class = null;
            }
            return entity_class;
         } else if (entity_class == EntityDemonSpider.class) {
            if (!check_depth || y <= 32 || is_blood_moon_up) {
               return entity_class;
            }
            return null;
         } else if (entity_class == EntityPhaseSpider.class) {
            if (!check_depth || y <= 32) {
               if(this.rand.nextInt(check_depth ? 10 : 2) == 0){
                  return EntityEnderSpider.class;
               }
               return entity_class;
            }
            return null;
         }
         return entity_class;
      }
      return null;
   }

   @ModifyConstant(method = {
           "tickBlocksAndAmbiance"
   }, constant = @Constant(intValue = 100000))
   private static int ModifyThunderFrequencyInNormal(int value) {
      return 25000;
   }

   @ModifyConstant(method = {
           "tickBlocksAndAmbiance"
   }, constant = @Constant(intValue = 20000))
   private static int ModifyThunderFrequencyInBloodMoon(int value) {
      return 5000;
   }

}
