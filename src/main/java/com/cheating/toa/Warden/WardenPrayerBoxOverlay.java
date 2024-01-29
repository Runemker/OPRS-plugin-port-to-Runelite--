package com.cheating.toa.Warden;

import net.runelite.api.Client;
import net.runelite.client.game.SpriteManager;
import com.cheating.toa.Prayer.NextAttack;
import com.cheating.toa.Prayer.PrayerBoxOverlay;
import com.cheating.CheatingConfig;

import javax.inject.Inject;
import java.util.Queue;

public class WardenPrayerBoxOverlay extends PrayerBoxOverlay
{
    private final Warden plugin;

    @Inject
    protected WardenPrayerBoxOverlay(Client client, CheatingConfig config, Warden plugin, SpriteManager spriteManager)
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
