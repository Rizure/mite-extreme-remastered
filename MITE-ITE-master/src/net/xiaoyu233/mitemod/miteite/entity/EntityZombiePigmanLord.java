package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityZombiePigmanLord extends EntityPigZombie {
    private int fx_counter;
    public EntityZombiePigmanLord(World par1World) {
        super(par1World);
    }

    @Override
    public void addRandomEquipment() {
        int day = this.getWorld().getDayOfOverworld();
        this.setCurrentItemOrArmor(0,new ItemStack(Items.VIBRANIUM_SWORD,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(1,new ItemStack(Items.VIBRANIUM_HELMET,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(2,new ItemStack(Items.VIBRANIUM_CHESTPLATE,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(3,new ItemStack(Items.VIBRANIUM_LEGGINGS,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(4,new ItemStack(Items.VIBRANIUM_BOOTS,1).randomizeForMob(this,day > 64));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage, 18 * Constant.getEliteMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.maxHealth, 60 * Constant.getEliteMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.4D * Constant.getEliteMobModifier("Speed",day));
    }

    @Override
    public void handleHealthUpdate(EnumEntityState par1) {
        super.handleHealthUpdate(par1);
        if (par1 == EnumEntityState.unused0){
            this.generateRandomParticles(EnumParticle.lava);
        }
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.voucherOverlord);
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = (day / 32) >= 3 ? 3 : ((day / 32) + 1);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.diamond);
            }
        }
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if (fx_counter > 0){
                fx_counter--;
            }else {
                this.fx_counter = 60;
                this.worldObj.setEntityState(this,EnumEntityState.unused0);
            }
        }
    }
}
