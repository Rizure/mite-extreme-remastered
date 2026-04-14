package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockFlowerPotSapling;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockFlowerPot.class)
public class BlockFlowerPotTrans extends Block {
   @Shadow
   public static ItemStack getPlantForMeta(int par0) {
      return null;
   }

   protected BlockFlowerPotTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   @ModifyConstant(method = "isValidMetadata", constant = @Constant(intValue = 13))
   private int modify(int constant) {
      return constant + 1;
   }

   @Inject(method = "getPlantForMeta", at = @At("HEAD"), cancellable = true)
   private static void appendSaplingPot(int par0, CallbackInfoReturnable<ItemStack> cir) {
      if (par0 == 13) cir.setReturnValue(new ItemStack(Blocks.sapling1));
   }

   @Inject(method = "onBlockActivated", at = @At(value = "INVOKE", target = "getMetaForPlant", ordinal = 0), cancellable = true)
   private void extendSaplingInPot(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z, CallbackInfoReturnable<Boolean> cir) {
      ItemStack item_stack = player.getHeldItemStack();
      if (item_stack.itemID != Blocks.sapling1.blockID)
         return;
      if (player.onServer()) {
         Block pot = world.getBlock(x, y, z);
         int metadata = world.getBlockMetadata(x, y, z);
         if (pot instanceof BlockFlowerPotSapling) {
            BlockBreakInfo info = new BlockBreakInfo(world, x, y, z);
            dropBlockAsEntityItem(info, BlockFlowerPotSapling.getPlantForMeta(metadata));
            world.playSoundAtBlock(x, y, z, "random.pop", 0.1F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }
         world.setBlock(x, y, z, Blocks.flowerPotSapling.blockID, BlockFlowerPotSapling.getMetaForPlant(item_stack), 2);
         if (!player.inCreativeMode())
            player.convertOneOfHeldItem(null);
      }
      cir.setReturnValue(true);
      cir.cancel();
   }

   @Inject(method = "getMetaForPlant", at = @At("HEAD"), cancellable = true)
   private static void inject(ItemStack par0ItemStack, CallbackInfoReturnable<Integer> cir) {
      int var1 = (par0ItemStack.getItem()).itemID;
      if (var1 == Blocks.sapling1.blockID)
         cir.setReturnValue((par0ItemStack.getItemSubtype() == 0) ? 13 : 0);
   }

   public boolean updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if (super.updateTick(par1World, par2, par3, par4, par5Random)) {
         return true;
      } else if (par1World.getBlockLightValue(par2, par3, par4) >= 8 && par5Random.nextInt(50) == 0) {
         int metadata = par1World.getBlockMetadata(par2, par3, par4);
         if (par1World.getBlock(par2,par3,par4) instanceof BlockFlowerPotSapling){
            if (BlockFlowerPotSapling.getPlantForMeta(metadata) != null) {
               par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, BlockFlowerPotSapling.getPlantForMeta(metadata)));
               if(par5Random.nextInt(10) == 0)
                  par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, new ItemStack(Item.goldNugget)));
            }
         } else if (par1World.getBlock(par2,par3,par4) instanceof BlockFlowerPotMulti){
            if (BlockFlowerPotMulti.getPlantForMeta(metadata) != null) {
               par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, BlockFlowerPotMulti.getPlantForMeta(metadata)));
               if(par5Random.nextInt(10) == 0)
                  par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, new ItemStack(Item.goldNugget)));
            }
         } else if (par1World.getBlock(par2,par3,par4) instanceof BlockFlowerPot){
            if (BlockFlowerPot.getPlantForMeta(metadata) != null) {
               par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, BlockFlowerPot.getPlantForMeta(metadata)));
               if(par5Random.nextInt(10) == 0)
                  par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3, par4 + 0.5F, new ItemStack(Item.goldNugget)));
            }
         }
         return true;
      } else {
         return false;
      }
   }
   @Inject(method = "<init>", at = @At("RETURN"))
   private void setTickRandomlyToGrow(CallbackInfo callbackInfo){
      this.setTickRandomly(true);
   }
}
