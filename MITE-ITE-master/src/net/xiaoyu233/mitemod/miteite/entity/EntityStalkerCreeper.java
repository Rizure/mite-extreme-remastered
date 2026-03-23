package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;

public class EntityStalkerCreeper extends EntityCreeper {
   public EntityStalkerCreeper(World par1World) {
      super(par1World);
      this.setSize(this.width * getScale(), this.height * getScale());
      this.explosionRadius *= 1.5F;
      this.modifyExplosionByConfig();
   }
   @Override
   public void onLivingUpdate(){
      super.onLivingUpdate();
      if(this.onServer()){
         int x = this.getBlockPosX();
         int y = this.getBlockPosY() + 1;
         int z = this.getBlockPosZ();
         if(this.worldObj.getBlockLightValue(x,y,z) == 0 && this.getWorld().isOverworld()){
            this.addPotionEffect(new MobEffect(MobEffectList.invisibility.id, 5, 1, true));
         }
      }
   }

   public int getFragParticle() {
      return Items.fragsStalkerCreeper.itemID;
   }

   public int getExperienceValue() {
      return super.getExperienceValue() * 2;
   }

   public float getNaturalDefense(DamageSource damage_source) {
      return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : 1.0F);
   }

   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player && this.rand.nextBoolean() && this.worldObj.isOverworld()) {
         this.dropItem(Items.voucherDestruction);
      }

      int num_drops = this.rand.nextInt(3);
      if (num_drops == 0) {
         num_drops = this.rand.nextInt(2);
      }

      int fortune = damage_source.getLootingModifier();
      if (fortune > 0) {
         num_drops += this.rand.nextInt(fortune + 1);
      }

      if (num_drops > 0 && !recently_hit_by_player) {
         num_drops -= this.rand.nextInt(num_drops + 1);
      }

      for(int i = 0; i < num_drops; ++i) {
         this.dropItem(this.getDropItemId(), 1);
      }
   }
   private void modifyExplosionByConfig() {
      if (Configs.wenscConfig.infernalCreeperBoost.ConfigValue) {
         this.explosionRadius *= 1.5F;
      }
   }
}
