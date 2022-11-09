package com.cheating.toa.Scarabas;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.toa.Kephri.KephriOverlay;
import com.cheating.toa.Room;
import com.cheating.toa.ToaPlugin;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scarabas extends Room {
    @Inject
    private Client client;

    @Inject
    private ScarabasOverlay scarabasOverlay;

    @Getter
    private boolean scarabasActive;

    @Inject
    protected Scarabas(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Override
    public void init(){
        scarabasActive = false;
    }

    @Getter(AccessLevel.PACKAGE)
    private final List<GameObject> objects = new ArrayList<>();

    @Override
    public void load()
    {
        overlayManager.add(scarabasOverlay);
    }

    @Override
    public void unload()
    {
        overlayManager.remove(scarabasOverlay);
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (!inRoomRegion(ToaPlugin.SCABARAS_REGION)){
            scarabasActive = false;
            return;
        }
        scarabasActive = true;
    }
}
