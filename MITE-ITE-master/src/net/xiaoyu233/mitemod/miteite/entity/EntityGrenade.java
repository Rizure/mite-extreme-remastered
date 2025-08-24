package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class EntityGrenade extends EntityProjectile {
    public EntityGrenade(World par1World) {
        super(par1World);
    }

    public EntityGrenade(World par1World, EntityLiving par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
        this.motionY += 0.5D;
    }
    protected float getGravityVelocity() {
        return 0.08F;
    }
    public EntityGrenade(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    public void onUpdate() {
        super.onUpdate();
        if(this.ticksExisted > this.rand.nextInt(10)){
            this.worldObj.spawnParticle(EnumParticle.largesmoke, this.posX, this.posY, this.posZ, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D);
        }
        if(this.ticksExisted > 60){
            this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ,4.0F,2.0F,false);
            for(int var3 = 0; var3 < 24; ++var3) {
                this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D));
            }
            for(int var3 = 0; var3 < 24; ++var3) {
                this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D));
            }
            this.setDead();
        }
    }
    @Override
    protected void onImpact(RaycastCollision rc) {
        if (!this.worldObj.isRemote) {
            this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ,4.0F,2.0F,false);
        }
        for(int var3 = 0; var3 < 24; ++var3) {
            this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D));
        }
        for(int var3 = 0; var3 < 24; ++var3) {
            this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D));
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
    public Item getModelItem()
    {
        return Items.grenade;
    }
}
