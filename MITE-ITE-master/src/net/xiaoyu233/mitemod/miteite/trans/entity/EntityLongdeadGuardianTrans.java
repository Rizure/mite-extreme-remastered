package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

import static net.xiaoyu233.mitemod.miteite.util.MonsterUtil.getRandomWeaponTier;

@Mixin(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianTrans extends EntityLongdead {
   @Shadow
   ItemStack stowed_item_stack;
   public EntityLongdeadGuardianTrans(World world) {
      super(world);
   }

   @Overwrite
   public void addRandomWeapon() {
      super.addRandomWeapon();
      int day_of_world = MinecraftServer.F().getOverworld().getDayOfOverworld();
      if(this.getSkeletonType() == 0){
         if(day_of_world > 64){
            this.stowed_item_stack = (new ItemStack(Constant.SWORDS[getRandomWeaponTier(rand,day_of_world)])).randomizeForMob(this, true);
         }else {
            this.stowed_item_stack = new ItemStack(Item.daggerAncientMetal).randomizeForMob(this, true);
         }
      }
   }

   @Overwrite
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
   }

   @Overwrite
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }
   protected boolean willChangeWeapon(){
      return true;
   }

}
