package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bgu.class)
public abstract class RenderBipedTrans extends bhe{
   public RenderBipedTrans(bbo par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   @Shadow
   protected bbj a;

   @Shadow
   protected void c() {
   }

//   @ModifyConstant(method = {
//           "a(Lnet/minecraft/EntityInsentient;F)V",
//   }, constant = @Constant(intValue = 256))
//   private static int injected(int value) {
//      return net.xiaoyu233.mitemod.miteite.util.Constant.blockIDLimit;
//   }

   @Overwrite
   protected void a(EntityInsentient par1EntityLiving, float par2) {
      float var3 = 1.0F;
      GL11.glColor3f(var3, var3, var3);
      super.c(par1EntityLiving, par2);
      ItemStack var4 = par1EntityLiving.getHeldItemStack();
      ItemStack var5 = par1EntityLiving.func_130225_q(3);
      if (var5 != null) {
         GL11.glPushMatrix();
         this.a.c.c(0.0625F);
         if (var5.getItem().itemID < net.xiaoyu233.mitemod.miteite.util.Constant.blockIDLimit) {
            if (bfr.a(Block.blocksList[var5.itemID].getRenderType())) {
               float var6 = 0.625F;
               GL11.glTranslatef(0.0F, -0.25F, 0.0F);
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(var6, -var6, -var6);
            }

            this.getRenderManager().f.a(par1EntityLiving, var5, 0);
         } else if (var5.getItem().itemID == Item.skull.itemID) {
            float var6 = 1.0625F;
            GL11.glScalef(var6, -var6, -var6);
            String var7 = "";
            if (var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner")) {
               var7 = var5.getTagCompound().getString("SkullOwner");
            }

            bjb.a.a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var5.getItemSubtype(), var7);
         }

         GL11.glPopMatrix();
      }

      if (var4 != null) {
         GL11.glPushMatrix();
         if (this.i.s) {
            float var6 = 0.5F;
            GL11.glTranslatef(0.0F, 0.625F, 0.0F);
            GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
            GL11.glScalef(var6, var6, var6);
         }

         this.a.f.c(0.0625F);
         GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
         if (var4.itemID < net.xiaoyu233.mitemod.miteite.util.Constant.blockIDLimit && bfr.a(Block.blocksList[var4.itemID].getRenderType())) {
            float var6 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var6 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-var6, -var6, var6);
         } else if (var4.getItem() instanceof ItemBow) {
            float var6 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var6, -var6, var6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if (Item.itemsList[var4.itemID].n_()) {
            float var6 = 0.625F;
            if (Item.itemsList[var4.itemID].o_()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            this.c();
            GL11.glScalef(var6, -var6, var6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            float var6 = 0.375F;
            if(!(var4.getItem() instanceof ItemDoor)){
               GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            }
            if(var4.getItem() instanceof ItemDoor){
               var6 = 1.0F;
//               GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
               GL11.glTranslatef(-0.0F, 0.15F, 0.0F);
               GL11.glRotatef(-120.0F,1.0F,0.0F,0.0F);
               GL11.glRotatef(120.0F,0.0F,1.0F,0.0F);
               GL11.glRotatef(35.0F,0.0F,0.0F,1.0F);
               GL11.glTranslatef(0.0F,-0.4F, 0.0F);
//               GL11.glRotatef(90.0F,0.0F,0.0F,1.0F);
//               GL11.glRotatef(-90.0F,0.0F,1.0F,0.0F);
//               GL11.glRotatef(90.0F,1.0F,0.0F,0.0F);
            }
            GL11.glScalef(var6, var6, var6);
            if(!(var4.getItem() instanceof ItemDoor)){
               GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }
         }

         this.getRenderManager().f.a(par1EntityLiving, var4, 0);
         if (var4.getItem().b()) {
            this.getRenderManager().f.a(par1EntityLiving, var4, 1);
         }

         GL11.glPopMatrix();
      }

   }
}
