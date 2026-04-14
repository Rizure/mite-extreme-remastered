package net.xiaoyu233.mitemod.miteite.item;

import java.util.Random;

public enum GemModifierTypes {

   health("health", "Green", 1),
   protection("protection", "Purple", 1),
   recover("recover", "Aquamarine", 1),
   damage("damage", "Scarlet", 0),
   haste("haste", "Teal", 0),

   predation("predation", "Magenta", 0),
   execute("execute", "Grey", 0),
   goldize("goldize", "Golden", 0),
   polish("polish", "Aqua", 1),

   medication("medication", "Orange", 0),
   teleport("teleport", "DarkGreen", 0);

   public String gemName;
   public String iconName;
   private int type;
   public static int HELDING_TOOLS_GEM_TYPE = 0;
   public static int ARMOR_GEM_TYPE = 1;

   GemModifierTypes(String gemName, String iconName, int type) {
      this.gemName = gemName;
      this.iconName = iconName;
      this.type = type;
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
         return 0.01f;
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
            if(result == HELDING_TOOLS_GEM_TYPE && random.nextInt(4) != 0){
               result = random.nextInt(lowerTierGemTypes);
            }
            break;
         case 2:
            result = lowerTierGemTypes + random.nextInt(skillGemTypes_L1);
            if(result == HELDING_TOOLS_GEM_TYPE && random.nextInt(3) == 0){
               result = polish.ordinal();
            }
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
