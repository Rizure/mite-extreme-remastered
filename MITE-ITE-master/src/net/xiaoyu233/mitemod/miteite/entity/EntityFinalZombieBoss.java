package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.Enchantment;
import net.minecraft.GenericAttributes;
import net.minecraft.ItemStack;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityFinalZombieBoss extends EntityZombieBoss{
    public EntityFinalZombieBoss(World par1World) {
        super(par1World);
    }
    @Override
    protected void addRandomEquipment() {
        ItemStack Weapon = new ItemStack(Items.VIBRANIUM_BATTLE_AXE);
        Weapon.addEnchantment(Enchantment.sharpness,9);
        Weapon.addEnchantment(Enchantment.unbreaking,5);
        Weapon.addEnchantment(Enchantment.piercing,7);
        Weapon.setItemDamage(0);
        this.setCurrentItemOrArmor(0, Weapon);
        this.setHelmet(new ItemStack(Items.VIBRANIUM_HELMET).setItemDamage(0));
        this.setCuirass(new ItemStack(Items.VIBRANIUM_CHESTPLATE).setItemDamage(0));
        this.setLeggings(new ItemStack(Items.VIBRANIUM_LEGGINGS).setItemDamage(0));
        this.setBoots(new ItemStack(Items.VIBRANIUM_BOOTS).setItemDamage(0));
        this.getBoots().addEnchantment(Enchantment.protection,7);
        this.getBoots().addEnchantment(Enchantment.unbreaking,5);
        this.getBoots().addEnchantment(Enchantment.featherFalling,4);
        this.getBoots().addEnchantment(Enchantment.speed,5);
        this.getLeggings().addEnchantment(Enchantment.protection,7);
        this.getLeggings().addEnchantment(Enchantment.unbreaking,5);
        this.getLeggings().addEnchantment(Enchantment.free_action,4);
        this.getCuirass().addEnchantment(Enchantment.protection,7);
        this.getCuirass().addEnchantment(Enchantment.unbreaking,5);
        this.getCuirass().addEnchantment(Enchantment.thorns,3);
        this.getCuirass().addEnchantment(Enchantment.regeneration,5);
        this.getCuirass().addEnchantment(Enchantment.endurance,4);
        this.getHelmet().addEnchantment(Enchantment.protection,7);
        this.getHelmet().addEnchantment(Enchantment.respiration,3);
        this.getHelmet().addEnchantment(Enchantment.aquaAffinity,1);
        this.getHelmet().addEnchantment(Enchantment.unbreaking,5);
    }
    @Override
    protected boolean isFinal(){
        return true;
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.maxHealth, 1250);
    }
}
