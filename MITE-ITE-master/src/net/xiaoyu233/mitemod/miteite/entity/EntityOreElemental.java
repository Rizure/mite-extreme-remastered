package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class EntityOreElemental extends EntityEarthElemental {
    public EntityOreElemental(World world) {
        super(world);
        this.danger_level = this.rand.nextInt((available_ore.length - 4)) + (world.isUnderworld() ? 4 : 0);
    }
    private int fx_counter;
    private int spawnCounter;
    private int spawnSums;
    private boolean haveTryToSpawnExchanger = false;
    private boolean modifiedAttribute = false;
    private int danger_level;
    private final Block[] available_ore = {Block.oreCoal,Block.oreCopper,Block.oreSilver,Block.oreGold,Block.oreLapis,Block.oreIron,Block.oreRedstone,Block.oreEmerald,Block.oreMithril,Block.oreDiamond, Blocks.fancyRed,Block.oreAdamantium};
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
        this.modifiedAttribute = par1NBTTagCompound.getBoolean(" modifiedAttribute");
    }
    @Override
    public void setTypeForBlock(Block block, boolean heated) {
        this.setType(block == Block.whiteStone ? END_STONE_NORMAL : (block == Block.netherrack ? NETHERRACK_NORMAL : (block == Block.obsidian ? OBSIDIAN_NORMAL : STONE_NORMAL)));
        if (heated) {
            this.convertToMagma();
        }
    }
    private void initRandomOreHead(){
        this.setCurrentItemOrArmor(4, (new ItemStack(available_ore[this.danger_level], 1)));
    }
    @Override
    public GroupDataEntity onSpawnWithEgg(GroupDataEntity data) {
        this.setCanPickUpLoot(true);
        this.addRandomEquipment();
        return super.onSpawnWithEgg(data);
    }
    @Override
    public float getNaturalDefense() {
        return 5.0F;
    }
    protected void addRandomEquipment() {
        int day = this.getWorld().getDayOfOverworld();
        this.setHeldItemStack((new ItemStack(Items.VIBRANIUM_PICKAXE, 1)).randomizeForMob(this, day > 64));
        this.initRandomOreHead();
        this.setLeggings((new ItemStack(Items.VIBRANIUM_LEGGINGS, 1)).randomizeForMob(this, day > 64));
        this.setBoots((new ItemStack(Items.VIBRANIUM_BOOTS, 1)).randomizeForMob(this, day > 64));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
        this.setEntityAttribute(GenericAttributes.followRange, 60.0);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.25 * (double)Constant.getEliteMobModifier("Speed", day));
        if (!this.getWorld().isOverworld()) {
            this.setEntityAttribute(GenericAttributes.maxHealth, 30.0 * (double)Constant.getEliteMobModifier("Health", day));
            this.setEntityAttribute(GenericAttributes.attackDamage, 12.0 * (double)Constant.getEliteMobModifier("Damage", day));
        } else {
            this.setEntityAttribute(GenericAttributes.maxHealth, 15.0 * (double)Constant.getEliteMobModifier("Health", day));
            this.setEntityAttribute(GenericAttributes.attackDamage, 9.0 * (double)Constant.getEliteMobModifier("Damage", day));
        }

    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.voucherStrike);
            int day = this.getWorld().getDayOfOverworld();
            int ore_count = 2 + Math.min((day + 16) / 32, 3);
            for (int i1 = 0; i1 < ore_count; i1++) {
                this.dropItem(Item.getItem(available_ore[this.danger_level]));
            }
            int diamond_count = Math.min((day + 16) / 32, 3);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.diamond);
            }
        }
    }
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.rand.nextInt(400) == 0){
            this.setSurroundingsAsTarget();
        }
    }
    public boolean setSurroundingsAsTarget() {
        List<Entity> targets  = this.getNearbyEntities(8.0F,4.0F);
        if(!targets.isEmpty()){
            int i = this.rand.nextInt(targets.size());
            if(targets.get(i) instanceof EntityZombie){
                EntityZombie hostile = (EntityZombie) targets.get(i);
                if(hostile.getHeldItemStack() != null && hostile.getHeldItemStack().getItem() instanceof ItemPickaxe){
                    this.setRevengeTarget(hostile);
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if(!this.modifiedAttribute){
                this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + danger_level * 2);
                this.heal(danger_level * 2);
                this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(this.getEntityAttributeValue(GenericAttributes.attackDamage) + danger_level * 0.5);
                this.modifiedAttribute = true;
            }
            if (fx_counter > 0){
                fx_counter--;
            }else {
                this.fx_counter = 60;
                this.entityFX(EnumEntityFX.summoned);
            }
            if(Configs.wenscConfig.isSpawnDragger.ConfigValue) {
                EntityLiving target = this.getAttackTarget();
                if(target instanceof EntityPlayer) {
                    if (spawnSums < this.danger_level) {
                        if (this.spawnCounter < 20) {
                            ++this.spawnCounter;
                        } else {
                            EntityZombie zombie;
                            if(!this.getWorld().isOverworld() && this.rand.nextInt(4) == 0){
                                zombie = new EntityRevenant(this.worldObj);
                            } else {
                                zombie = new EntityZombie(this.worldObj);
                            }
                            if (zombie.entityId == 207) {
                                return;
                            }
                            zombie.setPosition(this.posX, this.posY, this.posZ);
                            zombie.refreshDespawnCounter(-9600);
                            this.worldObj.spawnEntityInWorld(zombie);
                            zombie.onSpawnWithEgg(null);
                            zombie.setHeldItemStack(Items.pickaxeRustedIron.getItemStackForStatsIcon());
                            zombie.setAttackTarget(this.getTarget());
                            zombie.entityFX(EnumEntityFX.summoned);
                            this.spawnCounter = 0;
                            ++spawnSums;
                        }
                    }
                    if(!haveTryToSpawnExchanger) {
                        if(rand.nextInt(20) == 0) {
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
    public boolean isImmuneTo(DamageSource damage_source) {
        if (this.isNormalClay()) {
            return super.isImmuneTo(damage_source);
        } else if (damage_source == DamageSource.fall) {
            return false;
        } else if (damage_source.isMelee() && damage_source.getResponsibleEntity() instanceof EntityIronGolem) {
            return false;
        } else {
            ItemStack item_stack = damage_source.getItemAttackedWith();
            if (item_stack != null && item_stack.getItem() instanceof ItemTool && item_stack.getItemAsTool().isEffectiveAgainstBlock(this.available_ore[this.danger_level], 0)) {
                return false;
            }else {
                return !damage_source.isLavaDamage() && !damage_source.isFireDamage();
            }
        }
    }
}
