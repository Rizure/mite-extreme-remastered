package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public class BlockExtendedToolbench extends BlockWorkbench {
   private IIcon displayOnCreativeTab;
   private IIcon icon_flint_top;
   private IIcon icon_obsidian_top;
   protected IIcon[] front_icons = new IIcon[10];
   protected IIcon[] side_icons = new IIcon[10];
   public static final Material[] tool_materials;

   protected BlockExtendedToolbench(int par1) {
      super(par1);
   }
   public String getMetadataNotes() {
      String[] array = new String[this.getNumSubBlocks()];

      for(int i = 0; i < array.length; ++i) {
         array[i] = i + "=" + getToolMaterial(i).getCapitalizedName() + " Tools";
      }

      return StringHelper.implode(array, ", ", true, true);
   }

   public boolean isValidMetadata(int metadata) {
      return metadata >= 0 && metadata < 10;
   }

   public int getBlockSubtypeUnchecked(int metadata) {
      return metadata;
   }

   public static Material getToolMaterial(int metadata) {
      if (metadata > 1 && metadata < 6) {
         return tool_materials[2];
      } else if (metadata >= 6) {
         return tool_materials[3];
      }
      return tool_materials[metadata];
   }

   public static ItemStack getBlockComponent(int metadata) {
      Material tool_material = BlockExtendedToolbench.getToolMaterial(metadata);
      if (tool_material == Material.flint) {
         return new ItemStack(Blocks.wood1, 1, metadata - 2);
      } else if (tool_material == Material.obsidian) {
         return new ItemStack(Blocks.wood1, 1, metadata - 6);
      }
      return null;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
      return super.onBlockActivated(world,x,y,z,player,face,offset_x,offset_y,offset_z);
   }

   static {
      tool_materials = new Material[]{Materials.vibranium, Materials.mitega, Material.flint, Material.obsidian};
   }

   public String getItemDisplayName(ItemStack itemStack) {
      return itemStack != null ? Translator.get("tile.toolbench." + BlockExtendedToolbench.getToolMaterial(itemStack.getItemSubtype()).getName() + ".name") : Translator.get("tile.toolbench.unknown.name");
   }

   public IIcon a(int side, int metadata) {
      if (metadata > 1 && metadata < 6) {
         return side == 1 ? this.icon_flint_top : Blocks.wood1.a(side, metadata - 2);
      } else if (metadata >= 6) {
         return side == 1 ? this.icon_obsidian_top : Blocks.wood1.a(side, metadata - 6);
      } else if (side == 0) {
         return Block.planks.m(side);
      } else if (side == 1) {
         return this.displayOnCreativeTab;
      } else {
         return side != 2 && side != 3 ? this.side_icons[metadata] : this.front_icons[metadata];
      }
   }

   public void a(mt par1IconRegister) {
      this.icon_flint_top = par1IconRegister.a("crafting_table/flint/top");
      this.icon_obsidian_top = par1IconRegister.a("crafting_table/obsidian/top");
      this.displayOnCreativeTab = par1IconRegister.a("crafting_table_top");

      for(int i = 0; i < 2; ++i) {
         this.front_icons[i] = par1IconRegister.a("crafting_table/" + getToolMaterial(i).toString() + "/front");
         this.side_icons[i] = par1IconRegister.a("crafting_table/" + getToolMaterial(i).toString() + "/side");
      }

   }
}
