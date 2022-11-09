package com.cheating.playerattack;

import com.cheating.Cheat;
import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.Util.WeaponMap;
import com.cheating.Util.WeaponStyle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import org.apache.commons.lang3.ObjectUtils;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerAttackPlugin extends Cheat {
	private final Map<Integer, Integer> customAnimationTickMap = new HashMap<>();

	@Getter
	private int ticksUntilNextAnimation;

	@Inject
	private PlayerOverlay playerOverlay;

	@Inject
	private Client client;

	@Inject
	private CheatingConfig playerAttackConfig;

	@Inject
	protected PlayerAttackPlugin(CheatingPlugin plugin, CheatingConfig config) {
		super(plugin, config);
	}

	private boolean skipTickCheck = false;
	private WeaponStyle weaponStyle;

	@Override
	public void startUp()
	{
		overlayManager.add(playerOverlay);
		parseCustomAnimationConfig(playerAttackConfig.customAnimationsPAT());
	}

	@Override
	public void shutDown()
	{
		overlayManager.remove(playerOverlay);
		customAnimationTickMap.clear();
	}

	@Override
	public void onConfigChanged(final ConfigChanged event)
	{
		switch (event.getKey())
		{
			case "customAnimationsPAT":
				parseCustomAnimationConfig(playerAttackConfig.customAnimationsPAT());
				break;
			default:
				break;
		}
	}

	@Subscribe
	public void onGameTick(final GameTick event)
	{
		if (ticksUntilNextAnimation > 0)
		{
			--ticksUntilNextAnimation;
		}

		final Player player = client.getLocalPlayer();

		if (ticksUntilNextAnimation > 0 || player == null)
		{
			return;
		}

		final int animationId = player.getAnimation();
		Integer entry = customAnimationTickMap.get(animationId);

		if (entry != null)
		{
			ticksUntilNextAnimation = entry;
		}

		if (skipTickCheck)
		{
			skipTickCheck = false;
		}
		else
		{
			if (client.getLocalPlayer() == null || client.getLocalPlayer().getPlayerComposition() == null)
			{
				return;
			}

			int equippedWeapon = ObjectUtils.defaultIfNull(client.getLocalPlayer().getPlayerComposition().getEquipmentId(KitType.WEAPON), -1);
			weaponStyle = WeaponMap.StyleMap.get(equippedWeapon);
		}
	}

	private void parseCustomAnimationConfig(final String config)
	{
		ConfigParser.parse(config).ifPresent(map -> {
			customAnimationTickMap.clear();
			customAnimationTickMap.putAll(map);
		});
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.isItemOp() && event.getItemOp() == 2)
		{
			WeaponStyle newStyle = WeaponMap.StyleMap.get(event.getItemId());
			if (newStyle != null)
			{
				skipTickCheck = true;
				weaponStyle = newStyle;
			}
		}
	}
}
