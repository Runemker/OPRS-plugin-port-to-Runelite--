package com.cheating.hydra.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.inject.Inject;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.GUIOverlayUtil;
import com.cheating.Util.Prayer;
import com.cheating.hydra.HydraPlugin;
import com.cheating.hydra.entity.Hydra;
import com.cheating.hydra.entity.HydraPhase;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import static net.runelite.client.ui.overlay.components.ComponentConstants.STANDARD_BACKGROUND_COLOR;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.util.ImageUtil;

public class AttackOverlay extends Overlay
{
    public static final int IMAGE_SIZE = 36;

    private static final String INFO_BOX_TEXT_PADDING = "        ";
    private static final Dimension INFO_BOX_DIMENSION = new Dimension(40, 40);

    private static final PanelComponent panelComponent = new PanelComponent();

    private static final InfoBoxComponent stunComponent = new InfoBoxComponent();
    private static final InfoBoxComponent phaseSpecialComponent = new InfoBoxComponent();
    private static final InfoBoxComponent prayerComponent = new InfoBoxComponent();

    private static final int STUN_TICK_DURATION = 7;

    static
    {
        panelComponent.setOrientation(ComponentOrientation.VERTICAL);
        panelComponent.setBorder(new Rectangle(0, 0, 0, 0));
        panelComponent.setPreferredSize(new Dimension(40, 0));

        stunComponent.setPreferredSize(INFO_BOX_DIMENSION);
        phaseSpecialComponent.setPreferredSize(INFO_BOX_DIMENSION);
        prayerComponent.setPreferredSize(INFO_BOX_DIMENSION);
    }

    private final Client client;

    private final CheatingPlugin plugin;
    private final CheatingConfig config;

    private final SpriteManager spriteManager;

    private int stunTicks;

    private Hydra hydra;

    private boolean init = false;

    @Inject
    private HydraPlugin hydraPlugin;

    @Inject
    public AttackOverlay(final Client client, final CheatingPlugin plugin, final CheatingConfig config, final SpriteManager spriteManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.spriteManager = spriteManager;



        setPriority(OverlayPriority.HIGH);
        setPosition(OverlayPosition.BOTTOM_RIGHT);
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

        clearPanelComponent();

        updateStunComponent();

        updatePhaseSpecialComponent();

        if (config.hidePrayerOnSpecial() && isSpecialAttack())
        {
            return panelComponent.render(graphics2D);
        }

        updatePrayerComponent();

        renderPrayerWidget(graphics2D);

        return panelComponent.render(graphics2D);
    }

    public void decrementStunTicks()
    {
        if (stunTicks > 0)
        {
            --stunTicks;
        }
    }

    public void setStunTicks()
    {
        stunTicks = STUN_TICK_DURATION;
    }

    private void clearPanelComponent()
    {
        final List<LayoutableRenderableEntity> children = panelComponent.getChildren();

        if (!children.isEmpty())
        {
            children.clear();
        }
    }

    private void updateStunComponent()
    {
        if (stunTicks <= 0)
        {
            return;
        }

        if (!init){
            init = true;
            stunComponent.setBackgroundColor(config.dangerColor());
            stunComponent.setImage(getStunImage());
        }

        stunComponent.setText(INFO_BOX_TEXT_PADDING + stunTicks);

        panelComponent.getChildren().add(stunComponent);
    }

    private void updatePhaseSpecialComponent()
    {
        final int nextSpec = hydra.getNextSpecialRelative();

        if (nextSpec > 3 || nextSpec < 0)
        {
            return;
        }

        if (nextSpec == 0)
        {
            phaseSpecialComponent.setBackgroundColor(config.dangerColor());
        }
        else if (nextSpec == 1)
        {
            phaseSpecialComponent.setBackgroundColor(config.warningColor());
        }
        else
        {
            phaseSpecialComponent.setBackgroundColor(STANDARD_BACKGROUND_COLOR);
        }

        phaseSpecialComponent.setImage(hydra.getPhase().getSpecialImage(spriteManager));
        phaseSpecialComponent.setText(INFO_BOX_TEXT_PADDING + nextSpec);

        panelComponent.getChildren().add(phaseSpecialComponent);
    }

    private void updatePrayerComponent()
    {
        final Prayer nextPrayer = hydra.getNextAttack().getPrayer();
        final int nextSwitch = hydra.getNextSwitch();

        if (nextSwitch == 1)
        {
            prayerComponent.setBackgroundColor(client.isPrayerActive(nextPrayer.getApiPrayer()) ? config.warningColor() : config.dangerColor());
        }
        else
        {
            prayerComponent.setBackgroundColor(client.isPrayerActive(nextPrayer.getApiPrayer()) ? config.safeColor() : config.dangerColor());
        }

        prayerComponent.setImage(hydra.getNextAttack().getImage(spriteManager));
        prayerComponent.setText(INFO_BOX_TEXT_PADDING + nextSwitch);

        panelComponent.getChildren().add(prayerComponent);
    }

    private void renderPrayerWidget(final Graphics2D graphics2D)
    {
        final Prayer prayer = hydra.getNextAttack().getPrayer();

        GUIOverlayUtil.renderPrayerOverlay(graphics2D, client, prayer, prayer == Prayer.PROTECT_FROM_MAGIC ? Color.CYAN : Color.GREEN);
    }

    private boolean isSpecialAttack()
    {
        final HydraPhase phase = hydra.getPhase();

        switch (phase)
        {
            case FLAME:
                final NPC npc = hydra.getNpc();
                return hydra.getNextSpecialRelative() == 0 || (npc != null && npc.getInteracting() == null);
            case POISON:
            case LIGHTNING:
            case ENRAGED:
                return hydra.getNextSpecialRelative() == 0;
        }

        return false;
    }

    private BufferedImage getStunImage()
    {

        final BufferedImage tmp = spriteManager.getSprite(hydraPlugin.BIG_ASS_GREY_ENTANGLE, 0);
        return tmp == null ? null : ImageUtil.resizeImage(tmp, IMAGE_SIZE, IMAGE_SIZE);
    }
}
