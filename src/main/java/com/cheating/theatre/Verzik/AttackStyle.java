package com.cheating.theatre.Verzik;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public enum AttackStyle {
    MAGIC(Color.CYAN, net.runelite.api.Prayer.PROTECT_FROM_MAGIC),
    RANGE(Color.GREEN, net.runelite.api.Prayer.PROTECT_FROM_MISSILES),
    MELEE(Color.RED, net.runelite.api.Prayer.PROTECT_FROM_MELEE),
    NONE(Color.WHITE, net.runelite.api.Prayer.PROTECT_ITEM);

    private final Color color;
    private final net.runelite.api.Prayer prayer;
}
