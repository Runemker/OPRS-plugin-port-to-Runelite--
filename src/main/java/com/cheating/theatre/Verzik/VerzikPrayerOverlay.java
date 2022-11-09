package com.cheating.theatre.Verzik;

import java.util.Queue;
import javax.inject.Inject;

import com.cheating.CheatingConfig;
import com.cheating.theatre.Prayer.TheatrePrayerOverlay;
import com.cheating.theatre.Prayer.TheatreUpcomingAttack;
import net.runelite.api.Client;

public class VerzikPrayerOverlay extends TheatrePrayerOverlay
{

    private final Verzik plugin;

    @Inject
    protected VerzikPrayerOverlay(Client client, CheatingConfig config, Verzik plugin)
    {
        super(client, config);
        this.plugin = plugin;
    }

    @Override
    protected Queue<TheatreUpcomingAttack> getAttackQueue()
    {
        return plugin.getUpcomingAttackQueue();
    }

    @Override
    protected long getLastTick()
    {
        return plugin.getLastTick();
    }

    @Override
    protected boolean isEnabled()
    {
        return getConfig().verzikPrayerHelper();
    }
}
