package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.util.List;

public class ItemEnhanceStone extends Item {
   public enum Types {
      iron(72, Material.iron),
      mithril(108, Material.mithril),
      adamantium(144, Material.adamantium),
      vibranium(180, Materials.vibranium),
      universal(180, Material.glowstone);
      private final int failChance;
      private IIcon iIcon;
      private final Material material;

      Types(int failChance, Material material) {
         this.failChance = failChance;
         this.material = material;
      }
   }

   private final Types type;

   public ItemEnhanceStone(Types type) {
      super(Constant.getNextItemID(), "enhance_stone/" + type.name());
      this.setMaterial(type.material);
      this.type = type;
      this.setMaxStackSize(8);
      if(this.getHardestMetalMaterial() != null){
         this.setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(getHardestMetalMaterial()) * 3f);
      }
   }

   public int getFailChance() {
      return this.type.failChance;
   }

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info) {
         if(this == Items.enhanceStoneUniversal){
            info.add("");
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("保护装备不受锻造掉级影响"));
         }
         if(this == Items.enhanceStoneIron){
            info.add("");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("牛奶香橙无水蛋糕"));
         }
         if(this == Items.enhanceStoneMithril){
            info.add("");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("酸奶薄荷无水蛋糕"));
         }
         if(this == Items.enhanceStoneAdamantium){
            info.add("");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("蓝莓树莓慕斯薄荷蛋糕"));
         }
         if(this == Items.enhanceStoneVibranium){
            info.add("");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("你有梦想吗"));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("你敢于追求自己的梦想吗"));
         }
      }

      super.addInformation(item_stack, player, info, extended_info, slot);
   }
}
