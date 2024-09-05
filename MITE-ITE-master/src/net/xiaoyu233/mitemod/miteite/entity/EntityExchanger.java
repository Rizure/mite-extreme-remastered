package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;

public class EntityExchanger extends EntitySkeleton {
    private int teleportDelay;

    private int max_num_evasions;
    private int num_evasions;

    public EntityExchanger(World par1World) {
        super(par1World);
        if (par1World != null && this.onServer()) {
            this.max_num_evasions = this.num_evasions = 6;
        }
        this.tasks.addTask(3, new PathfinderGoalAvoidPlayer(this, EntityPlayer.class, 12.0F, 1.1, 1.4));
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
        this.setEntityAttribute(GenericAttributes.attackDamage, (12) * Constant.getEliteMobModifier("Damage",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.maxHealth, (30) * Constant.getEliteMobModifier("Health",day,this.worldObj.isOverworld()));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27D * Constant.getEliteMobModifier("Speed",day,this.worldObj.isOverworld()));
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
                this.dropItem(Item.ironNugget);
                if(this.rand.nextInt(2) == 0){
                    this.dropItem(Item.enderPearl);
                }
            }
        }
    }

    public void exchangeSkill() {
        EntityPlayer target = (EntityPlayer)this.getEntityToAttack();
        if(target != null) {
            double entiX = target.posX;
            double entiY = target.posY;
            double entiZ = target.posZ;
            double currentX = this.posX;
            double currentY = this.posY;
            double currentZ = this.posZ;
            this.teleportToTarget(entiX, entiY, entiZ);
            target.setPositionAndUpdate(currentX, currentY, currentZ);
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
                this.worldObj.spawnParticle(EnumParticle.portal_underworld, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }

            if(this.entityToAttack == null) {
                entityToAttack = this.getClosestVulnerablePlayer(32F);
                if (entityToAttack instanceof EntityPlayer) {
                    this.worldObj.playSoundAtEntity(this.entityToAttack, "mob.endermen.stare", 1.0F, 1.0F);
                    this.entityToAttack.entityFX(EnumEntityFX.curse_effect_learned);
                }
                this.teleportDelay = 0;
            } else {
                if(entityToAttack.isDead || getDistanceToEntity(entityToAttack) > 32F) {
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
                    if (this.teleportDelay % 10 == 0 && this.teleportDelay < 70){
                        this.entityToAttack.entityFX(EnumEntityFX.curse_effect_learned);
                    }
                    if (this.teleportDelay < 70 && ++this.teleportDelay > 60) {
                        exchangeSkill();
                        this.teleportDelay = 70;
                    }
                }
            }
        }
    }

    public void teleportToTarget(double par1, double par3, double par5) {
        double var7 = this.posX;
        double var9 = this.posY;
        double var11 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;

        this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);

        double distance = (double)World.getDistanceFromDeltas(this.posX - var7, this.posY - var9, this.posZ - var11);
        this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y, z, (new SignalData()).setByte(EnumParticle.portal_underworld.ordinal()).setShort((int)(8.0D * distance)).setApproxPosition((double)MathHelper.floor_double(var7), (double)MathHelper.floor_double(var9), (double)MathHelper.floor_double(var11)));
        this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y + 1, z, (new SignalData()).setByte(EnumParticle.portal_underworld.ordinal()).setShort((int)(8.0D * distance)).setApproxPosition((double)MathHelper.floor_double(var7), (double)MathHelper.floor_double(var9 + 1.0D), (double)MathHelper.floor_double(var11)));
        this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
    }
    public boolean tryTeleportTo(double pos_x, double pos_y, double pos_z) {
        if (!this.isDead && !(this.getHealth() <= 0.0F)) {
            int x = MathHelper.floor_double(pos_x);
            int y = MathHelper.floor_double(pos_y);
            int z = MathHelper.floor_double(pos_z);
            if (y >= 1 && this.worldObj.blockExists(x, y, z)) {
                while(true) {
                    --y;
                    if (this.worldObj.isBlockSolid(x, y, z)) {
                        ++y;
                        if (!this.worldObj.isBlockSolid(x, y, z) && !this.worldObj.isLiquidBlock(x, y, z)) {
                            double delta_pos_x = pos_x - this.posX;
                            double delta_pos_y = pos_y - this.posY;
                            double delta_pos_z = pos_z - this.posZ;
                            AxisAlignedBB bb = this.boundingBox.translateCopy(delta_pos_x, delta_pos_y, delta_pos_z);
                            if (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb)) {
                                World var10000 = this.worldObj;
                                double distance = (double)World.getDistanceFromDeltas(delta_pos_x, delta_pos_y, delta_pos_z);
                                this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y, z, (new SignalData()).setByte(EnumParticle.runegate.ordinal()).setShort((int)(16.0 * distance)).setApproxPosition((double)MathHelper.floor_double(this.posX), (double)MathHelper.floor_double(this.posY), (double)MathHelper.floor_double(this.posZ)));
                                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                                this.setPosition(pos_x, pos_y, pos_z);
                                this.send_position_update_immediately = true;
                                return true;
                            }

                            return false;
                        }

                        return false;
                    }

                    if (y < 1) {
                        return false;
                    }

                    --pos_y;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean tryTeleportAwayFrom(Entity entity, double min_distance) {
        if (!this.isDead && !(this.getHealth() <= 0.0F)) {
            double min_distance_sq = min_distance * min_distance;
            int x = this.getBlockPosX();
            int y = this.getFootBlockPosY();
            int z = this.getBlockPosZ();
            double threat_pos_x = entity == null ? this.posX : entity.posX;
            double threat_pos_z = entity == null ? this.posZ : entity.posZ;

            for(int attempts = 0; attempts < 64; ++attempts) {
                int dx = this.rand.nextInt(11) - 5;
                int dy = this.rand.nextInt(9) - 4;
                int dz = this.rand.nextInt(11) - 5;
                if (Math.abs(dx) >= 3 || Math.abs(dz) >= 3) {
                    int try_x = x + dx;
                    int try_y = y + dy;
                    int try_z = z + dz;
                    double try_pos_x = (double)try_x + 0.5;
                    double try_pos_z = (double)try_z + 0.5;
                    World var10000 = this.worldObj;
                    if (!(World.getDistanceSqFromDeltas(try_pos_x - threat_pos_x, try_pos_z - threat_pos_z) < min_distance_sq) && try_y >= 1 && this.worldObj.blockExists(try_x, try_y, try_z)) {
                        do {
                            --try_y;
                        } while(!this.worldObj.isBlockSolid(try_x, try_y, try_z) && try_y >= 1);

                        if (try_y >= 1) {
                            ++try_y;
                            if (!this.worldObj.isBlockSolid(try_x, try_y, try_z) && !this.worldObj.isLiquidBlock(try_x, try_y, try_z) && this.tryTeleportTo(try_pos_x, (double)try_y, try_pos_z)) {
                                EntityPlayer target = this.findPlayerToAttack(Math.min(this.getMaxTargettingRange(), 24.0F));
                                if (target != null && target != this.getTarget()) {
                                    this.setTarget(target);
                                }

                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public EntityDamageResult attackEntityFrom(Damage damage) {
        DamageSource damage_source = damage.getSource();
        boolean has_phasedefend = false;
        if(damage_source != null){
            ItemStack item_stack = damage_source.getItemAttackedWith();
            if (item_stack != null && item_stack.hasEnchantment(Enchantments.enchantmentPhaseDefend, true)) {
                has_phasedefend = true;
            }
        }
        boolean can_evade = !has_phasedefend && !damage.isFallDamage() && !damage.isFireDamage() && !damage.isPoison();
        if (can_evade && this.num_evasions > 0) {
            --this.num_evasions;
            if (this.tryTeleportAwayFrom(this.getTarget(), 6.0)) {
                return null;
            }
        }
        return super.attackEntityFrom(damage);
    }
}
