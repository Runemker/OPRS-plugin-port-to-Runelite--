package com.cheating.cerberus.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.cheating.CheatingConfig;
import com.cheating.Util.InfoBoxComponent;
import com.cheating.Util.Prayer;
import com.cheating.cerberus.CerberusPlugin;
import com.cheating.cerberus.domain.Cerberus;
import com.cheating.cerberus.domain.CerberusAttack;
import com.cheating.cerberus.domain.Phase;
import com.cheating.cerberus.util.ImageManager;
import com.cheating.cerberus.util.Utility;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
public final class CurrentAttackOverlay extends Overlay
{
    private static final Color COLOR_BORDER = Color.WHITE;
    private static final Color COLOR_PRAYER_ENABLED = new Color(70, 61, 50, 225);
    private static final Color COLOR_PRAYER_DISABLED = new Color(150, 0, 0, 225);

    private static final int GAME_TICK_THRESHOLD = 6;

    private final Client client;
    private final CerberusPlugin plugin;
    private final CheatingConfig config;

    private final InfoBoxComponent infoBoxComponent;

    @Inject
    CurrentAttackOverlay(final Client client, final CerberusPlugin plugin, final CheatingConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        infoBoxComponent = new InfoBoxComponent();
        infoBoxComponent.setTextColor(Color.WHITE);

        setPriority(OverlayPriority.HIGHEST);
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        if (!config.showCurrentAttackCerberus() || plugin.getCerberus() == null)
        {
            return null;
        }

        final List<CerberusAttack> upcomingAttacks = plugin.getUpcomingAttacks();

        if (upcomingAttacks.isEmpty())
        {
            return null;
        }

        final CerberusAttack cerberusAttack = plugin.getUpcomingAttacks().get(0);

        if (cerberusAttack.getTick() > (plugin.getGameTick() + GAME_TICK_THRESHOLD))
        {
            return null;
        }

        final Prayer prayer;

        if (cerberusAttack.getAttack() == Cerberus.Attack.AUTO)
        {
            prayer = plugin.getPrayer();
        }
        else
        {
            prayer = cerberusAttack.getAttack().getPrayer();
        }

        if (prayer == null)
        {
            return null;
        }

        final CheatingConfig.InfoBoxComponentSize infoBoxComponentSize = config.infoBoxComponentSizeCerberus();

        final int size = infoBoxComponentSize.getSize();

        infoBoxComponent.setPreferredSize(new Dimension(size, size));

        final BufferedImage image = ImageManager.getCerberusBufferedImage(Phase.AUTO, prayer.getApiPrayer(), infoBoxComponentSize);

        infoBoxComponent.setImage(image);

        final Color backgroundColor = client.isPrayerActive(prayer.getApiPrayer()) ? COLOR_PRAYER_ENABLED : COLOR_PRAYER_DISABLED;

        infoBoxComponent.setBackgroundColor(backgroundColor);
        infoBoxComponent.setFont(Utility.getFontFromInfoboxComponentSize(infoBoxComponentSize));

        if (config.showCurrentAttackTimerCerberus())
        {
            final double timeUntilAttack = Math.max((double) ((cerberusAttack.getTick() - plugin.getGameTick()) * 600 - (System.currentTimeMillis() - plugin.getLastTick())) / 1000, 0);

            infoBoxComponent.setText(String.format("+%.1fs", timeUntilAttack));
        }
        else
        {
            infoBoxComponent.setText(null);
        }

        renderOutlineBorder(graphics2D, size);

        return infoBoxComponent.render(graphics2D);
    }

    private void renderOutlineBorder(final Graphics2D graphics2D, final int size)
    {
        final int x = -1;
        final int y = -1;

        final Rectangle rectangle = new Rectangle();

        rectangle.setLocation(x, y);
        rectangle.setSize(size + 1, size + 1);

        graphics2D.setColor(COLOR_BORDER);
        graphics2D.draw(rectangle);
    }
}
