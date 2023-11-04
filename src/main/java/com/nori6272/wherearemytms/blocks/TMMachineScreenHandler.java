package com.nori6272.wherearemytms.blocks;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import com.nori6272.wherearemytms.WAMTMenuType;

public class TMMachineScreenHandler extends AbstractContainerMenu {


    public TMMachineScreenHandler(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv);
    }

    public TMMachineScreenHandler(int pContainerId, Inventory inv) {
        super(WAMTMenuType.SCREEN.get(), pContainerId);
    }



    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

}