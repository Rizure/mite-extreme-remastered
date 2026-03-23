package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityDamageSource.class)
public class EntityDamageSourceTrans extends DamageSource{
   @Shadow
   private Entity immediate_entity;
   @Shadow
   private Entity responsible_entity;

   protected EntityDamageSourceTrans(String par1Str) {
      super(par1Str);
   }

   @Overwrite
   public ChatMessage getDeathMessage(EntityLiving par1EntityLivingBase) {
      Entity entity = this.responsible_entity == null ? this.immediate_entity : this.responsible_entity;
      ItemStack var2 = entity instanceof EntityLiving ? ((EntityLiving)entity).getHeldItemStack() : null;
      String var3 = "death.attack." + (this.is_hand_damage ? "hand_damage" : this.damageType);
      String var4 = var3 + ".item";
      return var2 != null && var2.hasDisplayName() && LocaleI18n.func_94522_b(var4) ?
              ChatMessage.createFromTranslationWithSubstitutions(var4, new Object[]{ChatMessage.createFromTranslationKey(par1EntityLivingBase.getEntityName()),ChatMessage.createFromTranslationKey(entity.getEntityName()), var2.getDisplayName()}) :
              ChatMessage.createFromTranslationWithSubstitutions(var3, new Object[]{ChatMessage.createFromTranslationKey(par1EntityLivingBase.getEntityName()),ChatMessage.createFromTranslationKey(entity.getEntityName())});
   }
}
