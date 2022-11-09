package com.cheating.gaunlet.Overlay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.gaunlet.GauntletPlugin;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.Util.Prayer;
import com.cheating.Util.PrayerHighlightMode;
import com.cheating.gaunlet.entity.Hunllef;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Point;

import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
public class OverlayPrayerWidget extends Overlay
{
    private final Client client;
    private final CheatingPlugin plugin;
    private final CheatingConfig config;

    @Inject
    private GauntletPlugin gauntletPlugin;

    @Inject
    OverlayPrayerWidget(final Client client, final CheatingPlugin plugin, final CheatingConfig config)
    {
        super(plugin);

        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        determineLayer();
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        final PrayerHighlightMode prayerHighlightMode = config.prayerOverlay();

        if (prayerHighlightMode == PrayerHighlightMode.NONE || prayerHighlightMode == PrayerHighlightMode.BOX)
        {
            return null;
        }

        final Hunllef hunllef = gauntletPlugin.getHunllef();

        if (hunllef == null || !gauntletPlugin.isInHunllef())
        {
            return null;
        }

        final NPC npc = hunllef.getNpc();

        if (npc == null || npc.isDead())
        {
            return null;
        }

        // Overlay outline on the prayer widget

        final Hunllef.AttackPhase phase = hunllef.getAttackPhase();

        final Prayer prayer = phase.getPrayer();

        final Color phaseColor = phase.getColor();

        final Rectangle rectangle = GUIOverlayUtil.renderPrayerOverlay(graphics2D, client, prayer, phaseColor);

        if (rectangle == null)
        {
            return null;
        }

        // Overlay tick count on the prayer widget

        final int ticksUntilAttack = hunllef.getTicksUntilNextAttack();

        final String text = String.valueOf(ticksUntilAttack);

        final int fontSize = 16;
        final int fontStyle = Font.BOLD;
        final Color fontColor = ticksUntilAttack == 1 ? Color.WHITE : phaseColor;

        final int x = (int) (rectangle.getX() + rectangle.getWidth() / 2);
        final int y = (int) (rectangle.getY() + rectangle.getHeight() / 2);

        final Point point = new Point(x, y);

        final Point canvasPoint = new Point(point.getX() - 3, point.getY() + 6);

        GUIOverlayUtil.renderTextLocation(graphics2D, text, fontSize, fontStyle, fontColor, canvasPoint, true, 0);

        return null;
    }

    public void determineLayer()
    {
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }
}
