package com.cheating;

import net.runelite.api.events.*;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public abstract class Cheat {
    protected final CheatingPlugin plugin;
    protected final CheatingConfig config;

    @Inject
    protected OverlayManager overlayManager;

    @Inject
    public Cheat(CheatingPlugin plugin, CheatingConfig config)
    {
        this.plugin = plugin;
        this.config = config;
    }

    public void startUp(){}
    public void shutDown(){}
    public void onConfigChanged(final ConfigChanged event){}

    public void onGameTick(final GameTick event){}

    public void onVarbitChanged(final VarbitChanged event){}
    public void onNpcSpawned(final NpcSpawned event){}
    public void onNpcDespawned(final NpcDespawned event){}
    public void onAnimationChanged(final AnimationChanged event){}
    public void onGameStateChanged(final GameStateChanged event){}
    public void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObjectC) { }
    public void onClientTick(ClientTick clientTick){}
    public void onNpcChanged(NpcChanged npcChanged){}
    public void onMenuEntryAdded(MenuEntryAdded entry) {}
    public void onMenuOptionClicked(MenuOptionClicked option){}
    public void onMenuOpened(MenuOpened menu) {}
    public void onGroundObjectSpawned(GroundObjectSpawned event){}
    public void onProjectileMoved(ProjectileMoved event){}
    public void onGameObjectSpawned(GameObjectSpawned event){}
    public void onGameObjectDespawned(GameObjectDespawned event){}
    public void onChatMessage(final ChatMessage event) {}
    public void onGraphicChanged(GraphicChanged event) {}
    public void onFocusChanged(FocusChanged event) {}
}
