package com.cheating.toa.Apmeken;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.toa.Room;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.GameObject;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.eventbus.Subscribe;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Apmeken extends Room {
    @Inject
    private Client client;

    @Inject
    protected Apmeken(CheatingPlugin plugin, CheatingConfig config) {
        super(plugin, config);
    }

    private static final int OVERHEAD_TEXT_TICK_TIMEOUT = 5;
    private static final int CYCLES_PER_GAME_TICK = Constants.GAME_TICK_LENGTH / Constants.CLIENT_TICK_LENGTH;
    private static final int CYCLES_FOR_OVERHEAD_TEXT = OVERHEAD_TEXT_TICK_TIMEOUT * CYCLES_PER_GAME_TICK;


    @Override
    public void init() {
    }

    @Getter(AccessLevel.PACKAGE)
    private final List<GameObject> objects = new ArrayList<>();

    @Override
    public void load() {
    }

    @Override
    public void unload() {
        ;
    }

    @Subscribe
    public void onChatMessage(final ChatMessage event) {
        switch (event.getMessage()) {
            case "<col=6800bf>You sense an issue with the roof supports.":
                client.getLocalPlayer().setOverheadText("PILLARS, PILLARS, PILLARS");
                client.getLocalPlayer().setOverheadCycle(CYCLES_FOR_OVERHEAD_TEXT);
                break;
            case "<col=6800bf>You sense some strange fumes coming from holes in the floor.":
                client.getLocalPlayer().setOverheadText("VENTS, VENTS, VENTS");
                client.getLocalPlayer().setOverheadCycle(CYCLES_FOR_OVERHEAD_TEXT);
                break;
            case "<col=6800bf>You sense Amascut's corruption beginning to take hold.":
                client.getLocalPlayer().setOverheadText("CORRUPTION, CORRUPTION, CORRUPTION");
                client.getLocalPlayer().setOverheadCycle(CYCLES_FOR_OVERHEAD_TEXT);
                break;
            case "<col=6800bf>You sense an issue somewhere in the room.":
                client.getLocalPlayer().setOverheadText("ISSUE, ISSUE, ISSUE");
                client.getLocalPlayer().setOverheadCycle(CYCLES_FOR_OVERHEAD_TEXT);
                break;
        }
    }
}
