package net.xiaoyu233.mitemod.miteite.tileentity;

import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.minecraft.NBTTagList;
import net.minecraft.TileEntity;

public class TileEntityHeadStone extends TileEntity {
    private ItemStack[] containingItemStacks = new ItemStack[48];
    private int containingExp = 0;
    public int getSizeInventory()
    {
        return this.containingItemStacks.length;
    }
    public ItemStack[] getInventory()
    {
        return this.containingItemStacks;
    }
    public void setInventoryWithIndex(ItemStack stack,int index){
        this.containingItemStacks[index] = stack;
    }
    public int getContainingExp() {
        return containingExp;
    }
    public void setContainingExp(int exp){
        this.containingExp = exp;
    }
    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return false;
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return false;
    }
    public void destroyInventory()
    {
        ItemStack[] item_stacks = this.containingItemStacks;
        for (int i = 0; i < item_stacks.length; ++i)
        {
            item_stacks[i] = null;
        }
    }
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.containingItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.containingItemStacks.length)
            {
                this.containingItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.containingExp = par1NBTTagCompound.getInteger("containingExp");
    }


    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItemStacks.length; ++var3)
        {
            if (this.containingItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.containingItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
        par1NBTTagCompound.setInteger("containingExp",this.containingExp);
    }


}
