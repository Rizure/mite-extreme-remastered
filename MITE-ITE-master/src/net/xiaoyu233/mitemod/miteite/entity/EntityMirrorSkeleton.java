package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.lwjgl.Sys;

import java.util.Collection;
import java.util.Iterator;

public class EntityMirrorSkeleton extends EntitySkeleton {
    private boolean hasClonePlayer = false;

    public EntityMirrorSkeleton(World par1World) {
        super(par1World);
    }

    public boolean canNeverPickUpItem(Item item) {
        return true;
    }
    @Override
    protected void addRandomEquipment() {
        this.addRandomWeapon();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
        if (day < 80) {
            this.setBoots((new ItemStack(Item.bootsGold)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsGold)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateGold)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetGold)).randomizeForMob(this, true));
        } else {
            MonsterUtil.addDefaultArmor(day, this, true);
        }
    }
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.voucherMagic);
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = Math.min((day + 16) / 32, 3);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.emerald);
            }
        }
    }

    @Override
    public void dropContainedItems() {

    }

    @Override
    protected void dropEquipment(boolean recently_hit_by_player, int par2) {

    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.attackDamage, 1);
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if(ticksExisted % 20 == 0) {
                EntityLiving target = this.getTarget();
                if(target instanceof EntityPlayer) {
                    if(hasClonePlayer == false) {
                        if(target.getMaxHealth() > this.getMaxHealth()) {
                            this.setEntityAttribute(GenericAttributes.maxHealth, target.getMaxHealth());
                            this.setHealth(target.getMaxHealth());
                        }
                        this.hasClonePlayer = true;
                    }

                    Collection collection = target.getActivePotionEffects();
                    Iterator var7 = collection.iterator();
                    while(var7.hasNext()) {
                        MobEffect var8 = (MobEffect)var7.next();
                        this.addPotionEffect(new MobEffect(var8.getPotionID(), var8.getDuration()));
                    }

                    if(target.getHelmet() != null) {
                        this.setHelmet(target.getHelmet());
                    }
                    if(target.getBoots() != null) {
                        this.setBoots(target.getBoots());
                    }
                    if(target.getCuirass() != null) {
                        this.setCuirass(target.getCuirass());
                    }
                    if(target.getLeggings() != null) {
                        this.setLeggings(target.getLeggings());
                    }
                    if(target.getHeldItemStack() != null) {
                        this.setCurrentItemOrArmor(0, target.getHeldItemStack());
                    }
                }
            }
        }
    }
}
