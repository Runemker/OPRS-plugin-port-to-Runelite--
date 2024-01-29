package com.cheating.toa.Kephri;

import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;

import javax.inject.Inject;

public class KephriDangerTile {

    @Getter
    private int ticksLeft;

	@Getter
	private int id;


	@Inject
	private Client client;

    @Getter
	private LocalPoint lpoint;

    public KephriDangerTile(LocalPoint point, int ticksLeft){
        this.ticksLeft = ticksLeft;
        this.lpoint = point;
    }
}
