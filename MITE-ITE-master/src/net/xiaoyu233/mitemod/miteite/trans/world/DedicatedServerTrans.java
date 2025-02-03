//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServer.class)
public class DedicatedServerTrans {

    public DedicatedServerTrans() {}

    @Inject(method = "playerLoggedIn",at = @At("RETURN"))
    public void playerLoggedIn(ServerPlayer par1EntityPlayerMP, CallbackInfo callbackInfo) {
        par1EntityPlayerMP.sendChatToPlayer(ChatMessage.createFromTranslationKey("[Server] ").appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme-REMASTERED模组已加载,当前版本:").setColor(EnumChatFormat.DARK_GREEN)).appendComponent(ChatMessage.createFromText(Constant.MITE_ITE_VERSION).setColor(EnumChatFormat.DARK_RED)));
        par1EntityPlayerMP.sendChatToPlayer(ChatMessage.createFromTranslationKey("[MITE-Extreme-REMASTERED]:").setColor(EnumChatFormat.WHITE)
                .appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme-REMASTERED由 ").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("Rikalzery").setColor(EnumChatFormat.WHITE))
                .appendComponent(ChatMessage.createFromTranslationKey(" 重写,").setColor(EnumChatFormat.DARK_AQUA)));
        par1EntityPlayerMP.sendChatToPlayer(ChatMessage.createFromTranslationKey("[MITE-Extreme-REMASTERED]:").setColor(EnumChatFormat.WHITE)
                .appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme由 ").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("wensc,洛小雨").setColor(EnumChatFormat.WHITE))
                .appendComponent(ChatMessage.createFromTranslationKey(" 重写,").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("「我对你好吗」特约赞助").setColor(EnumChatFormat.BROWN))
                .appendComponent(ChatMessage.createFromTranslationKey(" 下载地址：wensc.cn").setColor(EnumChatFormat.DARK_GREEN)));
        if (par1EntityPlayerMP.isFirstLogin == true) {
            par1EntityPlayerMP.isFirstLogin = false;
        }
        this.updatePlayersFile();
    }
    @Shadow
    public void updatePlayersFile() {
    }
}
