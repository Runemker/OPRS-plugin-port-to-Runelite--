package com.cheating.godwars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.cheating.CheatingConfig;
import com.cheating.Util.AttackStyle;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.Util.Prayer;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import static com.cheating.godwars.NPCContainer.BossMonsters.GENERAL_GRAARDOR;

@Singleton
@Slf4j
public class TimersOverlay extends Overlay
{
    private static final int TICK_PIXEL_SIZE = 60;
    private static final int BOX_WIDTH = 10;
    private static final int BOX_HEIGHT = 5;

    private final CheatingConfig config;
    private final Client client;

    @Inject
    private GodWarsPlugin godWarsPlugin;

    @Inject
    TimersOverlay(final CheatingConfig config, final Client client)
    {
        this.config = config;
        this.client = client;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGHEST);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Maps each tick to a set of attacks and their priorities
        TreeMap<Integer, TreeMap<Integer, Prayer>> tickAttackMap = new TreeMap<>();

        for (NPCContainer npc : godWarsPlugin.getNpcContainers())
        {
            if (npc.getNpc() == null)
            {
                continue;
            }

            int ticksLeft = npc.getTicksUntilAttack();
            final List<WorldPoint> hitSquares = GUIOverlayUtil.getHitSquares(npc.getNpc().getWorldLocation(), npc.getNpcSize(), 1, false);
            AttackStyle attackStyle = npc.getAttackStyle();

            if (config.showHitSquares() && attackStyle.getName().equals("Melee"))
            {
                for (WorldPoint p : hitSquares)
                {
                    GUIOverlayUtil.drawTiles(graphics, client, p, client.getLocalPlayer().getWorldLocation(), attackStyle.getColor(), 0, 0, 50);
                }
            }

            if (ticksLeft <= 0)
            {
                continue;
            }

            if (config.ignoreNonAttacking() && npc.getNpcInteracting() != client.getLocalPlayer() && npc.getMonsterType() != GENERAL_GRAARDOR)
            {
                continue;
            }

            // If you are not tank at bandos, prayer range instead of melee on graardor attack
            if (npc.getMonsterType() == GENERAL_GRAARDOR && npc.getNpcInteracting() != client.getLocalPlayer())
            {
                attackStyle = AttackStyle.RANGE;
            }

            final String ticksLeftStr = String.valueOf(ticksLeft);
            final int font = config.fontStyleGWD().getFont();
            final boolean shadows = config.shadowsGWD();
            Color color = (ticksLeft <= 1 ? Color.WHITE : attackStyle.getColor());

            if (!config.changeTickColor())
            {
                color = attackStyle.getColor();
            }

            final Point canvasPoint = npc.getNpc().getCanvasTextLocation(graphics, ticksLeftStr, 0);
            GUIOverlayUtil.renderTextLocation(graphics, ticksLeftStr, config.textSizeGWD(), font, color, canvasPoint, shadows, 0);

            if (config.showPrayerWidgetHelper() && attackStyle.getPrayer() != null)
            {
                Rectangle bounds = GUIOverlayUtil.renderPrayerOverlay(graphics, client, attackStyle.getPrayer(), color);

                if (bounds != null)
                {
                    renderTextLocation(graphics, ticksLeftStr, 16, config.fontStyleGWD().getFont(), color, centerPoint(bounds), shadows);
                }
            }

            if (config.guitarHeroMode())
            {
                TreeMap<Integer, Prayer> attacks = tickAttackMap.computeIfAbsent(ticksLeft, (k) -> new TreeMap<>());

                int priority = 999;
                switch (npc.getMonsterType())
                {
                    case SERGEANT_STRONGSTACK:
                        priority = 3;
                        break;
                    case SERGEANT_STEELWILL:
                        priority = 1;
                        break;
                    case SERGEANT_GRIMSPIKE:
                        priority = 2;
                        break;
                    case GENERAL_GRAARDOR:
                        priority = 0;
                        break;
                    default:
                        break;
                }

                attacks.putIfAbsent(priority, attackStyle.getPrayer());
            }
        }

        if (!tickAttackMap.isEmpty())
        {
            for (Map.Entry<Integer, TreeMap<Integer, Prayer>> tickEntry : tickAttackMap.entrySet())
            {
                Map.Entry<Integer, Prayer> attackEntry = tickEntry.getValue().firstEntry();
                Prayer prayer = attackEntry.getValue();
                if (prayer != null)
                {
                    renderDescendingBoxes(graphics, prayer, tickEntry.getKey());
                }
            }
        }

        return null;
    }

    private void renderDescendingBoxes(Graphics2D graphics, Prayer prayer, int tick)
    {
        final Color color = tick == 1 ? Color.RED : Color.ORANGE;
        final Widget prayerWidget = client.getWidget(prayer.getWidgetInfo());

        int baseX = (int) prayerWidget.getBounds().getX();
        baseX += prayerWidget.getBounds().getWidth() / 2;
        baseX -= BOX_WIDTH / 2;

        int baseY = (int) prayerWidget.getBounds().getY() - tick * TICK_PIXEL_SIZE - BOX_HEIGHT;
        baseY += TICK_PIXEL_SIZE - ((godWarsPlugin.getLastTickTime() + 600 - System.currentTimeMillis()) / 600.0 * TICK_PIXEL_SIZE);

        final Rectangle boxRectangle = new Rectangle(BOX_WIDTH, BOX_HEIGHT);
        boxRectangle.translate(baseX, baseY);

        GUIOverlayUtil.renderFilledPolygon(graphics, boxRectangle, color);
    }

    private void renderTextLocation(Graphics2D graphics, String txtString, int fontSize, int fontStyle, Color fontColor, Point canvasPoint, boolean shadows)
    {
        graphics.setFont(new Font("Arial", fontStyle, fontSize));
        if (canvasPoint != null)
        {
            final Point canvasCenterPoint = new Point(
                    canvasPoint.getX() - 3,
                    canvasPoint.getY() + 6);
            final Point canvasCenterPoint_shadow = new Point(
                    canvasPoint.getX() - 2,
                    canvasPoint.getY() + 7);
            if (shadows)
            {
                GUIOverlayUtil.renderTextLocation(graphics, canvasCenterPoint_shadow, txtString, Color.BLACK);
            }
            GUIOverlayUtil.renderTextLocation(graphics, canvasCenterPoint, txtString, fontColor);
        }
    }

    private Point centerPoint(Rectangle rect)
    {
        int x = (int) (rect.getX() + rect.getWidth() / 2);
        int y = (int) (rect.getY() + rect.getHeight() / 2);
        return new Point(x, y);
    }
}
