package com.github.yuitosaito.enigma.gui;

import com.github.yuitosaito.enigma.EnigmaMOD;
import com.github.yuitosaito.enigma.EnigmaModConfigCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnigmaModGuiIngame extends Gui {
    protected final Minecraft mc;

    public EnigmaModGuiIngame(Minecraft p_i1036_1_) {
        this.mc = p_i1036_1_;
    }

    public void renderGameOverlay() {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        this.mc.mcProfiler.startSection("enigma");
        GL11.glPushMatrix();
        String s = "Enigma: " + (EnigmaMOD.mode == 0 ? "Disable" : "") + (EnigmaMOD.mode == 1 ? "Enable" : "") + (EnigmaMOD.mode == 2 ? "Listen Mode" : "");
        int xGap = EnigmaModConfigCore.xGap;
        int yGap = EnigmaModConfigCore.yGap;
        switch (EnigmaModConfigCore.position) {
            case 0:
                fontrenderer.drawStringWithShadow(s, 2 + xGap, 2 + yGap, 16777215);
                break;
            case 1:
                fontrenderer.drawStringWithShadow(s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, 2 + yGap, 16777215);
                break;
            case 2:
                fontrenderer.drawStringWithShadow(s, k - fontrenderer.getStringWidth(s) - 2 + xGap, 2 + yGap, 16777215);
                break;
            case 3:
                fontrenderer.drawStringWithShadow(s, 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 4:
                fontrenderer.drawStringWithShadow(s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 5:
                fontrenderer.drawStringWithShadow(s, k - fontrenderer.getStringWidth(s) - 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 6:
                fontrenderer.drawStringWithShadow(s, 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
            case 7:
                fontrenderer.drawStringWithShadow(s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
            case 8:
                fontrenderer.drawStringWithShadow(s, k - fontrenderer.getStringWidth(s) - 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
        }
        GL11.glPopMatrix();
        this.mc.mcProfiler.endSection();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}