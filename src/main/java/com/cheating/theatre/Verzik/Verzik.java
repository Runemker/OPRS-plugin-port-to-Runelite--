package com.cheating.theatre.Verzik;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.Prayer;
import com.cheating.theatre.Prayer.TheatrePrayerUtil;
import com.cheating.theatre.Prayer.TheatreUpcomingAttack;
import com.cheating.theatre.Room;
import com.google.common.collect.ImmutableSet;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.overlay.infobox.Counter;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class Verzik extends Room
{
    @Inject
    private Client client;

    @Inject
    private VerzikOverlay verzikOverlay;

    @Inject
    private VerzikPrayerOverlay verzikPrayerOverlay;

    @Inject
    private InfoBoxManager infoBoxManager;

    @Inject
    private Verzik(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Getter
    private boolean verzikActive;

    @Getter
    private NPC verzikNPC;

    enum Phase
    {
        PHASE1,
        PHASE2,
        PHASE3
    }

    @Getter
    private Phase verzikPhase;

    enum SpecialAttack
    {
        WEB_COOLDOWN,
        WEBS,
        YELLOWS,
        GREEN,
        NONE
    }

    @Getter
    private SpecialAttack verzikSpecial = SpecialAttack.NONE;

    @Getter
    private int verzikYellows;

    @Getter
    private Map<Projectile, WorldPoint> verzikRangeProjectiles = new HashMap<>();

    @Getter
    private HashSet<NPC> verzikAggros = new HashSet<>();

    @Getter
    private Map<NPC, Pair<Integer, Integer>> verzikReds = new HashMap<>();

    @Getter
    private NPC verzikLocalTornado = null;

    @Getter
    private HashSet<MemorizedTornado> verzikTornadoes = new HashSet<>();

    private WorldPoint lastPlayerLocation0;
    private WorldPoint lastPlayerLocation1;

    @Getter
    private int verzikLightningAttacks = 4;

    private final List<Projectile> verzikRangedAttacks = new ArrayList<>();

    private final Predicate<Projectile> isValidVerzikAttack = (p) ->
            p.getRemainingCycles() > 0 && (p.getId() == VerzikConstant.VERZIK_RANGE_BALL || p.getId() == VerzikConstant.VERZIK_LIGHTNING_BALL);

    @Getter
    private int verzikTicksUntilAttack = -1;

    @Getter
    private int verzikTotalTicksUntilAttack = 0;

    @Getter
    private int verzikAttackCount;

    @Getter(AccessLevel.PACKAGE)
    private long lastTick;

    @Getter(AccessLevel.PACKAGE)
    Queue<TheatreUpcomingAttack> upcomingAttackQueue = new PriorityQueue<>();

    @Getter(AccessLevel.PACKAGE)
    Set<VerzikPoisonTile> verzikPoisonTiles = new HashSet<>();

    @Getter
    private boolean verzikEnraged = false;

    private boolean verzikFirstEnraged = false;
    private boolean verzikTickPaused = true;
    private boolean verzikRedPhase = false;
    private int verzikLastAnimation = -1;

    //P3 Counter vars

    @Getter
    private Counter specialCounter;
    private boolean infobox;

    private static BufferedImage CRAB_IMAGE;
    private static BufferedImage WEBS_IMAGE;
    private static BufferedImage YELLOW_IMAGE;
    private static BufferedImage GREEN_IMAGE;

    @Getter
    private AttackStyle attackStyle;

    //Menu Entry ID's
    private Set<Integer> WEAPON_SET;
    private static final Set<Integer> HELMET_SET = ImmutableSet.of(ItemID.SERPENTINE_HELM, ItemID.TANZANITE_HELM, ItemID.MAGMA_HELM);

    @Getter
    private boolean isHM;
    private static final Set<Integer> VERZIK_HM_ID = ImmutableSet.of(10847, 10848, 10849, 10850, 10851, 10852, 10853);

    private boolean verzikHardmodeSeenYellows = false;

    @Override
    public void init(){
        CRAB_IMAGE = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(CheatingPlugin.class, "/Verzik/Crabs.png"), 1280, 1053);
        GREEN_IMAGE = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(CheatingPlugin.class, "/Verzik/Green_ball.png"), 818, 817);
        WEBS_IMAGE = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(CheatingPlugin.class, "/Verzik/Webs.png"), 1197, 1669);
        YELLOW_IMAGE = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(CheatingPlugin.class, "/Verzik/Yellow_orb.png"), 1032, 1060);
    }

    @Override
    public void load()
    {
        updateConfig();
        overlayManager.add(verzikOverlay);
        overlayManager.add(verzikPrayerOverlay);
    }

    @Override
    public void unload()
    {
        removeInfobox();
        overlayManager.remove(verzikOverlay);
        overlayManager.remove(verzikPrayerOverlay);
        verzikCleanup();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged change)
    {
        if (change.getKey().equals("weaponSet"))
        {
            updateConfig();
        }
    }

    private void updateConfig()
    {
        WEAPON_SET = new HashSet<>();
        for (String s : Text.fromCSV(config.weaponSet()))
        {
            try
            {
                WEAPON_SET.add(Integer.parseInt(s));
                log.debug("Integer added to weapon set: " + Integer.parseInt(s));
            }
            catch (NumberFormatException ignored)
            {
            }

        }
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned)
    {
        NPC npc = npcSpawned.getNpc();
        switch (npc.getId())
        {
            case NpcID.WEB:
            case 10837:
            case 10854:
                if (verzikNPC != null && verzikNPC.getInteracting() == null)
                {
                    verzikSpecial = SpecialAttack.WEBS;
                }
                break;
            case NpcID.NYLOCAS_ISCHYROS_8381:
            case NpcID.NYLOCAS_HAGIOS_8383:
            case NpcID.NYLOCAS_TOXOBOLOS_8382:
            case 10858:
            case 10859:
            case 10860:
                verzikAggros.add(npc);
                break;
            case NpcID.NYLOCAS_MATOMENOS_8385:
            case 10845:
            case 10862:
                verzikReds.putIfAbsent(npc, new MutablePair<>(npc.getHealthRatio(), npc.getHealthScale()));
                break;
            case VerzikConstant.NPC_ID_TORNADO:
            case 10846:
            case 10863:
                if (verzikLocalTornado == null)
                {
                    verzikTornadoes.add(new MemorizedTornado(npc));
                }
                if (!verzikEnraged)
                {
                    verzikEnraged = true;
                    verzikFirstEnraged = true;
                }
                break;
            case NpcID.VERZIK_VITUR_8369:
            case 10830:
            case 10847:
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8370:
            case 10831:
            case 10848:
                verzikPhase = Phase.PHASE1;
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8371:
            case 10832:
            case 10849:
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8372:
            case 10833:
            case 10850:
                verzikPhase = Phase.PHASE2;
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8373:
            case 10834:
            case 10851:
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8374:
            case 10835:
            case 10852:
                verzikRangeProjectiles.clear();
                verzikPhase = Phase.PHASE3;
                verzikSpawn(npc);
                break;
            case NpcID.VERZIK_VITUR_8375:
            case 10836:
            case 10853:
                verzikSpawn(npc);
                break;
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned gameObject)
    {
        if (verzikActive && verzikPhase == Phase.PHASE2)
        {
            verzikPoisonTiles.add(new VerzikPoisonTile(WorldPoint.fromLocal(client, gameObject.getTile().getLocalLocation())));
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned npcDespawned)
    {
        NPC npc = npcDespawned.getNpc();
        switch (npc.getId())
        {
            case NpcID.NYLOCAS_ISCHYROS_8381:
            case NpcID.NYLOCAS_HAGIOS_8383:
            case NpcID.NYLOCAS_TOXOBOLOS_8382:
            case 10858:
            case 10859:
            case 10860:
                verzikAggros.remove(npc);
                break;
            case NpcID.NYLOCAS_MATOMENOS_8385:
            case 10845:
            case 10862:
                verzikReds.remove(npc);
                break;
            case VerzikConstant.NPC_ID_TORNADO:
            case 10846:
            case 10863:
                verzikTornadoes.remove(npc);
                if (verzikLocalTornado == npc)
                {
                    verzikLocalTornado = null;
                }
                break;
            case NpcID.VERZIK_VITUR_8369:
            case NpcID.VERZIK_VITUR_8370:
            case NpcID.VERZIK_VITUR_8371:
            case NpcID.VERZIK_VITUR_8372:
            case NpcID.VERZIK_VITUR_8373:
            case NpcID.VERZIK_VITUR_8374:
            case NpcID.VERZIK_VITUR_8375:
            case 10830:
            case 10831:
            case 10832:
            case 10833:
            case 10834:
            case 10835:
            case 10836:
            case 10847:
            case 10848:
            case 10849:
            case 10850:
            case 10851:
            case 10852:
            case 10853:
                verzikCleanup();
                break;
        }
    }

    @Subscribe
    public void onClientTick(ClientTick event)
    {
       if (config.purpleCrabAttackMES() && verzikNPC != null && ((verzikNPC.getId() == 8372 || verzikNPC.getId() == 10833 || verzikNPC.getId() == 10850))){
           MenuEntry[] oldEntries = client.getMenuEntries();
           MenuEntry[] newEntries = Arrays.stream(oldEntries)
                   .filter(e ->
                   {
                       final NPC npc = e.getNpc();
                       if (npc != null) {
                           if (npc.getName().contains("Nylocas Athanatos")){
                               Player player = client.getLocalPlayer();
                               PlayerComposition playerComp = player != null ? player.getPlayerComposition() : null;
                               if (playerComp == null || WEAPON_SET.contains(playerComp.getEquipmentId(KitType.WEAPON)) || HELMET_SET.contains(playerComp.getEquipmentId(KitType.HEAD))) {
                                   return true;
                               }
                               return false;
                           }
                       }

                       return true;
                   })
                   .toArray(MenuEntry[]::new);
           if (oldEntries.length != newEntries.length)
           {
               client.setMenuEntries(newEntries);
           }
       }
    }

    @Subscribe
    public void onProjectileMoved(ProjectileMoved event)
    {
        if (event.getProjectile().getId() == VerzikConstant.VERZIK_RANGE_BALL)
        {
            verzikRangeProjectiles.put(event.getProjectile(), WorldPoint.fromLocal(client, event.getPosition()));
        }
    }

    private void handleVerzikAttacks(Projectile p)
    {
        int id = p.getId();
        switch (id)
        {
            case VerzikConstant.VERZIK_RANGE_BALL:
                if (!verzikRangedAttacks.contains(p))
                {
                    verzikRangedAttacks.add(p);
                    --verzikLightningAttacks;
                }
                break;
            case VerzikConstant.VERZIK_LIGHTNING_BALL:
                if (!verzikRangedAttacks.contains(p))
                {
                    verzikRangedAttacks.add(p);
                    verzikLightningAttacks = 4;
                }
                break;
        }

    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (verzikActive)
        {
            lastTick = System.currentTimeMillis();
            TheatrePrayerUtil.updateNextPrayerQueue(getUpcomingAttackQueue());

            if (verzikPhase == Phase.PHASE2)
            {
                if (verzikNPC.getId() == NpcID.VERZIK_VITUR_8372 || verzikNPC.getId() == 10833 || verzikNPC.getId() == 10850)
                {
                    for (Projectile projectile : client.getProjectiles())
                    {
                        if (projectile.getRemainingCycles() > 0 && (projectile.getId() == VerzikConstant.VERZIK_RANGE_BALL || projectile.getId() == VerzikConstant.VERZIK_LIGHTNING_BALL))
                        {
                            handleVerzikAttacks(projectile);
                            break;
                        }
                    }

                    verzikRangedAttacks.removeIf((Projectile p) -> p.getRemainingCycles() <= 0);
                }

                if (!verzikRangeProjectiles.isEmpty())
                {
                    Iterator<Projectile> iterator = verzikRangeProjectiles.keySet().iterator();

                    while (iterator.hasNext())
                    {
                        Projectile projectile = (Projectile) iterator.next();
                        if (projectile.getRemainingCycles() < 1)
                        {
                            iterator.remove();
                        }
                    }
                }

                if (isHM)
                {
                    VerzikPoisonTile.updateTiles(verzikPoisonTiles);
                }
            }

            if (verzikPhase == Phase.PHASE3 && !verzikTornadoes.isEmpty())
            {
                if (lastPlayerLocation1 != null)
                {
                    for (MemorizedTornado tornado : verzikTornadoes)
                    {
                        WorldPoint tornadoLocation = tornado.getNpc().getWorldLocation();

                        if (tornado.getCurrentPosition() == null)
                        {
                            tornado.setCurrentPosition(tornadoLocation);
                        }
                        else
                        {
                            tornado.setLastPosition(tornado.getCurrentPosition());
                            tornado.setCurrentPosition(tornadoLocation);
                        }
                    }
                }

                if (lastPlayerLocation1 == null)
                {
                    lastPlayerLocation1 = client.getLocalPlayer().getWorldLocation();
                    lastPlayerLocation0 = lastPlayerLocation1;
                }
                else
                {
                    lastPlayerLocation1 = lastPlayerLocation0;
                    lastPlayerLocation0 = client.getLocalPlayer().getWorldLocation();

                    verzikTornadoes.removeIf(entry -> entry.getRelativeDelta(lastPlayerLocation1) != -1);
                }

                if (verzikTornadoes.size() == 1 && verzikLocalTornado == null)
                {
                    verzikTornadoes.forEach(tornado -> verzikLocalTornado = tornado.getNpc());
                }
            }

            Function<Integer, Integer> adjust_for_enrage = i -> isVerzikEnraged() ? i - 2 : i;

            if (verzikPhase == Phase.PHASE3)
            {
                boolean foundProjectile = false;
                for (Projectile p : client.getProjectiles()) {
                    if (p != null) {
                        if ((p.getInteracting() == client.getLocalPlayer()) && (p.getId() == VerzikConstant.VERZIK_P3_RANGE_PROJECTILE || p.getId() == VerzikConstant.VERZIK_P3_MAGE_PROJECTILE)) {
                            upcomingAttackQueue.add(new TheatreUpcomingAttack(
                                    (p.getRemainingCycles() / 30),
                                    (p.getId() == VerzikConstant.VERZIK_P3_MAGE_PROJECTILE ? Prayer.PROTECT_FROM_MAGIC : Prayer.PROTECT_FROM_MISSILES)
                            ));
                        }
                        if (p.getId() == VerzikConstant.VERZIK_P3_RANGE_PROJECTILE || p.getId() == VerzikConstant.VERZIK_P3_MAGE_PROJECTILE){
                            attackStyle = p.getId() == VerzikConstant.VERZIK_P3_MAGE_PROJECTILE ? AttackStyle.MAGIC : AttackStyle.RANGE;
                            foundProjectile = true;
                        }
                    }
                }

                if (!foundProjectile){
                    attackStyle = AttackStyle.NONE;
                }

                if (verzikYellows == 0)
                {
                    boolean foundYellow = false;

                    for (GraphicsObject object : client.getGraphicsObjects())
                    {
                        if (object.getId() == 1595)
                        {
                            verzikYellows = verzikHardmodeSeenYellows ? 2 : 14;
                            verzikHardmodeSeenYellows = true;
                            foundYellow = true;
                            break;
                        }
                    }

                    if (!foundYellow)
                    {
                        verzikHardmodeSeenYellows = false;
                    }
                }
                else
                {
                    verzikYellows--;
                }
            }

            if (verzikTickPaused)
            {
                switch (verzikNPC.getId())
                {
                    case NpcID.VERZIK_VITUR_8370:
                    case 10831:
                    case 10848:
                        verzikPhase = Phase.PHASE1;
                        verzikAttackCount = 0;
                        verzikTicksUntilAttack = 18;
                        verzikTickPaused = false;
                        break;
                    case NpcID.VERZIK_VITUR_8372:
                    case 10833:
                    case 10850:
                        verzikPhase = Phase.PHASE2;
                        verzikAttackCount = 0;
                        verzikTicksUntilAttack = 3;
                        verzikTickPaused = false;
                        break;
                    case NpcID.VERZIK_VITUR_8374:
                    case 10835:
                    case 10852:
                        verzikPhase = Phase.PHASE3;
                        verzikAttackCount = 0;
                        verzikTicksUntilAttack = 6;
                        verzikTickPaused = false;
                        break;
                }
            }
            else if (verzikSpecial == SpecialAttack.WEBS)
            {
                verzikTotalTicksUntilAttack++;

                if (verzikNPC.getInteracting() != null)
                {
                    verzikSpecial = SpecialAttack.WEB_COOLDOWN;
                    verzikAttackCount = 10;
                    verzikTicksUntilAttack = 10;
                    verzikFirstEnraged = false;
                }
            }
            else
            {
                verzikTicksUntilAttack = Math.max(0, verzikTicksUntilAttack - 1);
                verzikTotalTicksUntilAttack++;

                int animationID = verzikNPC.getAnimation();

                if (animationID > -1 && verzikPhase == Phase.PHASE1 && verzikTicksUntilAttack < 5 && animationID != verzikLastAnimation)
                {
                    if (animationID == VerzikConstant.VERZIK_P1_MAGIC)
                    {
                        verzikTicksUntilAttack = 14;
                        verzikAttackCount++;
                    }
                }

                if (animationID > -1 && verzikPhase == Phase.PHASE2 && verzikTicksUntilAttack < 3 && animationID != verzikLastAnimation)
                {
                    switch (animationID)
                    {
                        case VerzikConstant.VERZIK_P2_REG:
                        case VerzikConstant.VERZIK_P2_BOUNCE:
                            verzikTicksUntilAttack = 4;
                            verzikAttackCount++;
                            if (verzikAttackCount == 7 && verzikRedPhase)
                            {
                                verzikTicksUntilAttack = 8;
                            }
                            break;
                        case VerzikConstant.VERZIK_BEGIN_REDS:
                            verzikRedPhase = true;
                            verzikAttackCount = 0;
                            verzikTicksUntilAttack = 12;
                            break;
                    }
                }

                verzikLastAnimation = animationID;

                if (verzikPhase == Phase.PHASE3)
                {
                    verzikAttackCount = verzikAttackCount % VerzikConstant.P3_GREEN_ATTACK_COUNT;

                    if (verzikTicksUntilAttack <= 0)
                    {
                        verzikAttackCount++;

                        // First 9 Attacks, Including Crabs
                        if (verzikAttackCount < VerzikConstant.P3_WEB_ATTACK_COUNT)
                        {
                            verzikSpecial = SpecialAttack.NONE;
                            verzikTicksUntilAttack = adjust_for_enrage.apply(7);
                        }
                        // Between Webs and Yellows
                        else if (verzikAttackCount < VerzikConstant.P3_YELLOW_ATTACK_COUNT)
                        {
                            verzikSpecial = SpecialAttack.NONE;
                            verzikTicksUntilAttack = adjust_for_enrage.apply(7);
                        }
                        // Yellows - Can't Attack
                        else if (verzikAttackCount < VerzikConstant.P3_YELLOW_ATTACK_COUNT + 1)
                        {
                            verzikSpecial = SpecialAttack.YELLOWS;
                            if (isHM)
                            {
                                verzikTicksUntilAttack = 27;
                            }
                            else
                            {
                                verzikTicksUntilAttack = 21;
                            }
                        }
                        // Between Yellows and Green Ball
                        else if (verzikAttackCount < VerzikConstant.P3_GREEN_ATTACK_COUNT)
                        {
                            verzikSpecial = SpecialAttack.NONE;
                            verzikTicksUntilAttack = adjust_for_enrage.apply(7);
                        }
                        // Ready for Green Ball
                        else if (verzikAttackCount < VerzikConstant.P3_GREEN_ATTACK_COUNT + 1)
                        {
                            verzikSpecial = SpecialAttack.GREEN;
                            // 12 During Purples?
                            verzikTicksUntilAttack = 12;
                        }
                        else
                        {
                            verzikSpecial = SpecialAttack.NONE;
                            verzikTicksUntilAttack = adjust_for_enrage.apply(7);
                        }
                    }

                    if (verzikFirstEnraged)
                    {
                        verzikFirstEnraged = false;
                        if (verzikSpecial != SpecialAttack.YELLOWS || verzikTicksUntilAttack <= 7)
                        {
                            verzikTicksUntilAttack = 5;
                        }
                    }

                    //Update infobox for specials, if config disabled -> remove infobox
                    if (config.specialCounter()){
                        updateInfobox();
                    } else if (infobox){
                        log.debug("Special helper turned off during p3 verzik.");
                        removeInfobox();
                    }
                }
            }
        }
    }



    Color verzikSpecialWarningColor()
    {
        Color col = Color.WHITE;

        if (verzikPhase != Phase.PHASE3)
        {
            return col;
        }

        switch (verzikAttackCount)
        {
            case VerzikConstant.P3_CRAB_ATTACK_COUNT - 1:
                col = Color.MAGENTA;
                break;
            case VerzikConstant.P3_WEB_ATTACK_COUNT - 1:
                col = Color.ORANGE;
                break;
            case VerzikConstant.P3_YELLOW_ATTACK_COUNT - 1:
                col = Color.YELLOW;
                break;
            case VerzikConstant.P3_GREEN_ATTACK_COUNT - 1:
                col = Color.GREEN;
                break;
        }

        return col;
    }

    private void verzikSpawn(NPC npc)
    {
        isHM = VERZIK_HM_ID.contains(npc.getId());
        verzikActive = true;
        verzikNPC = npc;
        verzikSpecial = SpecialAttack.NONE;
        verzikFirstEnraged = false;
        verzikEnraged = false;
        verzikRedPhase = false;
        verzikTickPaused = true;
        verzikAttackCount = 0;
        verzikTicksUntilAttack = 0;
        verzikTotalTicksUntilAttack = 0;
        verzikLastAnimation = -1;
        verzikYellows = 0;
        verzikHardmodeSeenYellows = false;
        verzikLightningAttacks = 4;
    }

    private void verzikCleanup()
    {
        isHM = false;
        verzikNPC = null;
        verzikPhase = null;
        verzikSpecial = SpecialAttack.NONE;
        verzikAggros.clear();
        verzikReds.clear();
        verzikTornadoes.clear();
        verzikPoisonTiles.clear();
        verzikLocalTornado = null;
        verzikEnraged = false;
        verzikFirstEnraged = false;
        verzikRedPhase = false;
        verzikActive = false;
        verzikTickPaused = true;
        verzikTotalTicksUntilAttack = 0;
        verzikLastAnimation = -1;
        verzikYellows = 0;
        verzikHardmodeSeenYellows = false;
        verzikRangedAttacks.clear();
        verzikLightningAttacks = 4;
        removeInfobox();
        attackStyle = AttackStyle.NONE;
        upcomingAttackQueue.clear();
    }


    private void updateInfobox() {
        if (!infobox || specialCounter == null){
            specialCounter = new Counter(CRAB_IMAGE, plugin, 0);
            infoBoxManager.addInfoBox(specialCounter);
            infobox = true;
        }

        if (infobox && specialCounter != null) {
            switch ((int)Math.floor((verzikAttackCount / 5))) {
                case 0:
                    specialCounter.setImage(CRAB_IMAGE);
                    break;
                case 1:
                    specialCounter.setImage(WEBS_IMAGE);
                    break;
                case 2:
                    specialCounter.setImage(YELLOW_IMAGE);
                    break;
                case 3:
                    specialCounter.setImage(GREEN_IMAGE);
                    break;
            }
            infoBoxManager.updateInfoBoxImage(specialCounter);
            specialCounter.setCount(5-(verzikAttackCount % 5));
        }
    }

    private void removeInfobox(){
        //Unload infobox variables
        if (specialCounter != null) {
            infoBoxManager.removeInfoBox(specialCounter);
        }

        infobox = false;
        specialCounter = null;
    }
}
