package com.nori6272.wherearemytms;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.types.ElementalType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import com.nori6272.wherearemytms.items.BasePokemonTM;

import java.util.function.Supplier;

public class TMMessage {

    public CompoundTag nbt;

    public TMMessage(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public TMMessage(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(nbt);
    }

    public static void handle(TMMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Move move = Move.Companion.loadFromNBT(message.nbt);
            Player player = context.getSender();
            ItemStack stack =  player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.getItem() instanceof BasePokemonTM tm) {

                // Create
                CompoundTag stackNbt = stack.getOrCreateTag();
                stackNbt.putString("move", move.getName());
                ElementalType type = move.getType();
                int hue = type.getHue();
                stackNbt.putString("type", type.getDisplayName().getString());
                stackNbt.putInt("hue", hue);

                // Update
                stack.setTag(stackNbt);
                stack.setHoverName(Component.translatable(tm.title, move.getDisplayName())
                        .setStyle(Style.EMPTY
                                .withColor(hue)
                                .withItalic(false)));
                player.setItemInHand(player.getUsedItemHand(), stack);
            }
        });
        context.setPacketHandled(true);
    }
}
