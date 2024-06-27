package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public class EntityCreeperTrans extends EntityMonster {
   private boolean burntoDeath = false;
   @Shadow
   protected float explosionRadius;
   @Shadow
   private int fuseTime;

   public EntityCreeperTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23D * Constant.getNormalMobModifier("Speed",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, 20 * Constant.getNormalMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.explosionRadius = 1.25f;
//      this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)((double)day * 0.6D), 40));
      this.setExplosionTime(Configs.wenscConfig.creeperFuseTime.ConfigValue);
   }

   public int getExplosionTime() {
      return this.fuseTime;
   }

   public void setExplosionTime(int br) {
      this.fuseTime = br;
   }
   public void onDeath(DamageSource par1DamageSource) {
      super.onDeath(par1DamageSource);
      if (par1DamageSource.getResponsibleEntity() instanceof EntitySkeleton) {
         int var2 = Item.recordUnderworld.itemID + this.rand.nextInt(Item.recordLegends.itemID - Item.recordUnderworld.itemID + 1);
         this.dropItem(var2, 1);
      }
      if (par1DamageSource.isFireDamage() || par1DamageSource.isLavaDamage() || par1DamageSource.isExplosion()){
         this.burntoDeath = true;
      }
   }
   @Override
   public void onDeathUpdate(){
      super.onDeathUpdate();
      if (this.deathTime == 20 && (this.burntoDeath || this.getPowered())) {
         if (!this.worldObj.isRemote) {
            boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            float explosion_size_vs_blocks = this.explosionRadius * 0.715F;
            float explosion_size_vs_living_entities = this.explosionRadius * 1.1F;
            if (this.getPowered()) {
               this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 4.0F), this.posZ, explosion_size_vs_blocks * 2.0F, explosion_size_vs_living_entities * 2.0F, var2);
            } else {
               this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 4.0F), this.posZ, explosion_size_vs_blocks, explosion_size_vs_living_entities, var2);
            }
            this.entityFX(EnumEntityFX.frags);
            if(this.getPowered()){
               this.dropItem(Items.powder_thunder);
            }
         }
      }
   }
   @Shadow
   public boolean getPowered() {
      return false;
   }
}
