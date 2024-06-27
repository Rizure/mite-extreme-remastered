package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockSlabGroup4 extends BlockSlab {
    private static String[] types = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray"};

    public BlockSlabGroup4(int id, Material material) {
        super(id, material);
        this.setMaxStackSize(64);
        this.setCushioning(1000.0F);
        this.setLightValue(0.5F);
    }

    public int getGroup() {
        return 4;
    }

    public String[] getTypes() {
        return types;
    }

    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 16;
    }

    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 7;
    }
    public float getBlockHardness(int metadata)
    {
        return this.getModelBlock(metadata).getBlockHardness(metadata);
    }
    public Block getModelBlock(int metadata) {
        return Blocks.blockColorful;
    }
    public IIcon a(int side, int metadata) {
        Block model_block = this.getModelBlock(metadata & 7);
        return model_block.a(side, model_block.getBlockSubtypeUnchecked(metadata & 7));
    }
}
