package com.scouter.wherearemytms.blocks;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.pokemon.activestate.PokemonState;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.scouter.wherearemytms.Messages;
import com.scouter.wherearemytms.TMMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.cobblemon.mod.common.api.gui.GuiUtilsKt.drawPortraitPokemon;
import static com.cobblemon.mod.common.client.render.RenderHelperKt.drawScaledText;
import static com.cobblemon.mod.common.util.LocalizationUtilsKt.lang;
import static com.scouter.wherearemytms.WhereAreMyTMs.prefix;
import static com.scouter.wherearemytms.blocks.RenderUtils.s_blitk;

public class TMMachineScreen extends AbstractContainerScreen<TMMachineScreenHandler> {
    // this code is so cursed lmao
    // i dont even want to fix it
    // rendering is for losers

    // im literally rendering a part of it in this screen, some widgets elsewhere, theyre all hardcoded
    // i have random values, its not symmetrical
    // the buttons are massive and also tiny at the same time
    // theres like 18 for loops
    // for some reason ive done the screen and screenhandler this way instead of pure client side
    // and im STILL USING PACKETS

    public static final ResourceLocation battleInfoBase = prefix("textures/gui/battle_info_base.png");
    public static final ResourceLocation battleInfoUnderlay = prefix("textures/gui/battle_info_underlay.png");

    public static final ResourceLocation DEFAULT_LARGE = new ResourceLocation("uniform");

    private static final int INFO_OFFSET_X = 7;

    private static final int TILE_WIDTH = 131;
    private static final int TILE_HEIGHT = 40;
    private static final int PORTRAIT_DIAMETER = 28;
    private static final int PORTRAIT_OFFSET_X = 5;
    private static final int PORTRAIT_OFFSET_Y = 8;

    List<MoveOptionWidget> optionWidgets = new ArrayList<>();
    List<NextButtonWidget> buttonWidgets = new ArrayList<>();

    int currentSlot = -1;
    List<Pokemon> party;

    public TMMachineScreen(TMMachineScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(pPoseStack, x, y, 0, 0, imageHeight, imageHeight);
    }


    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float pPartialTick) {
        this.optionWidgets.clear();

        this.party = CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots();
        if (this.currentSlot == -1) {
            this.currentSlot = getDefaultSlot();
        }

        if (this.currentSlot != -1) {
            Pokemon pokemon = party.get(currentSlot);
            if (pokemon != null) {
                drawMainPokemon(
                        matrices,
                        width / 2.0F - (TILE_WIDTH / 2.0F) - 12,
                        height / 2.0F - (TILE_HEIGHT * 2F),
                        pokemon.getSpecies(),
                        pokemon.getLevel(),
                        pokemon.getAspects(),
                        pokemon.getDisplayName(),
                        pokemon.getGender(),
                        pokemon.getState()
                );

                drawMoveSet(
                        matrices,
                        width / 2.0F - 12,
                        height / 2.0F,
                        pokemon.getMoveSet(),
                        mouseX,
                        mouseY
                );

                drawButtons(
                        matrices,
                        width / 2.0F,
                        height / 2.0F - (TILE_HEIGHT * 1.5F),
                        mouseX,
                        mouseY
                );
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }



    private void drawMoveSet(
            PoseStack matrices,
            float originX,
            float originY,
            MoveSet moveSet,
            int mouseX,
            int mouseY
    ) {
        // i hate x and y more than z
        int column = 0;
        int row = 0;

        int xOffset = MoveOptionWidget.TILE_WIDTH;
        int yOffset = MoveOptionWidget.TILE_HEIGHT+12;

        for (Move move : moveSet) {
            float x = originX - (xOffset/2.0F) + (column * (xOffset + 12.0F));
            float y = originY + (row * yOffset);
            MoveOptionWidget widget = new MoveOptionWidget(move.getTemplate(), x, y);
            widget.hovered = widget.isHovered(mouseX, mouseY);
            widget.render(matrices);
            this.optionWidgets.add(widget);

            if (column == 1) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }
    }

    private void drawButtons(
            PoseStack matrices,
            float originX,
            float originY,
            int mouseX,
            int mouseY
    ) {
        NextButtonWidget back = new NextButtonWidget(true, originX-116, originY);
        NextButtonWidget forward = new NextButtonWidget(false, originX+104, originY);
        buttonWidgets.add(back);
        buttonWidgets.add(forward);

        for (NextButtonWidget widget : buttonWidgets) {
            widget.hovered = widget.isHovered(mouseX, mouseY);
            widget.render(matrices);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (MoveOptionWidget widget : optionWidgets) {
            if (widget.isHovered(mouseX, mouseY)) {
                CompoundTag nbtCompound = new CompoundTag();
                widget.moveTemplate.create().saveToNBT(nbtCompound);
                Messages.sendToServer(new TMMessage(nbtCompound));
                this.onClose();
                return true;
            }
        }
        // cursed
        // not even gonna bother caching the mons
        for (NextButtonWidget widget : buttonWidgets) {
            if (widget.isHovered(mouseX, mouseY)) {
                if (!widget.back) {
                    for (int i = currentSlot+1; i < party.size(); i++) {
                        Pokemon pokemon = party.get(i);
                        if (pokemon != null) {
                            this.currentSlot = i;
                            return true;
                        }
                    }
                } else if (this.currentSlot != 0) {
                    for (int i = currentSlot-1; i > -party.size()+currentSlot; i--) {
                        int m = i;
                        if (m < 0) {
                            m += party.size();
                        }
                        Pokemon pokemon = party.get(m);
                        if (pokemon != null) {
                            this.currentSlot = m;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void drawMainPokemon(
            PoseStack matrices,
            float x,
            float y,
            Species species,
            int level,
            Set<String> aspects,
            MutableComponent displayName,
            Gender gender,
            PokemonState state
    ) {
        // Underlay
        float portraitStartX = x + (PORTRAIT_OFFSET_X);
        s_blitk(
                matrices,
                battleInfoUnderlay,
                portraitStartX,
                y + PORTRAIT_OFFSET_Y,
                PORTRAIT_DIAMETER,
                PORTRAIT_DIAMETER
        );

        // Portrait
        enableScissor(
                Math.round(portraitStartX),
                Math.round(y + PORTRAIT_OFFSET_Y),
                Math.round(portraitStartX + PORTRAIT_DIAMETER),
                Math.round(y + PORTRAIT_DIAMETER + PORTRAIT_OFFSET_Y)
        );
        PoseStack matrixStack = new PoseStack();
        matrixStack.translate(
                portraitStartX + PORTRAIT_DIAMETER / 2.0,
                (double) y + PORTRAIT_OFFSET_Y - 5.0,
                0.0
        );
        matrixStack.pushPose();
        drawPortraitPokemon(
                species,
                aspects,
                matrixStack,
                18F,
                false,
                null
        );
        matrixStack.popPose();
        disableScissor();

        // Base
        s_blitk(
                matrices,
                battleInfoBase,
                x,
                y,
                TILE_WIDTH,
                TILE_HEIGHT
        );

        // Display Name
        float infoBoxX = x + PORTRAIT_DIAMETER + PORTRAIT_OFFSET_X + INFO_OFFSET_X;
        drawScaledText(
                matrices,
                DEFAULT_LARGE,
                displayName.withStyle(ChatFormatting.BOLD),
                infoBoxX,
                y + 7,
                1F,
                1F,
                Integer.MAX_VALUE,
                0x00FFFFFF,
                false,
                true
        );

        // Gender
        if (gender != Gender.GENDERLESS) {
            boolean isMale = gender == Gender.MALE;
            drawScaledText(
                    matrices,
                    DEFAULT_LARGE,
                    Component.literal(isMale ? "♂" : "♀").withStyle(ChatFormatting.BOLD),
                    infoBoxX + 53,
                    y + 7,
                    1F,
                    1F,
                    Integer.MAX_VALUE,
                    isMale ? 0x32CBFF : 0xFC5454,
                    false,
                    true
            );
        }

        // Lv. Text
        drawScaledText(
                matrices,
                DEFAULT_LARGE,
                lang("ui.lv").withStyle(ChatFormatting.BOLD),
                infoBoxX + 59,
                y + 7,
                1F,
                1F,
                Integer.MAX_VALUE,
                0x00FFFFFF,
                false,
                true
        );

        // Level Number
        drawScaledText(
                matrices,
                DEFAULT_LARGE,
                Component.literal(String.valueOf(level)).withStyle(ChatFormatting.BOLD),
                infoBoxX + 72,
                y + 7,
                1F,
                1F,
                Integer.MAX_VALUE,
                0x00FFFFFF,
                false,
                true
        );
    }


    private int getDefaultSlot() {
        int i = 0;
        for (Pokemon pokemon : this.party) {
            if (pokemon != null) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
