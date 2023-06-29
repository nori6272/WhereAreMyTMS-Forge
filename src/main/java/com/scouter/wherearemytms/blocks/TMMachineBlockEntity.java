package com.scouter.wherearemytms.blocks;


import com.scouter.wherearemytms.WhereAreMyTMs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class TMMachineBlockEntity extends BlockEntity implements MenuProvider {
    public TMMachineBlockEntity(BlockPos pos, BlockState state) {
        super(WAMTBlockEntity.TM_MACHINE_BLOCK_ENTITY.get(), pos, state);
    }


    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new TMMachineScreenHandler(pContainerId, pInventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.wherearemytms.tm_machine").withStyle(ChatFormatting.GRAY);
    }
}