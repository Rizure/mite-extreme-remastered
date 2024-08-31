//package net.xiaoyu233.mitemod.miteite.trans.container;
//
//import net.minecraft.InventorySubcontainer;
//import net.minecraft.ItemStack;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//
//@Mixin(InventorySubcontainer.class)
//public class InventorySubcontainerTrans {
//    @Shadow
//    private ItemStack[] inventoryContents;
//    @Overwrite
//    public ItemStack getStackInSlot(int par1) {
//        System.out.println("debug: inventorylength == " + inventoryContents.length);
//        return this.inventoryContents[par1];
//    }
//}
