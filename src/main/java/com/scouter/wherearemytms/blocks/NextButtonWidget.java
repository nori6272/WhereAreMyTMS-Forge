package com.scouter.wherearemytms.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

import static com.cobblemon.mod.common.api.gui.GuiUtilsKt.blitk;
import static com.scouter.wherearemytms.WhereAreMyTMs.prefix;

public class NextButtonWidget {
    public static final ResourceLocation backTexture = prefix("textures/gui/battle_back.png");
    public static final ResourceLocation forwardTexture = prefix("textures/gui/battle_forward.png");

    public static final int TILE_WIDTH = 58;
    public static final int TILE_HEIGHT = 34;

    public boolean back;
    float x;
    float y;
    boolean hovered;

    public NextButtonWidget(boolean back, float x, float y) {
        this.back = back;
        this.x = x - TILE_WIDTH/2.0F;
        this.y = y - TILE_HEIGHT/2.0F;
    }

    public void render(PoseStack matrices) {
        blitk(
                matrices,
                back ? backTexture : forwardTexture,
                x,
                y,
                TILE_HEIGHT,
                TILE_WIDTH,
                0,
                hovered ? TILE_HEIGHT : 0,
                TILE_WIDTH,
                TILE_HEIGHT*2,
                0,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                true,
                1F
        );
    }

    boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + TILE_WIDTH && mouseY >= y && mouseY <= y + TILE_HEIGHT;
    }
}
