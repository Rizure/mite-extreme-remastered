package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.*;
//import net.xiaoyu233.mitemod.miteite.entity.EntityThunderMan;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.render.entity.*;
//import net.xiaoyu233.mitemod.miteite.render.entity.RenderEntityThunderMan;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(bgl.class)
public class RenderManagerTrans {
   @Shadow
   private final Map<Class<? extends Entity>, bgm> q = new HashMap<>();

   private RenderManagerTrans() {}

   @Inject(
           method = "<init>",
           at = @At(value = "RETURN"))
   private void injectRegister(CallbackInfo callback){
      this.q.put(EntityAncientDragon.class, new RenderAncientDragon());
      this.q.put(EntityAnnihilationSkeleton.class, new RenderAnnihilationSkeleton());
      this.q.put(EntityWanderingWitch.class, new RenderWanderingWitch());
//      this.q.put(EntityThunderMan.class, new RenderEntityThunderMan());
      this.q.put(EntityZombieBoss.class, new RenderZombieBoss());
      this.q.put(EntitySpiderKing.class, new RenderSpiderKing(1.45F));
      this.q.put(EntitySpiderQueen.class, new RenderSpiderQueen(1.45F));
      this.q.put(EntityClusterSpider.class, new RenderClusterSpider(0.5F));
      this.q.put(EntityGrenade.class, new bgx(Items.grenade));
      for (bgm o : this.q.values()) {
         o.a(ReflectHelper.dyCast(bgl.class, this));
      }
   }
}
