package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityDragger extends EntitySkeleton{
    public int dragStrength = 0;
    public EntityDragger(World par1World) {
        super(par1World);
    }
    @Override
    protected void addRandomEquipment() {
        super.addRandomEquipment();
        this.setCurrentItemOrArmor(0, (new ItemStack(Items.enderPearl, 1)).randomizeForMob(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage, (12) * Constant.getEliteMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.maxHealth, (30) * Constant.getEliteMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getEliteMobModifier("Speed",day));
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            int looting = damage_source.getLootingModifier();
            this.dropItem(Items.voucherPhase);
            int count = this.rand.nextInt(3 + looting);
            for (int i1 = 0; i1 < count; i1++) {
                this.dropItem(Item.goldNugget);
                if(this.rand.nextInt(2) == 0){
                    this.dropItem(Item.enderPearl);
                }
            }
        }
    }
    @Override
    protected EntityPlayer findPlayerToAttack(float max_distance) {
        EntityPlayer player = this.getClosestVulnerablePlayer((double)max_distance);
        return player;
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            int var6;
            for (var6 = 0; var6 < 2; ++var6) {
                this.worldObj.spawnParticle(EnumParticle.sacred, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }

            if(this.entityToAttack == null) {
                entityToAttack = this.getClosestVulnerablePlayer(16F);
                if (entityToAttack instanceof EntityPlayer) {
                    this.entityToAttack.entityFX(EnumEntityFX.curse_effect_learned);
                }
                this.dragStrength = 0;
            } else {
                if(entityToAttack.isDead || getDistanceToEntity(entityToAttack) > 16F) {
                    this.entityToAttack = null;
                    return;
                }
                if (entityToAttack instanceof EntityPlayer) {
                    ItemStack[] var3 = ((EntityPlayer) entityToAttack).getWornItems();
                    for (ItemStack wornItem : var3) {
                        if (wornItem != null && wornItem.hasEnchantment(Enchantments.enchantmentPhaseDefend, false)) {
                            return;
                        }
                    }
                    if(this.dragStrength >= 0 && this.dragStrength < 1000D){
                        this.dragStrength ++;
                        double dragpos = Math.min(dragStrength / 10000.0D, 0.025D);
                        double dx = entityToAttack.posX - this.posX;
                        double dy = entityToAttack.posY - this.posY;
                        double dz = entityToAttack.posZ - this.posZ;
                        double r = getDistanceToEntity(entityToAttack);
                        if(r < 4.0F && dy < 1.0F){
                            return;
                        }
                        double destX = entityToAttack.posX - dx * dragpos;
                        double destY = entityToAttack.posY - dy * dragpos;
                        double destZ = entityToAttack.posZ - dz * dragpos;
                        ((EntityPlayer) entityToAttack).setPositionAndUpdate(destX,destY,destZ);
                    }
                }
            }
        }
    }
}
