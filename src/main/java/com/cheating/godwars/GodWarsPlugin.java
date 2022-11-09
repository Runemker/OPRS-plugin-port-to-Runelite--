package com.cheating.godwars;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.google.inject.Provides;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


public class GodWarsPlugin extends Cheat
{
    private static final int GENERAL_REGION = 11347;
    private static final int ARMA_REGION = 11346;
    private static final int SARA_REGION = 11602;
    private static final int ZAMMY_REGION = 11603;
    public static final int MINION_AUTO1 = 6154;
    public static final int MINION_AUTO2 = 6156;
    public static final int MINION_AUTO3 = 7071;
    public static final int MINION_AUTO4 = 7073;
    public static final int GENERAL_AUTO1 = 7018;
    public static final int GENERAL_AUTO2 = 7020;
    public static final int GENERAL_AUTO3 = 7021;
    public static final int ZAMMY_GENERIC_AUTO = 64;
    public static final int KRIL_AUTO = 6948;
    public static final int KRIL_SPEC = 6950;
    public static final int ZAKL_AUTO = 7077;
    public static final int BALFRUG_AUTO = 4630;
    public static final int ZILYANA_MELEE_AUTO = 6964;
    public static final int ZILYANA_AUTO = 6967;
    public static final int ZILYANA_SPEC = 6970;
    public static final int STARLIGHT_AUTO = 6376;
    public static final int BREE_AUTO = 7026;
    public static final int GROWLER_AUTO = 7037;
    public static final int KREE_RANGED = 6978;
    public static final int SKREE_AUTO = 6955;
    public static final int GEERIN_AUTO = 6956;
    public static final int GEERIN_FLINCH = 6958;
    public static final int KILISA_AUTO = 6957;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private TimersOverlay timersOverlay;

    @Inject
    private CheatingConfig config;

    @Inject
    protected GodWarsPlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Getter(AccessLevel.PACKAGE)
    private Set<NPCContainer> npcContainers = new HashSet<>();
    private boolean validRegion;

    @Getter(AccessLevel.PACKAGE)
    private long lastTickTime;

    @Override
    public void startUp()
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }
        if (regionCheck())
        {
            npcContainers.clear();
            for (NPC npc : client.getNpcs())
            {
                addNpc(npc);
            }
            validRegion = true;
            overlayManager.add(timersOverlay);
        }
        else if (!regionCheck())
        {
            validRegion = false;
            overlayManager.remove(timersOverlay);
            npcContainers.clear();
        }
    }

    @Override
    public void shutDown()
    {
        npcContainers.clear();
        overlayManager.remove(timersOverlay);
        validRegion = false;
    }

    @Override
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }

        if (regionCheck())
        {
            npcContainers.clear();
            for (NPC npc : client.getNpcs())
            {
                addNpc(npc);
            }
            validRegion = true;
            overlayManager.add(timersOverlay);
        }
        else if (!regionCheck())
        {
            validRegion = false;
            overlayManager.remove(timersOverlay);
            npcContainers.clear();
        }
    }

    @Override
    public void onNpcSpawned(NpcSpawned event)
    {
        if (!validRegion)
        {
            return;
        }

        addNpc(event.getNpc());
    }

    @Override
    public void onNpcDespawned(NpcDespawned event)
    {
        if (!validRegion)
        {
            return;
        }

        removeNpc(event.getNpc());
    }

    @Override
    public void onGameTick(GameTick Event)
    {
        lastTickTime = System.currentTimeMillis();

        if (!validRegion)
        {
            return;
        }

        handleBosses();
    }

    private void handleBosses()
    {
        for (NPCContainer npc : getNpcContainers())
        {
            npc.setNpcInteracting(npc.getNpc().getInteracting());

            if (npc.getTicksUntilAttack() >= 0)
            {
                npc.setTicksUntilAttack(npc.getTicksUntilAttack() - 1);
            }

            for (int animation : npc.getAnimations())
            {
                if (animation == npc.getNpc().getAnimation() && npc.getTicksUntilAttack() < 1)
                {
                    npc.setTicksUntilAttack(npc.getAttackSpeed());
                }
            }
        }
    }

    private boolean regionCheck()
    {
        return Arrays.stream(client.getMapRegions()).anyMatch(
                x -> x == ARMA_REGION || x == GENERAL_REGION || x == ZAMMY_REGION || x == SARA_REGION
        );
    }

    private void addNpc(NPC npc)
    {
        if (npc == null)
        {
            return;
        }

        switch (npc.getId())
        {
            case NpcID.SERGEANT_STRONGSTACK:
            case NpcID.SERGEANT_STEELWILL:
            case NpcID.SERGEANT_GRIMSPIKE:
            case NpcID.GENERAL_GRAARDOR:
            case NpcID.TSTANON_KARLAK:
            case NpcID.BALFRUG_KREEYATH:
            case NpcID.ZAKLN_GRITCH:
            case NpcID.KRIL_TSUTSAROTH:
            case NpcID.STARLIGHT:
            case NpcID.BREE:
            case NpcID.GROWLER:
            case NpcID.COMMANDER_ZILYANA:
            case NpcID.FLIGHT_KILISA:
            case NpcID.FLOCKLEADER_GEERIN:
            case NpcID.WINGMAN_SKREE:
            case NpcID.KREEARRA:
                if (config.gwd())
                {
                    npcContainers.add(new NPCContainer(npc));
                }
                break;
        }
    }

    private void removeNpc(NPC npc)
    {
        if (npc == null)
        {
            return;
        }

        switch (npc.getId())
        {
            case NpcID.SERGEANT_STRONGSTACK:
            case NpcID.SERGEANT_STEELWILL:
            case NpcID.SERGEANT_GRIMSPIKE:
            case NpcID.GENERAL_GRAARDOR:
            case NpcID.TSTANON_KARLAK:
            case NpcID.BALFRUG_KREEYATH:
            case NpcID.ZAKLN_GRITCH:
            case NpcID.KRIL_TSUTSAROTH:
            case NpcID.STARLIGHT:
            case NpcID.BREE:
            case NpcID.GROWLER:
            case NpcID.COMMANDER_ZILYANA:
            case NpcID.FLIGHT_KILISA:
            case NpcID.FLOCKLEADER_GEERIN:
            case NpcID.WINGMAN_SKREE:
            case NpcID.KREEARRA:
                npcContainers.removeIf(c -> c.getNpc() == npc);
                break;
        }
    }
}
