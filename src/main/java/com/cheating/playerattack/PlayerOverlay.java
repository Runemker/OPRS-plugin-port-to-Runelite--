package com.cheating.playerattack;

import com.cheating.CheatingConfig;
import com.cheating.Util.GUIOverlayUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
public class PlayerOverlay extends Overlay
{
    private final Client client;
    private final CheatingConfig config;

    private Player player;

    @Inject
    private PlayerAttackPlugin playerAttackPlugin;

    @Inject
    PlayerOverlay(final Client client, final CheatingConfig config)
    {
        this.client = client;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        player = client.getLocalPlayer();

        if (player == null)
        {
            return null;
        }

        if (config.debugAnimationIdsPAT())
        {
            renderDebugAnimationIds(graphics2D);
        }
        else
        {
            renderPlayerAttackTimer(graphics2D);
        }

        return null;
    }

    private void renderPlayerAttackTimer(final Graphics2D graphics2D)
    {
        final int ticksUntilNextAnimation = playerAttackPlugin.getTicksUntilNextAnimation();

        if (ticksUntilNextAnimation == 0)
        {
            return;
        }

        final String str = String.valueOf(ticksUntilNextAnimation);

        final Point point = player.getCanvasTextLocation(graphics2D, str, 0);

        if (point == null)
        {
            return;
        }

        GUIOverlayUtil.renderTextLocation(
                graphics2D,
                str,
                config.fontSizePAT(),
                config.fontStylePAT().getFont(),
                ticksUntilNextAnimation == 1 ? Color.WHITE : config.fontColorPAT(),
                point,
                config.fontShadowPAT(),
                config.fontZOffsetPAT() * -1
        );
    }

    private void renderDebugAnimationIds(final Graphics2D graphics2D)
    {
        final String str = "Anim Id: " + player.getAnimation();

        final Point point = player.getCanvasTextLocation(graphics2D, str, 0);

        if (point == null)
        {
            return;
        }

        GUIOverlayUtil.renderTextLocation(
                graphics2D,
                str,
                config.fontSizePAT(),
                config.fontStylePAT().getFont(),
                config.fontColorPAT(),
                point,
                config.fontShadowPAT(),
                config.fontZOffsetPAT() * -1
        );
    }
}