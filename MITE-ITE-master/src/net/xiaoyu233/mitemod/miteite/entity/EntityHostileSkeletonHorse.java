package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityHostileSkeletonHorse extends EntityHorse implements IMonster{
    private final PathfinderGoalMeleeAttack aiAttackOnCollide = new PathfinderGoalMeleeAttack(this, (double)1.95F, true);
    private final PathfinderGoalLeapAtTarget aiLeapAtTarget = new PathfinderGoalLeapAtTarget(this, 0.4F);
    private final PathfinderGoalAvoidPlayer aiAvoidPlayer = new PathfinderGoalAvoidPlayer(this, EntityPlayer.class, 12.0F, 1.3, 1.6);
    public EntityHostileSkeletonHorse(World par1World) {
        super(par1World);
        this.setHorseType(4);
        this.tasks.clear();
        this.targetTasks.clear();
        this.tasks.addTask(1, new PathfinderGoalFloat(this));
        this.tasks.addTask(6, new PathfinderGoalRandomStroll(this, (double)1.0F));
        this.tasks.addTask(7, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new PathfinderGoalRandomLookaround(this));
        this.targetTasks.addTask(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetTasks.addTask(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.tasks.addTask(3, new EntityAIGetOutOfWater(this, 1.0F));
        this.tasks.addTask(4, new EntityAIGetOutOfLava(this, 1.0F));
        this.setCombatTask();
    }

    public void onUpdate() {
        super.onUpdate();
        if(this.getTicksExistedWithOffset() % 100 == 0){
            this.setCombatTask();
            if(this.riddenByEntity == null){
                if(this.deathTime <= 0){
                    this.attackEntityFrom(new Damage(DamageSource.absolute,1000F));
                }
            }else {
                if(this.riddenByEntity instanceof EntityMonster){
                    ((EntityMonster)this.riddenByEntity).addPotionEffect(new MobEffect(MobEffectList.damageBoost.id, 120, 0));
                }
            }
        }
    }
    public GroupDataEntity onSpawnWithEgg(GroupDataEntity par1EntityLivingData) {
        this.setHorseType(4);
        GroupDataEntity par1EntityLivingData1 = new GroupDataHorse(4, 0);
        return par1EntityLivingData1;
    }
    private void setCombatTask(){
        this.tasks.removeTask(this.aiAvoidPlayer);
        this.tasks.removeTask(this.aiLeapAtTarget);
        this.tasks.removeTask(this.aiAttackOnCollide);
        if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLongdead){
            if(((EntityLongdead)this.riddenByEntity).getSkeletonType() == 0){
                this.tasks.addTask(2, this.aiAvoidPlayer);
            }else {
                this.tasks.addTask(3, this.aiLeapAtTarget);
                this.tasks.addTask(4, this.aiAttackOnCollide);
            }
        }
    }
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
        this.setEntityAttribute(GenericAttributes.followRange, 40.0F);
        this.setEntityAttribute(GenericAttributes.maxHealth, (double)30.0F * Constant.getNormalMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.attackDamage, (double)9.0F * Constant.getNormalMobModifier("Health",day));
    }
    public boolean isEntityBiologicallyAlive() {
        return false;
    }

    public boolean isFoodItem(ItemStack item_stack) {
        return false;
    }

    protected boolean isValidLightLevel() {
        return EntityMonster.isValidLightLevel(this);
    }
    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        return false;
    }
    public boolean isTame() {
        return false;
    }
    public int getExperienceValue() {
        return super.getExperienceValue() * 3;
    }

    @Override
    public boolean canSpawnInShallowWater() {
        return false;
    }
}
