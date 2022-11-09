package com.cheating.cerberus.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.cheating.CheatingConfig;
import com.cheating.Util.InfoBoxComponent;
import com.cheating.cerberus.CerberusPlugin;
import com.cheating.cerberus.domain.Cerberus;
import com.cheating.cerberus.domain.Phase;
import com.cheating.cerberus.util.ImageManager;
import com.cheating.cerberus.util.Utility;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.PanelComponent;

@Singleton
public final class UpcomingAttackOverlay extends Overlay
{
    private static final Color COLOR_NEXT_ATTACK_BORDER = Color.WHITE;

    private static final PanelComponent PANEL_COMPONENT = new PanelComponent();

    private static final int GAP_SIZE = 2;

    private final CerberusPlugin plugin;
    private final CheatingConfig config;

    @Inject
    public UpcomingAttackOverlay(final CerberusPlugin plugin, final CheatingConfig config)
    {
        this.plugin = plugin;
        this.config = config;

        // Clear background and border
        PANEL_COMPONENT.setBackgroundColor(null);
        PANEL_COMPONENT.setBorder(new Rectangle(0, 0, 0, 0));

        setPriority(OverlayPriority.HIGH);
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D)
    {
        final Cerberus cerberus = plugin.getCerberus();

        if (!config.showUpcomingAttacksCerberus() || cerberus == null)
        {
            return null;
        }

        // Remove upcoming attack infobox children
        PANEL_COMPONENT.getChildren().clear();

        final CheatingConfig.InfoBoxComponentSize infoBoxComponentSize = config.infoBoxComponentSizeCerberus();

        // Set size from config
        final int size = infoBoxComponentSize.getSize();
        final Dimension dimension = new Dimension(size, size);
        PANEL_COMPONENT.setPreferredSize(dimension);

        // Set orientation from config
        final ComponentOrientation orientation = config.upcomingAttacksOrientationCerberus().getOrientation();
        PANEL_COMPONENT.setOrientation(orientation);

        final boolean horizontal = orientation == ComponentOrientation.HORIZONTAL;
        final boolean reverse = config.reverseUpcomingAttacksCerberus();

        // Set gap between infobox children
        final Point gap = new Point(horizontal ? GAP_SIZE : 0, horizontal ? 0 : GAP_SIZE);
        PANEL_COMPONENT.setGap(gap);

        final int attacksShown = config.amountOfAttacksShown();

        for (int i = 0; i < attacksShown; ++i)
        {
            final int attack;

            if (reverse ^ !horizontal)
            {
                attack = attacksShown - i;
            }
            else
            {
                attack = i + 1;
            }

//			if (attack == 1)
//			{
//				renderOutlineBorder(graphics2D, size, horizontal, reverse, gap, attacksShown);
//			}

            // Get the image for the infobox
            final int cerberusHp = cerberus.getHp();
            final Phase phase = cerberus.getNextAttackPhase(attack, cerberusHp);
            final BufferedImage image = ImageManager.getCerberusBufferedImage(phase, plugin.getPrayer().getApiPrayer(), infoBoxComponentSize);

            if (image == null)
            {
                continue;
            }

            // Create infobox
            final InfoBoxComponent infoBoxComponent = new InfoBoxComponent();
            infoBoxComponent.setFont(Utility.getFontFromInfoboxComponentSize(infoBoxComponentSize));
            infoBoxComponent.setTextColor(Color.GREEN);
            infoBoxComponent.setBackgroundColor(Utility.getColorFromPhase(phase));
            infoBoxComponent.setPreferredSize(dimension);
            infoBoxComponent.setImage(image);

            // Set text
            final Phase nextThresholdPhase = cerberus.getNextAttackPhase(attack, cerberusHp - 200);

            if (!nextThresholdPhase.equals(phase))
            {
                final String text = infoBoxComponentSize == CheatingConfig.InfoBoxComponentSize.SMALL
                        ? nextThresholdPhase.name().substring(0, 1)
                        : infoBoxComponentSize == CheatingConfig.InfoBoxComponentSize.MEDIUM
                        ? nextThresholdPhase.name().substring(0, 2)
                        : nextThresholdPhase.name();

                infoBoxComponent.setText(String.format("%s +%d", text, cerberusHp % 200));
            }

            // Set title
            if (config.showUpcomingAttackNumberCerberus())
            {
                infoBoxComponent.setTitle(String.valueOf(cerberus.getPhaseCount() + attack));
            }

            // Add infobox to panel
            PANEL_COMPONENT.getChildren().add(infoBoxComponent);
        }

        return PANEL_COMPONENT.render(graphics2D);
    }

    private void renderOutlineBorder(final Graphics2D graphics2D, final int size, final boolean horizontalLayout, final boolean reverseLayout, final Point gap, final int numberOfAttacks)
    {
        int x = -1;
        int y = -1;

        if (horizontalLayout && reverseLayout)
        {
            x += (size + gap.getX()) * (numberOfAttacks - 1);
        }
        else if (!horizontalLayout && !reverseLayout)
        {
            y += (size + gap.getY()) * (numberOfAttacks - 1);
        }

        final Rectangle rectangle = new Rectangle();

        rectangle.setLocation(x, y);
        rectangle.setSize(size + 1, size + 1);

        graphics2D.setColor(COLOR_NEXT_ATTACK_BORDER);
        graphics2D.draw(rectangle);
    }
}
