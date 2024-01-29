package com.cheating.toa;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.coxhelper.CoxDebugBox;
import com.cheating.toa.Akkha.Akkha;
import com.cheating.toa.Apmeken.Apmeken;
import com.cheating.toa.Baba.Baba;
import com.cheating.toa.Het.Het;
import com.cheating.toa.Kephri.Kephri;
import com.cheating.toa.Scarabas.Scarabas;
import com.cheating.toa.Zebak.Zebak;
import com.cheating.toa.Warden.Warden;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.*;
import org.apache.commons.lang3.ArrayUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ToaPlugin extends Cheat {

    @Inject
    protected ToaPlugin(CheatingPlugin plugin, CheatingConfig config)
    {
        super(plugin, config);
    }

    public static final Integer AKKHA_REGION = 14676;
    public static final Integer CRONDIS_REGION = 15698;
    public static final Integer ZEBAK_REGION = 15700;
    public static final Integer SCABARAS_REGION = 14162;
    public static final Integer NEXUS_REGION = 14160;
    public static final Integer KEPHRI_REGION = 14164;
    public static final Integer APMEKEN_REGION = 15186;
    public static final Integer BABA_REGION = 15188;
    public static final Integer HET_REGION = 14674;
    public static final Integer WARDEN_P1_REGION = 15184;
    public static final Integer WARDEN_P2_REGION = 15696;

    private int[] regionIds = null;

    @Inject
    private Akkha akkha;

    @Inject
    private Het het;

    @Inject
    private Zebak zebak;

    @Inject
    private Kephri kephri;

    @Inject
    private Scarabas scarabas;

    @Inject
    private Baba baba;

    @Inject
    private Apmeken apmeken;

    @Inject
    private Warden warden;

    @Inject
    @Getter(AccessLevel.NONE)
    private ToaDebugBox toaDebugBox;

    @Inject
    private reflectPackMeth packetSM;

    private reflectMeth packet;
    @Inject
    private Client client;

    private Room[] rooms = null;


    @Override
    public void startUp() {
        this.overlayManager.add(this.toaDebugBox);

        if (rooms == null)
        {
            rooms = new Room[]{akkha, het, zebak, kephri, scarabas, baba, apmeken, warden};


            for (Room room : rooms)
            {
                log.info("Checking room");
                room.init();
                room.setpackMeth(packet);
            }
        }

        for (Room room : rooms)
        {
            room.load();
        }

        if (regionIds == null){
            regionIds = new int[]{AKKHA_REGION,CRONDIS_REGION, ZEBAK_REGION, SCABARAS_REGION, NEXUS_REGION, KEPHRI_REGION, APMEKEN_REGION, BABA_REGION, HET_REGION,  WARDEN_P1_REGION, WARDEN_P2_REGION};
        }
    }

    @Override
    public void shutDown() {
        this.overlayManager.remove(this.toaDebugBox);

        for (Room room : rooms)
        {
            room.unload();
        }
        //List<Prayer> test = new ArrayList<>();
        //test.add(0, Prayer.PROTECT_FROM_MELEE);
        //packet.toggleNormalPrayer(Prayer.PROTECT_FROM_MISSILES.getWidgetInfo().getPackedId());
        //packet.toggleNormalPrayers(test);
        //packet.toggleNormalPrayer(Prayer.PROTECT_FROM_MISSILES.getWidgetInfo().getPackedId());
        packet = null;
    }

    public void itemSwitch(String itemListStr){
        List<Integer> item = new ArrayList<Integer>();
        if(itemListStr != ""){
            String[] items = itemListStr.split(",");
            for(String itemStr:items){
                item.add(Integer.parseInt(itemStr));
            }
        }
        packet.equipItems(item);
    }

    @Override
    public void onGameTick(GameTick event) {
        akkha.onGameTick(event);
        zebak.onGameTick(event);
        kephri.onGameTick(event);
        scarabas.onGameTick(event);
        baba.onGameTick(event);
        warden.onGameTick(event);
    }

    @Override
    public void onVarbitChanged(final VarbitChanged event){
        akkha.onVarbitChanged(event);
        zebak.onVarbitChanged(event);
    }


    @Override
    public void onGameObjectSpawned(GameObjectSpawned gameObject)
    {
        akkha.onGameObjectSpawned(gameObject);
    }

    @Override
    public void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObjectC) {
        zebak.onGraphicsObjectCreated(graphicsObjectC);
        warden.onGraphicsObjectCreated(graphicsObjectC);
        kephri.onGraphicsObjectCreated(graphicsObjectC);
        baba.onGraphicsObjectCreated(graphicsObjectC);

    }

    @Override
    public void onChatMessage(final ChatMessage event) {
        apmeken.onChatMessage(event);
    }

    public boolean inRoomRegion()
    {
        for (int regionId : regionIds) {
            if (ArrayUtils.contains(client.getMapRegions(), regionId))
                return true;
        }
        return false;
    }


}