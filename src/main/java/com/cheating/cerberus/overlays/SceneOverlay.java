package com.cheating.cerberus.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.cheating.CheatingConfig;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.cerberus.CerberusPlugin;
import com.cheating.cerberus.domain.Arena;
import com.cheating.cerberus.domain.Cerberus;
import com.cheating.cerberus.domain.Ghost;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
public final class SceneOverlay extends Overlay
{
    private static final int MAX_RENDER_DISTANCE = 32;

    private static final int GHOST_TIME_WARNING = 2;
    private static final int GHOST_TIME_FONT_SIZE = 12;

    private static final int GHOST_TILE_OUTLINE_WIDTH = 2;
    private static final int GHOST_TILE_OUTLINE_APLHA = 255;
    private static final int GHOST_TILE_FILL_APLHA = 20;

    private static final int GHOST_YELL_TICK_WINDOW = 17;

    private final Client client;
    private final CerberusPlugin plugin;
    private final CheatingConfig config;

    private Cerberus cerberus;

    @Inject
    SceneOverlay(final Client client, final CerberusPlugin plugin, final CheatingConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPriority(OverlayPriority.HIGHEST);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        cerberus = plugin.getCerberus();

        if (cerberus == null)
        {
            return null;
        }

        renderGhostTiles(graphics2D);

        return null;
    }

    private void renderGhostTiles(final Graphics2D graphics2D)
    {
        if (!config.drawGhostTiles()
                || cerberus.getLastGhostYellTick() == 0
                || (plugin.getGameTick() - cerberus.getLastGhostYellTick()) >= GHOST_YELL_TICK_WINDOW)
        {
            return;
        }

        final Player player = client.getLocalPlayer();

        if (player == null)
        {
            return;
        }

        final WorldPoint playerTile = player.getWorldLocation();

        final Arena arena = Arena.getArena(playerTile);

        if (arena == null)
        {
            return;
        }

        final int numberOfTiles = 3;

        for (int i = 0; i < numberOfTiles; ++i)
        {
            final WorldPoint ghostTile = arena.getGhostTile(i);

            if (ghostTile == null || ghostTile.distanceTo(playerTile) >= MAX_RENDER_DISTANCE)
            {
                return;
            }

            renderGhostTileOutline(graphics2D, ghostTile, playerTile, i);

            renderGhostTileAttackTime(graphics2D, ghostTile, i);
        }
    }

    private void renderGhostTileOutline(final Graphics2D graphics2D, final WorldPoint ghostTile, final WorldPoint playerPoint, final int tileIndex)
    {
        Color ghostTileFillColor = Color.WHITE;

        final List<NPC> ghosts = plugin.getGhosts();

        if (tileIndex < ghosts.size())
        {
            final Ghost ghost = Ghost.fromNPC(ghosts.get(tileIndex));

            if (ghost != null)
            {
                ghostTileFillColor = ghost.getColor();
            }
        }

        GUIOverlayUtil.drawTiles(graphics2D, client, ghostTile, playerPoint, ghostTileFillColor, GHOST_TILE_OUTLINE_WIDTH, GHOST_TILE_OUTLINE_APLHA, GHOST_TILE_FILL_APLHA);
    }

    private void renderGhostTileAttackTime(final Graphics2D graphics2D, final WorldPoint ghostTile, final int tileIndex)
    {
        final LocalPoint localPoint = LocalPoint.fromWorld(client, ghostTile);

        if (localPoint == null)
        {
            return;
        }

        final Polygon polygon = Perspective.getCanvasTilePoly(client, localPoint);

        if (polygon == null)
        {
            return;
        }

        final long time = System.currentTimeMillis();

        final int tick = plugin.getGameTick();
        final int lastGhostsTick = cerberus.getLastGhostYellTick();

        //Update and get the time when the ghosts were summoned
        final long lastGhostsTime = Math.min(cerberus.getLastGhostYellTime(), time - (600 * (tick - lastGhostsTick)));
        cerberus.setLastGhostYellTime(lastGhostsTime);

        final double timeUntilGhostAttack = Math.max((double) ((lastGhostsTime + 600 * (13 + tileIndex * 2)) - System.currentTimeMillis()) / 1000, 0);

        final Color textColor = timeUntilGhostAttack <= GHOST_TIME_WARNING ? Color.RED : Color.WHITE;

        final String timeUntilAttack = String.format("%.1f", timeUntilGhostAttack);

        graphics2D.setFont(new Font("Arial", Font.PLAIN, GHOST_TIME_FONT_SIZE));

        final FontMetrics metrics = graphics2D.getFontMetrics();
        final Point centerPoint = getRectangleCenterPoint(polygon.getBounds());

        final Point newPoint = new Point(centerPoint.getX() - (metrics.stringWidth(timeUntilAttack) / 2), centerPoint.getY() + (metrics.getHeight() / 2));

        GUIOverlayUtil.renderTextLocation(graphics2D, timeUntilAttack, 12,
                Font.PLAIN, textColor, newPoint, true, 0);
    }

    private Point getRectangleCenterPoint(final Rectangle rect)
    {
        final int x = (int) (rect.getX() + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + rect.getHeight() / 2);

        return new Point(x, y);
    }
}