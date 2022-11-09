package com.cheating;

import com.cheating.cerberus.CerberusPlugin;
import com.cheating.coxhelper.CoxPlugin;
import com.cheating.gaunlet.GauntletPlugin;
import com.cheating.godwars.GodWarsPlugin;
import com.cheating.inferno.InfernoPlugin;
import com.cheating.playerattack.PlayerAttackPlugin;
import com.cheating.hydra.HydraPlugin;
import com.cheating.theatre.TheatrePlugin;
import com.cheating.toa.ToaPlugin;
import com.cheating.zulrah.ZulrahPlugin;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Cheating",
        description = "Ayyyyy"
)
public class CheatingPlugin extends Plugin {
    @Inject
    private PlayerAttackPlugin playerAttackPlugin;

    @Inject
    private GauntletPlugin gauntletPlugin;

    @Inject
    private TheatrePlugin theatrePlugin;

    @Inject
    private HydraPlugin hydraPlugin;

    @Inject
    private GodWarsPlugin godWarsPlugin;

    @Inject
    private CoxPlugin coxPlugin;

    @Inject
    private ZulrahPlugin zulrahPlugin;

    @Inject
    private InfernoPlugin infernoPlugin;

    @Inject
    private CerberusPlugin cerberusPlugin;

    @Inject
    private ToaPlugin toaPlugin;

    //Enable plugins
    private Cheat[] plugins = null;

    @Provides
    CheatingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CheatingConfig.class);
    }

    @Override
    protected void startUp() {
        if (plugins == null){
            plugins = new Cheat[]{playerAttackPlugin, gauntletPlugin, theatrePlugin, hydraPlugin, godWarsPlugin, coxPlugin, zulrahPlugin, infernoPlugin, cerberusPlugin, toaPlugin};
        }

        for(Cheat cheat : plugins){
            log.debug("Starting plugin: " + cheat.toString());
            cheat.startUp();
        }
    }

    @Override
    protected void shutDown() {
        for(Cheat cheat : plugins){
            cheat.shutDown();
        }
    }

    @Subscribe
    private void onConfigChanged(final ConfigChanged event) {
        if (!event.getGroup().equals("Cheating")) {
            return;
        }

        for(Cheat cheat : plugins){
            cheat.onConfigChanged(event);
        }
    }

    @Subscribe
    private void onGameTick(final GameTick event) {
        for(Cheat cheat : plugins){
            cheat.onGameTick(event);
        }
    }

    @Subscribe
    private void onVarbitChanged(final VarbitChanged event){
        for(Cheat cheat : plugins){
            cheat.onVarbitChanged(event);
        }
    }

    @Subscribe
    private void onNpcSpawned(final NpcSpawned event){
        for(Cheat cheat : plugins){
            cheat.onNpcSpawned(event);
        }
    }

    @Subscribe
    private void onNpcDespawned(final NpcDespawned event) {
        for(Cheat cheat : plugins){
            cheat.onNpcDespawned(event);
        }
    }

    @Subscribe
    private void onAnimationChanged(final AnimationChanged event){
        for(Cheat cheat : plugins){
            cheat.onAnimationChanged(event);
        }
    }

    @Subscribe
    private void onGameStateChanged(final GameStateChanged event){
        for(Cheat cheat : plugins){
            cheat.onGameStateChanged(event);
        }
    }

    @Subscribe
    public void onGraphicsObjectCreated(GraphicsObjectCreated event)
    {
        for(Cheat cheat : plugins){
            cheat.onGraphicsObjectCreated(event);
        }
    }

    @Subscribe
    public void onClientTick(ClientTick clientTick){
        for(Cheat cheat : plugins){
            cheat.onClientTick(clientTick);
        }
    }

    @Subscribe
    public void onNpcChanged(NpcChanged npcChanged)
    {
        for(Cheat cheat : plugins){
            cheat.onNpcChanged(npcChanged);
        }
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded entry) {
        for(Cheat cheat : plugins){
            cheat.onMenuEntryAdded(entry);
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked option) {
        for(Cheat cheat : plugins){
            cheat.onMenuOptionClicked(option);
        }
    }

    @Subscribe
    public void onMenuOpened(MenuOpened menu) {
        for(Cheat cheat : plugins){
            cheat.onMenuOpened(menu);
        }
    }

    @Subscribe
    public void onGroundObjectSpawned(GroundObjectSpawned event) {
        for(Cheat cheat : plugins){
            cheat.onGroundObjectSpawned(event);
        }
    }

    @Subscribe
    public void onProjectileMoved(ProjectileMoved event){
        for(Cheat cheat : plugins){
            cheat.onProjectileMoved(event);
        }
    }
    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event){
        for(Cheat cheat : plugins){
            cheat.onGameObjectSpawned(event);
        }
    }

    @Subscribe
    public void onGameObjectDespawned(GameObjectDespawned event){
        for(Cheat cheat : plugins){
            cheat.onGameObjectDespawned(event);
        }
    }

    @Subscribe
    public void onChatMessage(final ChatMessage event){
        for(Cheat cheat : plugins){
            cheat.onChatMessage(event);
        }
    }

    @Subscribe
    public void onGraphicChanged(GraphicChanged event) {
        for(Cheat cheat : plugins){
            cheat.onGraphicChanged(event);
        }
    }

    @Subscribe
    public void onFocusChanged(FocusChanged event) {
        for(Cheat cheat : plugins){
            cheat.onFocusChanged(event);
        }
    }
}