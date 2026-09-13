package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockEndAltar extends BlockEnderPortalFrame {
    private IIcon displayOnCreativeTab;
    private IIcon unlocalizedName;
    private static final AxisAlignedBB[] multiple_bounds_without_eye = new AxisAlignedBB[]{
            new AxisAlignedBB().setBounds((double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F, (double)0.8125F, (double)1.0F)
    };
    private static final AxisAlignedBB[] multiple_bounds_with_eye = new AxisAlignedBB[]{
            new AxisAlignedBB().setBounds((double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F, (double)0.8125F, (double)1.0F),
            new AxisAlignedBB().setBounds((double)0.25F, (double)0.8125F, (double)0.25F, (double)0.75F, (double)1.0F, (double)0.75F)};

    public BlockEndAltar(int par1) {
        super(par1);
        this.setBlockHardness(2.5F);
    }

    public String getMetadataNotes() {
        return "Bits 1 and 2 used for orientation, bit 4 set if ender eye inserted";
    }

    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 8;
    }

    public IIcon a(int par1, int par2) {
        return par1 == 1 ? this.displayOnCreativeTab : (par1 == 0 ? Block.obsidian.m(par1) : this.cW);
    }

    public void a(mt par1IconRegister) {
        this.cW = par1IconRegister.a(this.E() + "_side");
        this.displayOnCreativeTab = par1IconRegister.a(this.E() + "_top");
        this.unlocalizedName = par1IconRegister.a(this.E() + "_eye");
    }

    public IIcon q() {
        return this.unlocalizedName;
    }

    public int getRenderType() {
        return 26;
    }

    public void setBlockBoundsForItemRender(int item_damage) {
        this.setBlockBoundsForCurrentThread((double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F, (double)0.8125F, (double)1.0F);
    }

    public Object getCollisionBounds(World world, int x, int y, int z, Entity entity) {
        return isEnderEyeInserted(world.getBlockMetadata(x, y, z)) ? multiple_bounds_with_eye : multiple_bounds_without_eye;
    }

    public static boolean isEnderEyeInserted(int par0) {
        return true;
    }

    public boolean onBlockPlacedMITE(World world, int x, int y, int z, int metadata, Entity placer, boolean test_only) {
        if (!test_only && placer != null) {
            int placer_direction = ((MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + (double)0.5F) & 3) + 2) % 4;
            world.setBlockMetadata(x, y, z, placer_direction, 2);
        }

        return super.onBlockPlacedMITE(world, x, y, z, metadata, placer, test_only);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        return isEnderEyeInserted(var6) ? 15 : 0;
    }

    public boolean canBeCarried() {
        return false;
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return this.dropBlockAsItself(info);
    }

    public String getNameDisambiguationForReferenceFile(int metadata) {
        return "frame";
    }

    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }

    public boolean blocksPrecipitation(boolean[] blocks_precipitation, int metadata) {
        return true;
    }
}
