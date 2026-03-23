package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.IIcon;
import net.minecraft.Material;

import javax.swing.*;
import java.util.Random;

public enum GemModifierTypes {
   damage("damage", "Scarlet"),
   health("health", "Green"),
   protection("protection", "Purple"),
   recover("recover", "Aquamarine"),
   haste("haste", "Teal"),

   predation("predation", "Magenta"),
   execute("execute", "Grey"),
   goldize("goldize", "Golden"),
   polish("polish", "Aqua"),

   medication("medication", "Orange"),
   teleport("teleport", "DarkGreen");

   public String gemName;
   public String iconName;

   GemModifierTypes(String gemName, String iconName) {
      this.gemName = gemName;
      this.iconName = iconName;
   }

   public float getRate() {
      if (this == protection) {
         return 0.01f;
      } else if (this == recover) {
         return 0.125f;
      } else if (this == haste) {
         return 0.125f;
      } else if (this == polish) {
         return 0.01f;
      } else if (this == execute) {
         return 0.05f;
      } else if (this == predation) {
         return 0.075f;
      } else if (this == goldize) {
         return 0.001f;
      } else if (this == medication) {
         return 0.07f;
      } else if (this == teleport) {
         return 0.04f;
      } else {
         return 1f;
      }
   }

   public static final int lowerTierGemTypes = 5;
   public static final int skillGemTypes_L1 = 4;
   public static final int skillGemTypes_L2 = 2;

   public static int fetchingSubWithTier(Random random, int Tier) {
      int result;
      switch (Tier) {
         case 1:
            result = random.nextInt(lowerTierGemTypes);
            break;
         case 2:
            result = lowerTierGemTypes + random.nextInt(skillGemTypes_L1);
            break;
         case 3:
            result = lowerTierGemTypes + skillGemTypes_L1 + random.nextInt(skillGemTypes_L2);
            break;
         default:
            result = 0;
            break;
      }
      return result;
   }
}
