package com.cheating.hydra.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.Util.Prayer;
import com.cheating.hydra.HydraPlugin;
import com.cheating.hydra.entity.Hydra;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

public class PrayerOverlay extends Overlay
{
    private final Client client;
    private final CheatingPlugin plugin;
    private final CheatingConfig config;

    private Hydra hydra;

    @Inject
    private HydraPlugin hydraPlugin;

    @Inject
    private PrayerOverlay(final Client client, final CheatingPlugin plugin, final CheatingConfig config)
    {

        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        hydra = hydraPlugin.getHydra();

        if (hydra == null)
        {
            return null;
        }

        renderPrayerWidget(graphics2D);

        return null;
    }

    private void renderPrayerWidget(final Graphics2D graphics2D)
    {
        final Prayer prayer = hydra.getNextAttack().getPrayer();

        GUIOverlayUtil.renderPrayerOverlay(graphics2D, client, prayer, prayer == Prayer.PROTECT_FROM_MAGIC ? Color.CYAN : Color.GREEN);
    }

}