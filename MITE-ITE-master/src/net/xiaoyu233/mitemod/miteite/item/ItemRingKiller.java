package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemRingKiller extends Item{
    int level = 6;
    public ItemRingKiller(int par1, Material material) {
        super(par1, material, null);
        this.judgeMaterialLevel(material);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeModeTab.tabCombat);
        this.setCraftingDifficultyAsComponent(1.0E-9F);
    }
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }

    public int getLevel() {
        return this.level;
    }

    public void judgeMaterialLevel (Material material) {
        if(material == Materials.copper) {
            this.level = 1;
        } else if(material == Materials.iron) {
            this.level = 2;
        } else if(material == Materials.ancient_metal) {
            this.level = 3;
        } else if(material == Materials.mithril) {
            this.level = 4;
        } else if(material ==  Materials.adamantium) {
            this.level = 5;
        } else if(material == Materials.vibranium) {
            this.level = 6;
        }
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

    public String getToolType() {
        return "ringKiller";
    }

    public int getRingKillerSkillCoolDownTime(ItemStack itemStack) {
        switch (itemStack.getItemSubtype()) {
            case 0:
                return (13 - this.level * 2) * 20;
            case 1:
                return (7 - this.level) * 10;
            case 2:
            default:
                return (13 - this.level * 2) * 20;
        }
    }

    public float getRingKillerSkillRange(ItemStack itemStack) {
        switch (itemStack.getItemSubtype()) {
            case 0:
                return 4.0F;
            case 1:
                return 2.5F;
            case 2:
            default:
                return 6.0F;
        }
    }

    public float getRingKillerSkillDamage(ItemStack itemStack) {
        switch (itemStack.getItemSubtype()) {
            case 0:
                return this.level + 4.0F;
            case 1:
                return this.level + 1.0F;
            case 2:
            default:
                return this.level + 2.0F;
        }
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("F11是对大哥的认可", new Object[0]));
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("F12是对大哥的不舍", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("自动范围群体伤害", new Object[0]));
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("范围:" + this.getRingKillerSkillRange(item_stack), new Object[0]));
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("伤害:" + this.getRingKillerSkillDamage(item_stack), new Object[0]));
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("冷却:" + (float)this.getRingKillerSkillCoolDownTime(item_stack) / 20F + 'S', new Object[0]));
            info.add(EnumChatFormat.GREEN + Translator.getFormatted("模式:" + item_stack.getItemSubtype(), new Object[0]));
        }
    }
}
