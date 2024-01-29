package com.cheating.cerberus.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.cheating.CheatingConfig;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.Util.Prayer;
import com.cheating.cerberus.CerberusPlugin;
import com.cheating.cerberus.domain.CerberusAttack;
import com.cheating.cerberus.util.Utility;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
public class PrayerOverlay extends Overlay
{
    private static final int TICK_PIXEL_SIZE = 60;
    private static final int BOX_WIDTH = 10;
    private static final int BOX_HEIGHT = 5;

    private final Client client;
    private final CerberusPlugin plugin;
    private final CheatingConfig config;

    private final Map<Widget, Integer> lastBoxBaseYMap = new HashMap<>();

    @Inject
    private PrayerOverlay(final Client client, final CerberusPlugin plugin, final CheatingConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPriority(OverlayPriority.HIGHEST);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        renderPrayer(graphics2D);

        return null;
    }

    private void renderPrayer(final Graphics2D graphics2D)
    {
        if (!config.guitarHeroModeCerberus() || plugin.getCerberus() == null)
        {
            return;
        }

        final int gameTick = plugin.getGameTick();

        final List<CerberusAttack> upcomingAttacks = plugin.getUpcomingAttacks();

        boolean first = true;

        for (final CerberusAttack attack : upcomingAttacks)
        {
            final int tick = attack.getTick() - gameTick;

            if (tick > config.guitarHeroTicksCerberus())
            {
                continue;
            }

            final Prayer prayer = attack.getAttack().getPrayer();

            renderDescendingBoxes(graphics2D, prayer, tick);

            if (first)
            {
                renderPrayerWidget(graphics2D, prayer, tick);

                first = false;
            }
        }
    }

    private void renderDescendingBoxes(final Graphics2D graphics2D, final Prayer prayer, final int tick)
    {
        final Widget prayerWidget = client.getWidget(prayer.getWidgetInfo().getPackedId());

        if (prayerWidget == null || prayerWidget.isHidden())
        {
            return;
        }

        final long lastTick = plugin.getLastTick();

        int baseX = (int) prayerWidget.getBounds().getX();
        baseX += prayerWidget.getBounds().getWidth() / 2;
        baseX -= BOX_WIDTH / 2;

        int baseY = (int) prayerWidget.getBounds().getY() - tick * TICK_PIXEL_SIZE - BOX_HEIGHT;
        baseY += TICK_PIXEL_SIZE - ((lastTick + 600 - System.currentTimeMillis()) / 600.0 * TICK_PIXEL_SIZE);

        if (baseY > (int) prayerWidget.getBounds().getY() - BOX_HEIGHT)
        {
            return;
        }

        if (System.currentTimeMillis() - lastTick > 600)
        {
            lastBoxBaseYMap.put(prayerWidget, baseY);
        }
        else if (lastBoxBaseYMap.containsKey(prayerWidget))
        {
            if (lastBoxBaseYMap.get(prayerWidget) >= baseY && lastBoxBaseYMap.get(prayerWidget) < (int) (prayerWidget.getBounds().getY() - BOX_HEIGHT))
            {
                baseY = lastBoxBaseYMap.get(prayerWidget) + 1;
                lastBoxBaseYMap.put(prayerWidget, baseY);
            }
            else
            {
                lastBoxBaseYMap.remove(prayerWidget);
            }
        }

        final Rectangle boxRectangle = new Rectangle(BOX_WIDTH, BOX_HEIGHT);
        boxRectangle.translate(baseX, baseY);

        GUIOverlayUtil.renderFilledPolygon(graphics2D, boxRectangle, Color.ORANGE);
    }

    private void renderPrayerWidget(final Graphics2D graphics2D, final Prayer prayer, final int tick)
    {
        final Rectangle rectangle = GUIOverlayUtil.renderPrayerOverlay(graphics2D, client, prayer, Utility.getColorFromPrayer(prayer.getApiPrayer()));

        if (rectangle == null)
        {
            return;
        }

        final String text = String.valueOf(tick);

        final int fontSize = 16;
        final int fontStyle = Font.BOLD;
        final Color fontColor = tick == 1 ? Color.RED : Color.WHITE;

        final int x = (int) (rectangle.getX() + rectangle.getWidth() / 2);
        final int y = (int) (rectangle.getY() + rectangle.getHeight() / 2);

        final Point point = new Point(x - 3, y + 6);

        GUIOverlayUtil.renderTextLocation(graphics2D, text, fontSize, fontStyle, fontColor, point, true, 0);
    }
}
