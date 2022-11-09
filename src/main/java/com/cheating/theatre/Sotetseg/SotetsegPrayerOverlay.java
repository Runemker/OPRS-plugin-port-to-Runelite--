package com.cheating.theatre.Sotetseg;

import javax.inject.Inject;

import com.cheating.CheatingConfig;
import com.cheating.theatre.Prayer.TheatrePrayerOverlay;
import com.cheating.theatre.Prayer.TheatreUpcomingAttack;
import net.runelite.api.Client;

import java.util.Queue;

public class SotetsegPrayerOverlay extends TheatrePrayerOverlay
{
    private final Sotetseg plugin;

    @Inject
    protected SotetsegPrayerOverlay(Client client, CheatingConfig config, Sotetseg plugin)
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
        return getConfig().sotetsegPrayerHelper();
    }
}
