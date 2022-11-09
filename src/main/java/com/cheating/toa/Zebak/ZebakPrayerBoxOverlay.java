package com.cheating.toa.Zebak;

import com.cheating.CheatingConfig;
import com.cheating.toa.Prayer.NextAttack;
import com.cheating.toa.Prayer.PrayerBoxOverlay;
import net.runelite.api.Client;
import net.runelite.client.game.SpriteManager;

import javax.inject.Inject;
import java.util.Queue;

public class ZebakPrayerBoxOverlay extends PrayerBoxOverlay
{
    private final Zebak plugin;

    @Inject
    protected ZebakPrayerBoxOverlay(Client client, CheatingConfig config, Zebak plugin, SpriteManager spriteManager)
    {
        super(client, config, spriteManager);
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
