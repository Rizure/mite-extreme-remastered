package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.List;

public class EntitySpiderQueen extends EntityArachnid {
    private final int num_webs;
    public EntitySpiderQueen(World par1World) {
        super(par1World, 1.45F);
        this.num_webs = 0;
    }

    protected String getLivingSound() {
        return "imported.mob.demonspider.say";
    }

    protected String getHurtSound() {
        return "imported.mob.demonspider.hurt";
    }

    protected String getDeathSound() {
        return "imported.mob.demonspider.death";
    }

    protected float getSoundVolume(String sound) {
        return super.getSoundVolume(sound) * 1.3F;
    }

    protected float getSoundPitch(String sound) {
        return super.getSoundPitch(sound) * 0.6F;
    }
    public boolean peacefulDuringDay() {
        return false;
    }
    protected void applyEntityAttributes() {
        int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.followRange, 96.0);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.92);
        this.setEntityAttribute(GenericAttributes.maxHealth, 48 * Constant.getEliteMobModifier("Health",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.attackDamage, 8 * Constant.getEliteMobModifier("Damage",day,this.worldObj.isOverworld()));
    }
    public EntityDamageResult attackEntityAsMob(Entity target) {
        EntityDamageResult result = super.attackEntityAsMob(target);
        if (result != null && !result.entityWasDestroyed()) {
            if (result.entityLostHealth() && target instanceof EntityLiving) {
                target.getAsEntityLivingBase().addPotionEffect(new MobEffect(MobEffectList.poison.id, 1020, 0));
            }

            return result;
        } else {
            return result;
        }
    }
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.spawnCounter = par1NBTTagCompound.getShort("spawnCounter");
        this.spawnSums = par1NBTTagCompound.getByte("spawnSums");
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("spawnCounter", (short) this.spawnCounter);
        par1NBTTagCompound.setByte("spawnSums", (byte) this.spawnSums);
    }
    private int spawnCounter;
    private int spawnSums;
    private boolean gathering_troops = false;
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!getWorld().isRemote)
        {
            List nearby_spiders = this.worldObj.getEntitiesWithinAABB(EntityArachnid.class, this.boundingBox.expand(16.0D, 8.0D, 16.0D));
            Iterator i = nearby_spiders.iterator();
            if(this.getTicksExistedWithOffset() % 100 == 0){
                while (i.hasNext()){
                    EntityArachnid spiders = (EntityArachnid) i.next();
                    if(spiders != this){
                        if(spiders.getHealthFraction() < 1.0F){
                            spiders.healByPercentage(0.2F);
                            spiders.entityFX(EnumEntityFX.repair);
                        }
                    }
                }
            }

            if(this.getTarget()!=null){
                if(!this.isNoDespawnRequired() && this.getTarget() != null){
                    this.gathering_troops = true;
                    this.func_110163_bv();
                }
            }
            if (spawnSums < 8 && gathering_troops) {
                if (spawnCounter < 20) {
                    spawnCounter++;
                } else {
                    EntityBlackWidowSpider Spider = new EntityBlackWidowSpider(worldObj);
                    Spider.setPosition(posX + this.rand.nextInt(4) - this.rand.nextInt(4),posY, posZ - this.rand.nextInt(4) + this.rand.nextInt(4));
                    Spider.refreshDespawnCounter(-9600);
                    worldObj.spawnEntityInWorld(Spider);
                    Spider.onSpawnWithEgg(null);
                    Spider.entityFX(EnumEntityFX.summoned);
                    spawnCounter = 0;
                    spawnSums++;
                }
            }
        }
    }
    @Override
    public void onDeathUpdate(){
        super.onDeathUpdate();
        if (this.deathTime == 20) {
            EntityPotion potion = new EntityPotion(this.worldObj,this,16388);
            potion.setPosition(this.posX,this.posY - 1,this.posZ);
            this.worldObj.spawnEntityInWorld(potion);
        }
    }
    @Override
    public void onDeath(DamageSource damageSource){
        super.onDeath(damageSource);
        int day = this.worldObj.getDayOfOverworld();
        Entity player = damageSource.getResponsibleEntity();
        if(player instanceof EntityPlayer){
            int count = Math.min(3,day/ 32);
            for(int i = 0;i< count;i++){
                this.dropItem(Item.emerald);
            }
            this.dropItem(Items.voucherSpider);
            for(int i = 0;i< 8;i++){
                this.dropItem(Items.hugeSpiderLeg);
                if(this.rand.nextInt(4) == 0){
                    i++;
                }
            }
        }
    }
    public int getExperienceValue() {
        return super.getExperienceValue() * 5;
    }
}
