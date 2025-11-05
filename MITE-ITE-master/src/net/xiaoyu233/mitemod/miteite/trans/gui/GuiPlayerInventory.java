package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.trans.util.DamageTrans;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(axv.class)
public class GuiPlayerInventory extends axp {
    public GuiPlayerInventory(EntityPlayer par1EntityPlayer) {
        super(par1EntityPlayer.inventoryContainer);
        this.j = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
        par1EntityPlayer.incrementStatForThisWorldFromClient(AchievementList.openInventory);
        par1EntityPlayer.sendPacket(new CPacketSyncItems());
    }

    @Inject(method = "a(FII)V", at = @At("HEAD"))
    protected void InjectA(float par1, int par2, int par3, CallbackInfo callbackInfo) {
        this.c = 208;
    }

    @Redirect(method = "a(FII)V",at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/axv;b(IIIIII)V"))
    public void injectRecipeArrow(axv axvLocal,int var1, int val2, int val3, int val4, int val5, int val6) {
        axvLocal.b(var1, val2, 145, 200, val5, val6);
    }

    @Shadow
    protected void a(float v, int i, int i1) {
    }

    @Overwrite
    protected void b(int par1, int par2) {
        String emergencyWords;
        Object[] var3 = Arrays.stream(this.f.h.getWornItems()).filter(armor -> armor != null && armor.hasEnchantment(Enchantments.EMERGENCY, false)).toArray();
        if(var3.length > 0) {
            if(((ItemStack)var3[0]).getEmergencyCooldown() <= 0) {
                emergencyWords = "已就绪";
            } else {
                emergencyWords = ((ItemStack)var3[0]).getEmergencyCooldown() / 20 + "S";
            }
        } else {
            emergencyWords = "无此附魔";
        }
        this.o.b(bkb.a("container.crafting"), 87, 15, 4210752);

        int info_page = 2;
        int display_ticks = this.f.h.getTicksExistedWithOffset() % (100 * info_page);
        String emergency_cooldown_info = "紧急守备：" + emergencyWords;
        String money_info = bkb.a("container.shop.money",String.format("%.2f",this.e.player.money));
        float protection = this.f.h.getTotalProtection(null);
        int experience = this.e.player.experience;
        float health = this.e.player.getHealth();
        float maxHealth = this.e.player.getMaxHealth();
        String health_info = "生命值：" + String.format("%.1f", health) + "/" + String.format("%.1f", maxHealth);
        String protection_info = "保护：" + String.format("%.2f", protection);
        String experience_info = "经验：" + experience;
        switch (display_ticks / 100){
            case 0:
                this.o.b(emergency_cooldown_info, 87, 63, 4210752);
                this.o.b(protection_info, 87, 73, 4210752);
                break;
            case 1:
                this.o.b(money_info, 87, 63, 4210752);
                this.o.b(experience_info, 87, 73, 4210752);
                break;
            default:
                break;
        }
    }
}