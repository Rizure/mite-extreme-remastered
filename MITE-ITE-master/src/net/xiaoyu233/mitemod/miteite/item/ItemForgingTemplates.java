package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;

import java.util.List;

public class ItemForgingTemplates extends Item {
   public enum Types {
      copper(162, Material.copper),
      silver(162, Material.silver),
      gold(162, Material.gold),
      iron(144, Material.iron),
      ancient_metal(126, Material.ancient_metal),
      mithril(108, Material.mithril),
      adamantium(90, Material.adamantium),
      vibranium(72, Materials.vibranium);
      private final int failChance;
      private IIcon iIcon;
      private final Material material;

      Types(int failChance, Material material) {
         this.failChance = failChance;
         this.material = material;
      }
   }
   private final ItemForgingTemplates.Types type;

   public ItemForgingTemplates(ItemForgingTemplates.Types type) {
      super(Constant.getNextItemID(), "forging_template/" + type.name());
      this.setMaterial(type.material);
      this.addMaterial(Material.hardened_clay);
      this.type = type;
      this.setMaxStackSize(8);
      this.setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(getHardestMetalMaterial()) * 1.5f);
   }

   public int getFailChance() {
      return this.type.failChance;
   }

   public boolean hasExpAndLevel() {
      return true;
   }
   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      super.addInformation(item_stack, player, info, extended_info, slot);
      info.add(" ");
      info.add(EnumChatFormat.BLUE + Translator.getFormatted("附加一个副属性到其对应的装备上", new Object[0]));
      if (item_stack.hasTagCompound()) {
         if (extended_info) {
            NBTTagCompound compound = item_stack.stackTagCompound.getCompoundTag("modifiers");
            if (!compound.hasNoTags()) {
               info.add("搭载属性:");
               ToolModifierTypes[] var8 = ToolModifierTypes.values();
               for (ToolModifierTypes value : var8) {
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
               ArmorModifierTypes[] var9 = ArmorModifierTypes.values();
               for (ArmorModifierTypes value : var9) {
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
            }
         }
      }
   }
}
