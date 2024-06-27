package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;

public class RenderZombieBoss extends bhz {
    public void renderBoss(EntityZombieBoss par1EntityZombie, double par2, double par4, double par6, float par8, float par9) {

        bez.a(par1EntityZombie, false);
        super.a(par1EntityZombie, par2, par4, par6, par8, par9);
        this.b(par1EntityZombie, par2, par4, par6, par8, par9);
    }
    @Override
    public void a(EntityLiving par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
        this.renderBoss((EntityZombieBoss) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }
    @Override
    public void a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderBoss((EntityZombieBoss) par1Entity, par2, par4, par6, par8, par9);
    }
    @Override
    public void a(EntityInsentient par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderBoss((EntityZombieBoss) par1EntityLiving, par2, par4, par6, par8, par9);
    }
}
