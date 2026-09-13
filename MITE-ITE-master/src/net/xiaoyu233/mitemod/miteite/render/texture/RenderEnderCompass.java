package net.xiaoyu233.mitemod.miteite.render.texture;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.ClientStrongholdCache;

import java.util.List;

public class RenderEnderCompass extends bil {
    public double i;
    public double j;

    public RenderEnderCompass(String var1) {
        super(var1);
    }

    public void j() {
        Minecraft var1 = Minecraft.w();
        if (var1.f != null && var1.h != null) {
            this.a(var1.f, var1.h.posX, var1.h.posZ, (double)var1.h.rotationYaw, false, false);
        } else {
            this.a((World)null, (double)0.0F, (double)0.0F, (double)0.0F, true, false);
        }

    }

    public void a(World var1, double var2, double var4, double var6, boolean var8, boolean var9) {
        if (!this.a.isEmpty()) {
            double var10 = (double)0.0F;
            if (var1 != null && !var8) {
                ChunkPosition strongholdPos = ClientStrongholdCache.getStrongholdPos();
                if (var1.provider.isSurfaceWorld() && strongholdPos != null){
                    double var13 = (double) strongholdPos.x - var2;
                    double var15 = (double) strongholdPos.z - var4;
                    var6 %= (double)360.0F;
                    var10 = -((var6 - (double)90.0F) * Math.PI / (double)180.0F - Math.atan2(var15, var13));
                } else {
                    var10 = Math.random() * (double)(float)Math.PI * (double)2.0F;
                }
            }

            if (var9) {
                this.i = var10;
            } else {
                double var18;
                for(var18 = var10 - this.i; var18 < -Math.PI; var18 += (Math.PI * 2D)) {
                }

                while(var18 >= Math.PI) {
                    var18 -= (Math.PI * 2D);
                }

                if (var18 < (double)-1.0F) {
                    var18 = (double)-1.0F;
                }

                if (var18 > (double)1.0F) {
                    var18 = (double)1.0F;
                }

                this.j += var18 * 0.1;
                this.j *= 0.8;
                this.i += this.j;
            }

            int var19;
            for(var19 = (int)((this.i / (Math.PI * 2D) + (double)1.0F) * (double)this.a.size()) % this.a.size(); var19 < 0; var19 = (var19 + this.a.size()) % this.a.size()) {
            }

            if (var19 != this.g) {
                this.g = var19;
                bip.a((int[])this.a.get(this.g), this.e, this.f, this.c, this.d, false, false);
            }

        }
    }
}
