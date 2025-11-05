package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityZombieDoor extends EntityZombie {
    private int spawnCounter;
    private int spawnSums;
    private boolean haveTryToSpawnExchanger = false;

    private boolean modifiedAttribute = false;
    private final Item [] doorList = new Item[] {Items.doorWood, Items.doorGold, Items.doorCopper, Items.doorSilver, Items.doorIron,Items.doorAncientMetal ,Items.doorMithril, Items.doorAdamantium};
    private int danger_level;

    public EntityZombieDoor(World par1World) {
        super(par1World);
        this.danger_level = Constant.GARandom.nextInt(doorList.length);
    }

    public EntityZombieDoor(World par1World, int danger_level) {
        super(par1World);
        this.danger_level = danger_level;
    }

    @Override
    protected void addRandomEquipment() {
        super.addRandomEquipment();
        this.setCurrentItemOrArmor(0, (new ItemStack(doorList[this.danger_level], 1)).randomizeForMob(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage,(8) * Constant.getEliteMobModifier("Damage",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.maxHealth, (30) * Constant.getEliteMobModifier("Health",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.movementSpeed, (0.23D) * Constant.getEliteMobModifier("Speed",day,this.worldObj.isOverworld()));
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            if(this.getHeldItem() == Item.doorAdamantium){
                this.dropItem(Items.ban);
            }
            boolean shouldDropItem = this.danger_level > 3 || this.rand.nextBoolean();
            if(shouldDropItem){
                this.dropItem(Items.voucherDestruction);
            }
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = Math.min((day + 16) / 32, 3);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.emerald);
            }
            this.dropItem(Items.zombieBrain);
        }
    }
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("danger_level", (byte) this.danger_level);
        par1NBTTagCompound.setShort("spawnCounter", (short) this.spawnCounter);
        par1NBTTagCompound.setByte("spawnSums", (byte) this.spawnSums);
        par1NBTTagCompound.setBoolean("modifiedAttribute", this.modifiedAttribute);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.spawnCounter = par1NBTTagCompound.getShort("spawnCounter");
        this.spawnSums = par1NBTTagCompound.getByte("spawnSums");
        this.danger_level = par1NBTTagCompound.getByte("danger_level");
        this.modifiedAttribute = par1NBTTagCompound.getBoolean("modifiedAttribute");
    }
    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if(!this.modifiedAttribute){
                this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + danger_level * 4);
                this.heal(danger_level * 4);
                this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(this.getEntityAttributeValue(GenericAttributes.attackDamage) + danger_level);
                this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(this.getEntityAttributeValue(GenericAttributes.movementSpeed) + danger_level * 0.01D);
                this.modifiedAttribute = true;
            }
            EntityLiving target = this.getAttackTarget();
            if(target instanceof EntityPlayer) {
                if (spawnSums < 10) {
                    if (this.spawnCounter < 20) {
                        ++this.spawnCounter;
                    } else {
                        EntityZombie zombie = new EntityZombie(this.worldObj);
                        if (zombie.entityId == 207) {
                            return;
                        }
                        zombie.setPosition(this.posX, this.posY, this.posZ);
                        zombie.refreshDespawnCounter(-9600);
                        this.worldObj.spawnEntityInWorld(zombie);
                        zombie.onSpawnWithEgg(null);
                        zombie.addRandomWeapon();
                        zombie.setAttackTarget(this.getTarget());
                        zombie.entityFX(EnumEntityFX.summoned);
                        this.spawnCounter = 0;
                        spawnSums += 2;
                    }
                }
                if(Configs.wenscConfig.isSpawnDragger.ConfigValue) {
                    if(!this.haveTryToSpawnExchanger) {
                        if( rand.nextInt(10) == 0) {
                            EntityDragger entityExchanger = new EntityDragger(this.worldObj);
                            entityExchanger.setPosition(this.posX, this.posY, this.posZ);
                            entityExchanger.refreshDespawnCounter(-9600);
                            this.worldObj.spawnEntityInWorld(entityExchanger);
                            entityExchanger.onSpawnWithEgg(null);
                            entityExchanger.setAttackTarget(this.getTarget());
                            entityExchanger.entityFX(EnumEntityFX.summoned);
                        }
                        this.haveTryToSpawnExchanger = true;
                    }
                }
            }
        }
    }
}
