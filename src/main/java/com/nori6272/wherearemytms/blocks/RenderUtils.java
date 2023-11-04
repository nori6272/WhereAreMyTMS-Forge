package com.nori6272.wherearemytms.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.cobblemon.mod.common.api.gui.GuiUtilsKt.blitk;

public class RenderUtils {
    public static void s_blitk(PoseStack matrices, ResourceLocation identifier, float x, float y, int width, int height) {
        blitk(
                matrices,
                identifier,
                x,
                y,
                height,
                width,
                0,
                0,
                width,
                height,
                0,
                1,
                1,
                1,
                1F,
                true,
                1F
        );
    }

    public static void s_blitk(PoseStack matrices, ResourceLocation identifier, float x, float y, int width, int height, int vOffset, Color rgb, float opacity) {
        blitk(
                matrices,
                identifier,
                x,
                y,
                height,
                width,
                0,
                vOffset,
                width,
                height,
                0,
                rgb.getRed()/255F,
                rgb.getGreen()/255F,
                rgb.getBlue()/255F,
                opacity,
                true,
                1F
        );
    }
}
