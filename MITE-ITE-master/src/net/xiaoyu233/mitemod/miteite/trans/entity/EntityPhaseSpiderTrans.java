package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPhaseSpider.class)
public class EntityPhaseSpiderTrans extends EntityWoodSpider {

    @Shadow
    int num_evasions;

    public EntityPhaseSpiderTrans(World world) {
        super(world);
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
        this.setEntityAttribute(GenericAttributes.maxHealth, 6 * Constant.getNormalMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.attackDamage, 3 * Constant.getNormalMobModifier("Damage",day));
    }
    @Overwrite
    public EntityDamageResult attackEntityFrom(Damage damage) {
        boolean can_evade = true;
        DamageSource damage_source = damage.getSource();
        boolean has_phasedefend = false;
        if(damage_source != null){
            ItemStack item_stack = damage_source.getItemAttackedWith();
            if (item_stack != null && item_stack.hasEnchantment(Enchantments.enchantmentPhaseDefend, true)) {
                has_phasedefend = true;
            }
        }
        if (damage.isFallDamage() || damage.isFireDamage() || damage.isPoison() || has_phasedefend) {
            can_evade = false;
        }

        if (can_evade && this.num_evasions > 0) {
            --this.num_evasions;
            Entity entity = this.getTarget();
            if (entity == null) {
                entity = damage.getResponsibleEntityP();
            }

            if (this.tryTeleportAwayFrom(entity, 3.0)) {
                return null;
            }
        }

        return super.attackEntityFrom(damage);
    }
    @Shadow
    public boolean tryTeleportAwayFrom(Entity entity, double min_distance) {
        return false;
    }

}