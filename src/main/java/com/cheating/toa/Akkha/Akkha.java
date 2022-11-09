package com.cheating.toa.Akkha;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.AttackStyle;
import com.cheating.toa.Room;
import com.cheating.toa.ToaPlugin;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.Hooks;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.LinkedHashSet;

public class Akkha extends Room {

    public static final int ORB_ID = 11804;

    @Inject
    private Client client;

    @Inject
    private Hooks hooks;

    private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

    @Inject
    private AkkhaOverlay akkhaOverlay;

    @Getter
    private boolean akkhaActive;

    @Getter
    private int memorizeElementsLength;

    @Getter
    private LinkedHashSet<MemorizingTile> memorizingSequence = new LinkedHashSet<>();

    @Inject
    protected Akkha(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    @Override
    public void init(){
        memorizeElementsLength = 4;
        memorizingSequence = new LinkedHashSet<>();
    }

    @Override
    public void load()
    {
        overlayManager.add(akkhaOverlay);
        hooks.registerRenderableDrawListener(drawListener);
    }

    @Override
    public void unload()
    {
        overlayManager.remove(akkhaOverlay);
        hooks.unregisterRenderableDrawListener(drawListener);
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (!inRoomRegion(ToaPlugin.AKKHA_REGION)){
            akkhaActive = false;
            return;
        }
        akkhaActive = true;

        LinkedHashSet<MemorizingTile> tempTiles = new LinkedHashSet<>();
        for (MemorizingTile tile : memorizingSequence){
            if ((tile.getTicksLeft()-1)>0){
                tempTiles.add(new MemorizingTile(tile.getPoint(), tile.getTicksLeft()-1));
            }
        }
        memorizingSequence = tempTiles;
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event) {
        if (event.getVarbitId() == 14378){
            if (event.getValue() >= 4){
                memorizeElementsLength = 6;
            } else if (event.getValue() >= 2){
                memorizeElementsLength = 5;
            } else {
                memorizeElementsLength = 4;
            }
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event){
        GameObject object = event.getGameObject();
        switch (object.getId()){
            case 45869:
            case 45871:
            case 45870:
            case 45868:
                memorizingSequence.add(new MemorizingTile(object.getWorldLocation(), 2*memorizeElementsLength-1));
                break;
        }
    }

    @VisibleForTesting
    boolean shouldDraw(Renderable renderable, boolean drawingUI)
    {
        if (renderable instanceof NPC)
        {
            NPC npc = (NPC) renderable;
            if (npc.getId() == ORB_ID && config.hideOrbs()){
                return false;
            }
        }
        return true;
    }

}
