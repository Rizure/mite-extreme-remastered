package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.PrintStream;
import java.util.*;

@Mixin(Item.class)
public abstract class ItemTrans {
   @Shadow public abstract List getSubItems();

   @Shadow public abstract boolean getHasSubtypes();

   @Shadow public abstract int getNumSubtypes();

   @Shadow private int sugar_content;

   @Shadow public abstract boolean hasQuality();

   @Shadow private int maxDamage;

   @Shadow public abstract boolean isDamageable();

   @Shadow
   private static Item[] itemsList;
   @Shadow
   @Final
   private int itemID;

   @Shadow
   protected List materials;

   public Map<Integer, Double> soldPriceArray = new HashMap<>();

   public Map<Integer, Double> buyPriceArray = new HashMap<>();
   

   @ModifyConstant(method = {
           "<init>(ILjava/lang/String;I)V",
   }, constant = @Constant(intValue = 256))
   private static int injected(int value) {
      return 1024;
   }

   @Inject(method = "<init>()V",at = @At("RETURN"))
   private void injectCtor(CallbackInfo callbackInfo) {
      ReflectHelper.dyCast(Item.class, this).recipes = new aah[500];
   }

   @Inject(method = "<init>(ILjava/lang/String;I)V",at = @At("RETURN"))
   private void injectCtor(int par1, String texture, int num_subtypes, CallbackInfo callbackInfo) {
      ReflectHelper.dyCast(Item.class, this).recipes = new aah[500];
   }

   @Redirect(method = "<init>(ILjava/lang/String;I)V", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
   public void removePrint(PrintStream printStream, String messsage) {

   }

   // 向源类进行注入
   public Item setBuyPrice(double price) {
      if(this.getHasSubtypes()) {
         List subs = this.getSubItems();
         for (int i = 0; i < subs.size(); i++) {
            ItemStack itemStack = (ItemStack) subs.get(i);
            int sub = itemStack.getItemSubtype();
            this.buyPriceArray.put(sub, price);
         }
      } else {
         this.buyPriceArray.put(0, price);
      }
      return ReflectHelper.dyCast(Item.class,this);
   }


   // 向源类进行注入
   public Item setSoldPrice(double price) {
      if(this.getHasSubtypes()) {
         List subs = this.getSubItems();
         for (int i = 0; i < subs.size(); i++) {
            ItemStack itemStack = (ItemStack) subs.get(i);
            int sub = itemStack.getItemSubtype();
            this.soldPriceArray.put(sub, price);
         }
      } else {
         this.soldPriceArray.put(0, price);
      }
      return ReflectHelper.dyCast(Item.class, this);
   }


   public void setSugarContent(int sugarContent){
      this.sugar_content = sugarContent;
   }

   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      stack.fixNBT();
      NBTTagCompound tagCompound = stack.stackTagCompound;
      if (tagCompound != null) {
         if (tagCompound.hasKey("tool_exp")) {
            tagCompound.setInteger("tool_exp", tagCompound.getInteger("tool_exp") + exp);
            if (tagCompound.hasKey("tool_level")) {
               int currentLevel = tagCompound.getInteger("tool_level");
               int nextLevelExpReq = this.getExpReqForLevel(currentLevel, this.isWeapon(stack.getItem()));
               if (tagCompound.getInteger("tool_exp") >= nextLevelExpReq) {
                  tagCompound.setInteger("tool_level", currentLevel + 1);
                  tagCompound.setInteger("tool_exp", 0);
                  if (!player.worldObj.isRemote) {
                     player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "已升级,当前等级:" + (currentLevel + 1)).setColor(EnumChatFormat.DARK_AQUA));
                  }

                  if (!tagCompound.hasKey("modifiers")) {
                     tagCompound.setCompoundTag("modifiers", new NBTTagCompound());
                  }

                  this.onItemLevelUp(tagCompound, player, stack);
               }
            }
         }
      } else {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("tool_exp", 0);
         compound.setInteger("tool_level", 0);
         stack.stackTagCompound = compound;
      }
   }

   public List getMaterials() {
      return materials;
   }

   public int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes modifierType) {
      int effectLevel = modifiers.getInteger(modifierType.getNbtName()) + 1;
      modifiers.setInteger(modifierType.getNbtName(), effectLevel);
      return effectLevel;
   }

   @Overwrite
   public final int getMaxDamage(EnumQuality quality) {
      if (!this.isDamageable()) {
         Minecraft.setErrorMessage("getMaxDamage: item is not damageable, " + this);
      }
      return this.maxDamage;
   }

   @Shadow
   public ItemBlock getAsItemBlock() {
      return null;
   }

   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      return HashMultimap.create();
   }

   @Shadow
   private int getBurnTime(ItemStack item_stack) {
      return 0;
   }

   public int getCookTime() {
      return this.isBlock() ? 100 * (this.getAsItemBlock().getBlock().getMinHarvestLevel(-1) + 1) : 100;
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return 0;
   }

   @Overwrite
   public int getHeatLevel(ItemStack item_stack) {
      if (ReflectHelper.dyCast(this) == Items.BLAZE_COAL_POWDER) {
         return 5;
      } else if (ReflectHelper.dyCast(this) == Item.blazeRod) {
         return 4;
      } else {
         return this.getBurnTime(item_stack) > 0 ? 1 : 0;
      }
   }
//   @ModifyConstant(method = {
//           "getScaledDamage(F)I",
//   }, constant = @Constant(floatValue = 100.0F))
//   private static float reloaddamage(float value) {
//      return 1600.0F;
//   }

   @Shadow
   private Material getMaterialForRepairs() {
      return null;
   }

   public float getMeleeDamageBonus(ItemStack stack) {
      return 0.0F;
   }

   @Overwrite
   public Item getRepairItem() {
      Material material_for_repairs = this.getMaterialForRepairs();
      if (material_for_repairs == Material.copper) {
         return Item.copperNugget;
      } else if (material_for_repairs == Material.silver) {
         return Item.silverNugget;
      } else if (material_for_repairs == Material.gold) {
         return Item.goldNugget;
      } else if (material_for_repairs == Material.iron || material_for_repairs == Material.rusted_iron) {
         return Item.ironNugget;
      } else if (material_for_repairs == Material.mithril) {
         return Item.mithrilNugget;
      } else if (material_for_repairs == Material.adamantium) {
         return Item.adamantiumNugget;
      } else if (material_for_repairs == Material.ancient_metal) {
         return Item.ancientMetalNugget;
      } else if (material_for_repairs == Materials.vibranium) {
         return Items.VIBRANIUM_NUGGET;
      } else if (material_for_repairs == Materials.redstone) {
         return Items.redstone;
      } else {
         return null;
      }
   }

   public String getResourceLocationPrefix() {
      return "";
   }

   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer user) {
      return 0.0F;
   }

   public int getToolLevel(ItemStack itemStack) {
      return itemStack.stackTagCompound != null ? itemStack.getTagCompound().getInteger("tool_level") : 0;
   }

   public boolean hasExpAndLevel() {
      return false;
   }

   @Shadow
   public boolean isBlock() {
      return false;
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return false;
   }

   public boolean isWeapon(Item b) {
      return false;
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
   }

   @Shadow
   public Item setCreativeTab(CreativeModeTab table) {
      return null;
   }

   public void setCreativeTable(CreativeModeTab table) {
      this.setCreativeTab(table);
   }

   @Shadow
   public Item setMaxStackSize(int maxStackSize) {
      return null;
   }

   public void setResourceLocation(String location) {
      this.setTextureName(location);
   }

   @Shadow
   public Item setTextureName(String location) {
      return null;
   }
}
