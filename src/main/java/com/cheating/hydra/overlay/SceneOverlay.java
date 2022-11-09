package com.cheating.hydra.overlay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.hydra.HydraPlugin;
import com.cheating.hydra.entity.Hydra;
import com.cheating.hydra.entity.HydraPhase;
import net.runelite.api.Client;
import net.runelite.api.Deque;
import net.runelite.api.GraphicsObject;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import static net.runelite.api.Perspective.getCanvasTileAreaPoly;
import net.runelite.api.Point;
import net.runelite.api.Projectile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

@Singleton
public class SceneOverlay extends Overlay
{
    private static final int LIGHTNING_ID = 1666;

    private static final Area POISON_AREA = new Area();

    private static final int POISON_AOE_AREA_SIZE = 3;

    private static final int HYDRA_HULL_OUTLINE_STROKE_SIZE = 1;

    private final Client client;
    private final CheatingPlugin plugin;
    private final CheatingConfig config;
    private final ModelOutlineRenderer modelOutlineRenderer;

    private Hydra hydra;

    @Inject
    private HydraPlugin hydraPlugin;

    @Inject
    public SceneOverlay(final Client client, final CheatingPlugin plugin, final CheatingConfig config,
                        final ModelOutlineRenderer modelOutlineRenderer)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.modelOutlineRenderer = modelOutlineRenderer;

        setPriority(OverlayPriority.HIGH);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        hydra = hydraPlugin.getHydra();

        if (hydra == null)
        {
            return null;
        }

        renderHpUntilPhaseChange(graphics2D);
        renderHydraImmunityOutline();
        renderPoisonProjectileAreaTiles(graphics2D);
        renderLightning(graphics2D);
        renderFountainOutline(graphics2D);
        renderFountainTicks(graphics2D);

        return null;
    }

    private void renderPoisonProjectileAreaTiles(final Graphics2D graphics2D)
    {
        final Map<LocalPoint, Projectile> poisonProjectiles = hydraPlugin.getPoisonProjectiles();

        if (!config.poisonOutline() || poisonProjectiles.isEmpty())
        {
            return;
        }

        POISON_AREA.reset();

        for (final Map.Entry<LocalPoint, Projectile> entry : poisonProjectiles.entrySet())
        {
            if (entry.getValue().getEndCycle() < client.getGameCycle())
            {
                continue;
            }

            final LocalPoint localPoint = entry.getKey();

            final Polygon polygon = getCanvasTileAreaPoly(client, localPoint, POISON_AOE_AREA_SIZE);

            if (polygon != null)
            {
                POISON_AREA.add(new Area(polygon));
            }
        }

        drawOutlineAndFill(graphics2D, config.poisonOutlineColor(), config.poisonFillColor(), config.poisonStroke(), POISON_AREA);
    }

    private void renderLightning(final Graphics2D graphics2D)
    {
        final Deque<GraphicsObject> graphicsObjects = client.getGraphicsObjects();

        if (!config.lightningOutline() || hydra.getPhase() != HydraPhase.LIGHTNING)
        {
            return;
        }

        for (final GraphicsObject graphicsObject : graphicsObjects)
        {
            if (graphicsObject.getId() != LIGHTNING_ID)
            {
                continue;
            }

            final LocalPoint localPoint = graphicsObject.getLocation();

            if (localPoint == null)
            {
                return;
            }

            final Polygon polygon = Perspective.getCanvasTilePoly(client, localPoint);

            if (polygon == null)
            {
                return;
            }

            drawOutlineAndFill(graphics2D, config.lightningOutlineColor(), config.lightningFillColor(),
                    config.lightningStroke(), polygon);
        }
    }

    private void renderHydraImmunityOutline()
    {
        final NPC npc = hydra.getNpc();

        if (!config.hydraImmunityOutline() || !hydra.isImmunity() || npc == null || npc.isDead())
        {
            return;
        }

        final WorldPoint fountainWorldPoint = hydra.getPhase().getFountainWorldPoint();

        if (fountainWorldPoint != null)
        {
            final Collection<WorldPoint> fountainWorldPoints = WorldPoint.toLocalInstance(client, fountainWorldPoint);

            if (fountainWorldPoints.size() == 1)
            {
                WorldPoint worldPoint = null;

                for (final WorldPoint wp : fountainWorldPoints)
                {
                    worldPoint = wp;
                }

                final LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);

                if (localPoint != null)
                {
                    final Polygon polygon = getCanvasTileAreaPoly(client, localPoint, 3);

                    if (polygon != null)
                    {
                        int stroke = HYDRA_HULL_OUTLINE_STROKE_SIZE;

                        if (npc.getWorldArea().intersectsWith(new WorldArea(worldPoint, 1, 1)))
                        {
                            stroke++;
                        }

                        modelOutlineRenderer.drawOutline(npc, stroke, hydra.getPhase().getPhaseColor(), 0);
                        return;
                    }
                }
            }

        }

        modelOutlineRenderer.drawOutline(npc, HYDRA_HULL_OUTLINE_STROKE_SIZE, hydra.getPhase().getPhaseColor(), 0);
    }

    private void renderFountainOutline(final Graphics2D graphics2D)
    {
        final NPC npc = hydra.getNpc();
        final WorldPoint fountainWorldPoint = hydra.getPhase().getFountainWorldPoint();

        if (!config.fountainOutline() || !hydra.isImmunity() || fountainWorldPoint == null || npc == null || npc.isDead())
        {
            return;
        }

        final Collection<WorldPoint> fountainWorldPoints = WorldPoint.toLocalInstance(client, fountainWorldPoint);

        if (fountainWorldPoints.size() != 1)
        {
            return;
        }

        WorldPoint worldPoint = null;

        for (final WorldPoint wp : fountainWorldPoints)
        {
            worldPoint = wp;
        }

        final LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);

        if (localPoint == null)
        {
            return;
        }

        final Polygon polygon = getCanvasTileAreaPoly(client, localPoint, 3);

        if (polygon == null)
        {
            return;
        }

        Color color = hydra.getPhase().getFountainColor();

        if (!npc.getWorldArea().intersectsWith(new WorldArea(worldPoint, 1, 1)))
        {
            color = color.darker();
        }

        drawOutlineAndFill(graphics2D, color, new Color(color.getRed(), color.getGreen(), color.getBlue(), 30), 1, polygon);
    }

    private void renderFountainTicks(final Graphics2D graphics2D)
    {

        if (!config.fountainTicks())
        {
            return;
        }

        final Collection<WorldPoint> fountainWorldPoints = WorldPoint.toLocalInstance(client, HydraPhase.POISON.getFountainWorldPoint());
        fountainWorldPoints.addAll(WorldPoint.toLocalInstance(client, HydraPhase.LIGHTNING.getFountainWorldPoint()));
        fountainWorldPoints.addAll(WorldPoint.toLocalInstance(client, HydraPhase.FLAME.getFountainWorldPoint()));

        if (fountainWorldPoints.isEmpty())
        {
            return;
        }

        WorldPoint worldPoint;

        for (final WorldPoint wp : fountainWorldPoints)
        {
            worldPoint = wp;
            final LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);

            if (localPoint == null)
            {
                return;
            }


            final String text = String.valueOf(hydraPlugin.getFountainTicks());
            Point timeLoc = Perspective.getCanvasTextLocation(client, graphics2D, localPoint, text, graphics2D.getFontMetrics().getHeight());


            GUIOverlayUtil.renderTextLocation(
                    graphics2D,
                    text,
                    config.fountainTicksFontSize(),
                    config.fountainTicksFontStyle().getFont(),
                    config.fountainTicksFontColor(),
                    timeLoc,
                    config.fountainTicksFontShadow(),
                    config.fountainTicksFontZOffset() * -1
            );
        }


    }

    private void renderHpUntilPhaseChange(final Graphics2D graphics2D)
    {
        final NPC npc = hydra.getNpc();

        if (!config.showHpUntilPhaseChange() || npc == null || npc.isDead())
        {
            return;
        }

        final int hpUntilPhaseChange = hydra.getHpUntilPhaseChange();

        if (hpUntilPhaseChange == 0)
        {
            return;
        }

        final String text = String.valueOf(hpUntilPhaseChange);

        final Point point = npc.getCanvasTextLocation(graphics2D, text, 0);

        if (point == null)
        {
            return;
        }

        GUIOverlayUtil.renderTextLocation(
                graphics2D,
                text,
                config.fontSizeHydra(),
                config.fontStyleHydra().getFont(),
                config.fontColorHydra(),
                point,
                config.fontShadowHydra(),
                config.fontZOffsetHydra() * -1
        );
    }

    private static void drawOutlineAndFill(final Graphics2D graphics2D, final Color outlineColor, final Color fillColor, final float strokeWidth, final Shape shape)
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
