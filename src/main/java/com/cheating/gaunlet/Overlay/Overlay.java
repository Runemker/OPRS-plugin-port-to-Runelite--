package com.cheating.gaunlet.Overlay;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import net.runelite.client.plugins.Plugin;

public abstract class Overlay extends net.runelite.client.ui.overlay.Overlay
{
    Overlay(final Plugin plugin)
    {
        super(plugin);
    }

    public abstract void determineLayer();

    static void drawOutlineAndFill(final Graphics2D graphics2D, final Color outlineColor, final Color fillColor, final float strokeWidth, final Shape shape)
    {
        final Color originalColor = graphics2D.getColor();
        final Stroke originalStroke = graphics2D.getStroke();

        graphics2D.setStroke(new BasicStroke(strokeWidth));
        graphics2D.setColor(outlineColor);
        graphics2D.draw(shape);

        graphics2D.setColor(fillColor);
        graphics2D.fill(shape);

        graphics2D.setColor(originalColor);
        graphics2D.setStroke(originalStroke);
    }
}
