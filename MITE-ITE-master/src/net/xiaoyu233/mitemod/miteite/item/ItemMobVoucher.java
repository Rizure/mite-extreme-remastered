package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemMobVoucher extends Item {
   String entityName = "";

   public ItemMobVoucher(int id, String entityName) {
      super(id, Materials.copper, "voucher/voucher_" + entityName);
      this.entityName = entityName;
      this.setMaxStackSize(64);
      this.setCreativeTab(CreativeModeTab.tabMaterials);
   }

   @Override
   public boolean isHarmedByAcid() {
      return false;
   }

   @Override
   public boolean canCatchFire() {
      return false;
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         info.add(" ");
         switch (this.entityName) {
            case "core":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("当我1级的时候", new Object[0]));
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("我在用【破损的木棒】打【小龙虾】", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金锭材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("由怪物击杀凭证合成", new Object[0]));
               break;
            case "spider":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("蚁多咬死象", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("饰品合成材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("击杀特定怪物掉落", new Object[0]));
               break;
            case "guard":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("保护队友的最好方式", new Object[0]));
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("就是一个人把敌人全消灭", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("饰品合成材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("击杀未产生仇恨的猪人掉落", new Object[0]));
               break;
            case "fishing":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("永！不！空！军！", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金棒凭证材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("钓鱼概率获得", new Object[0]));
               break;
            case "planting":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("月球上不能种菜", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金棒凭证材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("收获作物时概率掉落", new Object[0]));
               break;
            case "villager":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("一个绿宝石出价3个小麦", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金棒凭证材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("村民交易刷新时概率掉落", new Object[0]));
               break;
            case "club_core":
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("当我100级的时候", new Object[0]));
               info.add(EnumChatFormat.BROWN + Translator.getFormatted("我在用【降魔六合棒】打【霹雳小龙虾】", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金棒合成材料之一", new Object[0]));
               break;
            default:
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("振金核心材料之一", new Object[0]));
               info.add(EnumChatFormat.BLUE + Translator.getFormatted("击杀特定怪物掉落", new Object[0]));
               break;
         }
      }
   }
}
