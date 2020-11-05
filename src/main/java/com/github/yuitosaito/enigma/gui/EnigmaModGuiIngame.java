package com.github.yuitosaito.enigma.gui;

import com.github.yuitosaito.enigma.EnigmaMOD;
import com.github.yuitosaito.enigma.EnigmaModConfigCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnigmaModGuiIngame extends Gui {
    private final Minecraft mc;

    public EnigmaModGuiIngame(Minecraft mc) {
        this.mc = mc;
    }

    public void renderGameOverlay() {
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = this.mc.fontRenderer;

        GL11.glColor4f(1F, 1F, 1F, 1F);

        //GL11.glBindTexture(GL11.GL_TEXTURE_2D,GL11.GL_3D_COLOR_TEXTURE);
        String s = StatCollector.translateToLocal("gui.enigma.ingame.enigma")
                .replace("%1",
                        ((EnigmaMOD.mode == 0 ? StatCollector.translateToLocal("gui.enigma.ingame.disable") : "")
                        + (EnigmaMOD.mode == 1 ? StatCollector.translateToLocal("gui.enigma.ingame.enable") : "")
                        + (EnigmaMOD.mode == 2 ? StatCollector.translateToLocal("gui.enigma.ingame.enablelisten") : "")));
        int xGap = EnigmaModConfigCore.xGap;
        int yGap = EnigmaModConfigCore.yGap;
        switch (EnigmaModConfigCore.position) {
            case 0:
                drawString(mc.fontRenderer,s, 2 + xGap, 2 + yGap, 16777215);
                break;
            case 1:
                drawString(mc.fontRenderer,s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, 2 + yGap, 16777215);
                break;
            case 2:
                drawString(mc.fontRenderer,s, k - fontrenderer.getStringWidth(s) - 2 + xGap, 2 + yGap, 16777215);
                break;
            case 3:
                drawString(mc.fontRenderer,s, 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 4:
                drawString(mc.fontRenderer,s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 5:
                drawString(mc.fontRenderer,s, k - fontrenderer.getStringWidth(s) - 2 + xGap, l / 2 - fontrenderer.FONT_HEIGHT / 2 + yGap, 16777215);
                break;
            case 6:
                drawString(mc.fontRenderer,s, 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
            case 7:
                drawString(mc.fontRenderer,s, k / 2 - fontrenderer.getStringWidth(s) / 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
            case 8:
                drawString(mc.fontRenderer,s, k - fontrenderer.getStringWidth(s) - 2 + xGap, l - fontrenderer.FONT_HEIGHT - 2 + yGap, 16777215);
                break;
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
    }
}