package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityBlizzard;

public class RenderBlizzard extends bhe {
    public static final int body_texture = 0;
    private int f;

    public RenderBlizzard() {
        super(new bba(), 0.5F);
        this.f = ((bba)this.i).a();
    }

    protected void setTextures() {
        this.setTexture(0, "textures/entity/blizzard");
    }

    public void a(EntityBlizzard par1EntityBlizzard, double par2, double par4, double par6, float par8, float par9) {
        int var10 = ((bba)this.i).a();
        if (var10 != this.f) {
            this.f = var10;
            this.i = new bba();
        }

        super.a(par1EntityBlizzard, par2, par4, par6, par8, par9);
    }

    protected bjo a(EntityBlizzard par1EntityBlizzard) {
        return this.textures[0];
    }

    public void a(EntityInsentient par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.a((EntityBlizzard)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void a(EntityLiving par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
        this.a((EntityBlizzard)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    protected bjo a(Entity par1Entity) {
        return this.a((EntityBlizzard)par1Entity);
    }

    public void a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.a((EntityBlizzard)par1Entity, par2, par4, par6, par8, par9);
    }
}
