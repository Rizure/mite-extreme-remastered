package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PathfinderGoalArrowAttack.class)
public abstract class PathfinderGoalArrowAttackTrans extends PathfinderGoal {
   @Shadow
   @Final
   private EntityInsentient entityHost;
   @Shadow
   private EntityLiving attackTarget;
   @Shadow
   private double entityMoveSpeed;

   private boolean foundHoldPlace = false;

   public EntityInsentient getEntityHost() {
      return entityHost;
   }
   @Inject(method = "updateTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/Navigation;tryMoveToEntityLiving(Lnet/minecraft/Entity;D)Z", shift = At.Shift.AFTER))
   private void requestAvailableHoldingPlace(CallbackInfo callbackInfo){
      this.entityHost.getNavigator().clearPathEntity();
      double radius = 12.0;
      int maxAttempts = 4;
      Random rand = this.entityHost.getRNG(); // 使用实体自带的随机数生成器
      World world = this.entityHost.worldObj;
      Vec3D targetEyePos = this.attackTarget.getEyePos();

      for (int attempt = 0; attempt < maxAttempts && !foundHoldPlace; ++attempt) {
         // 随机生成偏移量（水平面圆形区域）
         double angle = rand.nextDouble() * 2 * Math.PI;
         double distance = rand.nextDouble() * radius;
         double dx = Math.cos(angle) * distance;
         double dz = Math.sin(angle) * distance;
         double candidateX = this.entityHost.posX + dx;
         double candidateZ = this.entityHost.posZ + dz;

         // 获取候选位置的地面高度（保证实体可以站立）
         int blockX = MathHelper.floor_double(candidateX);
         int blockZ = MathHelper.floor_double(candidateZ);
         int groundY = world.getTopSolidOrLiquidBlock(blockX, blockZ);
         double candidateY = groundY; // 站在方块顶部

         // 计算候选位置的眼睛高度
         Vec3D eyePos = Vec3D.createVectorHelper(candidateX, candidateY + this.entityHost.getEyeHeight(), candidateZ);

         // 检查从候选位置到目标是否有视线
         Raycast testRaycast = new Raycast(world, eyePos, targetEyePos)
                 .setForPiercingProjectile(null)
                 .performVsBlocks();
         if (!testRaycast.hasBlockCollision()) {
            // 找到合适位置，移动过去
            this.entityHost.getNavigator().tryMoveToXYZ(candidateX, candidateY, candidateZ, this.entityMoveSpeed * 1.2F);
            foundHoldPlace = true;
         }
      }

      if (!foundHoldPlace) {
         // 没找到任何可见位置，停止移动（清除当前路径）
         this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed * 0.4F);
      }
      this.entityHost.getLookHelper().setLookPosition(this.attackTarget.posX, this.attackTarget.posY + (double)this.attackTarget.getEyeHeight(), this.attackTarget.posZ, 10.0F, (float)this.entityHost.getVerticalFaceSpeed());
   }
}
