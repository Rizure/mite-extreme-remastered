package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityBoneLord.class)
public class EntityBoneLordTrans extends EntitySkeletonTrans {
   public EntityBoneLordTrans(World par1World) {
      super(par1World);
   }

   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      super.dropFewItems(recently_hit_by_player, damage_source);
      if (recently_hit_by_player){
         int day = this.getWorld().getDayOfOverworld();
         int count = Math.min(day / 8, 6);
         for (int i1 = 0; i1 < count; i1++) {
            this.dropItemStack(new ItemStack(Items.dyePowder, 1, 4));
         }
      }
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      if (day < 64) {
         this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }
      this.initStockedWeapon();

   }

   @Override
   protected boolean willChangeWeapon() {
      return Configs.wenscConfig.boneLordAndLongdeadChangeWeaponChance.ConfigValue > this.rand.nextFloat();
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      boolean boneLordTweak = Configs.wenscConfig.boneLordTweak.ConfigValue;
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 44.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage, (boneLordTweak ? 12 : 8) * Constant.getEliteMobModifier("Damage",day));
      this.setEntityAttribute(GenericAttributes.maxHealth, (boneLordTweak ? 45 : 30) * Constant.getEliteMobModifier("Health",day));
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getEliteMobModifier("Speed",day));
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Overwrite
   public int getExperienceValue() {
      return Configs.wenscConfig.boneLordTweak.ConfigValue ? super.getExperienceValue() * 6 : super.getExperienceValue() * 3;
   }

}
