package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityArachnid.class)
public class EntityArachnidTrans extends EntityMonster {
    public EntityArachnidTrans(World par1World) {
        super(par1World);
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.maxHealth, 10.0D);
        this.setEntityAttribute(GenericAttributes.followRange, 28.0D);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 1.0D);
        this.setEntityAttribute(GenericAttributes.attackDamage, 6.0D);
    }
    @Override
    public void onDeath(DamageSource damageSource){
        super.onDeath(damageSource);
        Entity player = damageSource.getResponsibleEntity();
        if(player instanceof EntityPlayer){
            if(this.isPotionActive(MobEffectList.moveSpeed)){
                this.dropItem(Items.powder_wind);
            }
        }
    }
    @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "onUpdate",at = @At(value = "INVOKE",shift = At.Shift.BEFORE,target = "getEntityHit"),cancellable = true)
    public void InjectUpdate(CallbackInfo callbackInfo, Entity target, double distance, EntityLiving elb_target, Raycast raycast, RaycastCollision rc){
        if(rc == null){
            callbackInfo.cancel();
        }
    }
//    @Inject(method = "onUpdate",at = @At(value = "INVOKE",shift = At.Shift.AFTER,target = "getNearestEntityCollision"))
//    public void InjectUpdate_test(CallbackInfo callbackInfo){
//        System.out.println("omg");
//    }
//    @Overwrite
//    public void onUpdate() {
//        super.onUpdate();
//        if (!this.worldObj.isRemote && this.canClimbWalls()) {
//            this.setBesideClimbableBlock(this.isCollidedHorizontally);
//        }
//
//        if (!this.worldObj.isRemote) {
//            this.checkSwitchingToPeaceful();
//            if (this.num_webs > 0 && this.getTicksExistedWithOffset() % this.getTicksBetweenWebThrows() == 0) {
//                Entity target = this.getEntityToAttack();
//                if (target instanceof EntityLiving) {
//                    double distance = (double)this.getDistanceToEntity(target);
//                    if (distance <= (double)8.0F) {
//                        EntityLiving elb_target = (EntityLiving)target;
//                        Raycast raycast = (new Raycast(this.worldObj, this.getEyePos(), elb_target.getEyePos())).setForThrownWeb((Entity)null).performVsBlocks();
//                        if (raycast.hasBlockCollision()) {
//                            raycast.setLimit(elb_target.getFootPosPlusFractionOfHeight(0.25F));
//                            raycast.performVsBlocks();
//                        }
//
//                        RaycastCollision rc = raycast.getBlockCollision();
//                        if (rc == null) {
//                            raycast.setOriginator(this).performVsEntities();
//                            rc = raycast.getNearestEntityCollision();
//                            if (rc.getEntityHit() == target) {
//                                this.attackEntityWithRangedAttack((EntityLiving)target, 1.0F);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
