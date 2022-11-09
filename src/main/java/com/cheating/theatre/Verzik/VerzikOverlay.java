package com.cheating.theatre.Verzik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.inject.Inject;

import com.cheating.CheatingConfig;
import com.cheating.theatre.RoomOverlay;
import com.sun.jna.platform.win32.WinNT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class VerzikOverlay extends RoomOverlay
{
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.0");
    private static final int VERZIK_GREEN_BALL = 1598;
    private static final int VERZIK_LIGHTNING_BALL = 1585;

    @Inject
    private Verzik verzik;

    @Inject
    private SpriteManager spriteManager;
    private final ModelOutlineRenderer modelOutlineRenderer;

    @Inject
    protected VerzikOverlay(CheatingConfig config, SpriteManager spriteManager, final ModelOutlineRenderer modelOutlineRenderer)
    {
        super(config);
        this.spriteManager = spriteManager;
        this.modelOutlineRenderer = modelOutlineRenderer;
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (verzik.isVerzikActive())
        {
            renderVerzikTile(graphics);
            renderVerzikTickCounters(graphics);

            if (verzik.getVerzikPhase() == Verzik.Phase.PHASE2)
            {
                renderRangeProjectiles(graphics);
                renderRedCrabsInfo(graphics);
                renderLightningAttackInfo(graphics);
                renderHMPoisonTile(graphics);
            }

            if (verzik.getVerzikPhase() == Verzik.Phase.PHASE2 || verzik.getVerzikPhase() == Verzik.Phase.PHASE3)
            {
                renderNyloInfo(graphics);
            }

            if (verzik.getVerzikPhase() == Verzik.Phase.PHASE3)
            {
                renderVerzikTank(graphics);
                renderTornadoes(graphics);
                renderYellowSpecial(graphics);
                renderGreenBallInfo(graphics);
                renderVerzikWrongPrayerOutline();
            }
        }
        return null;
    }

    private void renderVerzikTile(final Graphics2D graphics){
        if (config.verzikTileOverlay())
        {
            int size = 1;
            final NPCComposition composition = verzik.getVerzikNPC().getTransformedComposition();
            if (composition != null)
            {
                size = composition.getSize();
            }
            LocalPoint lp = LocalPoint.fromWorld(client, verzik.getVerzikNPC().getWorldLocation());
            if (lp != null)
            {
                Polygon tilePoly = getCanvasTileAreaPoly(client, lp, size, false);
                if (tilePoly != null)
                {
                    if (verzik.isVerzikEnraged())
                    {
                        renderPoly(graphics, new Color(255, 110, 90), tilePoly);
                    }
                    else
                    {
                        renderPoly(graphics, new Color(255, 110, 230), tilePoly);
                    }
                }
            }
        }
    }

    private void renderVerzikTickCounters(final Graphics2D graphics){
        String tick_text = "";
        if (config.verzikAttackCounter() && verzik.getVerzikSpecial() != Verzik.SpecialAttack.WEBS)
        {
            tick_text += "[A] " + verzik.getVerzikAttackCount();
            if (config.verzikAutosTick() || config.verzikTotalTickCounter())
            {
                tick_text += " : ";
            }
        }
        if (config.verzikAutosTick() && verzik.getVerzikSpecial() != Verzik.SpecialAttack.WEBS)
        {
            tick_text += verzik.getVerzikTicksUntilAttack();
            if (config.verzikTotalTickCounter())
            {
                tick_text += " : ";
            }
        }
        if (config.verzikTotalTickCounter())
        {
            tick_text += "(" + verzik.getVerzikTotalTicksUntilAttack() + ")";
        }
        Point canvasPoint = verzik.getVerzikNPC().getCanvasTextLocation(graphics, tick_text, 60);

        if (canvasPoint != null)
        {
            Color col = verzik.verzikSpecialWarningColor();
            renderTextLocation(graphics, tick_text, col, canvasPoint);
        }
    }

    private void renderRangeProjectiles(final Graphics2D graphics){
        if (config.verzikProjectiles())
        {
            Iterator iterator = verzik.getVerzikRangeProjectiles().values().iterator();

            while (iterator.hasNext())
            {
                drawTile(graphics, (WorldPoint) iterator.next(), config.verzikProjectilesColor(), 1, 255, 20);
            }
        }
    }

    private void renderRedCrabsInfo(final Graphics2D graphics){
        if (config.verzikReds())
        {
            verzik.getVerzikReds().forEach((k, v) ->
            {
                int v_health = v.getValue();
                int v_healthRation = v.getKey();
                if (k.getName() != null && k.getHealthScale() > 0)
                {
                    v_health = k.getHealthScale();
                    v_healthRation = Math.min(v_healthRation, k.getHealthRatio());
                }
                float percentage = ((float) v_healthRation / (float) v_health) * 100f;
                Point textLocation = k.getCanvasTextLocation(graphics, String.valueOf(DECIMAL_FORMAT.format(percentage)), 80);

                if (textLocation != null)
                {
                    renderTextLocation(graphics, String.valueOf(DECIMAL_FORMAT.format(percentage)), Color.WHITE, textLocation);
                }
            });

            NPC[] reds = verzik.getVerzikReds().keySet().toArray(new NPC[0]);
            for (NPC npc : reds)
            {
                if (npc.getName() != null && npc.getHealthScale() > 0 && npc.getHealthRatio() < 100)
                {
                    Pair<Integer, Integer> newVal = new MutablePair<>(npc.getHealthRatio(), npc.getHealthScale());
                    if (verzik.getVerzikReds().containsKey(npc))
                    {
                        verzik.getVerzikReds().put(npc, newVal);
                    }
                }
            }
        }
    }

    private void renderLightningAttackInfo(final Graphics2D graphics){
        if (verzik.getVerzikPhase() == Verzik.Phase.PHASE2 && verzik.getVerzikNPC() != null && config.lightningAttackHelper())
        {
            Point imageLocation;
            if (verzik.getVerzikLightningAttacks() == 0)
            {
                BufferedImage lightningIcon = spriteManager.getSprite(558, 0);
                imageLocation = verzik.getVerzikNPC().getCanvasImageLocation(lightningIcon, 200);
                if (imageLocation != null)
                {
                    OverlayUtil.renderImageLocation(graphics, imageLocation, lightningIcon);
                }
            }
            else
            {
                String attacksLeft = Integer.toString(verzik.getVerzikLightningAttacks());
                imageLocation = Perspective.getCanvasTextLocation(client, graphics, verzik.getVerzikNPC().getLocalLocation(), attacksLeft, 200);
                renderTextLocation(graphics, attacksLeft, Color.WHITE, imageLocation);
            }
        }

        if (config.lightningAttackTick())
        {
            client.getProjectiles().forEach((p) -> {
                Actor getInteracting = p.getInteracting();

                if (p.getId() == VERZIK_LIGHTNING_BALL)
                {
                    Player localPlayer = client.getLocalPlayer();
                    if (getInteracting != null && getInteracting == localPlayer)
                    {
                        Point point = getProjectilePoint(p);
                        if (point != null)
                        {
                            Point textLocation = new Point(point.getX(), point.getY());
                            renderTextLocation(graphics, Integer.toString(p.getRemainingCycles() / 30), Color.ORANGE, textLocation);
                        }
                    }
                }

            });
        }
    }

    private void renderHMPoisonTile(final Graphics2D graphics){
        if (verzik.isHM() && config.verzikPoisonTileHighlight())
        {
            verzik.getVerzikPoisonTiles()
                    .stream()
                    .filter(VerzikPoisonTile::shouldHighlight)
                    .forEach(tile -> {
                        drawTile(graphics, tile.getTile(), config.verzikPoisonTileHighlightColor(), 1, 255, 20);
                    });
        }
    }

    private void renderVerzikTank(final Graphics2D graphics){
        if (config.verzikDisplayTank())
        {
            if (verzik.getVerzikNPC().getInteracting() != null)
            {
                Polygon tilePoly = verzik.getVerzikNPC().getInteracting().getCanvasTilePoly();
                if (tilePoly != null)
                {
                    renderPoly(graphics, Color.LIGHT_GRAY, tilePoly);
                }
            }
        }
    }

    private void renderTornadoes(final Graphics2D graphics){
        if (config.verzikTornado() && (!config.verzikPersonalTornadoOnly() || (config.verzikPersonalTornadoOnly() && verzik.getVerzikLocalTornado() != null)))
        {
            verzik.getVerzikTornadoes().forEach(k ->
            {
                if (k.getCurrentPosition() != null)
                {
                    drawTile(graphics, k.getCurrentPosition(), config.verzikTornadoColor(), 1, 120, 10);
                }

                if (k.getLastPosition() != null)
                {
                    drawTile(graphics, k.getLastPosition(), config.verzikTornadoColor(), 2, 180, 20);
                }
            });
        }
    }

    private void renderYellowSpecial(final Graphics2D graphics){
        if (config.verzikYellows())
        {
            if (verzik.getVerzikYellows() > 0)
            {
                String text = Integer.toString(verzik.getVerzikYellows());

                for (GraphicsObject object : client.getGraphicsObjects())
                {
                    if (object.getId() == 1595)
                    {
                        drawTile(graphics, WorldPoint.fromLocal(client, object.getLocation()), Color.YELLOW, 1, 255, 0);
                        LocalPoint lp = object.getLocation();
                        Point point = Perspective.getCanvasTextLocation(client, graphics, lp, text, 0);
                        renderTextLocation(graphics, text, Color.WHITE, point);
                    }
                }
            }
        }
    }

    private void renderGreenBallInfo(final Graphics2D graphics){
        if (config.verzikGreenBall() || config.verzikGreenBallTick())
        {
            for (Projectile p : client.getProjectiles())
            {
                if (p.getId() == VERZIK_GREEN_BALL)
                {
                    if (config.verzikGreenBallTick())
                    {
                        Point point = getProjectilePoint(p);
                        if (point != null)
                        {
                            Point textLocation = new Point(point.getX(), point.getY());
                            renderTextLocation(graphics, Integer.toString(p.getRemainingCycles() / 30), Color.GREEN, textLocation);
                        }
                    }

                    if (config.verzikGreenBall())
                    {
                        Polygon tilePoly;
                        if (config.verzikGreenBallMarker() == CheatingConfig.VERZIKBALLTILE.TILE)
                        {
                            tilePoly = p.getInteracting().getCanvasTilePoly();
                        }
                        else
                        {
                            tilePoly = getCanvasTileAreaPoly(client, p.getInteracting().getLocalLocation(), 3, true);
                        }

                        if (tilePoly != null)
                        {
                            renderPoly(graphics, config.verzikGreenBallColor(), tilePoly);
                        }
                    }
                }
            }
        }
    }

    private void renderNyloInfo(final Graphics2D graphics){
        if (config.verzikNyloPersonalWarning() || config.verzikNyloOtherWarning())
        {
            verzik.getVerzikAggros().forEach(k ->
            {
                if (k.getInteracting() != null && !k.isDead())
                {
                    if ((config.verzikNyloPersonalWarning() && k.getInteracting() == client.getLocalPlayer())
                            || (config.verzikNyloOtherWarning() && k.getInteracting() != client.getLocalPlayer()))
                    {
                        Color color = Color.LIGHT_GRAY;
                        if (k.getInteracting() == client.getLocalPlayer())
                        {
                            color = Color.YELLOW;
                        }

                        Point textLocation = k.getCanvasTextLocation(graphics, k.getInteracting().getName(), 80);
                        if (textLocation != null)
                        {
                            OverlayUtil.renderTextLocation(graphics, textLocation, k.getInteracting().getName(), color);
                        }

                        if (config.verzikNyloExplodeAOE())
                        {
                            int size = 1;
                            int thick_size = 1;
                            final NPCComposition composition = k.getComposition();

                            if (composition != null)
                            {
                                size = composition.getSize() + 2 * thick_size;
                            }

                            LocalPoint lp = LocalPoint.fromWorld(client, k.getWorldLocation());
                            if (lp != null)
                            {
                                lp = new LocalPoint(lp.getX() - thick_size * Perspective.LOCAL_TILE_SIZE, lp.getY() - thick_size * Perspective.LOCAL_TILE_SIZE);

                                Polygon tilePoly = getCanvasTileAreaPoly(client, lp, size, false);
                                if (tilePoly != null)
                                {
                                    renderPoly(graphics, color, tilePoly);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void renderVerzikWrongPrayerOutline()
    {
        //Check if config is enabled.
        if (!config.VerzikOverlayWrongPrayerOutline())
        {
            return;
        }

        AttackStyle attackStyle = verzik.getAttackStyle();

        if (client.isPrayerActive(attackStyle.getPrayer()) || attackStyle == AttackStyle.NONE) {
            return;
        }

        modelOutlineRenderer.drawOutline(verzik.getVerzikNPC(), config.VerzikWrongPrayerOutlineWidth(), attackStyle.getColor(),0);
    }

    private Point getProjectilePoint(Projectile p)
    {
        int x = (int) p.getX();
        int y = (int) p.getY();
        int z = (int) p.getZ();
        return Perspective.localToCanvas(client, new LocalPoint(x, y), 0, Perspective.getTileHeight(client, new LocalPoint(x, y), p.getFloor()) - z);
    }
}
