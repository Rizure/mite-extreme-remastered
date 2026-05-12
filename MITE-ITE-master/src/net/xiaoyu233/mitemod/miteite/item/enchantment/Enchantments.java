package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.yq;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.Enchantment.enchantmentsList;

public class Enchantments {
   //    public static final Enchantment DEFENCED = new EnchantmentDefence(getNextEnchantmentID(), yq.c,20);
   public static final Enchantment enchantmentCrit = new EnchantmentCrit(getNextEnchantmentID(), yq.c, 10);
   public static final Enchantment enchantmentExtend = new EnchantmentExtend(getNextEnchantmentID(), yq.c, 10);
   public static final Enchantment enchantmentEmergency = new EnchantmentEmergency(getNextEnchantmentID(), yq.c, 15);
   public static final Enchantment enchantmentConqueror = new EnchantmentConqueror(getNextEnchantmentID(), yq.c, 15);
   public static final Enchantment enchantmentBeheading = new EnchantmentBeheading(getNextEnchantmentID(), yq.c, 10);
   public static final Enchantment enchantmentPhaseDefend = new EnchantmentPhaseDefend(getNextEnchantmentID(), yq.c, 15);
   public static final Enchantment enchantmentChain = new EnchantmentChain(getNextEnchantmentID(), yq.b, 10);
   public static final Enchantment enchantmentLuckOfTheSea = new EnchantmentLuckOfTheSea(getNextEnchantmentID(), yq.b, 10);
   public static final Enchantment enchantmentPurify = new EnchantmentPurify(getNextEnchantmentID(), yq.b, 10);
   public static List<Enchantment> individualEnchantments = new ArrayList<>();

   public static void registerEnchantments() {
      Enchantments.registerEnchantmentsUnsafe(enchantmentPhaseDefend, enchantmentCrit, enchantmentExtend, enchantmentEmergency, enchantmentConqueror, enchantmentBeheading, enchantmentChain, enchantmentLuckOfTheSea, enchantmentPurify);
   }

   public static void registerEnchantmentsUnsafe(Enchantment... enchantments) {
      //This will duplicately register Enchantments
//        int j = 0;
//        for (int i = 80, bLength = enchantmentsList.length; i < bLength; i++) {
//            if (enchantmentsList[i] == null && j < enchantments.length) {
//                enchantmentsList[i + j] = enchantments[j];
//                if(enchantments[j] != null){
//                    j++;
//                }
//            }
//        }
      //Debug
//        for (int i = 0; i < enchantmentsList.length; i++){
//            System.out.println(i + ": " + enchantmentsList[i]);
//        }
      ArrayList<Enchantment> var0 = new ArrayList<>();
      for (Enchantment enchantment : enchantmentsList) {
         if (enchantment != null) {
            var0.add(enchantment);
         }
      }
      try {
         Field enchantmentsBookList = Enchantment.class.getDeclaredField("enchantmentsBookList");
         ReflectHelper.updateFinalModifiers(enchantmentsBookList);
         enchantmentsBookList.setAccessible(true);
         enchantmentsBookList.set(null, var0.toArray(new Enchantment[0]));
      } catch (IllegalAccessException | NoSuchFieldException e) {
         e.printStackTrace();
      }
   }

   private static int getNextEnchantmentID() {
      return Constant.nextEnchantmentID++;
   }
}
