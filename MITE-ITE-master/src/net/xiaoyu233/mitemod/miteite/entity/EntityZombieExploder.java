package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityZombieExploder extends EntityZombie {
    protected final float explosionRadius = 1.75f;
    private int lockdown_counter;
    public EntityZombieExploder(World par1World) {
        super(par1World);
    }
    protected void addRandomEquipment() {
        super.addRandomEquipment();
        this.setCurrentItemOrArmor(0, (new ItemStack(Block.tnt, 1)).randomizeForMob(this, false));
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("lockdown_counter", this.lockdown_counter);
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.lockdown_counter = par1NBTTagCompound.getInteger("lockdown_counter");
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage,(12) * Constant.getEliteMobModifier("Damage",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.maxHealth, (40) * Constant.getEliteMobModifier("Health",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.movementSpeed, (0.25D) * Constant.getEliteMobModifier("Speed",day,this.worldObj.isOverworld()));
    }
    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            if(damage_source.getResponsibleEntity() instanceof EntityPlayer){
                if(this.rand.nextInt(20) == 0){
                    ((EntityPlayer)damage_source.getResponsibleEntity()).inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.ban,1));
                }
                ((EntityPlayer)damage_source.getResponsibleEntity()).inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.zombieBrain,1));
                ((EntityPlayer)damage_source.getResponsibleEntity()).inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.voucherDestruction,1));
                int day = this.getWorld().getDayOfOverworld();
                int diamond_count = Math.min((day + 16) / 32, 2) + 1;
                for (int i1 = 0; i1 < diamond_count; i1++) {
                    ((EntityPlayer)damage_source.getResponsibleEntity()).inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.emerald,1));
                }
                for (int i1 = 0; i1 < 3; i1++) {
                    ((EntityPlayer)damage_source.getResponsibleEntity()).inventory.addItemStackToInventoryOrDropIt(new ItemStack(Block.tnt,1));
                }
            }
        }
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.getWorld().isRemote){
            int var6;
            for (var6 = 0; var6 < 2; ++var6) {
                this.worldObj.spawnParticle(EnumParticle.sacred, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }

            if(this.entityToAttack == null && this.lockdown_counter >= 0) {
                this.entityToAttack = this.getClosestVulnerablePlayer(16F);
                if (entityToAttack instanceof EntityPlayer) {
                    this.entityToAttack.entityFX(EnumEntityFX.curse_effect_learned);
                    if (entityToAttack instanceof EntityPlayer) {
                        ((EntityPlayer) entityToAttack).sendChatToPlayer(ChatMessage.createFromTranslationKey("[僵尸粉碎者] ").setColor(EnumChatFormat.BLUE).appendComponent(ChatMessage.createFromTranslationKey("你已被锁定").setColor(EnumChatFormat.YELLOW)));
                    }
                }
                if(this.lockdown_counter == 0){
                    this.lockdown_counter = 400;
                }
            } else {
                if (entityToAttack != null && (entityToAttack.isDead || getDistanceToEntity(entityToAttack) > 16F)) {
                    if (entityToAttack instanceof EntityPlayer) {
                        ((EntityPlayer) entityToAttack).sendChatToPlayer(ChatMessage.createFromTranslationKey("[僵尸粉碎者] ").setColor(EnumChatFormat.BLUE).appendComponent(ChatMessage.createFromTranslationKey("已丢失目标").setColor(EnumChatFormat.GREEN)));
                    }
                    this.entityToAttack = null;
                    return;
                }
                if (entityToAttack instanceof EntityPlayer) {
                    if(this.lockdown_counter >= 0 && this.lockdown_counter <= 400){
                        this.lockdown_counter --;
                        double dx = (entityToAttack.posX - this.posX);
                        double dy = (entityToAttack.posY - this.posY);
                        double dz = (entityToAttack.posZ - this.posZ);
                        double r = getDistanceToEntity(entityToAttack);
                        if(r < 4.0F && dy < 1.0F){
                            return;
                        }
                        if(this.lockdown_counter % 20 == 0){
                            EntityLiving target = this.getTarget();
                            if(target != null) {
                                int range = (int) getDistanceToEntity(target);
                                double div = 1.0D / (double) range;
                                for (int delta = 0; delta < range; delta++) {
                                    EntityRideMarker rideMarker = new EntityRideMarker(this.worldObj);
                                    rideMarker.setPosition(this.posX + dx * div * delta, this.posY + this.getEyeHeight() + dy * div * delta, this.posZ + dz * div * delta);
                                    this.worldObj.spawnEntityInWorld(rideMarker);
                                    rideMarker.entityFX(EnumEntityFX.curse_effect_learned);
                                }
                            }
                        }
                        if(this.lockdown_counter == -1){
                            EntityLiving target = this.getTarget();
                            if(target != null) {
                                int range = (int) getDistanceToEntity(target);
                                double div = 1.0D / (double) range;
                                for (int delta = 0; delta < range; delta++) {
                                    this.worldObj.createExplosion(this, this.posX + dx * div * delta, this.posY + 0.25F + dy * div * delta, this.posZ + dz * div * delta, 0.0F, 0.75F, false);
                                }
                            }
                            this.lockdown_counter = 400;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void onDeathUpdate(){
        super.onDeathUpdate();
        if (this.deathTime == 20) {
            if (!this.worldObj.isRemote) {
                boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                float explosion_size_vs_blocks = this.explosionRadius * 0.715F;
                float explosion_size_vs_living_entities = this.explosionRadius * 1.1F;
                for (int i = 0; i < 4; i ++){
                    EntityCreeper creeper = new EntityCreeper(this.worldObj);
                    creeper.setPosition(this.posX - this.rand.nextFloat() * 0.1D + 0.05D, this.posY, this.posZ - this.rand.nextFloat() * 0.1D + 0.05D);
                    creeper.refreshDespawnCounter(-9600);
                    this.worldObj.spawnEntityInWorld(creeper);
                    creeper.onSpawnWithEgg(null);
                    creeper.setAttackTarget(this.getTarget());
                    creeper.entityFX(EnumEntityFX.summoned);
                    creeper.setHealth(10.0F);
                    creeper.addPotionEffect(new MobEffect(MobEffectList.invisibility.id,100,1,true));
                }
                this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 4.0F), this.posZ, explosion_size_vs_blocks, explosion_size_vs_living_entities, var2);

                EntityZombieDoor zombie = new EntityZombieDoor(this.worldObj,2);
                zombie.setPosition(this.posX, this.posY, this.posZ);
                zombie.refreshDespawnCounter(-9600);
                this.worldObj.spawnEntityInWorld(zombie);
                zombie.onSpawnWithEgg(null);
                zombie.addRandomWeapon();
                zombie.setAttackTarget(this.getTarget());
                zombie.entityFX(EnumEntityFX.summoned);
                for(int var3 = 0; var3 < 24; ++var3) {
                    this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D), 4.0D * (this.rand.nextDouble() - 0.5D));
                }
                for(int var3 = 0; var3 < 24; ++var3) {
                    this.worldObj.spawnParticle(EnumParticle.explode, this.posX, this.posY, this.posZ, 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D), 2.0D * (this.rand.nextDouble() - 0.5D));
                }
                this.entityFX(EnumEntityFX.frags);
            }
        }
    }
}
