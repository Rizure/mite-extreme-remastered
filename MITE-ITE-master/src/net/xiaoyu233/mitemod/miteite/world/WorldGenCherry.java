package net.xiaoyu233.mitemod.miteite.world;

import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldGenerator;
import net.xiaoyu233.mitemod.miteite.block.Blocks;

import java.util.Random;

public class WorldGenCherry extends WorldGenerator {
   private final int branchCount;      // 分支数量（1-3）
   private final int branchLength;      // 分支水平延伸长度
   private final Block wood;
   private final int metaWood;          // 垂直方向原木 metadata（低2位为种类，高2位=00）
   private final Block leaves;
   private final int metaLeaves;        // 树叶 metadata（低2位为种类）
   private final int treeHeight;

   // 水平方向枚举，用于表示分支延伸方向
   private static final int[][] DIRECTIONS = {
           {0, -1}, // 北
           {1, 0},  // 东
           {0, 1},  // 南
           {-1, 0}  // 西
   };

   public WorldGenCherry(boolean par1, int branchCount, int branchLength, int treeHeight) {
      super(par1);
      this.branchCount = branchCount;
      this.branchLength = branchLength;
      this.wood = Blocks.wood1;
      this.metaWood = 1;               // 种类1，垂直方向（高2位00）
      this.leaves = Blocks.leaves1;
      this.metaLeaves = 1;              // 种类1
      this.treeHeight = treeHeight;
   }

   @Override
   public boolean generate(World world, Random random, int x, int y, int z) {
      // 基本高度检查
      if (y < 1 || y + treeHeight + 1 > 256) return false;

      // 检查树干底部是否可替换（可选，但为了鲁棒性，我们检查所有要放置的位置）
      if (!isSpaceAvailable(world, x, y, z, random)) return false;

      // 放置树干（垂直原木）
      for (int dy = 0; dy < treeHeight - 1; dy++) {
         setWood(world, x, y + dy, z, true); // true表示垂直方向
      }

      // 决定分支方向
      int dirIndex = random.nextInt(4); // 随机选择一个水平方向作为第一分支的方向
      int[] dir1 = DIRECTIONS[dirIndex];
      int[] dir2 = DIRECTIONS[(dirIndex + 2) % 4]; // 相反方向

      // 计算分支起始高度（离树干顶部一定距离）
      int firstBranchOffset = treeHeight - 2;          // 第一分支起始高度偏移（从树干底部起算）
      int secondBranchOffset = treeHeight - 3;         // 第二分支起始高度偏移
      if (secondBranchOffset >= firstBranchOffset) secondBranchOffset--; // 确保不同

      // 根据 branchCount 决定分支结构
      boolean hasMiddle = branchCount == 3;
      boolean hasBothSides = branchCount >= 2;

      // 树干实际高度（如果无顶枝，树干可能略矮，但这里保持完整树干）
      int trunkHeight = treeHeight; // 简化：始终放置完整树干

      // 收集树叶附着点
      java.util.ArrayList<int[]> leafAttachments = new java.util.ArrayList<>();

      // 中间顶枝（树干顶部）
      if (hasMiddle) {
         leafAttachments.add(new int[]{x, y + trunkHeight - 1, z});
      }

      // 生成第一分支
      int[] attach1 = generateBranch(world, random, x, y - 1, z, dir1, firstBranchOffset, trunkHeight);
      if (attach1 != null) leafAttachments.add(attach1);

      // 生成第二分支（如果需要）
      if (hasBothSides) {
         int[] attach2 = generateBranch(world, random, x, y - 1, z, dir2, secondBranchOffset, trunkHeight);
         if (attach2 != null) leafAttachments.add(attach2);
      }

      // 在每个树叶附着点生成椭球树叶
      for (int[] pos : leafAttachments) {
         generateLeafEllipsoid(world, random, pos[0], pos[1], pos[2]);
      }

      return true;
   }

   /**
    * 检查所有将要放置方块的位置是否可替换（简单实现，实际应遍历所有位置）
    * 这里为了简洁，只检查树干底部和顶部，实际生成中可扩展。
    */
   private boolean isSpaceAvailable(World world, int x, int y, int z, Random random) {
      // 简单检查树干底部和顶部
      if (!isReplaceableBottom(world, x, y - 1, z)) return false;
      if (!isReplaceableTop(world, x, y + treeHeight - 1, z)) return false;
      // 可进一步检查分支路径和树叶区域，但省略以保持简洁
      return true;
   }

   private boolean isReplaceableBottom(World world, int x, int y, int z) {
      int id = world.getBlockId(x, y, z);
      return id == Block.dirt.blockID || id == Block.grass.blockID;
   }

   private boolean isReplaceableTop(World world, int x, int y, int z) {
      int id = world.getBlockId(x, y, z);
      return id == 0 || id == leaves.blockID || id == wood.blockID;
   }
   /**
    * 放置原木，根据 isVertical 决定轴向
    */
   private void setWood(World world, int x, int y, int z, boolean isVertical) {
      int meta;
      if (isVertical) {
         meta = metaWood; // 垂直方向，高2位为00
      } else {
         // 水平方向，需要根据实际方向设置，但这里调用者需确保方向正确
         // 此方法仅用于垂直，水平方向由 generateBranch 直接计算 meta
         meta = metaWood; // 不应调用
      }
      setBlockAndMetadata(world, x, y, z, wood.blockID, meta);
   }

   /**
    * 根据水平方向获取原木 metadata
    * @param dx 水平X偏移（1或-1表示东西方向，0表示南北）
    * @param dz 水平Z偏移（1或-1表示南北方向，0表示东西）
    */
   private int getWoodMetaForDirection(int dx, int dz) {
      int base = metaWood & 0x3; // 低2位种类
      if (dx != 0) { // 东西方向
         return base | 0x4;      // 设置 b2 = 1 (东西)
      } else { // 南北方向
         return base | 0x8;      // 设置 b3 = 1 (南北)
      }
   }

   private void setLeaf(World world, int x, int y, int z) {
      if(world.getBlockId(x,y,z) == 0 || world.getBlockId(x,y,z) == Blocks.leaves1.blockID){
         setBlockAndMetadata(world, x, y, z, leaves.blockID, metaLeaves);
      }
   }

   /**
    * 生成一个分支，从树干分叉点开始，先水平延伸 branchLength 格，再垂直向上至树顶高度
    * 返回分支终点上方一格的位置（用于放置树叶椭球）
    */
   private int[] generateBranch(World world, Random random, int ox, int oy, int oz,
                                int[] dir, int offsetFromOrigin, int trunkHeight) {
      int startY = oy + offsetFromOrigin;
      int currentX = ox, currentY = startY, currentZ = oz;

      // 水平延伸 branchLength 格（放置水平原木）
      for (int i = 0; i < branchLength; i++) {
         currentX += dir[0];
         currentZ += dir[1];
         int meta = getWoodMetaForDirection(dir[0], dir[1]);
         setBlockAndMetadata(world, currentX, currentY, currentZ, wood.blockID, meta);
      }

      // 垂直向上延伸至树顶高度（放置垂直原木）
      int targetY = oy + trunkHeight - 1; // 树顶高度（树干最高一格）
      if (targetY > currentY) {
         while (currentY < targetY) {
            currentY++;
            setBlockAndMetadata(world, currentX, currentY, currentZ, wood.blockID, metaWood);
         }
      } else if (targetY < currentY) {
         // 理论上不会向下，但为了完整
         while (currentY > targetY) {
            currentY--;
            setBlockAndMetadata(world, currentX, currentY, currentZ, wood.blockID, metaWood);
         }
      }

      // 分支终点上方一格作为树叶附着点
      return new int[]{currentX, currentY + 1, currentZ};
   }

   /**
    * 生成5*3*5的椭球树叶（中心在 (cx, cy, cz)）
    * 水平半径2，垂直半径1，形状为扁椭球
    */
   private void generateLeafEllipsoid(World world, Random random, int cx, int cy, int cz) {
      for (int dx = -2; dx <= 2; dx++) {
         for (int dz = -2; dz <= 2; dz++) {
            for (int dy = -1; dy <= 2; dy++) {
               if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) <= 4) {
                  if((dy == -1 || dy == 2) && Math.abs(dx) == 2) continue;
                  if((dy == -1 || dy == 2) && Math.abs(dz) == 2) continue;
                  int x = cx + dx;
                  int y = cy + dy;
                  int z = cz + dz;
                  // 避免覆盖树干（树干位置已经放置原木，但树叶可以替换？这里简单放置，可能覆盖树干，但树干通常不会被树叶覆盖，因为树干在内部）
                  // 但为了自然，我们允许树叶覆盖空气或树叶，但不覆盖原木？但原木被树叶覆盖也合理，不过通常树干处不生成树叶。
                  // 这里简单放置，若位置已有原木，则跳过。
                  int id = world.getBlockId(x, y, z);
                  if (id == 0 || id == Blocks.leaves1.blockID) {
                     setLeaf(world, x, y, z);
                     if (random.nextInt(4) == 0) {
                        setLeaf(world, x, y - 1, z);
                     }
                  }
               }
            }
         }
      }
   }
}