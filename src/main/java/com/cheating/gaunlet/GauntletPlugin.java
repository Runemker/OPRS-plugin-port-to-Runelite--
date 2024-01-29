package com.cheating.gaunlet;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.gaunlet.Overlay.OverlayHunllef;
import com.cheating.gaunlet.Overlay.OverlayPrayerBox;
import com.cheating.gaunlet.Overlay.OverlayPrayerWidget;
import com.cheating.gaunlet.entity.Hunllef;
import com.cheating.gaunlet.entity.Tornado;
import com.cheating.Util.NPCCompositionHeadIcon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.overlay.Overlay;

import javax.inject.Inject;


public class GauntletPlugin extends Cheat
{
    @Inject
    protected GauntletPlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    public static final int ONEHAND_SLASH_AXE_ANIMATION = 395;
    public static final int ONEHAND_CRUSH_PICKAXE_ANIMATION = 400;
    public static final int ONEHAND_CRUSH_AXE_ANIMATION = 401;
    public static final int UNARMED_PUNCH_ANIMATION = 422;
    public static final int UNARMED_KICK_ANIMATION = 423;
    public static final int BOW_ATTACK_ANIMATION = 426;
    public static final int ONEHAND_STAB_HALBERD_ANIMATION = 428;
    public static final int ONEHAND_SLASH_HALBERD_ANIMATION = 440;
    public static final int ONEHAND_SLASH_SWORD_ANIMATION = 390;
    public static final int ONEHAND_STAB_SWORD_ANIMATION = 386;
    public static final int HIGH_LEVEL_MAGIC_ATTACK = 1167;
    public static final int HUNLEFF_TORNADO = 8418;

    private static final Set<Integer> MELEE_ANIM_IDS = new HashSet<>(Arrays.asList(ONEHAND_STAB_SWORD_ANIMATION, ONEHAND_SLASH_SWORD_ANIMATION,
            ONEHAND_SLASH_AXE_ANIMATION, ONEHAND_CRUSH_PICKAXE_ANIMATION,
            ONEHAND_CRUSH_AXE_ANIMATION, UNARMED_PUNCH_ANIMATION,
            UNARMED_KICK_ANIMATION, ONEHAND_STAB_HALBERD_ANIMATION,
            ONEHAND_SLASH_HALBERD_ANIMATION));

    private static final Set<Integer> ATTACK_ANIM_IDS = new HashSet<>();

    static
    {
        ATTACK_ANIM_IDS.addAll(MELEE_ANIM_IDS);
        ATTACK_ANIM_IDS.add(BOW_ATTACK_ANIMATION);
        ATTACK_ANIM_IDS.add(HIGH_LEVEL_MAGIC_ATTACK);
    }

    private static final Set<Integer> HUNLLEF_IDS = new HashSet<>(Arrays.asList(
            NpcID.CRYSTALLINE_HUNLLEF, NpcID.CRYSTALLINE_HUNLLEF_9022,
            NpcID.CRYSTALLINE_HUNLLEF_9023, NpcID.CRYSTALLINE_HUNLLEF_9024,
            NpcID.CORRUPTED_HUNLLEF, NpcID.CORRUPTED_HUNLLEF_9036,
            NpcID.CORRUPTED_HUNLLEF_9037, NpcID.CORRUPTED_HUNLLEF_9038
    ));

    private static final Set<Integer> TORNADO_IDS = new HashSet<>(Arrays.asList(NullNpcID.NULL_9025, NullNpcID.NULL_9039));

    private static final Set<Integer> DEMIBOSS_IDS = new HashSet<>(Arrays.asList(
            NpcID.CRYSTALLINE_BEAR, NpcID.CORRUPTED_BEAR,
            NpcID.CRYSTALLINE_DARK_BEAST, NpcID.CORRUPTED_DARK_BEAST,
            NpcID.CRYSTALLINE_DRAGON, NpcID.CORRUPTED_DRAGON
    ));

    private static final Set<Integer> STRONG_NPC_IDS = new HashSet<>(Arrays.asList(
            NpcID.CRYSTALLINE_SCORPION, NpcID.CORRUPTED_SCORPION,
            NpcID.CRYSTALLINE_UNICORN, NpcID.CORRUPTED_UNICORN,
            NpcID.CRYSTALLINE_WOLF, NpcID.CORRUPTED_WOLF
    ));

    private static final Set<Integer> WEAK_NPC_IDS = new HashSet<>(Arrays.asList(
            NpcID.CRYSTALLINE_BAT, NpcID.CORRUPTED_BAT,
            NpcID.CRYSTALLINE_RAT, NpcID.CORRUPTED_RAT,
            NpcID.CRYSTALLINE_SPIDER, NpcID.CORRUPTED_SPIDER
    ));

    private static final Set<Integer> RESOURCE_IDS = new HashSet<>(Arrays.asList(
            ObjectID.CRYSTAL_DEPOSIT, ObjectID.CORRUPT_DEPOSIT,
            ObjectID.PHREN_ROOTS, ObjectID.CORRUPT_PHREN_ROOTS,
            ObjectID.FISHING_SPOT_36068, ObjectID.CORRUPT_FISHING_SPOT,
            ObjectID.GRYM_ROOT, ObjectID.CORRUPT_GRYM_ROOT,
            ObjectID.LINUM_TIRINUM, ObjectID.CORRUPT_LINUM_TIRINUM
    ));

    private static final Set<Integer> UTILITY_IDS = new HashSet<>(Arrays.asList(
            ObjectID.SINGING_BOWL_35966, ObjectID.SINGING_BOWL_36063,
            ObjectID.RANGE_35980, ObjectID.RANGE_36077,
            ObjectID.WATER_PUMP_35981, ObjectID.WATER_PUMP_36078
    ));


    @Getter
    private final Set<Tornado> tornadoes = new HashSet<>();

    @Getter
    private Hunllef hunllef;

    @Getter
    @Setter
    private boolean wrongAttackStyle;

    @Getter
    @Setter
    private boolean switchWeapon;

    private boolean inGauntlet;

    @Getter
    private boolean inHunllef;

    @Inject
    private OverlayHunllef overlayHunllef;

    @Inject
    private OverlayPrayerWidget overlayPrayerWidget;

    @Inject
    private OverlayPrayerBox overlayPrayerBox;

    private Set<Overlay> overlays;

    @Override
    public void startUp()
    {
        if (overlays == null) {
            overlays = new HashSet<>(Arrays.asList(overlayHunllef, overlayPrayerWidget, overlayPrayerBox));
        }


        if (client.getGameState() == GameState.LOGGED_IN)
        {
            clientThread.invoke(this::pluginEnabled);
        }
    }

    @Override
    public void shutDown()
    {
        inGauntlet = false;
        inHunllef = false;

        hunllef = null;
        wrongAttackStyle = false;
        switchWeapon = false;

        //Gauntlet
        overlays.forEach(o -> overlayManager.remove(o));
    }

    @Override
    public void onConfigChanged(ConfigChanged event) {

    }

    @Override
    public void onVarbitChanged(final VarbitChanged event)
    {
        if (isHunllefVarbitSet(client))
        {
            if (!inHunllef)
            {
                initHunllef();
            }
        }
        else if (isGauntletVarbitSet(client))
        {
            if (!inGauntlet)
            {
                initGauntlet();
            }
        }
        else
        {
            if (inGauntlet || inHunllef)
            {
                shutDown();
            }
        }
    }

    @Override
    public void onGameTick(final GameTick event)
    {
        if (hunllef == null)
        {
            return;
        }

        hunllef.decrementTicksUntilNextAttack();

        for(NPC npc : client.getNpcs()){
            if(HUNLLEF_IDS.contains(npc.getId())){
                hunleffAttack(npc);
            }
        }

        if (!tornadoes.isEmpty())
        {
            tornadoes.forEach(Tornado::updateTimeLeft);
        }
    }

    @Override
    public void onGameStateChanged(final GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOADING:
                break;
            case LOGIN_SCREEN:
            case HOPPING:
                shutDown();
                break;
        }
    }


    @Override
    public void onNpcSpawned(final NpcSpawned event)
    {
        final NPC npc = event.getNpc();

        final int id = npc.getId();

        if (HUNLLEF_IDS.contains(id))
        {
            hunllef = new Hunllef(npc);
        }
        else if (TORNADO_IDS.contains(id))
        {
            tornadoes.add(new Tornado(npc));
        }
    }

    @Override
    public void onNpcDespawned(final NpcDespawned event)
    {
        final NPC npc = event.getNpc();

        final int id = npc.getId();

        if (HUNLLEF_IDS.contains(id))
        {
            hunllef = null;
        }
        else if (TORNADO_IDS.contains(id))
        {
            tornadoes.removeIf(t -> t.getNpc() == npc);
        }
    }


    @Override
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (!isHunllefVarbitSet(client) || hunllef == null)
        {
            return;
        }

        final Actor actor = event.getActor();

        final int animationId = actor.getAnimation();

        if (actor instanceof Player)
        {
            if (!ATTACK_ANIM_IDS.contains(animationId))
            {
                return;
            }

            final boolean validAttack = isAttackAnimationValid(animationId);

            if (validAttack)
            {
                wrongAttackStyle = false;
                hunllef.updatePlayerAttackCount();

                if (hunllef.getPlayerAttackCount() == 1)
                {
                    switchWeapon = true;
                }
            }
            else
            {
                wrongAttackStyle = true;
            }
        }
        else if (actor instanceof NPC)
        {
            if (animationId == HUNLEFF_TORNADO)
            {
                hunllef.updateAttackCount();
            }
        }
    }

    private boolean isAttackAnimationValid(final int animationId)
    {
        HeadIcon headIcon = null;

        NPCComposition hunllefComposition = hunllef.getNpc().getComposition();
        NPCCompositionHeadIcon npcCompositionHeadIcon = new NPCCompositionHeadIcon(client, hunllefComposition);
        headIcon = npcCompositionHeadIcon.getNPCHeadIcon();
        System.out.println("The current headIcon determined by reflection is: " + headIcon.name());

        if (headIcon == null)
        {
            return true;
        }

        switch (headIcon)
        {
            case MELEE:
                if (MELEE_ANIM_IDS.contains(animationId))
                {
                    return false;
                }
                break;
            case RANGED:
                if (animationId == BOW_ATTACK_ANIMATION)
                {
                    return false;
                }
                break;
            case MAGIC:
                if (animationId == HIGH_LEVEL_MAGIC_ATTACK)
                {
                    return false;
                }
                break;
        }

        return true;
    }

    private void pluginEnabled()
    {
        if (isGauntletVarbitSet(client))
        {
            initGauntlet();
        }

        if (isHunllefVarbitSet(client))
        {
            initHunllef();
        }
    }

    private void initGauntlet()
    {
        inGauntlet = true;
    }

    private void initHunllef()
    {
        inHunllef = true;

        overlayManager.add(overlayHunllef);
        overlayManager.add(overlayPrayerWidget);
        overlayManager.add(overlayPrayerBox);
    }

    private boolean isGauntletVarbitSet(Client client)
    {
        return client.getVarbitValue(9178) == 1;
    }

    private boolean isHunllefVarbitSet(Client client)
    {
        return client.getVarbitValue(9177) == 1;
    }

    private void hunleffAttack(NPC npc){
        int animation = npc.getAnimation();

        switch(animation) {
            case 8419:
                hunllef.updateAttackCount();
                break;
        }
    }
}