package com.cheating.theatre;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.theatre.Bloat.Bloat;
import com.cheating.theatre.Nylocas.Nylocas;
import com.cheating.theatre.Sotetseg.Sotetseg;
import com.cheating.theatre.Verzik.Verzik;
import com.cheating.theatre.Xarpus.Xarpus;
import com.cheating.theatre.maiden.Maiden;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.*;
import net.runelite.client.events.ConfigChanged;

import javax.inject.Inject;

@Slf4j
public class TheatrePlugin extends Cheat {

    @Inject
    protected TheatrePlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Inject
    private Maiden maiden;

    @Inject
    private Bloat bloat;

    @Inject
    private Nylocas nylocas;

    @Inject
    private Sotetseg sotetseg;

    @Inject
    private Xarpus xarpus;

    @Inject
    private Verzik verzik;

    @Inject
    private Client client;

    public static final Integer MAIDEN_REGION = 12869;
    public static final Integer BLOAT_REGION = 13125;
    public static final Integer NYLOCAS_REGION = 13122;
    public static final Integer SOTETSEG_REGION_OVERWORLD = 13123;
    public static final Integer SOTETSEG_REGION_UNDERWORLD = 13379;
    public static final Integer XARPUS_REGION = 12612;
    public static final Integer VERZIK_REGION = 12611;


    private Room[] rooms = null;

    private boolean tobActive;
    public static int partySize;

    @Override
    public void startUp() {
        if (rooms == null)
        {
            rooms = new Room[]{maiden, bloat, nylocas, sotetseg, xarpus, verzik};


            for (Room room : rooms)
            {
                log.info("Checking room");
                room.init();
            }
        }

        for (Room room : rooms)
        {
            room.load();
        }
    }

    @Override
    public void shutDown() {
        for (Room room : rooms)
        {
            room.unload();
        }
    }



    @Override
    public void onGameTick(GameTick event) {
        if (tobActive)
        {
            partySize = 0;
            for (int i = 330; i < 335; i++)
            {
                if (client.getVarcStrValue(i) != null && !client.getVarcStrValue(i).equals(""))
                {
                    partySize++;
                }
            }
        }

        maiden.onGameTick(event);
        bloat.onGameTick(event);
        nylocas.onGameTick(event);
        sotetseg.onGameTick(event);
        xarpus.onGameTick(event);
        verzik.onGameTick(event);
    }

    @Override
    public void onClientTick(ClientTick event) {
        nylocas.onClientTick(event);
        xarpus.onClientTick(event);
        verzik.onClientTick(event);
    }

    @Override
    public void onVarbitChanged(VarbitChanged event) {
        tobActive = client.getVarbitValue(Varbits.THEATRE_OF_BLOOD) > 1;

        bloat.onVarbitChanged(event);
        nylocas.onVarbitChanged(event);
        xarpus.onVarbitChanged(event);
    }

    @Override
    public void onNpcSpawned(NpcSpawned event) {
        maiden.onNpcSpawned(event);
        bloat.onNpcSpawned(event);
        nylocas.onNpcSpawned(event);
        sotetseg.onNpcSpawned(event);
        xarpus.onNpcSpawned(event);
        verzik.onNpcSpawned(event);
    }

    @Override
    public void onNpcDespawned(NpcDespawned event) {
        maiden.onNpcDespawned(event);
        bloat.onNpcDespawned(event);
        nylocas.onNpcDespawned(event);
        sotetseg.onNpcDespawned(event);
        xarpus.onNpcDespawned(event);
        verzik.onNpcDespawned(event);
    }

    @Override
    public void onNpcChanged(NpcChanged npcChanged) {
        nylocas.onNpcChanged(npcChanged);
    }



    @Override
    public void onGameStateChanged(GameStateChanged event) {
        bloat.onGameStateChanged(event);
        nylocas.onGameStateChanged(event);
        xarpus.onGameStateChanged(event);
    }

    @Override
    public void onMenuEntryAdded(MenuEntryAdded entry) {
        nylocas.onMenuEntryAdded(entry);
    }

    @Override
    public void onMenuOptionClicked(MenuOptionClicked option)
    {
        nylocas.onMenuOptionClicked(option);
    }

    @Override
    public void onMenuOpened(MenuOpened menu)
    {
        nylocas.onMenuOpened(menu);
    }

    @Override
    public void onConfigChanged(ConfigChanged event) {
        nylocas.onConfigChanged(event);
        verzik.onConfigChanged(event);
    }

    @Override
    public void onGroundObjectSpawned(GroundObjectSpawned event) {
        sotetseg.onGroundObjectSpawned(event);
        xarpus.onGroundObjectSpawned(event);
    }

    @Override
    public void onAnimationChanged(AnimationChanged event) {
        bloat.onAnimationChanged(event);
        nylocas.onAnimationChanged(event);
        sotetseg.onAnimationChanged(event);
    }

    @Override
    public void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObjectC)
    {
        bloat.onGraphicsObjectCreated(graphicsObjectC);
    }

    @Override
    public void onProjectileMoved(ProjectileMoved event)
    {
        verzik.onProjectileMoved(event);
    }

    @Override
    public void onGameObjectSpawned(GameObjectSpawned gameObject)
    {
        verzik.onGameObjectSpawned(gameObject);
    }


}
