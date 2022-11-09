package com.cheating.inferno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.cheating.CheatingConfig;
import com.cheating.inferno.displaymodes.InfernoPrayerDisplayMode;
import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

@Singleton
public class InfernoInfoBoxOverlay extends Overlay
{
    private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(150, 0, 0, 150);
    private final Client client;
    private final InfernoPlugin plugin;
    private final CheatingConfig config;
    private final SpriteManager spriteManager;
    private final PanelComponent imagePanelComponent = new PanelComponent();
    private BufferedImage prayMeleeSprite;
    private BufferedImage prayRangedSprite;
    private BufferedImage prayMagicSprite;

    @Inject
    private InfernoInfoBoxOverlay(final Client client, final InfernoPlugin plugin, final CheatingConfig config, final SpriteManager spriteManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.spriteManager = spriteManager;
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.prayerDisplayMode() != InfernoPrayerDisplayMode.BOTTOM_RIGHT
                && config.prayerDisplayMode() != InfernoPrayerDisplayMode.BOTH)
        {
            return null;
        }

        imagePanelComponent.getChildren().clear();

        if (plugin.getClosestAttack() != null)
        {
            final BufferedImage prayerImage = getPrayerImage(plugin.getClosestAttack());

            imagePanelComponent.getChildren().add(new ImageComponent(prayerImage));
            imagePanelComponent.setBackgroundColor(client.isPrayerActive(plugin.getClosestAttack().getPrayer().getApiPrayer())
                    ? ComponentConstants.STANDARD_BACKGROUND_COLOR
                    : NOT_ACTIVATED_BACKGROUND_COLOR);
        }
        else
        {
            imagePanelComponent.setBackgroundColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
        }

        return imagePanelComponent.render(graphics);
    }

    private BufferedImage getPrayerImage(InfernoNPC.Attack attack)
    {
        if (prayMeleeSprite == null)
        {
            prayMeleeSprite = spriteManager.getSprite(SpriteID.PRAYER_PROTECT_FROM_MELEE, 0);
        }
        if (prayRangedSprite == null)
        {
            prayRangedSprite = spriteManager.getSprite(SpriteID.PRAYER_PROTECT_FROM_MISSILES, 0);
        }
        if (prayMagicSprite == null)
        {
            prayMagicSprite = spriteManager.getSprite(SpriteID.PRAYER_PROTECT_FROM_MAGIC, 0);
        }

        switch (attack)
        {
            case MELEE:
                return prayMeleeSprite;
            case RANGED:
                return prayRangedSprite;
            case MAGIC:
                return prayMagicSprite;
        }

        return prayMagicSprite;
    }
}
