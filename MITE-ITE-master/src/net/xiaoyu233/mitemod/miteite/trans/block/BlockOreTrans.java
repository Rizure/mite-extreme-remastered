package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(BlockOre.class)
public class BlockOreTrans extends Block {

    protected BlockOreTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        int metadata_dropped = -1;
        int quantity_dropped = 1;
        int id_dropped;
        if (info.wasExploded()) {
            if (this == oreEmerald) {
                id_dropped = Item.shardEmerald.itemID;
            } else if (this == oreDiamond) {
                id_dropped = Item.shardDiamond.itemID;
            } else if (this == Blocks.fancyRed) {
                id_dropped = Items.shardFancyRed.itemID;
            } else if (this == oreLapis) {
                id_dropped = Item.dyePowder.itemID;
                metadata_dropped = 4;
                quantity_dropped = 3 + info.world.rand.nextInt(3);
            } else if (this == oreNetherQuartz) {
                id_dropped = Item.shardNetherQuartz.itemID;
            } else if (this == oreCoal) {
                id_dropped = -1;
            } else {
                id_dropped = this.blockID;
            }
        } else {
            if (info.wasHarvestedByPlayer() && info.getResponsiblePlayer().worldObj.areSkillsEnabled() && !info.getResponsiblePlayer().hasSkill(Skill.MINING)) {
                return super.dropBlockAsEntityItem(info);
            }
            EntityPlayer player = info.getResponsiblePlayer();
            float melting = 0.0f;
            boolean melt_enabled = false;
            if (player != null && player.getHeldItemStack() != null) {
                melting = (ToolModifierTypes.MELTING.getModifierValue(player.getHeldItemStack().getTagCompound()));
                melting *= info.responsible_item_stack.getItemAsTool().getMaterialHarvestLevel() - this.getMinHarvestLevel(0);
            }
            if(info.world.rand.nextFloat() < melting){
                melt_enabled = true;
            }

            if (this == oreCoal) {
                id_dropped = Item.coal.itemID;
            } else if (this == oreDiamond) {
                id_dropped = Item.diamond.itemID;
            } else if (this == oreLapis) {
                id_dropped = Item.dyePowder.itemID;
                metadata_dropped = 4;
                quantity_dropped = 3 + info.world.rand.nextInt(3);
            } else if (this == oreEmerald) {
                id_dropped = Item.emerald.itemID;
            }else if (this == oreCopper && melt_enabled) {
                id_dropped = Item.ingotCopper.itemID;
            }  else if (this == oreSilver && melt_enabled) {
                id_dropped = Item.ingotSilver.itemID;
            }  else if (this == oreGold && melt_enabled) {
                id_dropped = Item.ingotGold.itemID;
            }  else if (this == oreIron && melt_enabled) {
                id_dropped = Item.ingotIron.itemID;
            }  else if (this == oreMithril && melt_enabled) {
                id_dropped = Item.ingotMithril.itemID;
            } else if (this == oreAdamantium && melt_enabled) {
                id_dropped = Item.ingotAdamantium.itemID;
            } else if (this == oreNetherQuartz) {
                id_dropped = Item.netherQuartz.itemID;
            } else if(this == Blocks.fancyRed){
                id_dropped = Items.fancyRed.itemID;
            } else {
                id_dropped = this.blockID;
            }
        }

        if (metadata_dropped == -1) {
            metadata_dropped = id_dropped == this.blockID ? this.getItemSubtype(info.getMetadata()) : 0;
        }

        boolean suppress_fortune = id_dropped == this.blockID && BitHelper.isBitSet(info.getMetadata(), 1);
        if (id_dropped != -1 && info.getMetadata() == 0) {
            DedicatedServer.incrementTournamentScoringCounter(info.getResponsiblePlayer(), Item.getItem(id_dropped));
        }

        float chance = suppress_fortune ? 1.0F : 1.0F + (float)info.getHarvesterFortune() * 0.1F;
        return super.dropBlockAsEntityItem(info, id_dropped, metadata_dropped, quantity_dropped, chance);
    }
}
