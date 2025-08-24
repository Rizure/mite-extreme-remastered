package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityExperienceOrb.class)
public class EntityExperienceOrbTrans extends Entity{
   @Shadow
   public int xpOrbAge;
   @Shadow
   private int xpOrbHealth = 5;
   @Shadow
   private int xpValue;
   @Shadow
   public String player_this_belongs_to;
   @Shadow
   public boolean created_by_bottle_of_enchanting;
   public EntityExperienceOrbTrans(World par1World) {
      super(par1World);
   }
   @Overwrite
   public static int getXPSplit(int par0) {
       return par0 / 3 > 0 ? par0 / 3 : (par0 == 2 ? 2 : 1);
   }

    @Redirect(
            method = {"onUpdate"},
            at = @At(
                    ordinal = 0,
                    value = "INVOKE",
                    target = "Lnet/minecraft/EntityPlayer;getEyeHeight()F"
            )
    )
    public float modifyOrbAdsorptionCenter(EntityPlayer entityPlayer) {
        return -0.67F;
    }
   @Overwrite
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("Health", (short)((byte)this.xpOrbHealth));
      par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
      par1NBTTagCompound.setInteger("Value", this.xpValue);
      if (this.player_this_belongs_to != null) {
         par1NBTTagCompound.setString("player_this_belongs_to", this.player_this_belongs_to);
      }

      if (this.created_by_bottle_of_enchanting) {
         par1NBTTagCompound.setBoolean("created_by_bottle_of_enchanting", true);
      }

   }

   @Shadow
   protected void entityInit() {
      
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.xpOrbHealth = par1NBTTagCompound.getShort("Health") & 255;
      this.xpOrbAge = par1NBTTagCompound.getShort("Age");
      this.xpValue = par1NBTTagCompound.getInteger("Value");
      if (par1NBTTagCompound.hasKey("player_this_belongs_to")) {
         this.player_this_belongs_to = par1NBTTagCompound.getString("player_this_belongs_to");
      }

      this.created_by_bottle_of_enchanting = par1NBTTagCompound.getBoolean("created_by_bottle_of_enchanting");
   }
}
