package com.cheating.toa.Warden;

import net.runelite.api.Client;
import com.cheating.toa.Prayer.NextAttack;
import com.cheating.toa.Prayer.PrayerOverlay;
import com.cheating.CheatingConfig;

import javax.inject.Inject;
import java.util.Queue;

public class WardenPrayerOverlay extends PrayerOverlay
{
    private final Warden plugin;

    @Inject
    protected WardenPrayerOverlay(Client client, CheatingConfig config, Warden plugin)
    {
        super(client, config);
        this.plugin = plugin;
    }

    @Override
    protected Queue<NextAttack> getAttackQueue()
    {
        return plugin.getNextAttackQueue();
    }

    @Override
    protected long getLastTick()
    {
        return plugin.getLastTick();
    }

    @Override
    protected boolean isEnabled()
    {
        return getConfig().zebakPrayerHelper();
    }
}
