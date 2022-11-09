package com.cheating.toa.Baba;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.toa.Het.HetOverlay;
import com.cheating.toa.Room;
import com.cheating.toa.ToaPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Baba extends Room {
    @Inject
    private Client client;

    @Inject
    private BabaOverlay babaOverlay;

    @Inject
    protected Baba(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Getter
    private boolean babaActive;

    public static final int BOULDER_ID = 11782;
    public static final int BOULDER_BROKEN_ID = 11783;
    public static final int BANANA_ID = 45755;
    public static final int FALLING_BOULDER_ID = 2250;
    public static final int SACROPHAGUS_FLAME_ID = 2246;


    @Override
    public void init(){
    }

    @Getter(AccessLevel.PACKAGE)
    private final List<GameObject> objects = new ArrayList<>();

    @Override
    public void load()
    {
        overlayManager.add(babaOverlay);
    }

    @Override
    public void unload()
    {
        overlayManager.remove(babaOverlay);
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (!inRoomRegion(ToaPlugin.BABA_REGION)) {
            babaActive = false;
            return;
        }
        babaActive = true;
    }


}
