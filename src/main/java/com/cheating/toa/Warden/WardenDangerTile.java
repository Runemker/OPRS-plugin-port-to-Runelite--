package com.cheating.toa.Warden;

import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;

import javax.inject.Inject;

public class WardenDangerTile {

	@Getter
	private int ticksLeft;

	@Getter
	private int id;

	@Inject
	private Client client;

	@Getter
	private LocalPoint lpoint;

	public WardenDangerTile(LocalPoint point, int ticksLeftm, int ID){
		this.ticksLeft = ticksLeftm;
		this.lpoint = point;
		this.id = ID;
	}
}
