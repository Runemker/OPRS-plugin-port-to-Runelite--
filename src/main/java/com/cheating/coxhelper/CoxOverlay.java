package com.cheating.coxhelper;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.AttackStyle;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.coxhelper.FourToOne.ColorTileMarker;
import com.google.common.collect.ImmutableSet;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

@Singleton
public class CoxOverlay extends Overlay
{
    private static final Set<Integer> GAP = ImmutableSet.of(34, 33, 26, 25, 18, 17, 10, 9, 2, 1);
    private final Client client;
    private final CheatingConfig config;
    private final Olm olm;
    private final ModelOutlineRenderer outliner;

    @Inject
    private CoxPlugin coxPlugin;

    @Inject
    private CoxOverlay(final Client client, final CheatingConfig config, final Olm olm, ModelOutlineRenderer outliner)
    {
        this.client = client;
        this.config = config;
        this.olm = olm;
        this.outliner = outliner;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        for (WorldPoint point : this.olm.getHealPools())
        {
            this.drawTile(graphics, point, this.config.tpColor(), 2, 150);
        }

        for (WorldPoint point : this.olm.getPortals())
        {
            this.client.setHintArrow(point);
            this.drawTile(graphics, point, this.config.tpColor(), 2, 150);
        }

        if (coxPlugin.inRaid())
        {
            for (NPCContainer npcs : coxPlugin.getNpcContainers().values())
            {
                Color color;
                List<WorldPoint> hitSquares;
                int ticksLeft;
                switch (npcs.getNpc().getId())
                {
                    case NpcID.TEKTON:
                    case NpcID.TEKTON_7541:
                    case NpcID.TEKTON_7542:
                    case NpcID.TEKTON_7545:
                    case NpcID.TEKTON_ENRAGED:
                    case NpcID.TEKTON_ENRAGED_7544:
                        if (this.config.tekton())
                        {
                            hitSquares = this.getHitSquares(npcs.getNpc().getWorldLocation(), npcs.getNpcSize(), 1, false);
                            for (WorldPoint p : hitSquares)
                            {
                                this.drawTile(graphics, p, this.config.tektonColor(), 0, 0);
                            }
                            if (this.config.tektonTickCounter())
                            {
                                ticksLeft = npcs.getTicksUntilAttack();
                                final int attackTicksleft = coxPlugin.getTektonAttackTicks();
                                String attacksLeftStr;
                                Color attackcolor;
                                if (ticksLeft > 0)
                                {
                                    if (ticksLeft == 1)
                                    {
                                        color = npcs.getAttackStyle().getColor();
                                    }
                                    else
                                    {
                                        color = Color.WHITE;
                                    }
                                    final String ticksLeftStr = String.valueOf(ticksLeft);
                                    Point canvasPoint = npcs.getNpc().getCanvasTextLocation(graphics, ticksLeftStr, 0);
                                    this.renderTextLocation(graphics, ticksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), color, canvasPoint);
                                }
                                if (attackTicksleft >= 0 && coxPlugin.isTektonActive())
                                {
                                    if (attackTicksleft <= 1)
                                    {
                                        attackcolor = new Color(255, 0, 0, 255);
                                        attacksLeftStr = "Phase Over";
                                    }
                                    else
                                    {
                                        attackcolor = new Color(255, 255, 255, 255);
                                        attacksLeftStr = String.valueOf(attackTicksleft);
                                    }

                                    if (npcs.getNpc() != null)
                                    {
                                        Point canvasPoint = npcs.getNpc().getCanvasTextLocation(graphics, attacksLeftStr, 0);
                                        this.renderTextLocationAbove(graphics, attacksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), attackcolor, canvasPoint);
                                    }
                                }
                            }
                        }
                        break;
                    case NpcID.MUTTADILE:
                    case NpcID.MUTTADILE_7562:
                    case NpcID.MUTTADILE_7563:
                        if (this.config.muttadile())
                        {
                            hitSquares = this.getHitSquares(npcs.getNpc().getWorldLocation(), npcs.getNpcSize(), 1, false);
                            for (WorldPoint p : hitSquares)
                            {
                                this.drawTile(graphics, p, this.config.muttaColor(), 0, 0);
                            }
                        }
                        break;
                    case NpcID.GUARDIAN:
                    case NpcID.GUARDIAN_7570:
                    case NpcID.GUARDIAN_7571:
                    case NpcID.GUARDIAN_7572:
                        if (this.config.guardians())
                        {
                            hitSquares = this.getHitSquares(npcs.getNpc().getWorldLocation(), npcs.getNpcSize(), 2, true);
                            for (WorldPoint p : hitSquares)
                            {
                                this.drawTile(graphics, p, this.config.guardColor(), 0, 0);
                            }
                        }
                        if (this.config.guardinTickCounter())
                        {
                            ticksLeft = npcs.getTicksUntilAttack();
                            if (ticksLeft > 0)
                            {
                                if (ticksLeft == 1)
                                {
                                    color = npcs.getAttackStyle().getColor();
                                }
                                else
                                {
                                    color = Color.WHITE;
                                }
                                final String ticksLeftStr = String.valueOf(ticksLeft);
                                Point canvasPoint = npcs.getNpc().getCanvasTextLocation(graphics, ticksLeftStr, 0);
                                this.renderTextLocation(graphics, ticksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), color, canvasPoint);
                            }
                        }
                        break;
                    case NpcID.VANGUARD_7526:
                    case NpcID.VANGUARD_7527:
                    case NpcID.VANGUARD_7528:
                    case NpcID.VANGUARD_7529:
                        if (this.config.vangHighlight())
                        {
                            OverlayUtil.renderPolygon(graphics, npcs.getNpc().getConvexHull(), npcs.getAttackStyle().getColor());
                        }
                        break;
                }
            }

            if (this.olm.isCrippled())
            {
                int tick = this.olm.getCrippleTicks();
                final String tickStr = String.valueOf(tick);
                Point canvasPoint = this.olm.getHand().getCanvasTextLocation(graphics, tickStr, 50);
                this.renderTextLocation(graphics, tickStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), Color.GRAY, canvasPoint);
            }

            if (this.config.timers())
            {
                if (this.olm.getVictims().size() > 0)
                {
                    this.olm.getVictims().forEach(victim ->
                    {
                        final int ticksLeft = victim.getTicks();
                        String ticksLeftStr = String.valueOf(ticksLeft);
                        Color tickcolor;
                        switch (victim.getType())
                        {
                            case ACID:
                                if (ticksLeft > 0)
                                {
                                    if (ticksLeft > 1)
                                    {
                                        tickcolor = new Color(69, 241, 44, 255);
                                    }
                                    else
                                    {
                                        tickcolor = new Color(255, 255, 255, 255);
                                    }
                                    Point canvasPoint = victim.getPlayer().getCanvasTextLocation(graphics, ticksLeftStr, 0);
                                    this.renderTextLocation(graphics, ticksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), tickcolor, canvasPoint);
                                }
                                break;
                            case BURN:
                                if (ticksLeft > 0)
                                {
                                    if (GAP.contains(ticksLeft))
                                    {
                                        tickcolor = new Color(255, 0, 0, 255);
                                        ticksLeftStr = "GAP";
                                    }
                                    else
                                    {
                                        tickcolor = new Color(255, 255, 255, 255);
                                    }
                                    Point canvasPoint = victim.getPlayer().getCanvasTextLocation(graphics, ticksLeftStr, 0);
                                    this.renderTextLocation(graphics, ticksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), tickcolor, canvasPoint);
                                }
                                break;
                            case TELEPORT:
                                if (this.config.tpOverlay())
                                {
                                    if (ticksLeft > 0)
                                    {
                                        if (ticksLeft > 1)
                                        {
                                            tickcolor = new Color(193, 255, 245, 255);
                                        }
                                        else
                                        {
                                            tickcolor = new Color(255, 255, 255, 255);
                                        }
                                        Point canvasPoint = victim.getPlayer().getCanvasTextLocation(graphics, ticksLeftStr, 0);
                                        this.renderTextLocation(graphics, ticksLeftStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), tickcolor, canvasPoint);
                                    }
                                    this.renderActorOverlay(graphics, victim.getPlayer(), new Color(193, 255, 245, 255));
                                }
                                break;
                        }
                    });
                }
            }

            if (this.olm.isActive())
            {
                renderOlmWrongPrayerOutline();

                if (coxPlugin.isFourToOneActive()){
                    renderFourToOne(graphics);
                }

                GameObject head = this.olm.getHead();

                if (this.config.olmPShowPhase())
                {
                    if (head != null)
                    {
                        Color color = null;
                        switch (this.olm.getPhaseType())
                        {
                            case ACID:
                                color = Color.GREEN;
                                break;
                            case CRYSTAL:
                                color = Color.MAGENTA;
                                break;
                            case FLAME:
                                color = Color.RED;
                                break;
                        }
                        if (color != null)
                        {
                            outliner.drawOutline(head, 2, color, 0);
                        }
                    }
                }


                if (this.config.olmTick())
                {
                    if (head != null)
                    {
                        final int tick = this.olm.getTicksUntilNextAttack();
                        final int cycle = this.olm.getAttackCycle();
                        final String tickStr = String.valueOf(tick);
                        String cycleStr = "?";
                        switch (cycle)
                        {
                            case 4:
                                switch (this.olm.getSpecialCycle())
                                {
                                    case 1:
                                        cycleStr = "Crystals";
                                        break;
                                    case 2:
                                        cycleStr = "Lightning";
                                        break;
                                    case 3:
                                        cycleStr = "Portals";
                                        break;
                                    case 4:
                                        cycleStr = "Heal";
                                        break;
                                }
                                break;
                            case 3:
                                cycleStr = "Sauto";
                                break;
                            case 2:
                                cycleStr = "Null";
                                break;
                            case 1:
                                cycleStr = "Nauto";
                                break;
                            case -1:
                                cycleStr = "??";
                                break;
                        }
                        final String combinedStr = this.olm.getTicksUntilNextAttack() >= 1 ? cycleStr + ":" + tickStr : "??:?";
                        Point canvasPoint = head.getCanvasTextLocation(graphics, combinedStr, 130);
                        Color color = cycle == 4 ? this.config.olmSpecialColor() : Color.WHITE;
                        this.renderTextLocation(graphics, combinedStr, this.config.textSizeCOX(), this.config.fontStyleCOX().getFont(), color, canvasPoint);
                    }
                }
            }
        }

        return null;
    }

    private void drawTile(Graphics2D graphics, WorldPoint point, Color color, int strokeWidth, int outlineAlpha)
    {
        WorldPoint playerLocation = this.client.getLocalPlayer().getWorldLocation();
        if (point.distanceTo(playerLocation) >= 32)
        {
            return;
        }
        LocalPoint lp = LocalPoint.fromWorld(this.client, point);
        if (lp == null)
        {
            return;
        }

        Polygon poly = Perspective.getCanvasTilePoly(this.client, lp);
        if (poly == null)
        {
            return;
        }
        //OverlayUtil.renderPolygon(graphics, poly, color);
        graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineAlpha));
        graphics.setStroke(new BasicStroke(strokeWidth));
        graphics.draw(poly);
        graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
        graphics.fill(poly);
    }

    private void renderActorOverlay(Graphics2D graphics, Actor actor, Color color)
    {
        final int size = 1;
        final LocalPoint lp = actor.getLocalLocation();
        final Polygon tilePoly = Perspective.getCanvasTileAreaPoly(this.client, lp, size);

        if (tilePoly != null)
        {
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
            graphics.setStroke(new BasicStroke(2));
            graphics.draw(tilePoly);
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 10));
            graphics.fill(tilePoly);
        }
    }

    private void renderTextLocation(Graphics2D graphics, String txtString, int fontSize, int fontStyle, Color fontColor, Point canvasPoint)
    {
        graphics.setFont(new Font("Arial", fontStyle, fontSize));
        if (canvasPoint != null)
        {
            final Point canvasCenterPoint = new Point(
                    canvasPoint.getX(),
                    canvasPoint.getY());
            final Point canvasCenterPoint_shadow = new Point(
                    canvasPoint.getX() + 1,
                    canvasPoint.getY() + 1);
            if (this.config.shadowsCOX())
            {
                OverlayUtil.renderTextLocation(graphics, canvasCenterPoint_shadow, txtString, Color.BLACK);
            }
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint, txtString, fontColor);
        }
    }

    private void renderTextLocationAbove(Graphics2D graphics, String txtString, int fontSize, int fontStyle, Color fontColor, Point canvasPoint)
    {
        graphics.setFont(new Font("Arial", fontStyle, fontSize));
        if (canvasPoint != null)
        {
            final Point canvasCenterPoint = new Point(
                    canvasPoint.getX(),
                    canvasPoint.getY() + 20);
            final Point canvasCenterPoint_shadow = new Point(
                    canvasPoint.getX() + 1,
                    canvasPoint.getY() + 21);
            if (this.config.shadowsCOX())
            {
                OverlayUtil.renderTextLocation(graphics, canvasCenterPoint_shadow, txtString, Color.BLACK);
            }
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint, txtString, fontColor);
        }
    }

    private List<WorldPoint> getHitSquares(WorldPoint npcLoc, int npcSize, int thickness, boolean includeUnder)
    {
        final List<WorldPoint> little = new WorldArea(npcLoc, npcSize, npcSize).toWorldPointList();
        final List<WorldPoint> big = new WorldArea(npcLoc.getX() - thickness, npcLoc.getY() - thickness, npcSize + (thickness * 2), npcSize + (thickness * 2), npcLoc.getPlane()).toWorldPointList();
        if (!includeUnder)
        {
            big.removeIf(little::contains);
        }
        return big;
    }

    private void renderFourToOne(Graphics2D graphics){
        final Collection<ColorTileMarker> points = coxPlugin.getOlm().getPoints();

        //Null checks
        if (points == null){
            return;
        }

        graphics.setFont(new Font("Arial", config.fontStyleCOX().getFont(), 12));

        Stroke stroke = new BasicStroke((float) 2);
        for (final ColorTileMarker point : points)
        {
            WorldPoint worldPoint = point.getWorldPoint();
            if (worldPoint.getPlane() != client.getPlane())
            {
                continue;
            }

            Color tileColor = point.getColor();
            if (tileColor == null )
            {
                tileColor = Color.ORANGE;
            }

            GUIOverlayUtil.drawTile(graphics, worldPoint, client, tileColor, point.getLabel(), stroke);
        }
    }

    private void renderOlmWrongPrayerOutline()
    {
        //Check if config is enabled.
        if (!config.OlmOverlayWrongPrayerOutline())
        {
            return;
        }

        AttackStyle attackStyle = coxPlugin.getOlm().getAttackStyle();

        if (attackStyle == null){
            return;
        }

        if (client.isPrayerActive(attackStyle.getPrayer().getApiPrayer()))
        {
            return;
        }

        GameObject head = this.olm.getHead();

        if (head != null) {
            outliner.drawOutline(head, 2, attackStyle.getColor(),
                    0);
        }
    }
}
