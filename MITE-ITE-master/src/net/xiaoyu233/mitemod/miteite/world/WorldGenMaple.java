//package net.xiaoyu233.mitemod.miteite.world;
//
//import net.minecraft.Block;
//import net.minecraft.World;
//import net.minecraft.WorldGenerator;
//
//import java.util.Random;
//
//like s**t
//public class WorldGenMaple extends WorldGenerator {
//   private final int baseLogHeight;
//   private final Block wood;
//   private final int metaWood;
//   private final Block leaves;
//   private final int metaLeaves;
//   private final int baseLogRadius;
//   private final int growType;
//
//   public WorldGenMaple(boolean par1, int treeScale, Block wood, int metaWood, Block leaves, int metaLeaves, int growType) {
//      super(par1);
//      this.baseLogHeight = treeScale * 2 + 1;
//      this.baseLogRadius = Math.max(1, treeScale / 3);
//      this.wood = wood;
//      this.metaWood = metaWood;
//      this.leaves = leaves;
//      this.metaLeaves = metaLeaves;
//      this.growType = growType;
//   }
//
//   @Override
//   public boolean generate(World world, Random random, int baseX, int baseY, int baseZ) {
//      int rootHeight = baseLogHeight + random.nextInt(baseLogHeight);
//      int finalHeight = rootHeight + Math.max(3, random.nextInt(rootHeight / 2));
//      if (baseY < 1 || baseY + finalHeight + 1 > 256) return false;
//
//      // 检查空间是否可用
//      for (int y = baseY; y <= baseY + finalHeight; y++) {
//         for (int x = baseX - baseLogRadius; x <= baseX + baseLogRadius; x++) {
//            for (int z = baseZ - baseLogRadius; z <= baseZ + baseLogRadius; z++) {
//               int id = world.getBlockId(x, y, z);
//               if (id != 0 && id != leaves.blockID && id != Block.grass.blockID && id != Block.dirt.blockID && id != wood.blockID) {
//                  return false;
//               }
//            }
//         }
//      }
//
//      // 生成主树干，记录顶部半径和高度
//      int trunkDelta = baseLogRadius - 1;
//      int lastLayerY = 0;
//      int lastRadius = 0;
//      for (int deltaY = 0; deltaY <= rootHeight; deltaY++) {
//         int currentRadius = trunkDelta - (deltaY / 5);
//         if (currentRadius < 1) break;
//         for (int dx = -trunkDelta; dx <= trunkDelta; dx++) {
//            for (int dz = -trunkDelta; dz <= trunkDelta; dz++) {
//               double distance = Math.sqrt(dx * dx + dz * dz);
//               if (distance <= currentRadius) {
//                  this.setBlockAndMetadata(world, baseX + dx, baseY + deltaY, baseZ + dz, this.wood.blockID, this.metaWood);
//                  if(dx == currentRadius && random.nextInt(3) == 0 && trunkDelta != currentRadius){
//                     this.growTrunk(world, baseX + dx, baseY + deltaY, baseZ + dz, (byte) 1, (byte) 0, currentRadius * 3 + random.nextInt(4), currentRadius / 2, random);
//                  }
//                  if(dx == -currentRadius && random.nextInt(3) == 0 && trunkDelta != currentRadius){
//                     this.growTrunk(world, baseX + dx, baseY + deltaY, baseZ + dz, (byte) -1, (byte) 0, currentRadius * 3 + random.nextInt(4), currentRadius / 2, random);
//                  }
//                  if(dz == currentRadius && random.nextInt(3) == 0 && trunkDelta != currentRadius){
//                     this.growTrunk(world, baseX + dx, baseY + deltaY, baseZ + dz, (byte) 0, (byte) 1, currentRadius * 3 + random.nextInt(4), currentRadius / 2, random);
//                  }
//                  if(dz == -currentRadius && random.nextInt(3) == 0 && trunkDelta != currentRadius){
//                     this.growTrunk(world, baseX + dx, baseY + deltaY, baseZ + dz, (byte) 0, (byte) -1, currentRadius * 3 + random.nextInt(4), currentRadius / 2, random);
//                  }
//               }
//            }
//         }
//         lastLayerY = deltaY;
//         lastRadius = currentRadius;
//      }
//
//      // 分支参数：半径取主干顶部半径的2/3，长度与半径相关
//      int branchStartRadius = Math.max(1, lastRadius * 2 / 3);
//      int branchLength = branchStartRadius * 3 + random.nextInt(4);
//      int branchStartY = baseY + lastLayerY + 1;
//
//      // 随机选择分支方向
//      int mainBranchCount = 1 + random.nextInt(4);
//      boolean[] growDirection = new boolean[4];
//      while (mainBranchCount-- > 0) {
//         growDirection[random.nextInt(4)] = true;
//      }
//
//      // 生成各个方向的分支
//      if (growDirection[0]) { // 北
//         this.growTrunk(world, baseX, branchStartY, baseZ, (byte) -1, (byte) 0, branchLength, branchStartRadius, random);
//      }
//      if (growDirection[1]) { // 西
//         this.growTrunk(world, baseX, branchStartY, baseZ, (byte) 0, (byte) -1, branchLength, branchStartRadius, random);
//      }
//      if (growDirection[2]) { // 南
//         this.growTrunk(world, baseX, branchStartY, baseZ, (byte) 1, (byte) 0, branchLength, branchStartRadius, random);
//      }
//      if (growDirection[3]) { // 东
//         this.growTrunk(world, baseX, branchStartY, baseZ, (byte) 0, (byte) 1, branchLength, branchStartRadius, random);
//      }
//
//      return true;
//   }
//
//   private void growTrunk(World world, int startX, int startY, int startZ, byte faceX, byte faceZ, int maxLength, int startRadius, Random random) {
//      int curX = startX;
//      int curY = startY;
//      int curZ = startZ;
//      int curRadius = startRadius;
//      int length = 0;
//
//      while (length < maxLength && curRadius >= 1) {
//         // 放置当前层的木头（圆柱形）
//         for (int dx = -curRadius; dx <= curRadius; dx++) {
//            for (int dz = -curRadius; dz <= curRadius; dz++) {
//               double dist = Math.sqrt(dx * dx + dz * dz);
//               if (dist <= curRadius) {
//                  this.setBlockAndMetadata(world, curX + dx, curY, curZ + dz, this.wood.blockID, this.metaWood);
//               }
//            }
//         }
//
//         // 随机生成子分支（概率随生长降低）
//         if (random.nextInt(5) == 0 && curRadius > 1) {
//            // 子分支方向垂直于当前方向
//            byte childFaceX = (byte) (random.nextBoolean() ? faceZ : -faceZ);
//            byte childFaceZ = (byte) (random.nextBoolean() ? faceX : -faceX);
//            int childRadius = Math.max(1, curRadius / 2);
//            int childLength = childRadius * 2 + random.nextInt(3);
//            growTrunk(world, curX, curY + 1, curZ, childFaceX, childFaceZ, childLength, childRadius, random);
//         }
//
//         // 沿主要方向移动，允许随机偏移和轻微向上倾斜
//         int moveX = faceX;
//         int moveZ = faceZ;
//         if (random.nextInt(3) == 0) {
//            moveX += random.nextInt(3) - 1; // -1, 0, 1
//            moveZ += random.nextInt(3) - 1;
//         }
//         curX += moveX;
//         curZ += moveZ;
//         if (random.nextInt(2) == 0) {
//            curY++;
//         }
//
//         // 半径逐渐减小
//         curRadius--;
//         length++;
//      }
//
//      // 在分支末端生成树叶
//      generateLeaves(world, curX, curY, curZ, random);
//   }
//
//   private void generateLeaves(World world, int x, int y, int z, Random random) {
//      // 沿用原代码的树叶生成范围（5x4x5椭球）
//      for (int fx = -2; fx <= 2; fx++) {
//         for (int fy = -1; fy <= 2; fy++) {
//            for (int fz = -2; fz <= 2; fz++) {
//               if (Math.abs(fx) + Math.abs(fy) + Math.abs(fz) < 6) {
//                  int bx = x + fx;
//                  int by = y + fy;
//                  int bz = z + fz;
//                  if (world.getBlockId(bx, by, bz) == 0) {
//                     this.setBlockAndMetadata(world, bx, by, bz, this.leaves.blockID, this.metaLeaves);
//                  }
//               }
//            }
//         }
//      }
//   }
//}