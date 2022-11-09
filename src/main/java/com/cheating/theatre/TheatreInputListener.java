package com.cheating.theatre;

import com.cheating.CheatingConfig;
import com.cheating.theatre.Nylocas.Nylocas;
import com.google.inject.Provides;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.MouseAdapter;

public class TheatreInputListener extends MouseAdapter
{
    @Inject
    private Nylocas nylocas;

    @Provides
    CheatingConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(CheatingConfig.class);
    }

    @Inject
    private CheatingConfig config;

    @Override
    public MouseEvent mouseReleased(MouseEvent event)
    {
        if (nylocas.getNyloSelectionManager().isHidden())
        {
            return event;
        }

        if (nylocas.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            event.consume();
            return event;
        }
        return event;
    }

    @Override
    public MouseEvent mousePressed(MouseEvent event)
    {
        if (nylocas.getNyloSelectionManager().isHidden())
        {
            return event;
        }

        if (nylocas.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            event.consume();
            return event;
        }
        return event;
    }

    @Override
    public MouseEvent mouseClicked(MouseEvent event)
    {
        if (nylocas.getNyloSelectionManager().isHidden())
        {
            return event;
        }
        if (event.getButton() == MouseEvent.BUTTON1 && nylocas.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            if (nylocas.getNyloSelectionManager().getMeleeBounds().contains(event.getPoint()))
            {
                config.setHighlightMeleeNylo(!config.getHighlightMeleeNylo());
                nylocas.getNyloSelectionManager().getMelee().setSelected(config.getHighlightMeleeNylo());
            }
            else if (nylocas.getNyloSelectionManager().getRangeBounds().contains(event.getPoint()))
            {
                config.setHighlightRangeNylo(!config.getHighlightRangeNylo());
                nylocas.getNyloSelectionManager().getRange().setSelected(config.getHighlightRangeNylo());
            }
            else if (nylocas.getNyloSelectionManager().getMageBounds().contains(event.getPoint()))
            {
                config.setHighlightMageNylo(!config.getHighlightMageNylo());
                nylocas.getNyloSelectionManager().getMage().setSelected(config.getHighlightMageNylo());
            }
            event.consume();
        }
        return event;
    }

    @Override
    public MouseEvent mouseMoved(MouseEvent event)
    {
        if (nylocas.getNyloSelectionManager().isHidden())
        {
            return event;
        }

        nylocas.getNyloSelectionManager().getMelee().setHovered(false);
        nylocas.getNyloSelectionManager().getRange().setHovered(false);
        nylocas.getNyloSelectionManager().getMage().setHovered(false);

        if (nylocas.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            if (nylocas.getNyloSelectionManager().getMeleeBounds().contains(event.getPoint()))
            {
                nylocas.getNyloSelectionManager().getMelee().setHovered(true);
            }
            else if (nylocas.getNyloSelectionManager().getRangeBounds().contains(event.getPoint()))
            {
                nylocas.getNyloSelectionManager().getRange().setHovered(true);
            }
            else if (nylocas.getNyloSelectionManager().getMageBounds().contains(event.getPoint()))
            {
                nylocas.getNyloSelectionManager().getMage().setHovered(true);
            }
        }
        return event;
    }
}
