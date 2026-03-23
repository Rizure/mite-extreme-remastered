package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.EntityFinalZombieBoss;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.world.WorldGenCherry;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ItemConsumables extends Item {
   public String tag;

   public ItemConsumables(int id, Material material, String tag) {
      super(id, material, "usable");
      this.tag = tag;
      this.setMaxStackSize(16);
      this.setCraftingDifficultyAsComponent(100.0F);
      this.setCreativeTab(CreativeModeTab.tabTools);
   }

   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if(player.onServer()){
         ItemStack consumable = player.getHeldItemStack();
         Random rand = new Random(player.worldObj.rand.nextLong());
         if (consumable.getItem() == Items.colorBag) {
            for (int i = 0; i < ItemDye.dyeColors.length; i++) {
               player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.dyePowder, 1, rand.nextInt(ItemDye.dyeColors.length)));
            }
            player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
            player.makeSound("random.anvil_land", 0.4F, 0.4F);
            if(!player.inCreativeMode()){
               player.convertOneOfHeldItem((ItemStack) null);
            }
            return true;
         } else if (consumable.getItem() == Items.gemBlue) {
            for (int i = 0; i < 64; i++) {
               player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Blocks.blockColorful, 1, rand.nextInt(16)));
            }
            player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
            player.makeSound("random.anvil_land", 0.4F, 0.4F);
            if(!player.inCreativeMode()) {
               player.convertOneOfHeldItem((ItemStack) null);
            }
            return true;
         } else if (consumable.getItem() == Items.gemYellow) {
            for (int i = 0; i < 64; i++) {
               player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Blocks.blockGotcha, 1, rand.nextInt(16)));
            }
            player.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
            player.makeSound("random.anvil_land", 0.4F, 0.4F);
            if(!player.inCreativeMode()) {
               player.convertOneOfHeldItem((ItemStack) null);
            }
            return true;
         } else if (consumable.getItem() == Items.endScroll) {
            if (rand.nextInt(24) == 0) {
               player.makeSound("mob.zombie.remedy", 1.0F, 0.75F);
               player.convertOneOfHeldItem((ItemStack) null);
               player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.paper, 4));
               player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.shardObsidian, 4));
            }
            player.displayGUIChestForMinecart(player.getInventoryEnderChest());
            player.makeSound("mob.endermen.portal", 1.0F, 0.5F);
            return true;
         } else if (consumable.getItem() == Items.final_key) {
            RaycastCollision rc = player.getSelectedObject(partial_tick, false);
            if (rc != null) {
               if (rc.isBlock()) {
                  if (rc.getBlockHitID() == Blocks.mobSpawner.blockID) {
                     MinecraftServer server = MinecraftServer.F();
                     Iterator var4 = server.getConfigurationManager().playerEntityList.iterator();
                     while (var4.hasNext()) {
                        Object o = var4.next();
                        EntityPlayer listeners = (EntityPlayer) o;
                        listeners.sendChatToPlayer(ChatMessage.createFromText("强化BOSS已被释出"));
                     }
                     player.makeSound("mob.zombie.remedy", 1.0F, 0.75F);
                     int x = rc.block_hit_x;
                     int y = rc.block_hit_y;
                     int z = rc.block_hit_z;
                     player.worldObj.setBlockToAir(x, y, z);
                     EntityZombieBoss entityZombieBoss = new EntityFinalZombieBoss(player.worldObj);
                     entityZombieBoss.setPosition(x,y,z);
                     entityZombieBoss.refreshDespawnCounter(-9600);
                     entityZombieBoss.setAttackTarget(player);
                     if(entityZombieBoss.onServer()){
                        entityZombieBoss.entityFX(EnumEntityFX.summoned);
                     }
                     entityZombieBoss.onSpawnWithEgg(null);
                     player.worldObj.spawnEntityInWorld(entityZombieBoss);
                     return true;
                  }
               } else if (rc.isEntity()) {
                  if (rc.getEntityHit() instanceof EntityZombieBoss && !(rc.getEntityHit() instanceof EntityFinalZombieBoss)) {
                     (rc.getEntityHit()).setDead();
                     player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.itemEnhanceGem2, 1, GemModifierTypes.fetchingSubWithTier(player.getRNG(), 1)));
                     Item[] drops = {Item.netherQuartz, Item.netherQuartz, Item.emerald, Item.emerald, Item.diamond, Item.blazePowder, Item.ghastTear};
                     for (int i = 0; i < player.worldObj.getDayOfOverworld(); i += 12) {
                        player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(drops[rand.nextInt(drops.length)]));
                     }
                     player.makeSound("random.anvil_land", 0.5F, 0.5F);
                     player.convertOneOfHeldItem((ItemStack) null);
                  }
                  return true;
               }
            }
         } else if (consumable.getItem() == Items.chibiCreeperBaby){
            player.makeSound("mob.chicken.plop",1.0F,0.75F);
            if(rand.nextInt(10) == 0){
               player.addPotionEffect(new MobEffect(MobEffectList.digSpeed.id, 120 * 20, 0));
               if(rand.nextInt(10) == 0){
                  if(!player.inCreativeMode()) {
                     player.worldObj.createExplosion(player, player.posX, player.posY + 1.0F, player.posZ, 2.0F, 0.0F, false);
                     player.convertOneOfHeldItem((ItemStack) null);
                  }
               }
            }
            return true;
         } else if (consumable.getItem() == Items.chibiCreeperSoul){
            player.makeSound("mob.chicken.plop",1.0F,1.5F);
            if(rand.nextInt(10) == 0){
               player.addPotionEffect(new MobEffect(MobEffectList.digSpeed.id, 120 * 20, 3));
               if(rand.nextInt(25) == 0){
                  if(!player.inCreativeMode()) {
                     player.worldObj.createExplosion(player, player.posX, player.posY + 1.0F, player.posZ, 6.0F, 0.0F, false);
                     player.convertOneOfHeldItem((ItemStack) null);
                  }
               }
            }
            return true;
         }
      } else {
         player.bobItem();
         player.swingArm();
      }
      return true;
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         info.add(" ");
         if (Objects.equals(this.tag, "color_bag")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("豪德寺三花的卡片", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("虹龙洞的隐秘道具", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以获得一些染料", new Object[0]));
         }
         if (Objects.equals(this.tag, "gem_y")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("今日修仙不努力", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("万魂幡里做兄弟", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以获得一些灵石", new Object[0]));
         }
         if (Objects.equals(this.tag, "gem_b")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("不是，道友", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("你这人皇幡怎么冒黑烟啊", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以获得一些彩石", new Object[0]));
         }
         if (Objects.equals(this.tag, "final_key")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("将世界调成静音", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("聆听开锁师傅被炸飞的声音", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以开启刷怪笼，或是……", new Object[0]));
         }
         if (Objects.equals(this.tag, "end_scroll")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("天机不可泄露", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("自己知道就好", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以打开末影箱", new Object[0]));
         }
         if (Objects.equals(this.tag, "chibi_creeper_baby")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("Say hi & Explode.", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("BOOM!", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以获得急迫效果", new Object[0]));
         }
         if (Objects.equals(this.tag, "chibi_creeper_soul")) {
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("无论如何都难免一炸", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("那为何不试试自己控制它呢？", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("使用以获得更强的急迫效果", new Object[0]));
         }
      }
   }
}
