package com.cheating.toa.Baba;

import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;

import javax.inject.Inject;

public class BabaDangerTile {

    @Getter
    private int ticksLeft;

    @Inject
	private Client client;

    @Getter
	private LocalPoint lpoint;

    public BabaDangerTile(LocalPoint point, int ticksLeft){
        this.ticksLeft = ticksLeft;
        this.lpoint = point;
    }
}
