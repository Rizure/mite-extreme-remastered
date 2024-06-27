package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.Block.leaves;
import static net.xiaoyu233.mitemod.miteite.block.Blocks.leaves1;

@Mixin(BlockGrass.class)
public class BlockGrassTrans extends Block{
    protected BlockGrassTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Overwrite
    public boolean isLegalAt(World world, int x, int y, int z, int metadata) {
        Block block_above = world.getBlock(x, y + 1, z);
        if (block_above != null && block_above != leaves && block_above != leaves1 && block_above.blockMaterial != Material.snow && block_above.blockMaterial != Material.craftedSnow) {
            if (block_above instanceof BlockPiston) {
                return false;
            } else if (!block_above.hidesAdjacentSide(world, x, y + 1, z, ReflectHelper.dyCast(BlockGrass.class,this), 1)) {
                return true;
            } else {
                return !block_above.isFaceFlatAndSolid(world.getBlockMetadata(x, y + 1, z), EnumFace.BOTTOM);
            }
        } else {
            return true;
        }
    }
    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        if (info.wasHarvestedByPlayer() && !info.world.isFreezing(info.x, info.z)) {
            int fortune = info.getHarvesterFortune();
            if (fortune > 3) {
                fortune = 3;
            }

            if (info.world.isInRain(info.x, info.y + 1, info.z)) {
                fortune += 12;
            }

            if (fortune > 14) {
                fortune = 14;
            }

            if (info.world.rand.nextInt(16 - fortune) == 0) {
                this.dropBlockAsEntityItem(info, Item.wormRaw);
            }
            if (info.world.rand.nextInt(16 - fortune) == 1 && info.world.isInRain(info.x, info.y + 1, info.z)) {
                this.dropBlockAsEntityItem(info, Items.powder_earth);
            }
        }

        return this.dropBlockAsEntityItem(info, Block.dirt.blockID);
    }
}
