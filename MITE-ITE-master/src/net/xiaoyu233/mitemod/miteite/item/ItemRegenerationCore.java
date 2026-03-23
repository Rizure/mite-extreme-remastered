package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemRegenerationCore extends Item implements IDamageableItem {
   private Material reinforcement_material;
   public int level;

   ItemRegenerationCore(int id, Material reinforcement_material, int level) {
      super(id, reinforcement_material, "regeneration_core");
      this.level = level * 2;
      this.setCreativeTab(CreativeModeTab.tabTools);
      this.reinforcement_material = reinforcement_material;
      this.setMaxDamage(this.getMultipliedDurability());
   }

   public boolean isHarmedByAcid() {
      return false;
   }

   public boolean isHarmedByFire() {
      return false;
   }

   public boolean isHarmedByLava() {
      return false;
   }

   public final int getMultipliedDurability() {
      return 400 * (int) this.reinforcement_material.getDurability() * 20;
   }

   @Override
   public int getNumComponentsForDurability() {
      return 1;
   }

   @Override
   public int getRepairCost() {
      return 40;
   }

   ;

   public Material getMaterialForDurability() {
      return this.reinforcement_material;
   }

   public Material getMaterialForRepairs() {
      return this.reinforcement_material;
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         info.add(" ");
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("那天被老爹揍了后开始冷战，想着从", new Object[0]));
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("问他要不要吃饭来缓和一下气氛，结果", new Object[0]));
         info.add(EnumChatFormat.BROWN + Translator.getFormatted("脑抽来了句：你没吃饭吗", new Object[0]));
         info.add(EnumChatFormat.BLUE + Translator.get("每12秒恢复" + this.level + "%生命值"));
      }
   }
}
