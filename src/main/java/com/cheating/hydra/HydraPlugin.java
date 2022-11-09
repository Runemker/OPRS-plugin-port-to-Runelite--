package com.cheating.hydra;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.hydra.entity.Hydra;
import com.cheating.hydra.entity.HydraPhase;
import com.cheating.hydra.overlay.AttackOverlay;
import com.cheating.hydra.overlay.PrayerOverlay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

import com.cheating.hydra.overlay.SceneOverlay;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
public class HydraPlugin extends Cheat
{
    private static final String MESSAGE_NEUTRALIZE = "The chemicals neutralise the Alchemical Hydra's defences!";
    private static final String MESSAGE_STUN = "The Alchemical Hydra temporarily stuns you.";

    private static final int[] HYDRA_REGIONS = {5279, 5280, 5535, 5536};

    @Inject
    private Client client;

    @Getter
    @Inject
    private ClientThread clientThread;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    protected HydraPlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Inject
    private AttackOverlay attackOverlay;

    @Inject
    private SceneOverlay sceneOverlay;

    @Inject
    private PrayerOverlay prayerOverlay;

    private boolean atHydra;

    @Getter
    private Hydra hydra;

    public static final int HYDRA_POISON_PHASE_ID = 8615;
    public static final int HYDRA_LIGHTNING_PHASE_ID = 8619;
    public static final int HYDRA_FLAME_PHASE_ID = 8620;
    public static final int HYDRA_ENRAGED_PHASE_ID = 8621;


    public static final int HYDRA_1_1 = 8237;
    public static final int HYDRA_1_2 = 8238;
    public static final int HYDRA_LIGHTNING = 8241;
    public static final int HYDRA_2_1 = 8244;
    public static final int HYDRA_2_2 = 8245;
    public static final int HYDRA_FIRE = 8248;
    public static final int HYDRA_3_1 = 8251;
    public static final int HYDRA_3_2 = 8252;
    public static final int HYDRA_4_1 = 8257;
    public static final int HYDRA_4_2 = 8258;

    public static final int HYDRA_MAGIC = 1662;
    public static final int HYDRA_RANGED = 1663;
    public static final int HYDRA_POISON = 1644;

    public static final int BIG_ASS_GUTHIX_SPELL = 1774;
    public static final int BIG_SPEC_TRANSFER = 1959;
    public static final int BIG_SUPERHEAT = 1800;
    public static final int BIG_ASS_GREY_ENTANGLE = 1788;

    @Getter
    int fountainTicks = -1;
    int lastFountainAnim = -1;

    @Getter
    private final Map<LocalPoint, Projectile> poisonProjectiles = new HashMap<>();

    private int lastAttackTick = -1;

    @Getter
    private final Set<GameObject> vents = new HashSet<>();

    @Override
    public void startUp()
    {
        if (client.getGameState() == GameState.LOGGED_IN && isInHydraRegion())
        {
            init();
        }
    }

    private void init()
    {
        atHydra = true;

        addOverlays();

        for (final NPC npc : client.getNpcs())
        {
            onNpcSpawned(new NpcSpawned(npc));
        }
    }

    @Override
    public void shutDown()
    {
        atHydra = false;

        removeOverlays();

        hydra = null;
        poisonProjectiles.clear();
        lastAttackTick = -1;
        fountainTicks = -1;
        vents.clear();
        lastFountainAnim = -1;
    }

    @Override
    public void onGameStateChanged(final GameStateChanged event)
    {
        final GameState gameState = event.getGameState();

        switch (gameState)
        {
            case LOGGED_IN:
                if (isInHydraRegion())
                {
                    if (!atHydra)
                    {
                        init();
                    }
                }
                else
                {
                    if (atHydra)
                    {
                        shutDown();
                    }
                }
                break;
            case HOPPING:
            case LOGIN_SCREEN:
                if (atHydra)
                {
                    shutDown();
                }
            default:
                break;
        }
    }

    @Override
    public void onGraphicsObjectCreated(GraphicsObjectCreated event) {
        if (hydra != null){
            if (hydra.getPhase() == HydraPhase.LIGHTNING){
                if (event.getGraphicsObject().getId() == 1666 && hydra.getNextSpecialRelative() == 0){
                    hydra.setNextSpecial();
                }
            }

            if (hydra.getPhase() == HydraPhase.FLAME){
                if (event.getGraphicsObject().getId() == 1668 && hydra.getNextSpecialRelative() == 0){
                    hydra.setNextSpecial();
                }
            }
        }
    }

    @Override
    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        if (!isInHydraRegion())
        {
            return;
        }
        GameObject gameobject = event.getGameObject();
        int id = gameobject.getId();
        if (id == ObjectID.CHEMICAL_VENT_RED || id == ObjectID.CHEMICAL_VENT_GREEN || id == ObjectID.CHEMICAL_VENT_BLUE)
        {
            vents.add(gameobject);
        }
    }

    @Override
    public void onGameObjectDespawned(GameObjectDespawned event)
    {
        GameObject gameobject = event.getGameObject();
        vents.remove(gameobject);
    }


    @Override
    public void onGameTick(final GameTick event)
    {
        attackOverlay.decrementStunTicks();
        updateVentTicks();

        if (hydra != null) {
            CheckHydraTransition();
        }
    }

    private void updateVentTicks()
    {
        if (fountainTicks > 0)
        {
            fountainTicks--;
            if (fountainTicks == 0)
            {
                fountainTicks = 8;
            }
        }

        if (!vents.isEmpty())
        {
            for (final GameObject vent : vents)
            {
                int animation = getAnimation(vent);
                if (animation == 8279 && lastFountainAnim == 8280)
                {
                    fountainTicks = 2;
                }
                lastFountainAnim = animation;
                break; // all vents trigger at same time so dont bother going through them all
            }
        }

    }

    int getAnimation(GameObject gameObject)
    {
        final DynamicObject dynamicObject = (DynamicObject) gameObject.getRenderable();
        return dynamicObject.getAnimation().getId();
    }

    @Override
    public void onNpcSpawned(final NpcSpawned event)
    {
        final NPC npc = event.getNpc();

        if (npc.getId() == NpcID.ALCHEMICAL_HYDRA)
        {
            hydra = new Hydra(npc);
            if (client.isInInstancedRegion() && fountainTicks == -1) //handles the initial hydra spawn when your in the lobby but havent gone through the main doors
            {
                fountainTicks = 11;
            }
        }
    }

    @Override
    public void onNpcDespawned(final NpcDespawned event)
    {
        if (event.getNpc() == null || event == null){
            return;
        }

        NPC npc = event.getNpc();
        if (npc.getName() != null){
            if (npc.getName().contains("Alchemical Hydra")){
                hydra = null;
            }
        }
    }

    @Override
    public void onProjectileMoved(final ProjectileMoved event)
    {
        final Projectile projectile = event.getProjectile();

        if (hydra == null || client.getGameCycle() >= projectile.getStartCycle())
        {
            return;
        }

        final int projectileId = projectile.getId();

        if (hydra.getPhase().getSpecialProjectileId() == projectileId)
        {
            if (hydra.getAttackCount() >= hydra.getNextSpecial())
            {
                hydra.setNextSpecial();
            }

            poisonProjectiles.put(event.getPosition(), projectile);
        }
        else if (client.getTickCount() != lastAttackTick
                && (projectileId == Hydra.AttackStyle.MAGIC.getProjectileID() || projectileId == Hydra.AttackStyle.RANGED.getProjectileID()))
        {
            hydra.handleProjectile(projectileId);

            lastAttackTick = client.getTickCount();
        }
    }

    @Override
    public void onChatMessage(final ChatMessage event)
    {
        final ChatMessageType chatMessageType = event.getType();

        if (chatMessageType != ChatMessageType.SPAM && chatMessageType != ChatMessageType.GAMEMESSAGE)
        {
            return;
        }

        final String message = event.getMessage();

        if (message.equals(MESSAGE_NEUTRALIZE))
        {
            clientThread.invokeLater(() ->
            {
                hydra.setImmunity(false);
            });
        }
        else if (message.equals(MESSAGE_STUN))
        {
            attackOverlay.setStunTicks();
        }
    }

    private void addOverlays()
    {
        overlayManager.add(sceneOverlay);
        overlayManager.add(attackOverlay);
        overlayManager.add(prayerOverlay);
    }

    private void removeOverlays()
    {
        overlayManager.remove(sceneOverlay);
        overlayManager.remove(attackOverlay);
        overlayManager.remove(prayerOverlay);
    }

    private boolean isInHydraRegion()
    {
        return client.isInInstancedRegion() && Arrays.equals(client.getMapRegions(), HYDRA_REGIONS);
    }

    private void CheckHydraTransition(){
        HydraPhase phase = hydra.getPhase();

        for (NPC npc : client.getNpcs())
        {
            switch(npc.getId()){
                case HYDRA_LIGHTNING_PHASE_ID:
                    if (phase != HydraPhase.LIGHTNING){
                        hydra.changePhase(HydraPhase.LIGHTNING);
                    }
                    break;
                case HYDRA_FLAME_PHASE_ID:
                    if (phase != HydraPhase.FLAME){
                        hydra.changePhase(HydraPhase.FLAME);
                    }
                    break;
                case HYDRA_ENRAGED_PHASE_ID:
                    if (phase != HydraPhase.ENRAGED){
                        hydra.changePhase(HydraPhase.ENRAGED);
                    }
                    break;
            }
        }
    }
}

