package net.xiaoyu233.mitemod.miteite.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerExtremeWorkbench;
import org.lwjgl.opengl.GL11;

public class GuiExtremeWorkbench extends awy {
   private static final bjo t = new bjo("textures/gui/container/crafting_table.png");

   public GuiExtremeWorkbench(EntityPlayer player, World par2World, int par3, int par4, int par5) {
      super(new ContainerExtremeWorkbench(player, par3, par4, par5));
   }

   protected void a(float par1, int par2, int par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.f.J().a(t);
      int var4 = this.p;
      int var5 = this.q;
      this.b(var4, var5, 0, 0, this.c, this.d);
   }


   protected void b(int par1, int par2) {
      String var3 = Translator.get("tile.extreme_workbench.name");
      this.o.b(var3, 29, 6, 4210752);
      this.o.b(bkb.a("container.inventory"), 7, this.d - 96 + 3, 4210752);
   }
}
