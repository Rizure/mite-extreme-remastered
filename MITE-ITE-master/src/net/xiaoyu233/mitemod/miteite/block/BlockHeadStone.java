package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityHeadStone;

import java.util.List;

public class BlockHeadStone extends Block implements IContainer {
    protected BlockHeadStone(int par1, Material par2Material) {
        super(par1, par2Material, new BlockConstants());
        this.setHardness(BlockHardness.workbench);
        this.setStepSound(soundStoneFootstep);
        this.setCreativeTab(CreativeModeTab.tabDecorations);
        this.setMaxStackSize(1);
        this.setLightOpacity(0);
        this.setLightValue(1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityHeadStone();
    }

    @Override
    public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
        return true;
    }

    public float getCraftingDifficultyAsComponent(int metadata)
    {
        return -1.0F;
    }
    private void dropItems(World world,int x, int y, int z){
        TileEntityHeadStone headStone = (TileEntityHeadStone) world.getBlockTileEntity(x,y,z);
        for(int i = 0; i < headStone.getSizeInventory(); i++){
            EntityItem drops;
            if(headStone.getInventory()[i] != null){
                for(int j = 0; j < headStone.getInventory()[i].stackSize; j++){
                    drops = new EntityItem(world, (float)x + 0.5F, (float)y, (float)z + 0.5F,headStone.getInventory()[i].copy().setStackSize(1));
                    world.spawnEntityInWorld(drops);
                    float var15 = 0.1F;
                    drops.motionX = (float)world.rand.nextGaussian() * var15;
                    drops.motionY = (float)world.rand.nextGaussian() * var15 + 1F;
                    drops.motionZ = (float)world.rand.nextGaussian() * var15;
                }
            }
        }
        if(headStone.getContainingExp() > 0){
            world.spawnEntityInWorld(new EntityExperienceOrb(world, x,y,z, headStone.getContainingExp()));
        }
    }
    @Override
    public void breakBlock(World world, int x, int y, int z, int block_id, int metadata) {
        super.breakBlock(world, x, y, z, block_id, metadata);
        this.dropItems(world,x,y,z);
        world.removeBlockTileEntity(x, y, z);
    }

    @Override
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return -1;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z)
    {
        return false;
    }
}
