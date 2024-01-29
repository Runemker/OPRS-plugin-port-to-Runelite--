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
import net.runelite.api.GraphicsObject;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    private NPC baba;
    public int babaTicksUntilAttack = -1;
    public static final int BOULDER_ID = 11782;
    public static final int BOULDER_BROKEN_ID = 11783;
    public static final int BANANA_ID = 45755;
    public static final int FALLING_BOULDER_ID = 2250;
    public static final int SACROPHAGUS_FLAME_ID = 2246;

    @Getter
    private LinkedHashSet<BabaDangerTile> BabaDangerTiles = new LinkedHashSet<>();

    private int babaLastAnimation;

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
    public void onGraphicsObjectCreated(GraphicsObjectCreated event){
        GraphicsObject object = event.getGraphicsObject();
        if (!inRoomRegion(ToaPlugin.BABA_REGION)) {
            return;
        }
        switch (object.getId()){
            case 2250:
                //log.info("here");
                BabaDangerTiles.add(new BabaDangerTile(object.getLocation(), 6));
                break;
            case 2251:
                //log.info("here");
                BabaDangerTiles.add(new BabaDangerTile(object.getLocation(), 5));
                break;
        }
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (!inRoomRegion(ToaPlugin.BABA_REGION)) {
            babaActive = false;
            this.baba = null;
            return;
        }
        LinkedHashSet<BabaDangerTile> tempTiles = new LinkedHashSet<>();
        for (BabaDangerTile tile : BabaDangerTiles){
            if ((tile.getTicksLeft()-1)>=0){
                tempTiles.add(new BabaDangerTile(tile.getLpoint(), tile.getTicksLeft()-1));
            }
        }
        BabaDangerTiles = tempTiles;

        if (client.getNpcs() != null)
        {
            for (NPC babaD : client.getNpcs())
            {
                if (babaD.getId() == 11778)
                {
                    this.baba = babaD;
                    break;
                }
                else{
                    this.baba = null;
                }

            }
        }

        if (this.baba != null){
            babaActive = true;
            babaTicksUntilAttack = Math.max(0, babaTicksUntilAttack - 1);
            if (baba.getAnimation() > -1 && babaTicksUntilAttack < 5 && baba.getAnimation() != babaLastAnimation){
                babaTicksUntilAttack = 6;
            }


            //log.info("tick Happened");
        }}

    public NPC getBabaNPC(){
        return this.baba;
    }

}

