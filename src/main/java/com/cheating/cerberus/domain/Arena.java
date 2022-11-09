package com.cheating.cerberus.domain;

import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.coords.WorldPoint;

@Getter
@RequiredArgsConstructor
public enum Arena
{
    WEST(1231, 1249, 1243, 1257),
    NORTH(1295, 1313, 1307, 1321),
    EAST(1359, 1377, 1243, 1257);

    private final int x1, x2, y1, y2;

    @Nullable
    public static Arena getArena(final WorldPoint worldPoint)
    {
        for (final Arena arena : Arena.values())
        {
            if (worldPoint.getX() >= arena.getX1() && worldPoint.getX() <= arena.getX2() &&
                    worldPoint.getY() >= arena.getY1() && worldPoint.getY() <= arena.getY2())
            {
                return arena;
            }
        }

        return null;
    }

    public WorldPoint getGhostTile(final int ghostIndex)
    {
        if (ghostIndex > 2 || ghostIndex < 0)
        {
            return null;
        }

        return new WorldPoint(x1 + 8 + ghostIndex, y1 + 13, 0);
    }
}
