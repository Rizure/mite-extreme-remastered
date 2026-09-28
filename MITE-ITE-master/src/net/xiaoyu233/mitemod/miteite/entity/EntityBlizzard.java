package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.util.List;

public class EntityBlizzard extends EntityMonster {
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private boolean isHarmless = false;
    public int freezeTime;
    private int attackRound = 0;
    private boolean shouldSpawnParticle = false;

    public EntityBlizzard(World par1World, boolean isHarmless) {
        super(par1World);
        this.freezeTime = 600;
        this.isHarmless = isHarmless;
    }

    public EntityBlizzard(World par1World) {
        this(par1World, false);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.worldObj.getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage, (double)(4.0F * Constant.getNormalMobModifier("Damage", day)));
        this.setEntityAttribute(GenericAttributes.maxHealth, (double)(this.isHarmless ? 20.0F : 45.0F * Constant.getNormalMobModifier("Health", day)));
        this.setEntityAttribute(GenericAttributes.followRange, (double)24.0F);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.12 * (double)Constant.getNormalMobModifier("Speed", day));
    }

    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }

    protected String getHurtSound() {
        return "mob.blaze.hit";
    }

    protected String getDeathSound() {
        return "mob.blaze.death";
    }

    public float getBrightness(float par1) {
        return 1.0F;
    }

   public int c(float par1) {
      return 15728880;
   }

    protected float getSoundPitch(String sound) {
        return super.getSoundPitch(sound) * 0.5F;
    }

    protected float getSoundVolume(String sound) {
        return super.getSoundVolume(sound) * 4.0F;
    }

    public void onLivingUpdate() {
        if (this.onServer()) {
            if(this.freezeTime > 0){
                --this.freezeTime;
            }

            if (this.freezeTime < 200 && this.getTicksExistedWithOffset() % 60 == 0) {
                int max_candidates = 8;
                int[] candidate_x = new int[max_candidates];
                int[] candidate_y = new int[max_candidates];
                int[] candidate_z = new int[max_candidates];
                double[] candidate_distance_sq = new double[max_candidates];
                int max_distance = 16;
                int[] block_ids = new int[]{Blocks.blueIce.blockID};
                int candidates = this.worldObj.getNearestBlockCandidates(this.posX, this.posY + (double)(this.height * 0.75F), this.posZ, max_distance, max_distance / 4, max_candidates, block_ids, candidate_x, candidate_y, candidate_z, candidate_distance_sq);
                if (candidates != 0) {
                    this.setPositionAndUpdate(candidate_x[0], candidate_y[0] + 1, candidate_z[0]);
                }
            }

            if (this.freezeTime < 300 && this.getTicksExistedWithOffset() % 100 == 0){
                this.tryRecoverFromIceNearby();
            }

            if (this.isWet() && this.getTicksExistedWithOffset() % 100 == 0) {
                this.heal(1.0F);
            }

            if (this.getTicksExistedWithOffset() % 10 == 0 && this.freezeTime == 0){
                this.attackEntityFrom(new Damage(DamageSource.drown, this.getMaxHealth() / 10.0F));
            }

            if (this.getFireTick() > 0){
                this.attackEntityFrom(new Damage(DamageSource.inFire, this.getMaxHealth()));
            }

            if (this.getTicksExistedWithOffset() % 200 == 0){
                List<Entity> targets = this.getNearbyEntities(16, 6);
                for (int i = 0; i < targets.size(); i++) {
                    EntityLiving entities = targets.get(i) instanceof EntityMonster || (targets.get(i) instanceof EntityPlayer && !this.isHarmless) ? (EntityLiving) targets.get(i) : null;
                    if (entities != null && (!(entities instanceof EntityZombieBoss))) {
                        entities.addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, this.isHarmless ? 12 * 20 : 6 * 20, this.isHarmless ? 2 : 1));
                    }
                }
            }

            if (this.getTicksExistedWithOffset() % 200 == 0 && this.attackRound == 0){
                this.attackRound = 3;
            }

            if (this.getTicksExistedWithOffset() % 20 == 0){
                List<Entity> targets = this.getNearbyEntities(6, 3);
                if (this.getClosestVulnerablePlayer(6.0F) != null || (this.isHarmless && !targets.isEmpty()) && this.attackRound-- > 0){
                    for (int i = 0; i < targets.size(); i++) {
                        EntityLiving entities = targets.get(i) instanceof EntityMonster || (targets.get(i) instanceof EntityPlayer && !this.isHarmless) ? (EntityLiving) targets.get(i) : null;
                        if (entities != null && (!EntityZombieBoss.class.isInstance(entities))) {
                            entities.attackEntityFrom(new Damage(DamageSource.causeIndirectMagicDamage(entities, this), this.isHarmless ? 6.0F : 3.0F));
                            entities.addVelocity(MathHelper.sin(entities.rotationYaw * 3.1415927F / 180.0F) * 0.75F * 1.0F, 0.1D, -MathHelper.cos(entities.rotationYaw * 3.1415927F / 180.0F) * 0.75F * 1.0F);
                        }
                    }
                    this.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
                    this.shouldSpawnParticle = true;
                }
            }

            --this.heightOffsetUpdateTime;
            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
            }

            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset) {
                this.motionY += ((double)0.3F - this.motionY) * (double)0.3F;
            }
        }

        if (!this.onGround && this.motionY < (double)0.0F) {
            this.motionY *= 0.6;
        }

        for(int var1 = 0; var1 < 2; ++var1) {
            this.worldObj.spawnParticle(EnumParticle.fireworkSpark, this.posX + (this.rand.nextDouble() - (double)0.5F) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - (double)0.5F) * (double)this.width, (double)0.0F, (double)0.0F, (double)0.0F);
        }

        if(this.shouldSpawnParticle){
            for (int var3 = 0; var3 < 24; ++var3) {
                this.worldObj.spawnParticle(EnumParticle.fireworkSpark, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D));
            }
            for (int var3 = 0; var3 < 24; ++var3) {
                this.worldObj.spawnParticle(EnumParticle.fireworkSpark, this.posX, this.posY, this.posZ, 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D));
            }
            this.shouldSpawnParticle = false;
        }

        super.onLivingUpdate();
    }

    protected void attackEntity(Entity par1Entity, float par2) {

    }

    public EntityDamageResult attackEntityFrom(Damage damage) {
        if (damage.getSource() == DamageSource.inWall) {
            damage.scaleAmount(this.getMaxHealth() / 6.0F);
        }

        return super.attackEntityFrom(damage);
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.isHarmless = par1NBTTagCompound.getBoolean("isHarmless");
        this.attackRound = par1NBTTagCompound.getInteger("attackRound");
        this.freezeTime = par1NBTTagCompound.getInteger("freezeTime");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("isHarmless", this.isHarmless);
        par1NBTTagCompound.setInteger("attackRound", this.attackRound);
        par1NBTTagCompound.setInteger("freezeTime", this.freezeTime);
    }

    public float getNaturalDefense(DamageSource damage_source) {
        return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : this.isHarmless ? 5.0F : 7.0F);
    }

    protected void fall(float par1) {
    }

    public int getExperienceValue() {
        return super.getExperienceValue() * 100 * (this.isHarmless ? 0 : 1);
    }

    protected boolean canDespawn() {
        return false;
    }

    private boolean tryRecoverFromIceNearby() {
        if (!this.worldObj.isRemote && this.recentlyHit == 0 && this.distanceToNearestPlayer() > (double)2.0F) {
            int x = MathHelper.floor_double(this.posX);
            int y = MathHelper.floor_double(this.posY);
            int z = MathHelper.floor_double(this.posZ);

            for(int dx = -4; dx <= 4; ++dx) {
                for(int dy = -4; dy <= 4 + (int)this.height; ++dy) {
                    for(int dz = -4; dz <= 4; ++dz) {
                        int block_id = this.worldObj.getBlockId(x + dx, y + dy, z + dz);
                        if(block_id == Blocks.blueIce.blockID){
                            this.freezeTime = 600;
                        }
                        if(block_id == Block.ice.blockID && this.freezeTime < 300){
                            this.freezeTime = 300;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player) {
            int looting = damage_source.getLootingModifier();
            int num_drops = this.rand.nextInt(2 + looting);
            for (int i = 0; i < num_drops; i++) {
                if(!this.isHarmless){
                    this.dropItem(Items.blizzardRod);
                }
            }
            for (int i = 0; i < 16; i++) {
                this.dropItem(Item.snowball);
                if (this.rand.nextInt(3) == 0){
                    this.dropItem(Item.copperNugget);
                }
            }
        }
    }
}
