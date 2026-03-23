package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;

import java.util.List;

public class ItemFan extends ItemTool {
   private Material reinforcement_material;
   public ItemFan(int par1, Material material) {
      super(par1, material);
      this.reinforcement_material = material;
   }

   @Override
   public float getBaseDamageVsEntity() {
      return 0.0F;
   }

   @Override
   public float getBaseDecayRateForBreakingBlock(Block block) {
      return 0.5F;
   }

   @Override
   public float getBaseDecayRateForAttackingEntity(ItemStack itemStack) {
      return 4.0F;
   }

   @Override
   public String getToolType() {
      return "Fan";
   }

   public boolean tryEntityInteraction(Entity entity, EntityPlayer player, ItemStack item_stack) {
      return false;
   }

   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if(player.onClient()){
         player.swingArm();
      }else {
         float range = 8.0F;
         List<Entity> targets = player.getNearbyEntities(range, range / 3.0F);
         for (int i = 0; i < targets.size(); i++) {
            EntityLiving entityMonster = targets.get(i) instanceof EntityInsentient ? (EntityLiving) targets.get(i) : null;
            if (entityMonster != null && !(entityMonster instanceof EntityZombieBoss) && !(entityMonster instanceof EntityPlayer)) {
               if(!(((EntityInsentient)entityMonster).hasCustomNameTag()) && !(entityMonster instanceof EntityLivestock) && !(entityMonster instanceof EntityVillager)){
                  entityMonster.attackEntityFrom(new Damage(DamageSource.causeIndirectMagicDamage(entityMonster, player.getAsPlayer()), 1.0F));
                  entityMonster.addVelocity(-MathHelper.sin(player.rotationYaw * 3.1415927F / 180.0F) * 2.5F, 0.3D, MathHelper.cos(player.rotationYaw * 3.1415927F / 180.0F) * 2.5F);
                  player.tryDamageHeldItem(DamageSource.generic, 200);
               }
            }
         }
         int x = player.getBlockPosX();
         int y = player.getBlockPosY();
         int z = player.getBlockPosZ();
         for(int dx = -8; dx <= 8; dx++){
            for(int dy = -4; dy <= 4; dy++){
               for(int dz = -8; dz <= 8; dz++){
                  Block block = player.worldObj.getBlock(x + dx,y + dy,z + dz);
                  if(block instanceof BlockLongGrass){
                     BlockBreakInfo info = new BlockBreakInfo(player.worldObj,x + dx,y + dy,z + dz).setHarvestedBy(player);
                     info.dropBlockAsEntityItem(true);
                     player.tryDamageHeldItem(DamageSource.generic, this.getToolDecayFromBreakingBlock(info));
                  }
               }
            }
         }
         List<Entity> items = player.getNearbyEntities(8, 4);
         for (Entity item : items) {
            EntityItem entityItem = item instanceof EntityItem ? (EntityItem) item : null;
            if (entityItem != null) {
               entityItem.setPosition(x, y, z);
               player.tryDamageHeldItem(DamageSource.generic, 50);
            }
         }

         if(ctrl_is_down){
            boolean canTeleport = true;
            for(int dy = 0; dy <= 8; dy++){
               Block block = player.worldObj.getBlock(x,y + dy,z);
               if(block != null){
                  canTeleport = false;
               }
            }
            if(canTeleport){
               player.setPositionAndUpdate(player.posX,player.posY + 6.0F, player.posZ);
               player.tryDamageHeldItem(DamageSource.generic, 2000);
            }
            player.makeSound("mob.bat.takeoff",1.0F,0.5F);
         }else {
            player.makeSound("mob.enderdragon.wings",1.0F,1.5F);
         }
      }
      return true;
   }

   @Override
   public int getNumComponentsForDurability() {
      return 2;
   }


   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         info.add(" ");
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("决赛圈淘汰王1v1", new Object[0]));
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("一个开透一个开锁", new Object[0]));
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("我在天上看得一清二楚", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("位于快捷栏时免疫摔落", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("右键手动范围除草", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("并收取周遭的掉落物", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("手持获得缓降", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("按ctrl右键以腾空", new Object[0]));
      }
   }
   public Material getMaterialForDurability() {
      return this.reinforcement_material;
   }

   public Material getMaterialForRepairs() {
      return this.reinforcement_material;
   }
}
