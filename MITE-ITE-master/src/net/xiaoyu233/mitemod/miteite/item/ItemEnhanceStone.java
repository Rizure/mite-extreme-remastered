package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.IIcon;
import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.util.Constant;

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
}
