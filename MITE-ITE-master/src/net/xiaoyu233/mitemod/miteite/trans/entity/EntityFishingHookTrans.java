package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityFishingHook.class)
public abstract class EntityFishingHookTrans extends Entity {
   @Shadow
   public EntityPlayer angler;
   @Shadow
   public Entity bobber;
   @Shadow
   public int shake;
   @Shadow
   private boolean inGround;
   @Shadow
   private int ticksCatchable;

   public EntityFishingHookTrans(World par1World) {
      super(par1World);
   }

   @Redirect(method = "catchFish",
           at = @At(value = "NEW",
                   target = "(Lnet/minecraft/World;DDDI)Lnet/minecraft/EntityExperienceOrb;"))
   private EntityExperienceOrb ctorFishingExp(World par1World, double par2, double par4, double par6, int par8){
      return new EntityExperienceOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, Configs.wenscConfig.fishingXp.ConfigValue);
   }

   @Overwrite
   public Item getFishType() {
      int x;
      int y;
      int z;
      x = MathHelper.floor_double(this.posX);
      y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
      z = MathHelper.floor_double(this.posZ);
      long count = 0L;

      Item[] fishTypeListStage0 = new Item[]{Item.bootsLeather, Item.arrowRustedIron, Item.silverNugget, Item.goldNugget, Item.leather, Item.getItem(Block.waterlily), Item.rottenFlesh, Item.bone, Item.stick, Item.silk, Item.sinew, Item.bowlWater, Item.copperNugget, Item.coinCopper, Item.chainRustedIron, Item.getItem(Block.cactus), Item.getItem(Block.planks), Item.getItem(Block.sandStone), Item.getItem(Block.planks)};

      Item[] fishTypeListStage1 = new Item[]{Item.coinSilver, Item.coinGold, Item.ironNugget, Item.ingotIron, Item.ingotGold, Item.ingotSilver, Item.bow, Item.ancientMetalNugget, Item.mithrilNugget, Item.adamantiumNugget, Item.ingotIron, Item.appleGold, Items.powder_liquid, Item.carrot, Item.potato, Item.onion};

      Item[] fishTypeListStage2 = new Item[]{Items.VIBRANIUM_NUGGET, Item.ingotMithril, Item.ingotAdamantium, Item.ingotAncientMetal, Item.coinMithril, Item.coinAncientMetal};

      int k;
      for( k = -16; k <= 16; ++k) {
         for(int dz = -16; dz <= 16; ++dz) {
            for(int dy = -3; dy <= 0; ++dy) {
               Block block = this.worldObj.getBlock(x + k, y + dy, z + dz);
               if (block == Block.dirt || block == Block.grass || block == Block.sand) {
                  ++count;
               }
            }
         }
      }
      int fortune = EnchantmentManager.getEnchantmentLevel(Enchantments.enchantmentLuckOfTheSea.effectId, this.angler.getHeldItemStack());
      this.angler.triggerAchievement(Achievements.fishFortune);
      if (this.rand.nextInt(25) == 0) {
         return Items.voucherFishing;
      }
      int outcome = this.rand.nextInt(100 + fortune * 25);
      if (outcome > 100) {
         if (outcome > 200) {
            return fishTypeListStage2[this.rand.nextInt(fishTypeListStage2.length)];
         }
         if (outcome > 150) {
            return fishTypeListStage1[this.rand.nextInt(fishTypeListStage1.length)];
         }
         return fishTypeListStage0[this.rand.nextInt(fishTypeListStage0.length)];
      }
      if (this.worldObj.getBiomeGenForCoords(x, z) == BiomeBase.ocean && count == 0L) {
         if(this.rand.nextInt(10) == 0){
            return Item.fishLargeRaw;
         }
         if(this.rand.nextInt(10) == 0){
            return fishTypeListStage0[this.rand.nextInt(fishTypeListStage0.length)];
         }
      }
      return Item.fishRaw;
//      if (Configs.wenscConfig.isOpenExtremeFishing.ConfigValue) {
//
//      } else if (this.rand.nextFloat() < 0.8F) {
//         return Item.fishRaw;
//      } else {
//         x = MathHelper.floor_double(this.posX);
//         y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
//         z = MathHelper.floor_double(this.posZ);
//         if (this.worldObj.getBiomeGenForCoords(x, z) != BiomeBase.ocean) {
//            return Item.fishRaw;
//         } else {
//            for(int dx = -16; dx <= 16; ++dx) {
//               for(int dz = -16; dz <= 16; ++dz) {
//                  for(int dy = -3; dy <= 0; ++dy) {
//                     Block block = this.worldObj.getBlock(x + dx, y + dy, z + dz);
//                     if (block == Block.dirt || block == Block.grass || block == Block.sand) {
//                        return Item.fishRaw;
//                     }
//                  }
//               }
//            }
//
//            return Item.fishLargeRaw;
//         }
//      }
   }
}