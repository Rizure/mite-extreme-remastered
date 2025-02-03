package net.xiaoyu233.mitemod.miteite.entity;

import javafx.beans.binding.MapExpression;
import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ItemRegenerationCore;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.util.*;

public class EntityZombieBoss extends EntityZombie implements IBossbarEntity{
//    private Enchantment [] enhanceSpecialBookList = new Enchantment[] {Enchantment.protection, Enchantment.sharpness,  Enchantment.fortune, Enchantment.harvesting, Enchantments.EXTEND, Enchantment.efficiency, Enchantment.vampiric, Enchantment.butchering, Enchantment.featherFalling};
//    private Enchantment [] nonLevelsBookList = new Enchantment[] {Enchantments.enchantmentFixed, Enchantments.enchantmentChain, Enchantments.EMERGENCY};
    private Enchantment [] enhanceSpecialBookList = new Enchantment[] {
            Enchantment.protection,
            Enchantment.fireProtection,
            Enchantment.projectileProtection,
            Enchantment.blastProtection,
            Enchantment.sharpness,
            Enchantment.smite,
            Enchantment.baneOfArthropods,
            Enchantment.fireAspect,
            Enchantment.looting,
            Enchantment.knockback,
            Enchantment.fortune,
            Enchantment.harvesting,
            Enchantment.efficiency,
            Enchantment.unbreaking,
            Enchantment.power,
            Enchantment.punch,
            Enchantment.vampiric,
            Enchantment.butchering,
            Enchantment.featherFalling,
            Enchantment.arrow_recovery,
            Enchantment.fishing_fortune,
            Enchantment.stun,
            Enchantment.fertility,
            Enchantment.tree_felling,
            Enchantment.speed,
            Enchantment.regeneration,
            Enchantment.free_action,
            Enchantment.true_flight,
            Enchantment.quickness,
            Enchantment.aquaAffinity,
            Enchantment.poison,
            Enchantment.disarming,
            Enchantment.butchering,
            Enchantment.piercing,
            Enchantment.endurance,
            Enchantments.enchantmentLuckOfTheSea,
            Enchantments.CRIT,
            Enchantments.EXTEND,
            Enchantments.CONQUEROR,
            Enchantments.enchantmentChain,

    };
    private Enchantment [] nonLevelsBookList = new Enchantment[] {
            Enchantments.enchantmentPhaseDefend,
            Enchantments.EMERGENCY,
            Enchantment.flame,
            Enchantment.respiration,

    };
    private int thunderTick = 0;
    private int attackedCounter = 200;
    public Map<String, Float> attackDamageMap = new HashMap<>();

    public EntityZombieBoss(World par1World) {
        super(par1World);
    }

    protected void addRandomEquipment() {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.VIBRANIUM_WAR_HAMMER, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(1, new ItemStack(Items.VIBRANIUM_HELMET, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(2, new ItemStack(Items.VIBRANIUM_CHESTPLATE, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(3, new ItemStack(Items.VIBRANIUM_LEGGINGS, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(4, new ItemStack(Items.VIBRANIUM_BOOTS, 1).randomizeForMob(this, true));
    }

    public void addPotionEffect(MobEffect par1PotionEffect) {
        if(par1PotionEffect.getPotionID() == 1 || par1PotionEffect.getPotionID() == 5) {
            super.addPotionEffect(par1PotionEffect);
        }
    }

//    public void getEnchantBookDependsOnDamageRate(EntityPlayer entityPlayer, int rate) {
//        ItemStack var11 = null;
//        Enchantment dropEnchantment = Enchantment.enchantmentsList[rand.nextInt(Enchantment.enchantmentsList.length)];
//        if(dropEnchantment != null) {
//            if(dropEnchantment.getNumLevelsForVibranium() > 1) {
//                var11 = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, Math.min(rate, dropEnchantment.getNumLevelsForVibranium())));
//            } else {
//                if(rate >= 7) {
//                    var11 = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, 1));
//                } else {
//                    getEnchantBookDependsOnDamageRate(entityPlayer, rate);
//                }
//            }
//        } else {
//            getEnchantBookDependsOnDamageRate(entityPlayer, rate);
//        }
//        entityPlayer.inventory.addItemStackToInventoryOrDropIt(var11);
//    }

    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.gemBlue);
            this.broadcastDamage("僵尸BOSS挑战成功");
            MinecraftServer server = MinecraftServer.F();
            Iterator var4 = server.getConfigurationManager().playerEntityList.iterator();
            Item[] drops = {Item.netherQuartz,Item.netherQuartz,Item.emerald,Item.emerald,Item.diamond,Item.blazePowder,Item.ghastTear};
            Item[] final_drops = {Item.diamond,Item.diamond,Item.diamond,Item.diamond,Item.diamond,Items.fancyRed,Items.fancyRed};
            while (var4.hasNext()) {
                Object o = var4.next();
                EntityPlayer player = (EntityPlayer)o;
                if(attackDamageMap.containsKey(player.getEntityName())) {
                    float damage = attackDamageMap.get(player.getEntityName());
                    int nums = Math.round(damage) / 25;
                    while (nums-- > 0) {
                        player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(drops[this.rand.nextInt(drops.length)],1));
                        if(this.isFinal()){
                            player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(final_drops[this.rand.nextInt(final_drops.length)],1));
                            player.triggerAchievement(Achievements.killZombieBoss);
                        }
                    }
                }
            }
            ItemStack stack;
            Enchantment dropEnchantment;
            dropEnchantment = this.nonLevelsBookList[this.rand.nextInt(this.nonLevelsBookList.length)];
            stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
            if(this.rand.nextInt(4) == 0){
                this.dropItemStack(stack);
                if(!this.isFinal()){
                    return;
                }
            }
            dropEnchantment = this.enhanceSpecialBookList[this.rand.nextInt(this.enhanceSpecialBookList.length)];
            stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
            this.dropItemStack(stack);
            if(!this.isFinal()){
                this.dropItem(Items.cracked_key);
                this.dropItemStack(new ItemStack(Items.itemEnhanceGem3,1,this.rand.nextInt(GemModifierTypes.values().length)));
            }else {
                for(int i = 0; i < 3; i++){
                    this.dropItem(Items.voucherCore);
                }
                this.dropItemStack(new ItemStack(Items.itemEnhanceGem5,1,this.rand.nextInt(GemModifierTypes.values().length)));
            }
        }
    }
    protected boolean isFinal(){
        return false;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.worldObj.getDayOfOverworld();
        this.setEntityAttribute(GenericAttributes.attackDamage, 15 * Constant.getBossMobModifier("Damage",day));
        this.setEntityAttribute(GenericAttributes.maxHealth, 400 * Constant.getBossMobModifier("Health",day));
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3D);
    }

    @Override
    protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
    }

    public boolean isComfortableInLava()
    {
        return true;
    }

    public boolean getCanSpawnHere(boolean perform_light_check) {
        return true;
    }

    @Override
    public boolean handleLavaMovement() {
        return false;
    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    public boolean canBeDisarmed() {
        return false;
    }

    public boolean canCatchFire() {
        return false;
    }

    public boolean isPushedByWater() {
        return false;
    }


    @Override
    public boolean canNeverPickUpItem(Item item) {
        return true;
    }

    public boolean canBeKnockedBack() {
        return false;
    }

    @Override
    public EntityDamageResult attackEntityFrom(Damage damage) {
        if(damage.getSource().damageType.equals("player") || damage.getSource().damageType.equals("mob")) {
            if(damage.getSource().getResponsibleEntity() instanceof EntityPlayer) {
                EntityPlayer player = ((EntityPlayer) damage.getSource().getResponsibleEntity());
                player.removePotionEffect(MobEffectList.damageBoost.id);
                player.bossResetDamageBoostCounter = 200;
                this.attackedCounter = 200;
                damage.setAmount(damage.getAmount() * (this.isFinal() ? 0.3F : 0.75F));
                EntityDamageResult originDamage = super.attackEntityFrom(damage);
                try {
                    if(attackDamageMap.containsKey(player.getEntityName())) {
                        attackDamageMap.put(player.getEntityName(), attackDamageMap.get(player.getEntityName()) + originDamage.getAmountOfHealthLost());
                    } else {
                        attackDamageMap.put(player.getEntityName(), originDamage.getAmountOfHealthLost());
                    }
                } catch (Exception e) {
                    Minecraft.setErrorMessage("BOSS伤害计算错误分析");
                    Minecraft.setErrorMessage(e.getMessage());
                }
                return originDamage;
            }
            return null;
        } else {
            return null;
        }
    }


    public void broadcastDamage(String stateMessage) {
        MinecraftServer server = MinecraftServer.F();
        Iterator var4 = server.getConfigurationManager().playerEntityList.iterator();

        List<Map.Entry<String,Float>> list = new ArrayList<>(attackDamageMap.entrySet());
        Collections.sort(list, (e1, e2) -> (int) Math.floor(e2.getValue() - e1.getValue()));
        while(var4.hasNext()) {
            Object o = var4.next();
            EntityPlayer player = (EntityPlayer)o;
            for(int i = 0; i < Math.min(list.size(), 5); i++) {
                player.sendChatToPlayer(ChatMessage.createFromText("--" + stateMessage + "-伤害排名--"));
                player.sendChatToPlayer(ChatMessage.createFromText("第")
                        .appendComponent(ChatMessage.createFromText("§6" + (i + 1)))
                        .appendComponent(ChatMessage.createFromText("§r名: "))
                        .appendComponent(ChatMessage.createFromText("§n" + list.get(i).getKey()))
                        .appendComponent(ChatMessage.createFromText("§r - "))
                        .appendComponent(ChatMessage.createFromText("§b" + String.format("%.2f", list.get(i).getValue())))
                        .appendComponent(ChatMessage.createFromText("§r点伤害")));
            }
        }
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        if(target instanceof EntityPlayer) {
            ((EntityPlayer) target).isAttackByBossCounter = 30;
        }
        return super.attackEntityAsMob(target);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("attackedCounter", (short)this.attackedCounter);
        par1NBTTagCompound.setShort("thunderTick", (short)this.thunderTick);

        NBTTagList nbtTagList = new NBTTagList();
        for (Map.Entry<String, Float> integerEntry : this.attackDamageMap.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Attacker", (integerEntry).getKey());
            compound.setFloat("Damage", (integerEntry).getValue());
            nbtTagList.appendTag(compound);
        }
        par1NBTTagCompound.setTag("AttackDamageMap", nbtTagList);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.attackedCounter = par1NBTTagCompound.getShort("attackedCounter");
        this.thunderTick = par1NBTTagCompound.getShort("thunderTick");
        if (par1NBTTagCompound.hasKey("AttackDamageMap")) {
            NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackDamageMap");
            int count = attackCountMap.tagCount();

            for(int i = 0; i < count; ++i) {
                NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
                this.attackDamageMap.put(a.getString("Attacker"), a.getFloat("Damage"));
            }
        }
    }
    @Override
    public boolean isImmuneTo(DamageSource damage_source) {
        return super.isImmuneTo(damage_source) && damage_source.isExplosion();
    }

    public void addThunderAttack(EntityPlayer player, float damage) {
        if(player != null) {
            WorldServer var20 = (WorldServer)this.worldObj;
            var20.addWeatherEffect(new EntityLightning(var20, player.posX, player.posY, player.posZ));
            player.attackEntityFrom(new Damage(DamageSource.divine_lightning, damage));
        }
    }

    public boolean setSurroundingPlayersAsTarget() {
        this.setTarget(null);
        List <Entity>targets  = this.getNearbyEntities(16.0F,8.0F);
        if(!targets.isEmpty()){
            int i = this.rand.nextInt(targets.size());
            if(targets.get(i) instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) targets.get(i);
                this.setRevengeTarget(player);
                return true;
            }
        }
        return false;
    }
    public boolean shockSurroundingPlayers() {
        boolean succeed = false;
        List <Entity>targets  = this.getNearbyEntities(16.0F,8.0F);
        for(int i = 0; i < targets.size(); i++){
            if(targets.get(i) instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) targets.get(i);
                this.addThunderAttack(player,4.0F);
                succeed = true;
            }
        }
        return succeed;
    }

    public void healAndBroadcast() {
        if(this.getHealth() < this.getMaxHealth()) {
            this.heal(this.getMaxHealth());
            this.broadcastDamage("僵尸BOSS挑战失败");
            this.attackDamageMap.clear();
        }
        if(this.isFinal()){
            this.setDead();
        }
    }
    public void enhance(){
        EntityZombieBoss entityZombieBoss = new EntityFinalZombieBoss(this.worldObj);
        entityZombieBoss.setPosition(this.posX, this.posY, this.posZ);
        entityZombieBoss.refreshDespawnCounter(-9600);
        entityZombieBoss.setAttackTarget(this.getTarget());
        entityZombieBoss.entityFX(EnumEntityFX.summoned);
        entityZombieBoss.onSpawnWithEgg(null);
        this.worldObj.spawnEntityInWorld(entityZombieBoss);
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            thunderTick ++;
            if(attackedCounter <= 0) {
                this.healAndBroadcast();
            } else {
                attackedCounter --;
            }
            EntityLiving target = this.getTarget();
            if(!(target instanceof EntityPlayer)) {
                this.healAndBroadcast();
            }
            if(thunderTick % 40 == 0) {
                if(target != null && target instanceof EntityPlayer) {
                    if(((EntityPlayer) target).isAttackByBossCounter <= 0) {
                        if(this.isFinal()){
                            double currentX = this.posX;
                            double currentY = this.posY;
                            double currentZ = this.posZ;
                            target.setPositionAndUpdate(currentX, currentY, currentZ);
                            target.worldObj.createExplosion(this,target.posX,target.posY + (double)(target.height / 4.0F),target.posZ,0.0F,2.75F,true);
                        }else {
                            addThunderAttack((EntityPlayer)target, 6f);
                        }
                    }
                }
                if(thunderTick == 60) {
                    if(this.isFinal()){
                        this.shockSurroundingPlayers();
                    }
                    this.setSurroundingPlayersAsTarget();
                    thunderTick = 0;
                }
            }
        }
    }
}
