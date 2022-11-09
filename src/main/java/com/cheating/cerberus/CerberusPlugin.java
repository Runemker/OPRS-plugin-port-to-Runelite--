package com.cheating.cerberus;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.Prayer;
import com.cheating.cerberus.domain.Cerberus;
import com.cheating.cerberus.domain.CerberusAttack;
import com.cheating.cerberus.domain.Ghost;
import com.cheating.cerberus.domain.Phase;
import com.cheating.cerberus.overlays.CurrentAttackOverlay;
import com.cheating.cerberus.overlays.PrayerOverlay;
import com.cheating.cerberus.overlays.SceneOverlay;
import com.cheating.cerberus.overlays.UpcomingAttackOverlay;
import com.google.common.collect.ComparisonChain;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Projectile;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.http.api.item.ItemEquipmentStats;
import net.runelite.http.api.item.ItemStats;

@Slf4j
@Singleton
public class CerberusPlugin extends Cheat
{
    private static final int ANIMATION_ID_IDLE = -1;
    private static final int ANIMATION_ID_STAND_UP = 4486;
    private static final int ANIMATION_ID_SIT_DOWN = 4487;
    private static final int ANIMATION_ID_FLINCH = 4489;
    private static final int ANIMATION_ID_RANGED = 4490;
    private static final int ANIMATION_ID_MELEE = 4491;
    private static final int ANIMATION_ID_LAVA = 4493;
    private static final int ANIMATION_ID_GHOSTS = 4494;
    private static final int ANIMATION_ID_DEATH = 4495;

    private static final int PROJECTILE_ID_MAGIC = 1242;
    private static final int PROJECTILE_ID_RANGE = 1245;

    private static final int GHOST_PROJECTILE_ID_RANGE = 34;
    private static final int GHOST_PROJECTILE_ID_MAGIC = 100;
    private static final int GHOST_PROJECTILE_ID_MELEE = 1248;

    private static final int PROJECTILE_ID_NO_FUCKING_IDEA = 15;
    private static final int PROJECTILE_ID_LAVA = 1247;

    private static final Set<Integer> REGION_IDS = Set.of(4883, 5140, 5395);

    @Inject
    private Client client;

    @Inject
    private CheatingConfig config;

    @Inject
    private ItemManager itemManager;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private SceneOverlay sceneOverlay;

    @Inject
    private PrayerOverlay prayerOverlay;

    @Inject
    private CurrentAttackOverlay currentAttackOverlay;

    @Inject
    private UpcomingAttackOverlay upcomingAttackOverlay;

    @Getter
    private final List<NPC> ghosts = new ArrayList<>();

    @Getter
    private final List<CerberusAttack> upcomingAttacks = new ArrayList<>();

    private final List<Long> tickTimestamps = new ArrayList<>();

    @Inject
    protected CerberusPlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Getter
    @Nullable
    private Prayer prayer = Prayer.PROTECT_FROM_MAGIC;

    @Getter
    @Nullable
    private Cerberus cerberus;

    @Getter
    private int gameTick;

    private int tickTimestampIndex;

    @Getter
    private long lastTick;

    private boolean inArena;

    private int projectileRemainingCycle;

    @Override
    public void startUp()
    {
        if (client.getGameState() != GameState.LOGGED_IN || !inCerberusRegion())
        {
            return;
        }

        init();
    }

    private void init()
    {
        inArena = true;

        overlayManager.add(sceneOverlay);
        overlayManager.add(prayerOverlay);
        overlayManager.add(currentAttackOverlay);
        overlayManager.add(upcomingAttackOverlay);
    }

    @Override
    public void shutDown()
    {
        inArena = false;

        overlayManager.remove(sceneOverlay);
        overlayManager.remove(prayerOverlay);
        overlayManager.remove(currentAttackOverlay);
        overlayManager.remove(upcomingAttackOverlay);

        ghosts.clear();
        upcomingAttacks.clear();
        tickTimestamps.clear();

        prayer = Prayer.PROTECT_FROM_MAGIC;

        cerberus = null;

        projectileRemainingCycle = 0;
        gameTick = 0;
        tickTimestampIndex = 0;
        lastTick = 0;
    }

    @Override
    public void onGameStateChanged(final GameStateChanged event)
    {
        final GameState gameState = event.getGameState();

        switch (gameState)
        {
            case LOGGED_IN:
                if (inCerberusRegion())
                {
                    if (!inArena)
                    {
                        init();
                    }
                }
                else
                {
                    if (inArena)
                    {
                        shutDown();
                    }
                }
                break;
            case HOPPING:
            case LOGIN_SCREEN:
                if (inArena)
                {
                    shutDown();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onGameTick(final GameTick event)
    {
        if (cerberus == null)
        {
            return;
        }

        if (tickTimestamps.size() <= tickTimestampIndex)
        {
            tickTimestamps.add(System.currentTimeMillis());
        }
        else
        {
            tickTimestamps.set(tickTimestampIndex, System.currentTimeMillis());
        }

        long min = 0;

        for (int i = 0; i < tickTimestamps.size(); ++i)
        {
            if (min == 0)
            {
                min = tickTimestamps.get(i) + 600 * ((tickTimestampIndex - i + 5) % 5);
            }
            else
            {
                min = Math.min(min, tickTimestamps.get(i) + 600 * ((tickTimestampIndex - i + 5) % 5));
            }
        }

        tickTimestampIndex = (tickTimestampIndex + 1) % 5;

        lastTick = min;

        ++gameTick;

        if (config.calculateAutoAttackPrayer() && gameTick % 10 == 3)
        {
            setAutoAttackPrayer();
        }

        calculateUpcomingAttacks();

        if (ghosts.size() > 1)
        {
            /*
             * First, sort by the southernmost ghost (e.g with lowest y).
             * Then, sort by the westernmost ghost (e.g with lowest x).
             * This will give use the current wave and order of the ghosts based on what ghost will attack first.
             */
            ghosts.sort((a, b) -> ComparisonChain.start()
                    .compare(a.getLocalLocation().getY(), b.getLocalLocation().getY())
                    .compare(a.getLocalLocation().getX(), b.getLocalLocation().getX())
                    .result());
        }
    }

    @Override
    public void onProjectileMoved(final ProjectileMoved event)
    {
        if (cerberus == null)
        {
            return;
        }


        final Projectile projectile = event.getProjectile();

        if (projectile.getId() == PROJECTILE_ID_MAGIC){
            if (projectile.getRemainingCycles() > projectileRemainingCycle ) {
                //New magic projectile spawned
                final int hp = cerberus.getHp();
                final Phase expectedAttack = cerberus.getNextAttackPhase(1, hp);

                log.debug("gameTick={}, attack={}, cerbHp={}, expectedAttack={}, cerbProjectile={}, projectileRemainingCycle={}, actualRemainingCycles={}", gameTick, cerberus.getPhaseCount() + 1, hp, expectedAttack, "MAGIC", projectileRemainingCycle, projectile.getRemainingCycles());
                if (expectedAttack != Phase.TRIPLE) {
                    cerberus.nextPhase(Phase.AUTO);
                } else {
                    cerberus.setLastTripleAttack(Cerberus.Attack.MAGIC);
                }

                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.MAGIC);
            }
            projectileRemainingCycle = projectile.getRemainingCycles();
        }
    }

    @Override
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (cerberus == null)
        {
            return;
        }

        final Actor actor = event.getActor();

        final NPC npc = cerberus.getNpc();

        if (npc == null || actor != npc)
        {
            return;
        }

        final int animationId = npc.getAnimation();

        final int hp = cerberus.getHp();

        final Phase expectedAttack = cerberus.getNextAttackPhase(1, hp);

        switch (animationId)
        {
            case ANIMATION_ID_RANGED:
                log.debug("gameTick={}, attack={}, cerbHp={}, expectedAttack={}, cerbProjectile={}", gameTick, cerberus.getPhaseCount() + 1, hp, expectedAttack, "RANGED");
                if (expectedAttack != Phase.TRIPLE)
                {
                    cerberus.nextPhase(Phase.AUTO);
                }
                else
                {
                    cerberus.setLastTripleAttack(Cerberus.Attack.RANGED);
                }
                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.RANGED);
                break;
            case ANIMATION_ID_MELEE:
                log.debug("gameTick={}, attack={}, cerbHp={}, expectedAttack={}, cerbAnimation={}", gameTick, cerberus.getPhaseCount() + 1, hp, expectedAttack, "MELEE");

                cerberus.setLastTripleAttack(null);
                cerberus.nextPhase(expectedAttack);
                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.MELEE);
                break;
            case ANIMATION_ID_LAVA:
                log.debug("gameTick={}, attack={}, cerbHp={}, expectedAttack={}, cerbAnimation={}", gameTick, cerberus.getPhaseCount() + 1, hp, expectedAttack, "LAVA");

                cerberus.nextPhase(Phase.LAVA);
                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.LAVA);
                break;
            case ANIMATION_ID_GHOSTS:
                log.debug("gameTick={}, attack={}, cerbHp={}, expectedAttack={}, cerbAnimation={}", gameTick, cerberus.getPhaseCount() + 1, hp, expectedAttack, "GHOSTS");

                cerberus.nextPhase(Phase.GHOSTS);
                cerberus.setLastGhostYellTick(gameTick);
                cerberus.setLastGhostYellTime(System.currentTimeMillis());
                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.GHOSTS);
                break;
            case ANIMATION_ID_SIT_DOWN:
            case ANIMATION_ID_STAND_UP:
                cerberus = new Cerberus(cerberus.getNpc());
                gameTick = 0;
                lastTick = System.currentTimeMillis();
                upcomingAttacks.clear();
                tickTimestamps.clear();
                tickTimestampIndex = 0;
                cerberus.doProjectileOrAnimation(gameTick, Cerberus.Attack.SPAWN);
                break;
            case ANIMATION_ID_DEATH:
                cerberus = null;
                ghosts.clear();
                log.debug("Cerberus died");
                break;
            case ANIMATION_ID_FLINCH:
            case ANIMATION_ID_IDLE:
                break;
            default:
                log.debug("gameTick={}, animationId={} (UNKNOWN)", gameTick, animationId);
                break;
        }
    }

    @Override
    public void onNpcSpawned(final NpcSpawned event)
    {
        final NPC npc = event.getNpc();

        if (cerberus == null && npc != null && npc.getName() != null && npc.getName().toLowerCase().contains("cerberus"))
        {
            log.debug("onNpcSpawned name={}, id={}", npc.getName(), npc.getId());

            cerberus = new Cerberus(npc);
            gameTick = 0;
            projectileRemainingCycle = 0;
            tickTimestampIndex = 0;
            lastTick = System.currentTimeMillis();

            upcomingAttacks.clear();
            tickTimestamps.clear();
        }

        if (cerberus == null)
        {
            return;
        }

        final Ghost ghost = Ghost.fromNPC(npc);

        if (ghost != null)
        {
            ghosts.add(npc);
        }
    }

    @Override
    public void onNpcDespawned(final NpcDespawned event)
    {
        final NPC npc = event.getNpc();

        if (npc != null && npc.getName() != null && npc.getName().toLowerCase().contains("cerberus"))
        {
            cerberus = null;
            ghosts.clear();

            log.debug("onNpcDespawned name={}, id={}", npc.getName(), npc.getId());
        }

        if (cerberus == null && !ghosts.isEmpty())
        {
            ghosts.clear();
            return;
        }

        ghosts.remove(event.getNpc());
    }

    private void calculateUpcomingAttacks()
    {
        upcomingAttacks.clear();

        final Cerberus.Attack lastCerberusAttack = cerberus.getLastAttack();

        if (lastCerberusAttack == null)
        {
            return;
        }

        final int lastCerberusAttackTick = cerberus.getLastAttackTick();

        final int hp = cerberus.getHp();

        final Phase expectedPhase = cerberus.getNextAttackPhase(1, hp);
        final Phase lastPhase = cerberus.getLastAttackPhase();

        int tickDelay = 0;

        if (lastPhase != null)
        {
            tickDelay = lastPhase.getTickDelay();
        }

        for (int tick = gameTick + 1; tick <= gameTick + 10; ++tick)
        {
            if (ghosts.size() == 3)
            {
                final Ghost ghost;

                if (cerberus.getLastGhostYellTick() == tick - 13)
                {
                    ghost = Ghost.fromNPC(ghosts.get(ghosts.size() - 3));
                }
                else if (cerberus.getLastGhostYellTick() == tick - 15)
                {
                    ghost = Ghost.fromNPC(ghosts.get(ghosts.size() - 2));
                }
                else if (cerberus.getLastGhostYellTick() == tick - 17)
                {
                    ghost = Ghost.fromNPC(ghosts.get(ghosts.size() - 1));
                }
                else
                {
                    ghost = null;
                }

                if (ghost != null)
                {
                    switch (ghost.getType())
                    {
                        case ATTACK:
                            upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.GHOST_MELEE));
                            break;
                        case RANGED:
                            upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.GHOST_RANGED));
                            break;
                        case MAGIC:
                            upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.GHOST_MAGIC));
                            break;
                    }

                    continue;
                }
            }

            if (expectedPhase == Phase.TRIPLE)
            {
                if (cerberus.getLastTripleAttack() == Cerberus.Attack.MAGIC)
                {
                    if (lastCerberusAttackTick + 4 == tick)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.RANGED));
                    }
                    else if (lastCerberusAttackTick + 7 == tick)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.MELEE));
                    }
                }
                else if (cerberus.getLastTripleAttack() == Cerberus.Attack.RANGED)
                {
                    if (lastCerberusAttackTick + 4 == tick)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.MELEE));
                    }
                }
                else if (cerberus.getLastTripleAttack() == null)
                {
                    if (lastCerberusAttackTick + tickDelay + 2 == tick)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.MAGIC));
                    }
                    else if (lastCerberusAttackTick + tickDelay + 5 == tick)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.RANGED));
                    }
                }
            }
            else if (expectedPhase == Phase.AUTO)
            {
                if (lastCerberusAttackTick + tickDelay + 1 == tick)
                {
                    if (prayer == Prayer.PROTECT_FROM_MAGIC)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.MAGIC));
                    }
                    else if (prayer == Prayer.PROTECT_FROM_MISSILES)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.RANGED));
                    }
                    else if (prayer == Prayer.PROTECT_FROM_MELEE)
                    {
                        upcomingAttacks.add(new CerberusAttack(tick, Cerberus.Attack.MELEE));
                    }
                }
            }
        }
    }

    private void setAutoAttackPrayer()
    {
        int defenseStab = 0, defenseMagic = 0, defenseRange = 0;

        final ItemContainer itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);

        if (itemContainer != null)
        {
            final Item[] items = itemContainer.getItems();

            for (final Item item : items)
            {
                if (item == null)
                {
                    continue;
                }

                final ItemStats itemStats = itemManager.getItemStats(item.getId(), false);

                if (itemStats == null)
                {
                    continue;
                }

                final ItemEquipmentStats itemStatsEquipment = itemStats.getEquipment();

                if (itemStatsEquipment == null)
                {
                    continue;
                }

                defenseStab += itemStatsEquipment.getDstab();
                defenseMagic += itemStatsEquipment.getDmagic();
                defenseRange += itemStatsEquipment.getDrange();
            }
        }

        final int magicLvl = client.getBoostedSkillLevel(Skill.MAGIC);
        final int defenseLvl = client.getBoostedSkillLevel(Skill.DEFENCE);

        final int magicDefenseTotal = (int) (((double) magicLvl) * 0.7 + ((double) defenseLvl) * 0.3) + defenseMagic;
        final int rangeDefenseTotal = defenseLvl + defenseRange;

        int meleeDefenseTotal = defenseLvl + defenseStab;

        final Player player = client.getLocalPlayer();

        if (player != null)
        {
            final WorldPoint worldPointPlayer = client.getLocalPlayer().getWorldLocation();
            final WorldPoint worldPointCerberus = cerberus.getNpc().getWorldLocation();

            if (worldPointPlayer.getX() < worldPointCerberus.getX() - 1
                    || worldPointPlayer.getX() > worldPointCerberus.getX() + 5
                    || worldPointPlayer.getY() < worldPointCerberus.getY() - 1
                    || worldPointPlayer.getY() > worldPointCerberus.getY() + 5)
            {
                meleeDefenseTotal = Integer.MAX_VALUE;
            }
        }

        if (magicDefenseTotal <= rangeDefenseTotal && magicDefenseTotal <= meleeDefenseTotal)
        {
            prayer = Prayer.PROTECT_FROM_MAGIC;
        }
        else if (rangeDefenseTotal <= meleeDefenseTotal)
        {
            prayer = Prayer.PROTECT_FROM_MISSILES;
        }
        else
        {
            prayer = Prayer.PROTECT_FROM_MELEE;
        }
    }

    private boolean inCerberusRegion()
    {
        for (final int regionId : client.getMapRegions())
        {
            if (REGION_IDS.contains(regionId))
            {
                return true;
            }
        }

        return false;
    }
}