package net.xiaoyu233.mitemod.miteite.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockManmadeIce;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.EntityBlizzard;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieExploder;

import java.util.Random;

public class WorldGenBlizzardCave extends WorldGenerator {

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        // === 1. 椭球尺寸定义（随机但限制范围） ===
        // X轴半径：9~11
        int radiusX = 5 + par2Random.nextInt(3);
        // Y轴半径：5~7
        int radiusY = 3 + par2Random.nextInt(3);
        // Z轴半径：9~11
        int radiusZ = 5 + par2Random.nextInt(3);

        // 用于统计出口数量
        int exitCount = 0;

        boolean towardsActiveX = false;
        boolean towardsNegativeX = false;
        boolean towardsActiveZ = false;
        boolean towardsNegativeZ = false;

        int exits = 0;
        // === 2. 预检测：检查椭球体边界内的所有方块 ===
        // 先检测整个椭球体范围（扩大一圈用于边界检查）
        for (int x = par3 - radiusX - 1; x <= par3 + radiusX + 1; x++) {
            for (int y = par4 - radiusY - 1; y <= par4 + radiusY + 1; y++) {
                for (int z = par5 - radiusZ - 1; z <= par5 + radiusZ + 1; z++) {
                    // 计算归一化距离（椭球体方程）
                    double dx = (double)(x - par3) / radiusX;
                    double dy = (double)(y - par4) / radiusY;
                    double dz = (double)(z - par5) / radiusZ;
                    double distSq = dx*dx + dy*dy + dz*dz;

                    // 如果当前方块在椭球内部或边界上
                    if (distSq <= 1.0) {
                        // 检查底部支撑（椭球最下层）
                        if (y == par4 - radiusY) {
                            if (!par1World.getBlockMaterial(x, y - 1, z).isSolid()) {
                                return false; // 底部无支撑
                            }
                        }

                        // 检查顶部（椭球最上层）不能有沙子/沙砾
                        if (y == par4 + radiusY) {
                            Block block = par1World.getBlock(x, y, z);
                            if (block instanceof BlockFalling) {
                                return false;
                            }
                        }

                        if(x == par3 + radiusX){
                            Block block = par1World.getBlock(x + 1, y, z);
                            if (block == null) {
                                if(!towardsActiveX){
                                    exits++;
                                }
                                towardsActiveX = true;
                            }
                        }

                        if(x == par3 - radiusX){
                            Block block = par1World.getBlock(x - 1, y, z);
                            if (block == null) {
                                if(!towardsNegativeX){
                                    exits++;
                                }
                                towardsNegativeX = true;
                            }
                        }

                        if(z == par5 + radiusZ){
                            Block block = par1World.getBlock(x, y, z + 1);
                            if (block == null) {
                                if(!towardsActiveZ){
                                    exits++;
                                }
                                towardsActiveZ = true;
                            }
                        }

                        if(z == par5 - radiusZ){
                            Block block = par1World.getBlock(x, y, z - 1);
                            if (block == null) {
                                if(!towardsNegativeZ){
                                    exits++;
                                }
                                towardsNegativeZ = true;
                            }
                        }

                        if(exits > 2){
                            return false;
                        }
                    }
                }
            }
        }

        // === 3. 构建椭球地牢（双层墙壁 + 内部空洞） ===
        for (int x = par3 - radiusX - 1; x <= par3 + radiusX + 1; x++) {
            for (int y = par4 - radiusY - 1; y <= par4 + radiusY + 1; y++) {
                for (int z = par5 - radiusZ - 1; z <= par5 + radiusZ + 1; z++) {
                    double dx = (double)(x - par3) / radiusX;
                    double dy = (double)(y - par4) / radiusY;
                    double dz = (double)(z - par5) / radiusZ;
                    double distSq = dx*dx + dy*dy + dz*dz;

                    // 只在椭球内部及边界操作
                    if (distSq <= 1.0) {
                        // 计算到椭球表面的"层数"
                        double surfaceDist = 1.0 - distSq;

                        // 判断是否为墙壁区域（表面附近）
                        boolean isOuterWall = (surfaceDist < 0.3 && surfaceDist > 0); // 外层
                        boolean isInnerWall = (surfaceDist < 0.6 && surfaceDist >= 0.3); // 内层
                        boolean isInterior = (surfaceDist >= 0.6); // 内部空洞

                        // 内层墙壁：雪块（blockSnow）
                        if (isInnerWall) {
                            par1World.setBlockToAir(x, y, z);
                            // 检查上方是否为外层墙壁（冰砖）
                            double dxUp = (double)(x - par3) / radiusX;
                            double dyUp = (double)(y + 1 - par4) / radiusY;
                            double dzUp = (double)(z - par5) / radiusZ;
                            double distUpSq = dxUp*dxUp + dyUp*dyUp + dzUp*dzUp;
                            boolean hasOuterAbove = (distUpSq <= 1.0 && (1.0 - distUpSq) < 0.15);

                            if (hasOuterAbove) {
                                // 1/8 概率不生成雪块（留空）
                                if (par2Random.nextInt(8) != 0) {
                                    if(par2Random.nextInt(3) == 0){
                                        par1World.setBlock(x, y, z, Block.ice.blockID, 0, 2);
                                    }else {
                                        par1World.setBlock(x, y, z, Block.blockSnow.blockID, 0, 2);
                                    }
                                }
                            }
                        }
                        // 外层墙壁：冰砖（packedIce）
                        else if (isOuterWall) {
                            par1World.setBlock(x, y, z, Blocks.packedIce.blockID, 0, 2);
                        }
                        // 内部空洞：清除所有方块
                        else if (isInterior) {
                            // 保留底部和顶部边缘的支撑结构（防止塌陷）
                            par1World.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }

        for(int kx = -radiusX; kx <= radiusX; kx++){
            for(int ky = -radiusY; ky <= radiusY; ky++){
                for(int kz = -radiusZ; kz <= radiusZ; kz++){
                    if(par1World.getBlock(par3 + kx, par4 + ky, par5 + kz) instanceof BlockManmadeIce){
                        if(kx > 0){
                            if(par1World.isAirBlock(par3 + kx + 1, par4 + ky, par5 + kz)){
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz);
                                par1World.setBlockToAir(par3 + kx - 1, par4 + ky, par5 + kz);
                            }
                        }else {
                            if(par1World.isAirBlock(par3 + kx - 1, par4 + ky, par5 + kz)){
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz);
                                par1World.setBlockToAir(par3 + kx + 1, par4 + ky, par5 + kz);
                            }
                        }
                        if(kz > 0){
                            if(par1World.isAirBlock(par3 + kx, par4 + ky, par5 + kz + 1)){
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz);
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz - 1);
                            }
                        }else {
                            if(par1World.isAirBlock(par3 + kx, par4 + ky, par5 + kz - 1)){
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz);
                                par1World.setBlockToAir(par3 + kx, par4 + ky, par5 + kz + 1);
                            }
                        }
                    }
                }
            }
        }


        // === 4. 放置蓝冰（Blue Ice） ===
        boolean placedBlueIce = false;
        for(int kx = -radiusX; kx <= radiusX && !placedBlueIce; kx++){
            for(int ky = -radiusY; ky <= 0 && !placedBlueIce; ky++){
                for(int kz = -radiusZ; kz <= radiusZ && !placedBlueIce; kz++){
                    if(par1World.isAirBlock(par3 + kx, par4 + ky, par5 + kz) &&
                            par1World.isAirBlock(par3 + kx, par4 + ky + 1, par5 + kz) &&
                            par1World.isAirBlock(par3 + kx, par4 + ky + 2, par5 + kz) &&
                            par1World.isAirBlock(par3 + kx - 1, par4 + ky + 1, par5 + kz) &&
                            par1World.isAirBlock(par3 + kx + 1, par4 + ky + 1, par5 + kz) &&
                            par1World.isAirBlock(par3 + kx, par4 + ky + 1, par5 + kz + 1) &&
                            par1World.isAirBlock(par3 + kx, par4 + ky + 1, par5 + kz - 1) &&
                            par1World.getBlock(par3 + kx, par4 + ky - 1, par5 + kz) instanceof BlockManmadeIce){
                        par1World.setBlock(par3 + kx, par4 + ky, par5 + kz, Blocks.blueIce.blockID);
                        placedBlueIce = true;
                        EntityBlizzard blaze = new EntityBlizzard(par1World);
                        blaze.setPosition(par3 + kx, par4 + ky, par5 + kz);
                        blaze.refreshDespawnCounter(-9600);
                        blaze.onSpawnWithEgg(null);
                        par1World.spawnEntityInWorld(blaze);
                    }
                }
            }
        }

        return true;
    }
}
