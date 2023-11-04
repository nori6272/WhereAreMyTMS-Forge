package com.nori6272.wherearemytms.items;

import com.cobblemon.mod.common.api.moves.*;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.nori6272.wherearemytms.config.WhereAreMyTMsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.StreamSupport;

public class BasePokemonTM extends Item {
    public String title;
    public String usesKey;
    public boolean oneTimeUse;

    public BasePokemonTM(Properties pProperties, String title, String useKey, boolean oneTimeUse) {
        super(pProperties);
        this.title = title;
        this.usesKey = useKey;
        this.oneTimeUse = oneTimeUse;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pPlayer.level().isClientSide || !pPlayer.getUsedItemHand().equals(pUsedHand))
            return InteractionResult.SUCCESS;
        if (!(pInteractionTarget instanceof PokemonEntity pokemonEntity) || !pokemonEntity.isOwnedBy(pPlayer))
            return InteractionResult.FAIL;
        CompoundTag compoundTag = pStack.getOrCreateTag();
        Pokemon pokemon = pokemonEntity.getPokemon();
        if (compoundTag.contains("move")) {
            MoveTemplate moveTemplate = Moves.INSTANCE.getByName(compoundTag.getString("move"));
            if (moveTemplate == null) return InteractionResult.FAIL;
            if (!teach(pPlayer, pokemon, moveTemplate)) return InteractionResult.FAIL;
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (this.oneTimeUse && !pPlayer.isCreative()) {
                pStack.shrink(1);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    protected boolean teach(Player user, Pokemon pokemon, MoveTemplate moveTemplate) {
        MoveSet moves = pokemon.getMoveSet();
        BenchedMoves benchedMoves = pokemon.getBenchedMoves();

        // Move is already a part of moveset
        if (moveInMoveSets(moveTemplate, moves, benchedMoves)) {
            user.sendSystemMessage(createResponse(
                    "response.wherearemytms.already_learned",
                    pokemon.getDisplayName(),
                    moveTemplate.getDisplayName(),
                    ChatFormatting.RED)
            );
            return false;
        }

        // Move is not part of learnset
        Learnset learnset = pokemon.getForm().getMoves();
        if (WhereAreMyTMsConfig.ALLOW_TUTOR_MOVES.get()) {
            if (learnset.getEggMoves().contains(moveTemplate)) {
                addMove(user, pokemon, moveTemplate, moves, benchedMoves);
                return true;
            }
        }

        if (learnset.getTmMoves().contains(moveTemplate)) {
            addMove(user, pokemon, moveTemplate, moves, benchedMoves);
            return true;
        }

        user.sendSystemMessage(createResponse(
                "response.wherearemytms.cannot_learn",
                pokemon.getDisplayName(),
                moveTemplate.getDisplayName(),
                ChatFormatting.RED)
        );
        return false;
    }


    private static Component createResponse(String key, MutableComponent pokemon, MutableComponent move, ChatFormatting color) {
        return Component.translatable(key, pokemon.getString(), move.getString()).withStyle(color);
    }


    public static boolean moveInMoveSets(MoveTemplate moveTemplate, MoveSet moves, BenchedMoves benchedMoves) {
        boolean containsMoves = moves.getMoves().stream().anyMatch(move -> move.getTemplate() == moveTemplate) ||
                StreamSupport.stream(benchedMoves.spliterator(), false).anyMatch(move -> move.getMoveTemplate() == moveTemplate);
        return containsMoves;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbtCompound = pStack.getOrCreateTag();
        if (nbtCompound.contains("type") && nbtCompound.contains("hue")) {
            pTooltipComponents.add(Component.literal(
                            nbtCompound.getString("type"))
                    .setStyle(Style.EMPTY.withColor(nbtCompound.getInt("hue")))
            );
        } else {
            pTooltipComponents.add(Component.translatable(this.usesKey).withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("description.wherearemytms.requires_printing").withStyle(ChatFormatting.GRAY));
        }

    }

    private void addMove(Player user, Pokemon pokemon, MoveTemplate moveTemplate, MoveSet moves, BenchedMoves benchedMoves) {
        if (moves.hasSpace()) {
            moves.add(moveTemplate.create());
        } else {
            benchedMoves.add(new BenchedMove(moveTemplate, 0));
        }

        user.sendSystemMessage(createResponse(
                "response.wherearemytms.success",
                pokemon.getDisplayName(),
                moveTemplate.getDisplayName(),
                ChatFormatting.GOLD)
        );
    }

    public static boolean isTMBlank(ItemStack stack) {
        return !stack.getOrCreateTag().contains("move");
    }

    public static boolean isTMBlank(CompoundTag nbtCompound) {
        return !nbtCompound.contains("move");
    }
}
