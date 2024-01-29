package com.cheating.toa;


import com.cheating.CheatingConfig;

public interface PluginLifecycleComponent
{

    default boolean isEnabled(CheatingConfig config, RaidState raidState)
    {
        return true;
    }

    void startUp();

    void shutDown();

}
