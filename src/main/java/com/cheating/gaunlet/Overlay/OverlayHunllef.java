package com.cheating.gaunlet.Overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.gaunlet.GauntletPlugin;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.gaunlet.entity.Hunllef;
import com.cheating.gaunlet.entity.Tornado;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.Point;

import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

@Singleton
public class OverlayHunllef extends Overlay
{
    private final Client client;
    private final CheatingPlugin plugin;
    private final CheatingConfig config;
    private final ModelOutlineRenderer modelOutlineRenderer;

    private Hunllef hunllef;

    private int timeout;

    @Inject
    private GauntletPlugin gauntletPlugin;

    @Inject
    private OverlayHunllef(final Client client, final CheatingPlugin plugin, final CheatingConfig config, final ModelOutlineRenderer modelOutlineRenderer)
    {
        super(plugin);

        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.modelOutlineRenderer = modelOutlineRenderer;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        determineLayer();
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        hunllef = gauntletPlugin.getHunllef();

        if (hunllef == null)
        {
            return null;
        }

        final NPC npc = hunllef.getNpc();

        if (npc == null || !gauntletPlugin.isInHunllef())
        {
            return null;
        }

        renderTornadoes(graphics2D);

        renderHunllefWrongPrayerOutline();

        renderHunllefAttackCounter(graphics2D);

        renderFlashOnWrongAttack(graphics2D);

        renderFlashOn51Method(graphics2D);

        return null;
    }

    public void determineLayer()
    {
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    private void renderTornadoes(final Graphics2D graphics2D)
    {
        if ((!config.tornadoTickCounter() && !config.tornadoTileOutline()) || gauntletPlugin.getTornadoes().isEmpty())
        {
            return;
        }

        for (final Tornado tornado : gauntletPlugin.getTornadoes())
        {
            final int timeLeft = tornado.getTimeLeft();

            if (timeLeft < 0)
            {
                continue;
            }

            final NPC npc = tornado.getNpc();

            if (config.tornadoTileOutline())
            {

                final Polygon polygon = Perspective.getCanvasTilePoly(client, npc.getLocalLocation());

                if (polygon == null)
                {
                    continue;
                }

                drawOutlineAndFill(graphics2D, config.tornadoOutlineColor(), config.tornadoFillColor(),
                        config.tornadoTileOutlineWidth(), polygon);
            }

            if (config.tornadoTickCounter())
            {
                final String ticksLeftStr = String.valueOf(timeLeft);

                final Point point = npc.getCanvasTextLocation(graphics2D, ticksLeftStr, 0);

                if (point == null)
                {
                    return;
                }

                GUIOverlayUtil.renderTextLocation(graphics2D, ticksLeftStr, config.tornadoFontSize(),
                        config.tornadoFontStyle().getFont(), config.tornadoFontColor(), point,
                        config.tornadoFontShadow(), 0);
            }
        }
    }

    private void renderHunllefWrongPrayerOutline()
    {
        if (!config.hunllefOverlayWrongPrayerOutline())
        {
            return;
        }

        final Hunllef.AttackPhase phase = hunllef.getAttackPhase();

        if (client.isPrayerActive(phase.getPrayer().getApiPrayer()))
        {
            return;
        }

        modelOutlineRenderer.drawOutline(hunllef.getNpc(), config.hunllefWrongPrayerOutlineWidth(), phase.getColor(),
                0);
    }

    private void renderHunllefAttackCounter(final Graphics2D graphics2D)
    {
        if (!config.hunllefOverlayAttackCounter())
        {
            return;
        }

        final NPC npc = hunllef.getNpc();

        final String text = String.format("%d | %d", hunllef.getAttackCount(),
                hunllef.getPlayerAttackCount());

        final Point point = npc.getCanvasTextLocation(graphics2D, text, 0);

        if (point == null)
        {
            return;
        }

        final Font originalFont = graphics2D.getFont();

        graphics2D.setFont(new Font(Font.SANS_SERIF,
                config.hunllefAttackCounterFontStyle().getFont(), config.hunllefAttackCounterFontSize()));

        GUIOverlayUtil.renderTextLocation(graphics2D, point, text, hunllef.getAttackPhase().getColor());

        graphics2D.setFont(originalFont);
    }

    private void renderFlashOn51Method(final Graphics2D graphics2D)
    {
        if (!config.flashOn51Method() || !gauntletPlugin.isSwitchWeapon())
        {
            return;
        }

        final Color originalColor = graphics2D.getColor();

        graphics2D.setColor(config.flashOn51MethodColor());

        graphics2D.fill(client.getCanvas().getBounds());

        graphics2D.setColor(originalColor);

        if (++timeout >= config.flashOn51MethodDuration())
        {
            timeout = 0;
            gauntletPlugin.setSwitchWeapon(false);
        }
    }

    private void renderFlashOnWrongAttack(final Graphics2D graphics2D)
    {
        if (!config.flashOnWrongAttack() || !gauntletPlugin.isWrongAttackStyle())
        {
            return;
        }

        final Color originalColor = graphics2D.getColor();

        graphics2D.setColor(config.flashOnWrongAttackColor());

        graphics2D.fill(client.getCanvas().getBounds());

        graphics2D.setColor(originalColor);

        if (++timeout >= config.flashOnWrongAttackDuration())
        {
            timeout = 0;
            gauntletPlugin.setWrongAttackStyle(false);
        }
    }

}
