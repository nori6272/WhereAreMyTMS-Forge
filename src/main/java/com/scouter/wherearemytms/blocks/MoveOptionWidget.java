package com.scouter.wherearemytms.blocks;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.client.gui.MoveCategoryIcon;
import com.cobblemon.mod.common.client.gui.TypeIcon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.cobblemon.mod.common.api.gui.GuiUtilsKt.blitk;
import static com.cobblemon.mod.common.client.render.RenderHelperKt.drawScaledText;
import static com.scouter.wherearemytms.WhereAreMyTMs.prefix;
import static com.scouter.wherearemytms.blocks.RenderUtils.s_blitk;
import static com.scouter.wherearemytms.blocks.TMMachineScreen.DEFAULT_LARGE;

public class MoveOptionWidget {
    public static final ResourceLocation moveTexture = prefix("textures/gui/battle_move.png");
    public static final ResourceLocation moveOverlayTexture = prefix("textures/gui/battle_move_overlay.png");

    public static final int TILE_WIDTH = 92;
    public static final int TILE_HEIGHT = 24;

    MoveTemplate moveTemplate;
    float x;
    float y;
    boolean hovered;

    public MoveOptionWidget(MoveTemplate moveTemplate, float x, float y) {
        this.moveTemplate = moveTemplate;
        this.x = x - TILE_WIDTH/2.0F;
        this.y = y - TILE_HEIGHT/2.0F;
    }

    public void render(PoseStack matrices) {
        var disabled = moveTemplate.getPp() <= 0;
        var opacity = disabled ? 0.5F : 1F;
        var rgb = new Color(moveTemplate.getElementalType().getHue());

        // Base
        blitk(
                matrices,
                moveTexture,
                x,
                y,
                TILE_HEIGHT,
                TILE_WIDTH,
                0,
                hovered ? TILE_HEIGHT : 0,
                TILE_WIDTH,
                TILE_HEIGHT*2,
                0,
                rgb.getRed()/255F,
                rgb.getGreen()/255F,
                rgb.getBlue()/255F,
                opacity,
                true,
                1F
        );

        // Overlay
        s_blitk(
                matrices,
                moveOverlayTexture,
                this.x,
                this.y,
                TILE_WIDTH,
                TILE_HEIGHT,
                0,
                new Color(1.0F, 1.0F, 1.0F),
                opacity
        );

        // Type Icon
        new TypeIcon(
                x - 9,
                y + 2,
                moveTemplate.getElementalType(),
                null,
                false,
                false,
                15F,
                7.5F,
                opacity
        ).render(matrices);

        // Move Category
        new MoveCategoryIcon(
                x + 48,
                y + 14.5,
                moveTemplate.getDamageCategory(),
                opacity
        ).render(matrices);

        // Display Name
        drawScaledText(
                matrices,
                DEFAULT_LARGE,
                moveTemplate.getDisplayName().withStyle(ChatFormatting.BOLD),
                x + 17,
                y + 2,
                1F,
                opacity,
                Integer.MAX_VALUE,
                0x00FFFFFF,
                false,
                true
        );
    }

    boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + TILE_WIDTH && mouseY >= y && mouseY <= y + TILE_HEIGHT;
    }
}
